import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseIntegrationTest {

    protected static SessionFactory sessionFactory;

    @BeforeAll
    static void setUp() {
        // Load the test configuration
        sessionFactory = new Configuration()
                .configure("hibernate-test.cfg.xml")
                .buildSessionFactory();
    }

    @AfterAll
    static void tearDown() {
        // Close the session factory after all tests
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
