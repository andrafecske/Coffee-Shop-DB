package org.example.Controller;

import org.example.Exceptions.*;
import org.example.Utils.FoodType;
import org.example.Utils.Role;
import org.example.model.*;
import org.example.service.CoffeeShopService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CoffeeShopController {
    private final CoffeeShopService coffeeShopService;

    /**
     * Constructs a CoffeeShopController with the specified CoffeeShopService.
     *
     * @param coffeeShopService the service layer to handle business logic.
     */
    public CoffeeShopController(CoffeeShopService coffeeShopService) {
        this.coffeeShopService = coffeeShopService;
    }

    /**
     * Adds a new admin to the system.
     *
     * @param admin the admin to be added.
     */
    public void addAdmin(Admin admin) {
        if(admin == null) {
            System.out.println("Admin is null");
            return;
        }
        try{
            coffeeShopService.addAdmin(admin);
        }
        catch (ValidationException e) {
            throw e;
        }

        System.out.println("Admin added successfully");
    }

    /**
     * Lists all the admins in the system.
     */
    public List<Admin> listAllAdmins() {
        List<Admin> admins = coffeeShopService.getAllAdmins();

        if (admins.isEmpty()) {
            System.out.println("No admins found.");
        }
        return admins;
    }

    public List<Admin> sortAdminsByName(){
        List<Admin> admins = new ArrayList<>(listAllAdmins());
        admins.sort(Comparator.comparing(Admin::getName));
        return admins;
    }

    public List<Admin> filterAdminsByRole(Role role) {
        List<Admin> admins = new ArrayList<>(listAllAdmins()); // Create a modifiable list copy

        // Use an iterator to safely remove elements
        Iterator<Admin> iterator = admins.iterator();
        while (iterator.hasNext()) {
            Admin admin = iterator.next();
            if (!admin.getRole().equals(role)) {
                iterator.remove(); // Safe removal while iterating
            }
        }

        if (admins.isEmpty()) {
            System.out.println("No admins found.");
        }
        return admins;
    }

    /**
     * Retrieves an admin by their ID.
     *
     * @param id the ID of the admin to retrieve.
     * @return the admin with the specified ID.
     */
    public Admin getAdminById(Integer id) {
        return coffeeShopService.getAdminById(id);
    }

    public Admin getAdminByEmail(String email) {
        return coffeeShopService.getAdminByEmail(email);
    }


    /**
     * Updates the details of an existing admin.
     *
     * @param admin the admin with updated details.
     */
    public void updateAdmin(Admin admin) {
        try {
            coffeeShopService.updateAdmin(admin);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes an admin from the system.
     *
     * @param admin the admin to be deleted.
     */
//    public void deleteAdmin(Admin admin) {
//        coffeeShopService.deleteAdmin(admin);
//        System.out.println("Admin deleted successfully");
//    }

    public void deleteAdmin(Admin admin) {
        try {
            coffeeShopService.deleteAdmin(admin);
         //   System.out.println("Admin deleted successfully.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    //client operations


    /**
     * Adds a new client to the system.
     *
     * @param client the client to be added.
     */
    public void addClient(Client client) {
        try {
            coffeeShopService.addClient(client);
        } catch (ValidationException e) {
            throw e; // Rethrow if you want higher layers to handle this further.
        } catch (BusinessLogicException e) {
            throw e;
        }
    }



    /**
     * Lists all clients in the system.
     */
    public void listAllClients() {
        List<Client> clients = coffeeShopService.getAllClients();

        if(clients.isEmpty()){
            System.out.println("No clients found");
        }
        else{
            System.out.println("Clients found");
            for(Client client : clients){
                System.out.println(client);
            }
        }
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id the ID of the client to retrieve.
     * @return the client with the specified ID.
     */
    public Client getClientById(int id) {
        return coffeeShopService.getClientById(id); // Assumes this method exists in ClientService
    }


    public Client getClientByEmail(String email){
        return coffeeShopService.getClientByEmail(email);
    }
    /**
     * Adds points to a client's account.
     *
     * @param clientId the ID of the client to add points to.
     * @param points the number of points to be added.
     * @return the updated current points of the client.
     */
    public int addPoints(int clientId,int points) {
        Client client = coffeeShopService.getClientById(clientId);
        Card card = client.getCard();
        card.setCurrentPoints(card.getCurrentPoints() + points);
        coffeeShopService.updateClient(client);
        return card.getCurrentPoints();

    }

    /**
     * Removes points from a client's account.
     *
     * @param clientId the ID of the client to remove points from.
     * @param points the number of points to be removed.
     */
    public void removePoints(int clientId,int points) {
        Client client = coffeeShopService.getClientById(clientId);
        Card card = client.getCard();
        card.setCurrentPoints(card.getCurrentPoints() - points);
        coffeeShopService.updateClient(client);
    }

    /**
     * Deletes a client from the system.
     *
     * @param client the client to be deleted.
     */
    public void deleteClient(Client client) {
        try {
            coffeeShopService.deleteClient(client);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Updates the details of an existing client.
     *
     * @param client the client with updated details.
     */
    public void updateClient(Client client) {
        try {
            coffeeShopService.updateClient(client);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    //food operations

    public List<Food> filterFoodsByType(FoodType type) {
        List<Food> foods = new ArrayList<>(coffeeShopService.getAllFoods());

        Iterator<Food> iterator = foods.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();
            if(!food.getFoodType().equals(type)) {
                iterator.remove();
            }
        }
        if (foods.isEmpty()) {
            System.out.println("No food found.");
        }
        return foods;
    }


    public void addFood(Food food) {
        try {
            coffeeShopService.addFood(food);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }



    /**
     * Lists all food items in the system.
     */
    public void listAllFoods() {
        List<Food> foodList = coffeeShopService.getAllFoods();

        if(foodList.isEmpty()) {
            System.out.println("No food found");
        }
        else {
            System.out.println("Food list:");
            for(Food food : foodList) {
                System.out.println(food);
            }
        }
    }

    /**
     * Retrieves a food item by its ID.
     *
     * @param id the ID of the food to retrieve.
     * @return the food with the specified ID.
     */
    public Food getFoodById(int id) {
        return coffeeShopService.getFoodById(id);
    }


    /**
     * Deletes a food item from the system.
     *
     * @param food the food to be deleted.
     */
    public void deleteFood(Food food) {
        try {
            coffeeShopService.deleteFood(food);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Updates the details of an existing food item.
     *
     * @param food the food with updated details.
     */
    public void updateFood(Food food) {
        try {
            coffeeShopService.updateFood(food);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    //coffee operations


    public void addCoffee(Coffee coffee) {
        try {
            coffeeShopService.addCoffee(coffee);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    /**
     * Lists all coffees in the system.
     */
    public void listAllCoffees() {
        List<Coffee> coffeeList = coffeeShopService.getAllCoffee();
        if(coffeeList.isEmpty())
            System.out.println("There are no coffees");
        else{
            for (Coffee coffee : coffeeList) {
                System.out.println(coffee);
            }
        }

    }

    /**
     * Deletes a coffee from the system.
     *
     * @param coffee the coffee to be deleted.
     */
    public void deleteCoffee(Coffee coffee) {
        try {
            coffeeShopService.deleteCoffee(coffee);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Updates the details of an existing coffee.
     *
     * @param coffee the coffee with updated details.
     */
    public void updateCoffee(Coffee coffee) {
        try {
            coffeeShopService.updateCoffee(coffee);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Retrieves a coffee by its ID.
     *
     * @param id the ID of the coffee to retrieve.
     * @return the coffee with the specified ID.
     */
    public Coffee getCoffeeById(int id) {
        return coffeeShopService.getCoffeeById(id);
    }

    //order operations

    /**
     * Adds a new order for a client.
     *
     * @param clientId the ID of the client placing the order.
     * @param foodIds the list of food item IDs for the order.
     * @param coffeeIds the list of coffee item IDs for the order.
     * @return the created order.
     */
    public Order addOrder(Integer clientId, List<Integer> foodIds, List<Integer> coffeeIds) {
        try {
            // Input validation
            if (clientId == null) {
                throw new ValidationException("Client ID cannot be null.", null);
            }

            if ((foodIds == null || foodIds.isEmpty()) && (coffeeIds == null || coffeeIds.isEmpty())) {
                throw new ValidationException("At least one product (food or coffee) must be provided to create an order.", null);
            }

            // Create the order
            Order order = coffeeShopService.addOrder(clientId, foodIds, coffeeIds);

            // Add points for the client
            int currPoints = addPoints(clientId, order.getPoints());
            System.out.println("Order created successfully: " + order);
            System.out.println("Your current points: " + currPoints);

            return order;

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (DataBaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        return null; // Return null in case of failure
    }



    /**
     * Deletes an order for a client.
     *
     * @param order the order to be deleted.
     * @param clientId the ID of the client who placed the order.
     */
    public void deleteOrder(Order order, Integer clientId) {
        try {
            if (order == null) {
                throw new ValidationException("Order cannot be null.", null);
            }
            if (clientId == null) {
                throw new ValidationException("Client ID cannot be null.", null);
            }

            // Delete the order through the service
            int pointsToDelete = order.getPoints();
            coffeeShopService.deleteOrder(order);

            // Adjust client points
            removePoints(clientId, pointsToDelete);
            System.out.println("Order deleted and points updated successfully.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Updates an existing order for a client.
     *
     * @param order the order with updated details.
     * @param clientId the ID of the client who placed the order.
     */
    public void updateOrder(Order order, Integer clientId) {
        try {
            // Validate input
            if (order == null) {
                throw new ValidationException("Order cannot be null.", null);
            }

            if (clientId == null) {
                throw new ValidationException("Client ID cannot be null.", null);
            }

            // Calculate previous points and update the order
            int prevPoints = order.getPoints();
            coffeeShopService.updateOrder(order);

            // Calculate new points
            int currPoints = order.getPoints();

            // Adjust client's points
            removePoints(clientId, prevPoints);

            Client client = coffeeShopService.getClientById(clientId);
            if (client == null) {
                throw new EntityNotFoundException("Client not found for ID: " + clientId, null);
            }

            Card card = client.getCard();
            if (card == null) {
                throw new BusinessLogicException("Client does not have an associated card.", null);
            }

            card.setCurrentPoints(card.getCurrentPoints() + currPoints);
            System.out.println("Order updated successfully. Current points: " + card.getCurrentPoints());
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (DataBaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return the order with the specified ID.
     */
    public Order getOrderById(int id) {
        return coffeeShopService.getOrderById(id);
    }

    /**
     * Retrieves a list of all food items in a given order.
     *
     * @param order the order containing the food items.
     * @return a list of food items in the order.
     */
    public List<Food> getFoods(Order order) {
        List<Food> foods = new ArrayList<>();
        for (Product product : order.getProducts()) {
            if (product instanceof Food) {
                foods.add((Food) product); // Cast product to Food and add to the list
            }
        }
        return foods;
    }

    /**
     * Retrieves a list of all coffee items in a given order.
     *
     * @param order the order containing the coffee items.
     * @return a list of coffee items in the order.
     */
    public List<Coffee> getCoffees(Order order) {
        List<Coffee> coffees = new ArrayList<>();
        for (Product product : order.getProducts()) {
            if (product instanceof Coffee) {
                coffees.add((Coffee) product);
            }
        }return  coffees;

    }



    /**
     * Removes a food item by its ID from the order.
     *
     * @param foodid the ID of the food item to be removed.
     * @param order the order from which the food item will be removed.
     * @return true if the food item was successfully removed, false otherwise.
     */
    public boolean removeFoodById(int foodid, Order order) {
        return order.getProducts().removeIf(product -> product instanceof Food && product.getId() == foodid);
    }

    /**
     * Removes a coffee item by its ID from the order.
     *
     * @param coffeeid the ID of the coffee item to be removed.
     * @param order the order from which the coffee item will be removed.
     * @return true if the coffee item was successfully removed, false otherwise.
     */
    public boolean removeCoffeeById(int coffeeid, Order order) {
        return order.getProducts().removeIf(product -> product instanceof Coffee && product.getId() == coffeeid);
    }

    /**
     * Displays the current points of a client.
     *
     * @param clientId the ID of the client whose points will be displayed.
     */
    public void viewPoints(Integer clientId) {
        Client client = coffeeShopService.getClientById(clientId);
        System.out.println("Your current points: " + client.getCard().getCurrentPoints());
    }

    /**
     * Displays all orders in the system.
     */
    public void viewOrders(){
        List<Order> orders = coffeeShopService.getOrders();
        if(orders.isEmpty())
            System.out.println("There are no orders");
        else{
            for (Order order : orders) {
                System.out.println(order);
            }
        }

    }

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all orders.
     */
    public List<Order> getAllOrders(){
        return coffeeShopService.getOrders();
    }

    public List<Order> sortClientOrdersByPrice(Integer clientId){
        List<Order> orders = new ArrayList<>(getAllOrders());

        List<Order> clientOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getClientID().equals(clientId)) {
                clientOrders.add(order);
            }
        }

        clientOrders.sort(Comparator.comparingDouble(Order::getTotalCost));

        return clientOrders;
    }

    //offer operations

    /**
     * Adds a new offer to the system.
     *
     * @param foodIds the list of food IDs included in the offer.
     * @param coffeeIds the list of coffee IDs included in the offer.
     * @param pointCost the point cost for the offer.
     * @return the created offer.
     */
    public Offer addOffer(List<Integer> foodIds, List<Integer> coffeeIds, int pointCost, String name) {
        try {
            // Delegate to service
            Offer offer = coffeeShopService.addOffer(foodIds, coffeeIds, pointCost, name);
            System.out.println("Offer added successfully: " + offer);
            return offer;

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (DataBaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return null; // Return null in case of failure
    }


    /**
     * Lists all offers available in the system.
     */
    public void listAllOffers() {
        List<Offer> offers = coffeeShopService.getAllOffers();
        if(offers.isEmpty()) {
            System.out.println("No offer found");
        }
        else {
            System.out.println("Offer list:");
            for(Offer offer : offers) {
                System.out.println(offer);
            }
        }
    }


//    public void listAllOffersClients(Integer clientID) {
//        List<Offer> offers = coffeeShopService.getAllOffers();
//        Client client = coffeeShopService.getClientById(clientID);
//        List<Offer> availableOffers = new ArrayList<Offer>();
//        if(offers.isEmpty()) {
//            System.out.println("No offer found");
//        }
//        else {
//            System.out.println("Offer list:");
//            for(Offer offer : offers) {
//                if(offer.pointCost < client.getCard().getCurrentPoints())
//                    availableOffers.add(offer);
//            }
//        }
//        if(availableOffers.isEmpty()) {
//            System.out.println("You cannot afford any offers. Press enter to proceed.");
//        }
//        else
//        {
//            for(Offer offer : availableOffers) {
//                System.out.println(offer);
//            }
//        }
//    }

//    public void listAllOffersClients(Integer clientID) {
//        List<Offer> offers = coffeeShopService.getAllOffers();
//        Client client = coffeeShopService.getClientById(clientID);
//        if(offers.isEmpty()) {
//            System.out.println("No offer found");
//        }
//        else {
//            System.out.println("Offer list:");
//            for(Offer offer : offers) {
//                if(offer.pointCost < client.getCard().getCurrentPoints())
//                {System.out.println(offer.clientView());}
//            }
//        }
//    }

    public void listAllOffersClients(Integer clientID) {
        List<Offer> offers = coffeeShopService.getAllOffers();
        Client client = coffeeShopService.getClientById(clientID);

        if (client == null) {
            System.out.println("Client not found.");
            return;
        }

        if (client.getCard() == null) {
            System.out.println("Error: Client does not have a valid card.");
            return;
        }

        List<Offer> availableOffers = new ArrayList<>();

        if (offers == null || offers.isEmpty()) {
            System.out.println("No offers found.");
            return;
        }

        // Filter offers the client can afford based on points balance
        for (Offer offer : offers) {
            if (offer.getPointCost() <= client.getCard().getCurrentPoints()) {
                availableOffers.add(offer);
            }
        }

        if (availableOffers.isEmpty()) {
            System.out.println("You cannot afford any offers. Press enter to proceed.");
        } else {
            System.out.println("Available Offers you can afford:");
            for (Offer offer : availableOffers) {
                System.out.println(offer);
            }
        }
    }


    public List<Offer> getAllOffers(){
        return coffeeShopService.getAllOffers();
    }

    /**
     * Retrieves an offer by its ID.
     *
     * @param id the ID of the offer to retrieve.
     * @return the offer with the specified ID.
     */
    public Offer getOfferById(int id) {
        return coffeeShopService.getOfferById(id);
    }

    /**
     * Deletes an offer from the system.
     *
     * @param offer the offer to be deleted.
     */
    public void deleteOffer(Offer offer) {
        try {
            // Delegate to the service layer
            coffeeShopService.deleteOffer(offer);
            System.out.println("Offer deleted successfully: " + offer);

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Entity Not Found Error: " + e.getMessage());
        } catch (DataBaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    //OFFER ORDER OPERATIONS

    /**
     * Adds an offer order for a client.
     *
     * @param offerId  the ID of the offer being ordered.
     * @param clientId the ID of the client placing the order.
     * @return
     */

    public void addOfferOrder(Integer offerId, Integer clientId){
        coffeeShopService.addOfferOrder(offerId, clientId);
        Offer offer = getOfferById(offerId);
        removePoints(clientId, offer.getPointCost());
    }

//    public void addOfferOrder(Integer offerId, Integer clientId) {
//        try {
//            Offer offer = getOfferById(offerId);
//
//            if (offer == null) {
//                throw new EntityNotFoundException("Offer not found with ID: " + offerId, null);
//            }
//
//            if (clientId == null) {
//                throw new ValidationException("Client ID cannot be null", null);
//            }
//
////            // Handle points validation explicitly through logic
////            if (coffeeShopService.hasEnoughPoints(clientId, offer.getPointCost())) {
////                return coffeeShopService.addOfferOrder(offerId, clientId);
////            } else {
////                throw new BusinessLogicException("Client does not have enough points to redeem the offer.", null);
////            }
//
//        } catch (ValidationException e) {
//            System.out.println("Validation Error: " + e.getMessage());
//        } catch (EntityNotFoundException e) {
//            System.out.println("Entity Not Found Error: " + e.getMessage());
//        } catch (BusinessLogicException e) {
//            System.out.println("Business Logic Error: " + e.getMessage());
//        } catch (DataBaseException e) {
//            System.out.println("Database Error: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("An unexpected error occurred: " + e.getMessage());
//        }
//
//    }


}
