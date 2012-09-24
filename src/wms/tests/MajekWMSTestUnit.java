package wms.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wms.model.AbstractEntity;
import wms.model.Measure;

public class MajekWMSTestUnit {
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

		List<Measure> readMeasures = this.testReadMeasures();
		this.testWriteMeasures(this.getHigherId(readMeasures));
		this.testUpdateMeasures(readMeasures);

		System.out.println("Tests - ends");
	}

	private void testUpdateMeasures(List<Measure> readMeasures) {
		Integer readId = null;
		session.beginTransaction();
		for (Measure measure : readMeasures) {
			readId = measure.getId();
			
			measure.setAbbreviation(Integer.valueOf(readId * measure.hashCode()).toString());
			session.update(measure);
		}
		session.getTransaction().commit();
		System.out.println(String.format("Updated %d entities of measures", readMeasures.size()));
	}

	private void testWriteMeasures(Integer id) {
		session.beginTransaction();
		id++;
		for (Integer i = id; i < id + 4; i++) {
			Serializable m = session.save(new Measure(i.toString(), i.toString()));
			System.out.println(String.format("Saved %s with id = %d",
					Measure.class.getSimpleName(), m));
		}
		session.getTransaction().commit();
	}

	private Integer getHigherId(List<?> data) {
		AbstractEntity ae = (AbstractEntity) data.get(data.size() - 1);
		System.out.println(String.format("Last entity of %s has id = %d", ae
				.getClass().getSimpleName(), ae.getId()));
		return ae.getId();
	}

	@SuppressWarnings("unchecked")
	private List<Measure> testReadMeasures() {
		session.beginTransaction();
		List<Measure> result = session.createQuery("from Measure")
				.list();
		session.getTransaction().commit();

		System.out.println(String.format("Read %d measure entitites",
				result.size()));

		return result;
	}

}
