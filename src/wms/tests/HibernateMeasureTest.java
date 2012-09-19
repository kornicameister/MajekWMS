package wms.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wms.model.Measure;

public class HibernateMeasureTest {
	private SessionFactory sessionFactory;
	private File hibernateCfg;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		this.hibernateCfg = new File("WebContent/META-INF/hibernate.cfg.xml");
		if(!this.hibernateCfg.exists()){
			fail("Cfg file for hibernate not found");
		}
		
		this.sessionFactory = new Configuration().configure(this.hibernateCfg)
				.buildSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMeasure() {
		Session session = this.sessionFactory.openSession();
		
		session.beginTransaction();
		List<Measure> result = session.createQuery("from Measure").list();
		for(Measure m : result){
			System.out.println(m);
		}
		session.getTransaction().commit();
		
		session.close();
	}

}
