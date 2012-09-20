package wms.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wms.model.WarehouseType;

public class RelationshipTest {
	private File hibernateCfg;
	private SessionFactory sessionFactory;

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

	@Test
	public void testWarehouseTypeWrite() {
		Session session = this.sessionFactory.openSession();
		ArrayList<Serializable> ids = new ArrayList<>();

		session.beginTransaction();
		ids.add(session.save("warehouseType", new WarehouseType("Test_1", "t1",
				"This is test_1 warehouse type")));
		ids.add(session.save("warehouseType", new WarehouseType("Test_2", "t2",
				"This is test_2 warehouse type")));
		ids.add(session.save("warehouseType", new WarehouseType("Test_3", "t3",
				"This is test_3 warehouse type")));
		ids.add(session.save("warehouseType", new WarehouseType("Test_4", "t4",
				"This is test_4 warehouse type")));
		session.getTransaction().commit();

		session.close();
		
		System.out.println(ids);
	}

	@Test
	public void testWarehouseTypeRead() {
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();

		session.getTransaction().commit();

		session.close();
	}

	@Test
	public void testWarehouseWrite() {
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();

		session.getTransaction().commit();

		session.close();
	}

	@Test
	public void testWarehouseRead() {
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();

		session.getTransaction().commit();

		session.close();
	}

	@Test
	public void testDelete() {
		Session session = this.sessionFactory.openSession();

		session.beginTransaction();

		session.getTransaction().commit();

		session.close();
	}

}
