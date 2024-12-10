package org.example.service;

import org.example.Exceptions.BusinessLogicException;
import org.example.Exceptions.DataBaseException;
import org.example.Exceptions.EntityNotFoundException;
import org.example.Exceptions.ValidationException;
import org.example.Repository.IRepository;
import org.example.model.*;


import java.util.ArrayList;
import java.util.List;

public class CoffeeShopService {
    private final IRepository<Admin> adminRepo;
    private final IRepository<Client> clientRepo;
    private final IRepository<Food> foodRepo;
    private final IRepository<Coffee> coffeeRepo;
    private final IRepository<Order> orderRepo;
    private final IRepository<Offer> offerRepo;
    private final IRepository<OfferOrder> offerOrderRepo;

    public CoffeeShopService(IRepository<Admin> adminRepo, IRepository<Client> clientRepo, IRepository<Food> foodRepo, IRepository<Coffee> coffeeRepo, IRepository<Order> orderRepo, IRepository<Offer> offerRepo, IRepository<OfferOrder> offerOrderRepo) {
        this.adminRepo = adminRepo;
        this.clientRepo = clientRepo;
        this.foodRepo = foodRepo;
        this.coffeeRepo = coffeeRepo;
        this.orderRepo = orderRepo;
        this.offerRepo = offerRepo;
        this.offerOrderRepo = offerOrderRepo;
    }


    //admin operations
    public void addAdmin(Admin admin) {
        adminRepo.create(admin);
    }

    /**
     * Retrieves all admins from the repository.
     *
     * @return a list of all admins
     */
    public List<Admin> getAllAdmins() {
        return adminRepo.getAll();
    }

    /**
     * Updates an existing admin in the repository.
     *
     * @param admin the admin to be updated
     */

    public void updateAdmin(Admin admin) {
        Admin exists = adminRepo.read(admin.getId());
        if (exists == null) {
            throw new EntityNotFoundException("Admin with ID " + admin.getId() + " not found.", null);
        }
        adminRepo.update(admin.getId(), admin);
    }


    /**
     * Deletes an admin from the repository.
     *
     * @param admin the admin to be deleted
     */

    public void deleteAdmin(Admin admin) {
        if (admin == null) {
            throw new ValidationException("Admin cannot be null.", null);
        }

        try {
            Admin existingAdmin = adminRepo.read(admin.getId());
            if (existingAdmin != null) {
                adminRepo.delete(admin.getId());
            } else {
                throw new EntityNotFoundException("Admin with ID " + admin.getId() + " not found.", null);
            }
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while deleting the admin.", e);
        }
    }

    /**
     * Retrieves an admin by their ID.
     *
     * @param adminId the ID of the admin to be retrieved
     * @return the admin with the given ID
     */
    public Admin getAdminById(Integer adminId) {
        return adminRepo.read(adminId);
    }


    //client operations

    /**
     * Adds a new client to the repository.
     *
     * @param client the client to be added
     */
    public void addClient(Client client) {
        try {
            if (client == null) {
                throw new ValidationException("Client cannot be null.", null);
            }

            if (isAdminDuplicate(client)) {
                throw new BusinessLogicException("Cannot create client. An admin with the same name and ID already exists.", null);
            }

            clientRepo.create(client);
            System.out.println("Client added successfully.");
        } catch (ValidationException | BusinessLogicException e) {
            throw e; // Re-throw specific exceptions for higher layers to handle.
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while adding the client to the database.", e);
        }
    }



    /**
     * Checks if a client shares the same ID and name as an existing admin.
     *
     * @param client the client to check for duplication
     * @return {@code true} if there is a duplicate, {@code false} otherwise
     */

