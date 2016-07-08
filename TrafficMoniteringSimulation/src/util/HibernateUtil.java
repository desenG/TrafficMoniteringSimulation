package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory LeftSessionFactory = buildSessionFactory("Left");
	private static final SessionFactory CenterSessionFactory = buildSessionFactory("Center");
	private static final SessionFactory RightSessionFactory = buildSessionFactory("Right");
	private static SessionFactory buildSessionFactory(String region) {
		try {
			
			String hibernateXmlConfFile="hibernate_"+region+".cfg.xml";
			//System.out.println(hibernateXmlConfFile);
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure(hibernateXmlConfFile).buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getLeftSessionFactory() {
		return LeftSessionFactory;
	}
	public static SessionFactory getCenterSessionFactory() {
		return CenterSessionFactory;
	}
	public static SessionFactory getRightSessionFactory() {
		return RightSessionFactory;
	}
}
