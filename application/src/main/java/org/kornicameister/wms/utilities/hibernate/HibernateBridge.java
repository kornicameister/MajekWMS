package org.kornicameister.wms.utilities.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.kornicameister.wms.model.hibernate.WMSModelConstants;

public final class HibernateBridge {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static boolean accessHibernate() throws HibernateBridgeException {
        if (sessionFactory != null) {
            throw new HibernateBridgeException("Already connected to hibernate");
        }

        Configuration configuration = new Configuration();
        configuration = configuration.configure(WMSModelConstants.HIBERNATE_CFG
                .toString());

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        HibernateBridge.sessionFactory = configuration
                .buildSessionFactory(serviceRegistry);

        return !HibernateBridge.sessionFactory.isClosed();
    }

    public static boolean closeHibernate() {
        if (HibernateBridge.sessionFactory != null) {
            sessionFactory.close();
            return HibernateBridge.sessionFactory.isClosed();
        }
        return false;
    }

}
