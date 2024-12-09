
import org.example.Controller.CoffeeShopController;
import org.example.Repository.DBRepo;
import org.example.Utils.Role;
import org.example.model.*;
import org.example.Utils.*;
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

    @Test
    void testFoodAndCoffeeOperations() {
        //food and coffee
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // add operation
        Food food = new Food(15, 10, "Pizza", FoodType.MEAL);
        coffeeShopController.addFood(food);  // Assuming this method exists in your controller
        Integer foodId = food.getId();
        assertNotNull(foodId, "Food ID should not be null after persistence");

        Food retrievedFood = foodRepository.read(foodId);
        assertNotNull(retrievedFood, "Food should not be null");
        assertEquals("Pizza", retrievedFood.getName());
        assertEquals(FoodType.MEAL, retrievedFood.getFoodType());

        Coffee coffee = new Coffee(16, 20, "Capuccino", true ,MilkType.WHOLE);
        coffeeShopController.addCoffee(coffee);
        Integer coffeeId = coffee.getId();
        assertNotNull(coffeeId, "Coffee ID should not be null after persistence");

        Coffee retrievedCoffee = coffeeRepository.read(coffeeId);
        assertNotNull(retrievedCoffee, "Coffee should not be null");
        assertEquals("Capuccino", retrievedCoffee.getName());
        assertEquals(MilkType.WHOLE, retrievedCoffee.getMilkType());


        transaction.commit();

        // --- Update Operation ---
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        // Retrieve food to update
        Food foodToUpdate = foodRepository.read(foodId);
        assertNotNull(foodToUpdate, "Food should not be null before update");

        // Update food details
        foodToUpdate.setName("Burger");
        foodToUpdate.setFoodType(FoodType.MEAL);
        foodToUpdate.setPrice(18);  // Assuming price update is also part of the update

        coffeeShopController.updateFood(foodToUpdate);  // Assuming this method exists in your controller

        // Retrieve the food again to verify update
        Food updatedFood = foodRepository.read(foodId);
        assertNotNull(updatedFood, "Food should not be null after update");
        assertEquals("Burger", updatedFood.getName());
        assertEquals(FoodType.MEAL, updatedFood.getFoodType());
        assertEquals(18, updatedFood.getPrice(), "Food price should be updated");

        transaction.commit();

        // --- Delete Operation ---
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        // Delete the food
        coffeeShopController.deleteFood(foodToUpdate);  // Assuming this method exists in your controller

        // Verify that the food is deleted
        Food deletedFood = foodRepository.read(foodId);
        assertNull(deletedFood, "Food should be null after deletion");

        transaction.commit();
        session.close();
    }



}
