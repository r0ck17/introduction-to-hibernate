package by.javaguru.hibernate.util;

import by.javaguru.hibernate.entity.Ticket;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class ConnectionManager {
    private static SessionFactory sessionFactory;

    private ConnectionManager() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Ticket.class)
                    .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
