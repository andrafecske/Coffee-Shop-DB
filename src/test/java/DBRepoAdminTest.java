
import org.example.Controller.CoffeeShopController;
import org.example.Exceptions.BusinessLogicException;
import org.example.Exceptions.ValidationException;
import org.example.Repository.DBRepo;
import org.example.Utils.Role;
import org.example.model.*;
import org.example.Utils.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.example.service.CoffeeShopService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        orderRepository = new DBRepo<>(sessionFactory, Order.class);
        offerRepository = new DBRepo<>(sessionFactory, Offer.class);
        offerOrderDBRepo = new DBRepo<>(sessionFactory, OfferOrder.class);


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


  @Test
    void testOrderOperations() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List <Integer> foodIds = new ArrayList<Integer>();
        foodIds.add(4);
        foodIds.add(5);

        List<Integer> coffeeIds =  new ArrayList<Integer>();
        coffeeIds.add(3);

        Client client = new Client(21, "Ioana");
        coffeeShopController.addClient(client);
        Integer clientId = client.getId();
        Card card = client.getCard();

        Order addedOrder = coffeeShopController.addOrder(clientId, foodIds, coffeeIds);
        Integer orderId = addedOrder.getId();
        assertNotNull(orderId, "Order ID should not be null after persistence");

        System.out.println(card.getCurrentPoints());

        Order retrievedOrder = coffeeShopController.getOrderById(orderId);
        transaction.commit();
        assertNotNull(retrievedOrder, "Retrieved order should not be null");
        assertEquals(clientId, retrievedOrder.getClientID(), "Client ID should match");
        assertEquals(3, retrievedOrder.getProducts().size(), "Order should contain three products");
        session.refresh(card);


        coffeeShopController.deleteOrder(retrievedOrder, clientId);
        assertNull(coffeeShopController.getOrderById(orderId));

        session.close();

    }

   //@Test
    void testOfferOperations() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        List<Integer> foodIds = List.of(4, 5);  // Assuming these IDs exist in the database
        List<Integer> coffeeIds = List.of(3);  // Assuming this ID exists in the database


        String offerName = "woahhh";
        int pointCost = 50;
        Offer newOffer = coffeeShopController.addOffer(foodIds, coffeeIds, pointCost, offerName);
        assertNotNull(newOffer, "New offer should not be null");
        assertNotNull(newOffer.getId(), "Offer ID should not be null after persistence");
        assertEquals(pointCost, newOffer.getPointCost(), "Offer point cost should match");
        assertEquals(offerName, newOffer.getName(), "Offer name should match");
        assertEquals(3, newOffer.getProducts().size(), "Offer should contain three products");

        Offer retrievedOffer = coffeeShopController.getOfferById(newOffer.getId());
        assertNotNull(retrievedOffer, "Retrieved offer should not be null");
        assertEquals(newOffer.getId(), retrievedOffer.getId(), "Retrieved offer ID should match");
        assertEquals(newOffer.getName(), retrievedOffer.getName(), "Retrieved offer name should match");
        assertEquals(newOffer.getPointCost(), retrievedOffer.getPointCost(), "Retrieved offer point cost should match");
        assertEquals(newOffer.getProducts().size(), retrievedOffer.getProducts().size(), "Retrieved offer product count should match");


        coffeeShopController.deleteOffer(newOffer);
        Offer deletedOffer = coffeeShopController.getOfferById(newOffer.getId());
        assertNull(deletedOffer, "Deleted offer should be null");

        transaction.commit();
        session.close();
    }


    @Test
    public void testOfferOrderOperations() {
        // Set up the client
        Card card = new Card();
        card.setCurrentPoints(137); // Ensure enough points for the test

        Client client = new Client();
        client.setName("John Doe");
        client.setAge(30);
        client.setCard(card);

        clientRepository.create(client);

        // Set up the offer
        Offer newOffer = new Offer();
        newOffer.setName("slay");
        newOffer.setPointCost(30);

        // Insert offer into DB
        offerRepository.create(newOffer);

        // Fetch the latest client details for service
        assertNotNull(client.getCard(), "Client's card should not be null.");
        assertTrue(
                client.getCard().getCurrentPoints() >= newOffer.getPointCost(),
                "Client does not have enough points to redeem the offer."
        );

        // Perform the service call without try/catch
        coffeeShopController.addOfferOrder(newOffer.getId(), client.getId());

        // Assert points were deducted successfully
        Client updatedClient = clientRepository.read(client.getId());
        assertNotNull(updatedClient, "Updated client should not be null.");
        assertEquals(
                107,
                updatedClient.getCard().getCurrentPoints(),
                "Points should be deducted correctly upon successful offer redemption."
        );

        System.out.println("Points successfully deducted. Test passed.");
    }




    @Test
    void testAddAdminValidationException() {
        try {
            // Arrange: Simulate invalid admin input
            Admin invalidAdmin = new Admin(-1, "Invalid Admin", Role.Manager);  // Invalid age (-1)

            // Attempt to add admin which should throw a ValidationException
            coffeeShopController.addAdmin(invalidAdmin);

            // If no exception was thrown, the test should fail
            fail("Expected ValidationException was not thrown.");
        } catch (ValidationException e) {
            // Assert: Check that the exception message is what we expect
            assertEquals("Admin age cannot be less than or equal to zero.", e.getMessage());
        } catch (Exception e) {
            // In case other unexpected exceptions occur
            fail("Unexpected exception: " + e.getMessage());
        }
    }




}
