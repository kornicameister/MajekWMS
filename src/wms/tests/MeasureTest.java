package wms.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wms.model.Measure;

/**
 * This is singleton class that I use to write test for Hibernate part of the
 * data model.
 * 
 * @author kornicameister
 * @version 0.0.1
 */
public class MeasureTest {
	private SessionFactory sessionFactory;
	private File hibernateCfg;
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
	}

	@After
	public void tearDown() throws Exception {
		session.close();
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@Test
	public void testMeasureRead() {
		for (Measure m : this.readMeasureRecords()) {
			assertNotNull("Measure object is null, something went wrong...", m);
			System.out.println(m);
		}
	}

//	@Test
//	public void testMeasureDelete() {
//		List<Measure> result = readMeasureRecords();
//		doDelete(result);
//
//		System.out.println("Deleted all records without any problem...");
//	}

//	private void doDelete(List<Measure> result) {
//		session.beginTransaction();
//		for (Measure m : result) {
//			session.delete(m);
//		}
//		session.getTransaction().commit();
//	}

	@Test
	public void testMeasureWrite() {
		ArrayList<Serializable> id = new ArrayList<>();
		session.beginTransaction();
		id.add(session.save(new Measure("meter", "m")));
		id.add(session.save(new Measure("liter", "l")));
		id.add(session.save(new Measure("kilogram", "kg")));
		session.getTransaction().commit();

//		session.beginTransaction();
//		for (Serializable s : id) {
//			System.out.println(s);
//			session.delete(session.get(Measure.class, s));
//		}
//		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	private List<Measure> readMeasureRecords() {
		session.beginTransaction();
		List<Measure> result = session.createQuery("from Measure").list();
		session.getTransaction().commit();
		return result;
	}
}