    private boolean isAdminDuplicate(Client client){
        List<Admin> admins = adminRepo.getAll();
        for(Admin admin : admins){
            if(admin.getName().equals(client.getName()) && admin.getId().equals(client.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id the ID of the client to be retrieved
     * @return the client with the given ID
     */
    public Client getClientById(int id){
        return clientRepo.read(id);
    }

    /**
     * Deletes a client from the repository.
     *
     * @param client the client to be deleted
     */
    public void deleteClient(Client client){
        if(client == null){
            System.out.println("Client is null");
            return;
        }
        clientRepo.delete(client.getId());
        System.out.println("Client deleted successfully");

    }

    /**
     * Updates an existing client in the repository.
     *
     * @param client the client to be updated
     */

    public void updateClient(Client client){
        if(client == null){
            System.out.println("Client is null");
            return;
        }
        Client exists = clientRepo.read(client.getId());
        if (exists != null) {
            clientRepo.update(client.getId(), client);
        }else{
            System.out.println("Client not found");
        }
    }

    /**
     * Retrieves all clients from the repository.
     *
     * @return a list of all clients
     */

    public List<Client> getAllClients(){
        return clientRepo.getAll();
    }



    //food operations

    /**
     * Adds a new {@link Food} item to the food repository.
     * <p>
     * If the specified {@code food} object is {@code null}, this method logs a message
     * and exits without adding the item to the repository. Otherwise, it saves the
     * food item in the repository.
     * </p>
     *
     * @param food the {@link Food} object to be added to the repository.
     */

    public void addFood(Food food) {
        try {
            if (food == null) {
                System.out.println("Food is null");
                return;
            }


            foodRepo.create(food);
            System.out.println("Food added successfully");
        } catch (Exception e) {
            System.out.println("Error adding food to repository: " + e.getMessage());
        }
    }

    /**
     * Retrieves all {@link Food} items currently stored in the food repository.
     *
     * @return a {@link List} of {@link Food} items available in the repository.
     */
    public List<Food> getAllFoods(){
        return foodRepo.getAll();
    }

    public Food getFoodById(int id){
        return  foodRepo.read(id);
    }

    /**
     * Adds a new {@link Food} item to the food repository.
     * <p>
     * If the specified {@code food} object is {@code null}, this method logs a message
     * and exits without adding the item to the repository. Otherwise, it saves the
     * food item in the repository.
     * </p>
     *
     * @param food the {@link Food} object to be added to the repository.
     */
    public void deleteFood(Food food){
        if(food == null){
            System.out.println("Food is null");
            return;
        }
        foodRepo.delete(food.getId());
    }

    /**
     * Updates an existing {@link Food} item in the repository with new information.
     * <p>
     * If the {@code food} parameter is {@code null}, a message is logged and the method exits
     * without performing an update. If the {@code food} item exists in the repository, it
     * is updated with the new details. Otherwise, a message indicates that the food item
     * was not found.
     * </p>
     *
     * @param food the updated {@link Food} object to store in the repository.
     */
    public void updateFood(Food food){
        if(food == null){
            System.out.println("Food is null");
            return;
        }
        Food exists = foodRepo.read(food.getId());
        if (exists != null) {
            foodRepo.update(food.getId(), food);
            System.out.println("Food updated successfully");
        }else{
            System.out.println("Food not found");
        }
    }


    //coffee operations

    /**
     * Adds a new coffee item to the coffee repository.
     *
     * @param coffee the {@link Coffee} object to be added
     *               <p>If {@code coffee} is null, an error message is displayed and the operation is not performed.</p>
     */
    public void addCoffee(Coffee coffee) {
        try {
            if (coffee == null) {
                System.out.println("Coffee is null");
                return;
            }

            coffeeRepo.create(coffee);
            System.out.println("Coffee added successfully");

        } catch (Exception e) {
            System.out.println("Error adding coffee to repository: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all coffee items from the coffee repository.
     *
     * @return a {@link List} of {@link Coffee} objects currently stored in the repository
     */

    public List<Coffee> getAllCoffee(){
        return coffeeRepo.getAll();
    }

    /**
     * Retrieves a specific coffee item by its ID.
     *
     * @param id the unique identifier of the coffee item
     * @return the {@link Coffee} object corresponding to the specified ID,
     *         or {@code null} if no coffee item with the specified ID exists
     */
    public Coffee getCoffeeById(int id){
        return coffeeRepo.read(id);
    }


    /**
     * Deletes a coffee item from the coffee repository.
     *
     * @param coffee the {@link Coffee} object to be deleted
     *               <p>If {@code coffee} is null, an error message is displayed and the operation is not performed.</p>
     */
    public void deleteCoffee(Coffee coffee){
        if(coffee == null){
            System.out.println("Coffee is null");
            return;
        }
        coffeeRepo.delete(coffee.getId());
    }

    /**
     * Updates the information of an existing {@link Coffee} object in the repository.
     * <p>
     * If the specified {@code coffee} object is {@code null}, this method will log a message
     * and exit without making any changes. If the coffee is not found in the repository,
     * it will notify that the coffee is not found. Otherwise, it updates the coffee's details.
     * </p>
     *
     * @param coffee the {@link Coffee} object containing the updated information.
     *               Must have a valid ID to be located in the repository.
     */

    public void updateCoffee(Coffee coffee){
        if(coffee == null){
            System.out.println("Coffee is null");
            return;
        }
        Coffee exists = coffeeRepo.read(coffee.getId());
        if (exists != null) {
            coffeeRepo.update(coffee.getId(), coffee);
            System.out.println("Coffee updated successfully");
        }else {
            System.out.println("Coffee not found");
        }

    }

    /**
     * Creates a new {@link Order} for a specific client using a list of food and coffee item IDs.
     * <p>
     * The method generates a new unique order ID, retrieves each {@link Food} and {@link Coffee} item
     * based on the provided IDs, and adds them to the order. The newly created order is then stored
     * in the repository.
     * </p>
     *
     * @param clientID the unique identifier of the client placing the order.
     * @param foodIDs a list of unique identifiers for each {@link Food} item in the order.
     * @param coffeeIDs a list of unique identifiers for each {@link Coffee} item in the order.
     * @return the newly created {@link Order} object with all specified products.
     */
    public Order addOrder(Integer clientID, List<Integer> foodIDs, List<Integer> coffeeIDs) {
        List<Product> products = new ArrayList<>();
        for (Integer foodID : foodIDs) {
            Food food = getFoodById(foodID);
            products.add(food);
        }
        for (Integer coffeeID : coffeeIDs) {
            Coffee coffee = getCoffeeById(coffeeID);
            products.add(coffee);
        }
        Order order = new Order(clientID, products);

        orderRepo.create(order);
        return order;
    }

    /**
     * Retrieves all {@link Order} objects stored in the repository.
     *
     * @return a list of all existing {@link Order} objects.
     */
    public List<Order> getOrders() {
        return orderRepo.getAll();
    }

    /**
     * Retrieves a specific {@link Order} from the repository based on its unique identifier.
     *
     * @param orderID the unique identifier of the {@link Order} to retrieve.
     * @return the {@link Order} object with the specified ID, or {@code null} if not found.
     */
    public Order getOrderById(Integer orderID) {
        return orderRepo.read(orderID);
    }

    /**
     * Updates an existing {@link Order} in the repository with new data.
     * <p>
     * If the specified order exists, this method recalculates the points and total cost
     * associated with the order and then updates the repository entry with the new details.
     * If the order is not found, a message is printed indicating this.
     * </p>
     *
     * @param order the {@link Order} object containing the updated information.
     */
    public void updateOrder(Order order) {
        if (order == null) {
            System.out.println("Order is null");
            return;}

        Order exists = orderRepo.read(order.getId());
        if (exists != null) {
            order.calculatePoints();
            order.calculateTotalCost();
            orderRepo.update(order.getId(), order);
            System.out.println("Order updated successfully");
        }else{
            System.out.println("Order not found");
        }
    }

    /**
     * Updates an existing {@link Order} in the repository with new data.
     * <p>
     * If the specified order exists, this method recalculates the points and total cost
     * associated with the order and then updates the repository entry with the new details.
     * If the order is not found, a message is printed indicating this.
     * </p>
     *
     * @param order the {@link Order} object containing the updated information.
     */
    public void deleteOrder(Order order) {
        if (order == null) {
            System.out.println("Order is null");
            return;
        }
        orderRepo.delete(order.getId());
    }

    //OFFER OPERATIONS


    /**
     * Creates and adds a new {@link Offer} to the repository based on specified food and coffee IDs, along with a point cost.
     * <p>
     * This method generates a unique offer ID and assembles a list of products by retrieving
     * {@link Food} and {@link Coffee} items from their respective IDs. These products are then
     * grouped into a new {@link Offer} with the specified point cost, and the offer is stored in the repository.
     * </p>
     *
     * @param foodIds   a list of {@code Integer} IDs representing the food items to be included in the offer.
     * @param coffeeIds a list of {@code Integer} IDs representing the coffee items to be included in the offer.
     * @param pointCost an {@code int} representing the point cost of the offer.
     * @return the newly created {@link Offer} object.
     */
    public Offer addOffer(List<Integer> foodIds, List<Integer> coffeeIds, int pointCost, String name) {

        List<Product> products = new ArrayList<>();
        for (Integer foodID : foodIds) {
            Food food = getFoodById(foodID);
            products.add(food);
        }
        for (Integer coffeeID : coffeeIds) {
            Coffee coffee = getCoffeeById(coffeeID);
            products.add(coffee);
        }
        Offer offer = new Offer(products, pointCost, name);
        offerRepo.create(offer);
        return offer;
    }

    /**
     * Retrieves an {@link Offer} from the repository by its ID.
     * <p>
     * This method calls the repository's read method to fetch the offer corresponding to the given ID.
     * If the offer exists, it will be returned; otherwise, {@code null} will be returned.
     * </p>
     *
     * @param id the ID of the offer to be retrieved.
     * @return the {@link Offer} with the specified ID, or {@code null} if not found.
     */
    public Offer getOfferById(Integer id) {
        return offerRepo.read(id);
    }

    /**
     * Retrieves all {@link Offer} objects from the repository.
     * <p>
     * This method calls the repository's getAll method to fetch a list of all available offers.
     * </p>
     *
     * @return a list of all {@link Offer} objects in the repository.
     */
    public List<Offer> getAllOffers() {
        return offerRepo.getAll();
    }

    /**
     * Deletes a specified {@link Offer} from the repository.
     * <p>
     * This method first checks if the provided offer is {@code null}. If it is not {@code null},
     * it proceeds to delete the offer from the repository using its ID.
     * </p>
     *
     * @param offer the {@link Offer} to be deleted. If the offer is {@code null}, it will not be deleted.
     */
    public void deleteOffer(Offer offer) {
        if (offer == null) {
            System.out.println("Offer is null");
        }
        offerRepo.delete(offer.getId());
    }

    //OFFER ORDER OPERATIONS
    /**
     * Creates an {@link OfferOrder} by associating a {@link Client} with an {@link Offer}.
     * <p>
     * This method checks if the specified {@link Client} has enough points to purchase the specified {@link Offer}.
     * If the client has sufficient points, an {@link OfferOrder} is created, the points are deducted from the client's card,
     * and the offer order is stored in the repository. Otherwise, a message is printed indicating insufficient points.
     * </p>
     *
     * @param offerId the ID of the {@link Offer} to be purchased.
     * @param clientId the ID of the {@link Client} making the purchase.
     * @return the created {@link OfferOrder} if the client has enough points; {@code null} if the points are insufficient.
     */

    public OfferOrder addOfferOrder (Integer offerId, Integer clientId){
        Offer offer = getOfferById(offerId);
        Client client = getClientById(clientId);

        OfferOrder offerOrder = new OfferOrder(client, offer);
        client.getCard().setCurrentPoints(client.getCard().getCurrentPoints()-offer.pointCost);
        offerOrderRepo.create(offerOrder);
        return offerOrder;
    }


}
