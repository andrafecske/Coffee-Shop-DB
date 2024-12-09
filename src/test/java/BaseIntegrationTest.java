import org.example.model.Admin;
import org.example.model.Client;

import org.example.model.Food;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public abstract class BaseIntegrationTest {

    protected static SessionFactory sessionFactory;

    @BeforeAll
    static void setUp() {
        // Load the test configuration
        sessionFactory = new Configuration().configure().buildSessionFactory();

    }

    @AfterAll
    static void tearDown() {
        // Close the session factory after all tests
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
