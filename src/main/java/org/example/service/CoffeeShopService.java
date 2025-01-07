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
        if (admin.getAge() <= 0) {
            throw new ValidationException("Admin age cannot be less than or equal to zero.", null);
        }

        if (admin.getName().isEmpty()) {
            throw new ValidationException("Admin name cannot be empty.", null);
        }

        if (admin.getName().matches(".*\\d.*")) {
            throw new ValidationException("Admin name cannot contain digits.", null);
        }

        if (admin.getEmail().isEmpty()) {
            throw new ValidationException("Admin email cannot be empty.", null);
        }

        // Basic email format validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!admin.getEmail().matches(emailRegex)) {
            throw new ValidationException("Invalid email format.", null);
        }

        if (admin.getPassword().isEmpty()) {
            throw new ValidationException("Admin password cannot be empty.", null);
        }

        // Add the admin to the repository
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

    public Admin getAdminByEmail(String email) {
        for (Admin admin : adminRepo.getAll()) {
            if (admin.getEmail() != null && admin.getEmail().equalsIgnoreCase(email)) {
                return admin;
            }
        }
        throw new EntityNotFoundException("User with email " + email + " not found.", null);
    }



    //client operations

    /**
     * Adds a new client to the repository.
     *
     * @param client the client to be added
     */
    public void addClient(Client client) {
        if (client == null) {
            throw new ValidationException("Client cannot be null.", null);
        }

        if (client.getName().matches(".*\\d.*")) {
            throw new ValidationException("Name cannot contain numeric values.", null);
        }

        if (client.getAge() <= 0) {
            throw new ValidationException("Client age must be greater than zero.", null);
        }

        if (isClientDuplicate(client)) {
            throw new BusinessLogicException("Cannot create client. A user with the same email already exists.", null);
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!client.getEmail().matches(emailRegex)) {
            throw new ValidationException("Invalid email format.", null);
        }

        try {
            clientRepo.create(client);
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

    private boolean isClientDuplicate(Client client) {
        if (client == null || client.getEmail() == null) {
            throw new ValidationException("Client or client email cannot be null.", null);
        }

        // Check if email exists in Admin list
        boolean existsInAdmins = adminRepo.getAll().stream()
                .anyMatch(admin -> client.getEmail().equalsIgnoreCase(admin.getEmail()));

        // Check if email exists in Client list
        boolean existsInClients = clientRepo.getAll().stream()
                .anyMatch(existingClient -> client.getEmail().equalsIgnoreCase(existingClient.getEmail()));

        return existsInAdmins || existsInClients;
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

    public Client getClientByEmail(String email) {
        for (Client client : clientRepo.getAll()) {
            if (client.getEmail() != null && client.getEmail().equals(email)) {
                return client;
            }
        }
        throw new EntityNotFoundException("Client with email " + email + " not found.", null);
    }

    /**
     * Deletes a client from the repository.
     *
     * @param client the client to be deleted
     */
    public void deleteClient(Client client) {
        try {
            if (client == null) {
                throw new ValidationException("Client cannot be null.", null);
            }

            // Attempt to delete the client
            clientRepo.delete(client.getId());
            System.out.println("Client deleted successfully.");
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Client with ID " + client.getId() + " not found. Unable to delete.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while deleting the client from the database.", e);
        }
    }


    /**
     * Updates an existing client in the repository.
     *
     * @param client the client to be updated
     */

    public void updateClient(Client client) {
        try {
            if (client == null) {
                throw new ValidationException("Client cannot be null.", null);
            }

            // Check if the client exists in the database
            Client exists = clientRepo.read(client.getId());
            if (exists == null) {
                throw new EntityNotFoundException("Client with ID " + client.getId() + " not found. Unable to update.", null);
            }

            // Perform the update
            clientRepo.update(client.getId(), client);
            System.out.println("Client updated successfully.");
        } catch (ValidationException | EntityNotFoundException e) {
            throw e; // Re-throw specific exceptions for higher layers to handle.
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while updating the client in the database.", e);
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
                throw new ValidationException("Food cannot be null.", null);
            }

            foodRepo.create(food);
            //System.out.println("Food added successfully.");
        } catch (ValidationException e) {
            throw e; // Re-throw specific exceptions for higher layers to handle
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while adding food to the database.", e);
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
    public void deleteFood(Food food) {
        try {
            if (food == null) {
                throw new ValidationException("Food cannot be null.", null);
            }

            // Attempt to delete the food
            foodRepo.delete(food.getId());
            System.out.println("Food deleted successfully.");
        } catch (ValidationException e) {
            throw e; // Re-throw specific validation exceptions
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Food with ID " + food.getId() + " not found. Unable to delete.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while deleting food from the database.", e);
        }
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
    public void updateFood(Food food) {
        try {
            if (food == null) {
                throw new ValidationException("Food cannot be null.", null);
            }

            Food exists = foodRepo.read(food.getId());
            if (exists == null) {
                throw new EntityNotFoundException("Food with ID " + food.getId() + " not found. Unable to update.", null);
            }

            // Update the food entity
            foodRepo.update(food.getId(), food);
            System.out.println("Food updated successfully.");
        } catch (ValidationException e) {
            throw e; // Pass specific validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Food with ID " + food.getId() + " does not exist in the database.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while updating food in the database.", e);
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
                throw new ValidationException("Coffee cannot be null.", null);
            }

            coffeeRepo.create(coffee);
            System.out.println("Coffee added successfully.");
        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while adding coffee to the database.", e);
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
    public void deleteCoffee(Coffee coffee) {
        try {
            if (coffee == null) {
                throw new ValidationException("Coffee cannot be null.", null);
            }

            coffeeRepo.delete(coffee.getId());
            System.out.println("Coffee deleted successfully.");
        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Coffee with ID " + coffee.getId() + " not found. Unable to delete.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while deleting coffee from the database.", e);
        }
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

    public void updateCoffee(Coffee coffee) {
        try {
            if (coffee == null) {
                throw new ValidationException("Coffee cannot be null.", null);
            }

            Coffee exists = coffeeRepo.read(coffee.getId());
            if (exists == null) {
                throw new EntityNotFoundException("Coffee with ID " + coffee.getId() + " not found. Unable to update.", null);
            }

            coffeeRepo.update(coffee.getId(), coffee);
            System.out.println("Coffee updated successfully.");
        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Coffee with ID " + coffee.getId() + " does not exist in the database.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while updating coffee in the database.", e);
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
        try {
            if (clientID == null) {
                throw new ValidationException("Client ID cannot be null.", null);
            }
            if ((foodIDs == null || foodIDs.isEmpty()) && (coffeeIDs == null || coffeeIDs.isEmpty())) {
                throw new ValidationException("Both food and coffee ID lists cannot be null or empty.", null);
            }

            List<Product> products = new ArrayList<>();

            // Process Food IDs
            for (Integer foodID : foodIDs) {
                if (foodID == null) {
                    throw new ValidationException("Food ID cannot be null.", null);
                }
                Food food = getFoodById(foodID);
                if (food == null) {
                    throw new EntityNotFoundException("Food with ID " + foodID + " not found.", null);
                }
                products.add(food);
            }

            // Process Coffee IDs
            for (Integer coffeeID : coffeeIDs) {
                if (coffeeID == null) {
                    throw new ValidationException("Coffee ID cannot be null.", null);
                }
                Coffee coffee = getCoffeeById(coffeeID);
                if (coffee == null) {
                    throw new EntityNotFoundException("Coffee with ID " + coffeeID + " not found.", null);
                }
                products.add(coffee);
            }

            // Create the order
            Order order = new Order(clientID, products);
            orderRepo.create(order);
            System.out.println("Order created successfully.");
            return order;

        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Failed to add order due to missing products.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while adding the order to the database.", e);
        }
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
        try {
            if (order == null) {
                throw new ValidationException("Order cannot be null.", null);
            }

            // Check if the order exists
            Order existingOrder = orderRepo.read(order.getId());
            if (existingOrder == null) {
                throw new EntityNotFoundException("Order with ID " + order.getId() + " not found.", null);
            }

            // Recalculate order details
            order.calculatePoints();
            order.calculateTotalCost();

            // Update the order
            orderRepo.update(order.getId(), order);
            System.out.println("Order updated successfully.");
        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Order update failed because the order was not found.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while updating the order in the database.", e);
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
        try {
            if (order == null) {
                throw new ValidationException("Order cannot be null.", null);
            }

            orderRepo.delete(order.getId());
            System.out.println("Order deleted successfully.");
        } catch (ValidationException e) {
            throw e; // Pass validation exception up
        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("Order with ID " + order.getId() + " not found. Unable to delete.", e);
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Error occurred while deleting the order from the database.", e);
        }
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
        try {
            // Input validation
            if ((foodIds == null || foodIds.isEmpty()) && (coffeeIds == null || coffeeIds.isEmpty())) {
                throw new ValidationException("An offer must include at least one product (food or coffee).", null);
            }
            if (pointCost <= 0) {
                throw new ValidationException("Point cost must be a positive integer.", null);
            }
            if (name == null || name.isBlank()) {
                throw new ValidationException("Offer name cannot be null or empty.", null);
            }

            if(existsName(name)) {
                throw new BusinessLogicException("Offer name already exists.", null);
            }

            // Prepare the list of products
            List<Product> products = new ArrayList<>();
            for (Integer foodID : foodIds) {
                Food food = getFoodById(foodID); // Throws exception if not found
                products.add(food);
            }
            for (Integer coffeeID : coffeeIds) {
                Coffee coffee = getCoffeeById(coffeeID); // Throws exception if not found
                products.add(coffee);
            }

            // Create and save the offer
            Offer offer = new Offer(products, pointCost, name);
            offerRepo.create(offer);

            System.out.println("Offer created successfully: " + offer);
            return offer;

        } catch (EntityNotFoundException e) {
            throw new BusinessLogicException("One or more products in the offer could not be found.", e);
        } catch (Exception e) {
            throw new DataBaseException("An error occurred while creating the offer.", e);
        }
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
        try {
            // Validation: Check if the offer is null
            if (offer == null) {
                throw new ValidationException("The offer to delete cannot be null.", null);
            }

            // Check if the offer exists in the repository
            Offer existingOffer = offerRepo.read(offer.getId());
            if (existingOffer == null) {
                throw new EntityNotFoundException("The offer with ID " + offer.getId() + " does not exist.", null);
            }

            // Proceed to delete
            offerRepo.delete(offer.getId());
            System.out.println("Offer deleted successfully: " + offer);

        } catch (ValidationException | EntityNotFoundException e) {
            throw e; // Rethrow validation and entity-specific exceptions for the controller to handle
        } catch (Exception e) {
            throw new DataBaseException("An error occurred while deleting the offer.", e);
        }
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
    public void addOfferOrder(Integer offerId, Integer clientId) {
        try {
            Offer offer = getOfferById(offerId);
            Client client = getClientById(clientId);

            if (offer == null || client == null) {
                throw new EntityNotFoundException("Offer or Client not found.", null);
            }

            if (client.getCard().getCurrentPoints() < offer.getPointCost()) {
                throw new BusinessLogicException("Not enough points to redeem the offer.", null);
            }


            // Save client changes to DB
            clientRepo.update(clientId,client); // Ensure persistence here

            OfferOrder offerOrder = new OfferOrder(client, offer);
            offerOrderRepo.create(offerOrder);

            //return offerOrder;
        } catch (Exception e) {
            throw new DataBaseException("Error during the offer redemption.", e);
        }
    }

    public boolean existsName(String offerName){
        List<Offer> offers = offerRepo.getAll();
        for (Offer offer : offers) {
            if (offer.getName().equals(offerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnoughPoints(Integer clientId, int pointCost) {
        Client client = getClientById(clientId);
        return client.getCard().getCurrentPoints() >= pointCost;
    }



}
