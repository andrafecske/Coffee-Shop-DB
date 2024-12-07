package org.example.service;

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

    public CoffeeShopService(IRepository<Admin> adminRepo, IRepository<Client> clientRepo, IRepository<Food> foodRepo, IRepository<Coffee> coffeeRepo, IRepository<Order> orderRepo) {
        this.adminRepo = adminRepo;
        this.clientRepo = clientRepo;
        this.foodRepo = foodRepo;
        this.coffeeRepo = coffeeRepo;
        this.orderRepo = orderRepo;
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
        if (exists != null) {
            adminRepo.update(admin.getId(), admin);
        } else {
            System.out.println("Admin not found");
        }
    }


    /**
     * Deletes an admin from the repository.
     *
     * @param admin the admin to be deleted
     */

    public void deleteAdmin(Admin admin) {
        if(admin == null){
            System.out.println("Admin is null");
            return;}

        Admin exists = adminRepo.read(admin.getId());
        if (exists != null) {
            adminRepo.delete(admin.getId());
        }else{
            System.out.println("Admin not found");
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
                System.out.println("Client is null");
                return;
            }

            if (isAdminDuplicate(client)) {
                System.out.println("Can't create client, there is already an admin with the same name and id");
                return;
            }

            clientRepo.create(client);
            System.out.println("Client added successfully");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the client: " + e.getMessage());
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





}
