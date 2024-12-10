package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
import org.example.Utils.FoodType;
import org.example.model.Coffee;
import org.example.model.Food;
import org.example.model.Offer;
import org.example.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUI {
    private final CoffeeShopController coffeeShopController;
    private final Scanner scanner;
    private Integer id; //id of the client
    public ClientUI(CoffeeShopController coffeeShopController, Scanner scanner) {
        this.coffeeShopController = coffeeShopController;
        this.scanner = scanner;
    }
    public void start(Integer id){
        boolean continueLoop = true;
        this.id = id;

        while(continueLoop){
            System.out.println("""
                    Coffee Shop
                    
                   
                    1. View Menu
                    2. Create Order
                    3. Update Order
                    4. Delete Order
                    5. View my points
                    6. Claim offers
                    7. View your orders
                    8. Sort orders by price
                    9. Filter foods bt type
                    10. Exit
                    
                    """);
            System.out.println("Choose an option: ");
            String option = scanner.nextLine();

            switch(option){
                case "1":
                    System.out.println("Foods in the menu:");
                    coffeeShopController.listAllFoods();
                    System.out.println("Coffee menu:");
                    coffeeShopController.listAllCoffees();
                    break;

                case "2":

                    List<Integer> foods = orderFood();
                    if (foods.isEmpty()) {
                        System.out.println("No food items selected.");
                    } else {
                        System.out.println("Selected food items: " + foods);
                    }

                    List<Integer> coffees = orderCoffee();
                    if (coffees.isEmpty()) {
                        System.out.println("No coffee items selected.");
                    } else {
                        System.out.println("Selected coffee items: " + coffees);
                    }

                    if (foods.isEmpty() && coffees.isEmpty()) {
                        System.out.println("Order creation canceled. No items selected.");
                        break;
                    }

                    Order order = coffeeShopController.addOrder(id, foods, coffees);
                    System.out.println("Order added successfully! " + order);
                    break;

                case "3":
                    updateOrder(scanner);
                    break;

                case "4":
                    deleteOrder(scanner);
                    break;

                case "5":
                    viewPoints();
                    break;

                case "6":
                    viewPoints();
                    Integer offerID=0;
                    System.out.println("Here are the offers you can afford, enter the name of the one you want to use:");
                    coffeeShopController.listAllOffersClients(id);
                    String offerName = scanner.nextLine();
                    List<Offer> offers = coffeeShopController.getAllOffers();

                    for(Offer offer: offers){
                        if(offer.getName().equals(offerName)){
                            offerID = offer.getId();
                            break;
                        }
                    }

                    if(offerID == 0)
                    {
                        System.out.println("Offer not found!");
                        break;
                    }
                    coffeeShopController.addOfferOrder(offerID, id);
                    System.out.println("Order added successfully!");
                    viewPoints();
                    break;

                case"7":
                    viewOrders();
                    break;

                case"8":
                    System.out.println("Your orders sorted by price:");
                    List<Order> orders = coffeeShopController.sortClientOrdersByPrice(id);
                    for(Order order1 : orders){
                        System.out.println(order1);
                    }
                    break;

                case "9":
                    System.out.println("Enter type of foods you want to see(SNACK/SANDWICH/DESSERT/MEAL):");
                    FoodType type = FoodType.valueOf(scanner.nextLine());
                    List<Food> filteredFoods = new ArrayList<>(coffeeShopController.filterFoodsByType(type));
                    System.out.println("Filtered foods by type:");
                    for(Food food : filteredFoods) {
                        System.out.println(food);
                    }
                    break;

                case "10":
                    continueLoop = false;
                    break;

            }



        }
    }

    public List<Integer> orderFood() {
        List<Integer> foods = new ArrayList<>();
        while (true) {
            try {
                coffeeShopController.listAllFoods();
                System.out.println("What food would you like to order? Enter the ID or press Enter if you would like to stop ordering");
                String id = scanner.nextLine();

                if (id.isEmpty()) {
                    break; // Exit the loop if input is empty
                }

                Integer intId = Integer.parseInt(id.trim()); // Convert input to Integer
                if (coffeeShopController.getFoodById(intId) != null) {
                    foods.add(intId); // Add valid ID to the list
                } else {
                    System.out.println("Invalid ID");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numeric ID.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        return foods;
    }


    public List<Integer> orderCoffee() {
        List<Integer> coffees = new ArrayList<>();
        while (true) {
            try {
                coffeeShopController.listAllCoffees();
                System.out.println("What coffee would you like to order? Enter the ID or press Enter if you would like to stop ordering");
                String id = scanner.nextLine();

                if (id.isEmpty()) {
                    break; // Exit the loop if input is empty
                }

                Integer intId = Integer.parseInt(id.trim()); // Convert input to Integer
                if (coffeeShopController.getCoffeeById(intId) != null) {
                    coffees.add(intId); // Add valid ID to the list
                } else {
                    System.out.println("Invalid ID");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numeric ID.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        return coffees;
    }


    /**
     * Allows the client to update an existing order by providing the order ID.
     * The method displays the current products in the order, and then prompts
     * the client to add or remove foods and coffees from the order.
     *
     * It ensures that only valid food and coffee IDs are added or removed.
     * After making changes, the order is updated and displayed.
     *
     * @param scanner The scanner object used to read input from the client.
     */
    private void updateOrder(Scanner scanner){
        try {
            System.out.print("Enter the ID of the Order to update: ");
            viewOrders();
            int id = Integer.parseInt(scanner.nextLine());

            // Retrieve the existing Admin
            Order existingOrder = coffeeShopController.getOrderById(id);
            if (existingOrder == null) {
                System.out.println("Order with ID " + id + " not found.");
                return;
            }

            // Prompt for Age
            System.out.println("Current Products in Order: " + existingOrder.getProducts());
            System.out.println("Foods: "+ coffeeShopController.getFoods(existingOrder));
            System.out.println("Coffee: "+ coffeeShopController.getCoffees(existingOrder));

            System.out.print("Enter new Foods (comma-separated IDs), or press Enter to keep the existing Foods: ");
            String foodInput = scanner.nextLine();

            List<Food> additionalFoods;
            if (foodInput.isEmpty()) {
                System.out.println("No new foods added");// Keep existing products
            } else {
                additionalFoods = new ArrayList<>();
                String[] productIds = foodInput.split(",");
                for (String productId : productIds) {
                    try {
                        int productIdInt = Integer.parseInt(productId.trim());
                        Food food = coffeeShopController.getFoodById(productIdInt);
                        if (food != null) {
                            additionalFoods.add(food);
                            System.out.println("Item successfully added");
                        } else {
                            System.out.println("Food with ID " + productIdInt + " not found. Skipping.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid product ID: " + productId + ". Skipping.");
                    }

                }
                if(!additionalFoods.isEmpty()) {
                    existingOrder.getProducts().addAll(additionalFoods);
                }
            }

            System.out.print("Enter new Coffees (comma-separated IDs), or press Enter to keep the existing Coffees: ");
            String coffeeInput = scanner.nextLine();

            List<Coffee> additionalCoffees;
            if (coffeeInput.isEmpty()) {
                System.out.println("No new coffees added");// Keep existing products
            } else {
                additionalCoffees = new ArrayList<>();
                String[] productIds = coffeeInput.split(",");
                for (String productId : productIds) {
                    try {
                        int productIdInt = Integer.parseInt(productId.trim());
                        Coffee coffee = coffeeShopController.getCoffeeById(productIdInt);
                        if (coffee != null) {
                            additionalCoffees.add(coffee);
                            System.out.println("Item successfully added");
                        } else {
                            System.out.println("Coffee with ID " + productIdInt + " not found. Skipping.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid product ID: " + productId + ". Skipping.");
                    }
                }
                if(!additionalCoffees.isEmpty()){
                    existingOrder.getProducts().addAll(additionalCoffees);
                }
            }


            System.out.print("Enter the IDs of Foods to remove (comma-separated), or press Enter to skip: ");
            String foodRemoveInput = scanner.nextLine();
            if (!foodRemoveInput.isEmpty()) {
                String[] foodIds = foodRemoveInput.split(",");
                for (String foodId : foodIds) {
                    try {
                        int foodIdInt = Integer.parseInt(foodId.trim());
                        boolean removed = coffeeShopController.removeFoodById(foodIdInt, existingOrder);
                        if (removed) {
                            System.out.println("Food with ID " + foodIdInt + " removed.");
                        } else {
                            System.out.println("Food with ID " + foodIdInt + " not found in order. Skipping.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Food ID: " + foodId + ". Skipping.");
                    }
                }
            }

            // Enter and Remove Coffees
            System.out.print("Enter the IDs of Coffees to remove (comma-separated), or press Enter to skip: ");
            String coffeeRemoveInput = scanner.nextLine();
            if (!coffeeRemoveInput.isEmpty()) {
                String[] coffeeIds = coffeeRemoveInput.split(",");
                for (String coffeeId : coffeeIds) {
                    try {
                        int coffeeIdInt = Integer.parseInt(coffeeId.trim());
                        boolean removed = coffeeShopController.removeCoffeeById(coffeeIdInt, existingOrder);
                        if (removed) {
                            System.out.println("Coffee with ID " + coffeeIdInt + " removed.");
                        } else {
                            System.out.println("Coffee with ID " + coffeeIdInt + " not found in order. Skipping.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Coffee ID: " + coffeeId + ". Skipping.");
                    }
                }
            }


            coffeeShopController.updateOrder(existingOrder, existingOrder.getClientID());
            System.out.println("Order updated successfully.");
            System.out.println(existingOrder);


        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input.");
        }


    }

    public void viewPoints() {
        try {
            coffeeShopController.viewPoints(id); // Attempt to fetch and display points
        } catch (NullPointerException e) {
            System.out.println("Error: Unable to retrieve points. Please check your account details.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void viewOrders() {
        try {
            List<Order> orders = coffeeShopController.getAllOrders(); // Fetch all orders
            boolean hasOrders = false;

            for (Order order : orders) {
                if (order.getClientID().equals(id)) {
                    System.out.println(order);
                    hasOrders = true;
                }
            }

            if (!hasOrders) {
                System.out.println("You have no orders.");
            }
        } catch (NullPointerException e) {
            System.out.println("Error: Unable to retrieve orders. Please check your data.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Allows the client to delete an existing order by entering the order ID.
     * The method retrieves the order from the coffee shop controller by ID
     * and deletes it. If the order is found, it will be deleted; otherwise,
     * an error message will be displayed.
     *
     * @param scanner The scanner object used to read input from the client.
     */
    private void deleteOrder(Scanner scanner){
        try {
            System.out.print("Enter the ID of the Order to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Order orderToDelete = coffeeShopController.getOrderById(id);

            if (orderToDelete != null) {
                coffeeShopController.deleteOrder(orderToDelete, orderToDelete.getClientID());  // Pass the Admin object to delete
                System.out.println("Order with ID " + id + " has been deleted.");
            } else {
                System.out.println("Order with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
        }
    }


}



