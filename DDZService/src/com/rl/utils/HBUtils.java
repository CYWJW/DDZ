package com.rl.utils;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HBUtils {
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");  
		ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
	public static void closeSession(Session session) {
		session.close();
	}
	
	public static void closeSessionFactory(SessionFactory sessionFactory) {
		sessionFactory.close();
	}
}
