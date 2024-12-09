
import org.example.Controller.CoffeeShopController;
import org.example.Repository.DBRepo;
import org.example.Utils.Role;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.example.service.CoffeeShopService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DBRepoAdminTest extends BaseIntegrationTest {
    private DBRepo<Admin> adminRepository;
    private DBRepo<Client> clientRepository;
    private DBRepo<Food> foodRepository;
    private DBRepo<Coffee> coffeeRepository;
    private DBRepo<Order> orderRepository;
    private DBRepo<Offer> offerRepository;
    private DBRepo<OfferOrder> offerOrderDBRepo;


    private CoffeeShopService coffeeShopService;
    private CoffeeShopController coffeeShopController;


    @BeforeEach
    void setUpRepository() {
        adminRepository = new DBRepo<>(sessionFactory, Admin.class);
        clientRepository = new DBRepo<>(sessionFactory, Client.class);
        coffeeRepository = new DBRepo<>(sessionFactory, Coffee.class);
        foodRepository = new DBRepo<>(sessionFactory, Food.class);


        coffeeShopService = new CoffeeShopService(adminRepository, clientRepository,foodRepository,coffeeRepository, orderRepository, offerRepository, offerOrderDBRepo);
        coffeeShopController = new CoffeeShopController(coffeeShopService);
    }
    @Test
    void testAdminOperations() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Admin admin = new Admin(25, "Jane Doe", Role.Manager);
        coffeeShopController.addAdmin(admin);
        Integer adminId = admin.getId();
        assertNotNull(adminId, "Admin ID should not be null after persistence");

        // Retrieve the admin using its ID
        Admin retrievedAdmin = adminRepository.read(adminId);
        assertNotNull(retrievedAdmin, "Admin should not be null");
        assertEquals("Jane Doe", retrievedAdmin.getName());
        assertEquals(Role.Manager, retrievedAdmin.getRole());

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Admin adminToUpdate = adminRepository.read(adminId);
        assertNotNull(adminToUpdate, "Admin should not be null before update");

        adminToUpdate.setName("John Doe");
        adminToUpdate.setRole(Role.ClientManager);
        coffeeShopController.updateAdmin(adminToUpdate);  // Assuming this method exists in your controller

        Admin updatedAdmin = adminRepository.read(adminId);
        assertNotNull(updatedAdmin, "Admin should not be null after update");
        assertEquals("John Doe", updatedAdmin.getName());
        assertEquals(Role.ClientManager, updatedAdmin.getRole());

        transaction.commit();

        // --- Delete Operation ---
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        // Delete the admin
        coffeeShopController.deleteAdmin(admin);  // Assuming this method exists in your controller

        // Verify that the admin is deleted
        Admin deletedAdmin = adminRepository.read(adminId);
        assertNull(deletedAdmin, "Admin should be null after deletion");

        transaction.commit();
        session.close();
    }

    @Test
    void testClientOperations() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Client client = new Client(30, "Alice");
        coffeeShopController.addClient(client);
        Integer clientId = client.getId();
        assertNotNull(clientId, "Client ID should not be null after persistence");

        Client retrievedClient = clientRepository.read(clientId);
        assertNotNull(retrievedClient, "Client should not be null");
        assertEquals("Alice", retrievedClient.getName());
        assertNotNull(retrievedClient.getCard(), "Client should have a card associated with it");

        transaction.commit();

        // --- Update Operation ---
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();


        // Retrieve client to update
        Client clientToUpdate = clientRepository.read(clientId);
        assertNotNull(clientToUpdate, "Client should not be null before update");
        clientToUpdate.setName("Bob Johnson");
        coffeeShopController.updateClient(clientToUpdate);

        Client updatedClient = clientRepository.read(clientId);
        assertNotNull(updatedClient, "Client should not be null after update");
        assertEquals("Bob Johnson", updatedClient.getName());


        coffeeShopController.deleteClient(client);  // Assuming this method exists in your controller

        // Verify that the client is deleted
        Client deletedClient = clientRepository.read(clientId);
        assertNull(deletedClient, "Client should be null after deletion");
        session.close();
    }

}
