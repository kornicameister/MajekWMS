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

public class Measure {
	private File hibernateCfg;
	private SessionFactory sessionFactory;
	private Session session;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		this.hibernateCfg = new File("WebContent/META-INF/hibernate.cfg.xml");
		if (!this.hibernateCfg.exists()) {
			fail("Cfg file for hibernate not found");
		}

		this.sessionFactory = new Configuration().configure(this.hibernateCfg)
				.buildSessionFactory();
		session = this.sessionFactory.openSession();
		System.out.println("Connection initialized...");
	}

	@After
	public void tearDown() throws Exception {
		session.close();
		if (sessionFactory != null) {
			sessionFactory.close();
		}
		System.out.println("Connection closed...");
	}

	@Test
	public void test() {
		System.out.println("Tests - starts");

		List<Measure> readMeasures = this.testRead();

		System.out.println("Tests - ends");
	}

	@SuppressWarnings("unchecked")
	private List<Measure> testRead() {
		session.beginTransaction();
		List<Measure> result = session.createQuery("from Measure").list();
		session.getTransaction().commit();
		
		System.out.println(String.format("Read %d measure entitites", result.size()));
		
		return result;
	}

}
