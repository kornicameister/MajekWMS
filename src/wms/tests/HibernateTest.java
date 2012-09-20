package wms.tests;

import static org.junit.Assert.assertNotNull;
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
import wms.model.Warehouse;

/**
 * This is singleton class that I use to write test for Hibernate part
 * of the data model.
 * 
 * @author kornicameister
 * @version 0.0.1
 */
public class HibernateTest {
	private SessionFactory sessionFactory;
	private File hibernateCfg;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		this.hibernateCfg = new File("WebContent/META-INF/hibernate.cfg.xml");
		if (!this.hibernateCfg.exists()) {
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
	public void testMeasureRead() {
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();
		List<Measure> result = session.createQuery("from Measure").list();
		for (Measure m : result) {
			assertNotNull("Measure object is null, something went wrong...", m);
			System.out.println(m);
		}
		session.getTransaction().commit();

		session.close();
	}

//	@Test
//	public void testMeasureWrite() {
//		Session session = this.sessionFactory.openSession();
//
//		session.beginTransaction();
//		assertNotNull("Save should be ok...", session.save(new Measure("meter", "m")));
//		assertNotNull("Save should be ok...", session.save(new Measure("liter", "l")));
//		assertNotNull("Save should be ok...", session.save(new Measure("kilogram", "kg")));
//		session.getTransaction().commit();
//
//		session.close();
//	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWarehouseRead(){
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();
		List<Warehouse> result = session.createQuery("from Warehouse").list();
		for (Warehouse m : result) {
			assertNotNull("Warehouse object is null, something went wrong...", m);
			System.out.println(m);
		}
		session.getTransaction().commit();

		session.close();
	}
}
