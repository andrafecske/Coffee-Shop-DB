package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
import org.example.Exceptions.BusinessLogicException;
import org.example.Exceptions.ValidationException;
import org.example.Utils.FoodType;
import org.example.Utils.MilkType;
import org.example.Utils.Role;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final CoffeeShopController controller;
    private final Scanner scanner;

    public AdminUI(CoffeeShopController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void start() {
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("""
                     Coffee Shop Management System
                     
                     1. Admin Management
                     2. Client Management
                     3. Food Management
                     4. View all orders ever
                     5. Offer Management
                     6. Exit
                     """);
            System.out.print("Choose a category: ");
            String mainOption = scanner.nextLine();

            switch (mainOption) {
                case "1":
                    manageAdmins(scanner);
                    break;

                case "2":
                    manageClients(scanner);
                    break;

                case "3":
                    manageFood(scanner);
                    break;

                case "4":
                    viewOrders();
                    break;

                case "5":
                    manageOffers(scanner);
                    break;

                case "6":
                    continueLoop = false;
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


    public void startFoodManager(){
        boolean continueLoop = true;

        while(continueLoop){
            System.out.println("""
                     Coffee Shop Management System
                     1. Food Management
                     2. View all orders ever
                     3. Exit
                     """);
            System.out.print("Choose a category: ");
            String mainOption = scanner.nextLine();
            switch (mainOption) {
                case "1":
                    manageFood(scanner);
                    break;
                case "2":
                    viewOrders();
                    break;
                case "3":
                    continueLoop = false;
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }

    public void startClientManager() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("""
                    Coffee Shop Management System
                    1. Client management
                    2. View all orders ever
                    3. Exit
                    """);
            System.out.print("Choose a category: ");
            String mainOption = scanner.nextLine();
            switch (mainOption) {
                case "1":
                    manageClients(scanner);
                    break;
                case "2":
                    viewOrders();
                    break;
                case "3":
                    continueLoop = false;
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


    //admin management operations

    private void manageAdmins(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.println("""
                    Admin Management
                    
                    1. View all admins
                    2. Add admin
                    3. Update admin
                    4. Delete admin
                    5. Sort admins by name
                    6. View admins by role
                    7. Back to main menu
                    """);
            System.out.print("Choose an option: ");
            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "1":
                    List<Admin> allAdmins = controller.listAllAdmins();
                    for (Admin admin : allAdmins) {
                        System.out.println(admin);
                    }
                    break;

                case "2":
                    addAdmin(scanner);
                    break;

                case "3":
                    updateAdmin(scanner);
                    break;

                case "4":
                    deleteAdmin(scanner);
                    break;

                case "5":
                    System.out.println("Admins sorted by name:" + "\n");
                    List<Admin> admins = controller.sortAdminsByName();
                    for (Admin admin : admins) {
                        System.out.println(admin);
                    }
                    break;

                case "6":
                    System.out.println("Enter role to filter by(Manager/FoodManager/ProductManager):");
                    Role role = Role.valueOf(scanner.nextLine());
                    List<Admin> filteredAdminsByRole = controller.filterAdminsByRole(role);
                    System.out.println("Filtered admins by role:");
                    for (Admin admin : filteredAdminsByRole) {
                        System.out.println(admin);
                    }
                    break;

                case "7":
                    adminLoop = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }

    // Client Management Operations
    private void manageClients(Scanner scanner) {
        boolean clientLoop = true;
        while (clientLoop) {
            System.out.println("""
                     Client Management
                     
                     1. View all clients
                     2. Add client
                     3. Update client
                     4. Delete client
                     5. Back to main menu
                     """);
            System.out.print("Choose an option: ");
            String clientOption = scanner.nextLine();

            switch (clientOption) {
                case "1":
                    controller.listAllClients();
                    break;

                case "2":
                    addClient(scanner);
                    break;

                case "3":
                    updateClient(scanner);
                    break;

                case "4":
                    deleteClient(scanner);
                    break;

                case "5":
                    clientLoop = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    // Food Management Operations
    private void manageFood(Scanner scanner) {
        boolean foodLoop = true;
        while (foodLoop) {
            System.out.println("""
                     Food Management
                     
                     1. View all food items
                     2. Add food item
                     3. Update food item
                     4. Delete food item
                     5. View all coffee items
                     6. Add coffee item
                     7. Update coffee item
                     8. Delete coffee item
                     9. Back to main menu
                     """);
            System.out.print("Choose an option: ");
            String foodOption = scanner.nextLine();

            switch (foodOption) {
                case "1":
                    controller.listAllFoods();
                    break;

                case "2":
                    addFood(scanner);
                    break;

                case "3":
                    updateFood(scanner);
                    break;

                case "4":
                    deleteFood(scanner);
                    break;

                case "5":
                    controller.listAllCoffees();
                    break;
                case "6":
                    addCoffee(scanner);
                    break;
                case "7":
                    updateCoffee(scanner);
                    break;
                case "8":
                    deleteCoffee(scanner);
                    break;

                case "9":
                    foodLoop = false;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void manageOffers(Scanner scanner) {
        boolean offerLoop = true;
        while (offerLoop) {
            System.out.println("""
                    Offer Management
                    
                    1. View all offers
                    2. Add offer
                    3. Delete offer
                    4. Back to main menu""");

            System.out.print("Choose an option: ");
            String offerOption = scanner.nextLine();

            switch (offerOption) {
                case "1":
                    viewOffers();
                    break;

                case "2":
                    List<Integer> foods = offerFood(scanner);
                    System.out.println(foods);
                    List<Integer> coffees = offerCoffee(scanner);
                    System.out.println(coffees);
                    System.out.println("How many points does this offer cost? ");
                    int points = Integer.parseInt(scanner.nextLine());
                    System.out.println("What is the offer's name?");
                    String name = scanner.nextLine();
                    Offer offer = controller.addOffer(foods, coffees, points, name);
                    System.out.println("Offer added successfully!" + "\n" + offer + "Offer ID:" + offer.getId());
                    break;

                case "3":
                    System.out.println("Current active offers: ");
                    List<Offer> offers = controller.getAllOffers();
                    for (Offer off : offers) {
                        System.out.println(off);
                        System.out.println(off.offerID + "id of the offer from above");
                    }
                    //viewOffers();
                    deleteOffer(scanner);
                    break;

                case "4":
                    offerLoop = false;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }


    private void addAdmin(Scanner scanner) {
        try {
            System.out.print("Enter Admin Age: ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) {
                System.out.println("Age cannot be empty.");
                return;
            }
            int age = Integer.parseInt(ageInput);

            if (age < 0) {
                System.out.println("Age cannot be negative.");
                return;
            }

            System.out.print("Enter Admin Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            if (name.matches(".*\\d.*"))
            {
                System.out.println("Name cannot contain digits.");
                return;
            }

            System.out.print("Enter Admin Role (Manager/ProductManager/ClientManager): ");
            String roleInput = scanner.nextLine().trim();
            if (roleInput.isEmpty()) {
                System.out.println("Role cannot be empty.");
                return;
            }

            Role role;
            if ("Manager".equalsIgnoreCase(roleInput)) {
                role = Role.Manager;
            } else if ("ProductManager".equalsIgnoreCase(roleInput)) {
                role = Role.ProductManager;
            } else if ("ClientManager".equalsIgnoreCase(roleInput)) {
                role = Role.ClientManager;
            } else {
                System.out.println("Invalid role. Please enter one of: Manager, ProductManager, ClientManager.");
                return;
            }

            // Create a new Admin and add it using the controller
            Admin admin = new Admin(age, name, role);
            controller.addAdmin(admin);
          //  System.out.println("Admin added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for age.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Adds a new admin to the system by prompting the user for necessary details such as ID, age, name, and role.
     * Validates inputs and catches any format issues with ID and age entries.
     *
     * @param scanner The Scanner used for user input.
     */
    private void updateAdmin(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the Admin to update: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("Admin ID cannot be empty.");
                return;
            }
            int id = Integer.parseInt(idInput);

            // Retrieve the existing Admin
            Admin existingAdmin = controller.getAdminById(id);
            if (existingAdmin == null) {
                System.out.println("Admin with ID " + id + " not found.");
                return;
            }

            // Prompt for Age
            System.out.print("Enter new Age (or press Enter to keep " + existingAdmin.getAge() + "): ");
            String ageInput = scanner.nextLine().trim();
            int age;
            if (ageInput.isEmpty()) {
                age = existingAdmin.getAge();
            } else {
                age = Integer.parseInt(ageInput);
                if (age <= 0) {
                    System.out.println("Age must be greater than 0.");
                    return;
                }
            }

            // Prompt for Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingAdmin.getName() + "'): ");
            String nameInput = scanner.nextLine().trim();
            String name = nameInput.isEmpty() ? existingAdmin.getName() : nameInput;

            // Prompt for Role
            System.out.print("Enter new Role (Manager/ProductManager/ClientManager) (or press Enter to keep "
                    + existingAdmin.getRole() + "): ");
            String roleInput = scanner.nextLine().trim();
            Role role;
            if (roleInput.isEmpty()) {
                role = existingAdmin.getRole();
            } else {
                role = giveRole(roleInput, existingAdmin.getRole());
            }

            // Update the Admin
            Admin updatedAdmin = new Admin(age, name, role);
            updatedAdmin.setId(id); // Ensure the ID remains the same
            controller.updateAdmin(updatedAdmin);
            System.out.println("Admin updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for ID or age.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role input. Please enter a valid role (Manager, ProductManager, ClientManager).");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    private Role giveRole(String roleInput, Role currentRole) {
        if (roleInput.isEmpty()) {
            return currentRole; // Keep the existing role
        }

        switch (roleInput.toLowerCase()) {
            case "manager":
                return Role.Manager;
            case "productmanager":
                return Role.ProductManager;
            case "clientmanager":
                return Role.ClientManager;
            default:
                throw new IllegalArgumentException("Invalid role: " + roleInput);
        }
    }

    private void deleteAdmin(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the Admin to delete: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("Admin ID cannot be empty.");
                return;
            }

            int id = Integer.parseInt(idInput);

            // Retrieve the Admin object to delete
            Admin adminToDelete = controller.getAdminById(id);

            if (adminToDelete != null) {
                System.out.println("Are you sure you want to delete Admin: " + adminToDelete.getName() + " (ID: " + id + ")? (yes/no)");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if ("yes".equals(confirmation)) {
                    controller.deleteAdmin(adminToDelete); // Pass the Admin object to delete
                    System.out.println("Admin with ID " + id + " has been deleted.");
                } else {
                    System.out.println("Deletion canceled.");
                }
            } else {
                System.out.println("Admin with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for the ID.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Adds a new client to the system by prompting the user for the client's ID, age, and name.
     * After gathering the required information, it creates a new Client object and adds it to the system.
     *
     * @param scanner The Scanner used for user input.
     */

    private void addClient(Scanner scanner) {
        try {
            // Prompt and validate Client ID
//            System.out.print("Enter Client ID: ");
//            String idInput = scanner.nextLine().trim();
//            if (idInput.isEmpty()) {
//                System.out.println("Client ID cannot be empty.");
//                return;
//            }
//            int id = Integer.parseInt(idInput);

            // Prompt and validate Age
            System.out.print("Enter Age: ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) {
                System.out.println("Age cannot be empty.");
                return;
            }
            int age = Integer.parseInt(ageInput);
            if (age <= 0) {
                System.out.println("Age must be a positive number.");
                return;
            }

            // Prompt and validate Name
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }
            if (name.matches(".*\\d.*"))
            {
                System.out.println("Name cannot contain digits.");
                return;
            }

            // Create and add the client
            Client client = new Client(age, name); // Create Client object
            controller.addClient(client);         // Add client using the controller

            // Display success message
            System.out.println("Client added successfully! Client's ID: " + client.getId());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric value for ID and Age.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Updates the details of an existing client by prompting the user for the client's ID, age, and name.
     * Allows the user to retain the current values by pressing Enter without typing new information.
     * If the client ID is invalid, an appropriate message is displayed.
     *
     * @param scanner The Scanner used for user input.
     */
    private void updateClient(Scanner scanner) {
        try {
            // Prompt for Client ID
            System.out.print("Enter the ID of the Client to update: ");
            String idInput = scanner.nextLine().trim();
//            if (idInput.isEmpty()) {
//                System.out.println("Client ID cannot be empty.");
//                return;
//            }
            int id = Integer.parseInt(idInput);

            // Retrieve the existing Client
            Client existingClient = controller.getClientById(id);
//            if (existingClient == null) {
//                System.out.println("Client with ID " + id + " not found.");
//                return;
//            }

            // Prompt for new Age
            System.out.print("Enter new Age (or press Enter to keep " + existingClient.getAge() + "): ");
            String ageInput = scanner.nextLine().trim();
            int age;
            if (ageInput.isEmpty()) {
                age = existingClient.getAge();
            } else {
                age = Integer.parseInt(ageInput);
                if (age <= 0) {
                    System.out.println("Age must be a positive number.");
                    return;
                }
            }

            // Prompt for new Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingClient.getName() + "'): ");
            String nameInput = scanner.nextLine().trim();
            String name = nameInput.isEmpty() ? existingClient.getName() : nameInput;

            // Update the Client
            Client updatedClient = new Client(age, name);
            updatedClient.setId(id); // Retain the same ID
            controller.updateClient(updatedClient);

            // Display success message
            System.out.println("Client updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for age or ID.");
        } catch (NullPointerException e) {
            System.out.println("Error: Missing client details. Please ensure all fields are valid.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * Deletes an existing client from the system by prompting the user for the client's ID.
     * If the client is found, it is removed from the system; otherwise, an appropriate message is displayed.
     * Handles invalid ID input gracefully.
     *
     * @param scanner The Scanner used for user input.
     */
    private void deleteClient(Scanner scanner) {
        try {
            // Prompt for Client ID
            System.out.print("Enter the ID of the Client to delete: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("Client ID cannot be empty.");
                return;
            }

            int id = Integer.parseInt(idInput);

            // Retrieve the Client to delete
            Client clientToDelete = controller.getClientById(id);

            if (clientToDelete != null) {
                // Confirm deletion
                System.out.print("Are you sure you want to delete Client with ID " + id + " (yes/no)? ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("yes")) {
                    controller.deleteClient(clientToDelete);  // Pass the Client object to delete
                    System.out.println("Client with ID " + id + " has been deleted.");
                } else {
                    System.out.println("Deletion canceled.");
                }
            } else {
                System.out.println("Client with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numeric ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }




    //food management
    /**
     * Adds a new food item to the system by prompting the user for the food's ID, name, points, price, and type.
     * Validates the food type input and creates a new Food object, which is then added to the system.
     *
     * @param scanner The Scanner used for user input.
     */

    private void addFood(Scanner scanner) {
        try {
            // Prompt for Food Name
            System.out.println("Enter Food Name:");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Food name cannot be empty.");
                return;
            }

            // Prompt for Food Points
            System.out.println("Enter Food Points:");
            String pointsInput = scanner.nextLine().trim();
            if (pointsInput.isEmpty()) {
                System.out.println("Food points cannot be empty.");
                return;
            }
            int points = Integer.parseInt(pointsInput);

            // Prompt for Food Price
            System.out.println("Enter Food Price:");
            String priceInput = scanner.nextLine().trim();
            if (priceInput.isEmpty()) {
                System.out.println("Food price cannot be empty.");
                return;
            }
            int price = Integer.parseInt(priceInput);

            // Prompt for Food Type
            System.out.print("Enter FoodType (SNACK/SANDWICH/DESSERT/MEAL): ");
            String typeInput = scanner.nextLine().trim().toUpperCase();
            if (typeInput.isEmpty()) {
                System.out.println("Food type cannot be empty.");
                return;
            }

            FoodType type;
            try {
                type = FoodType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter one of the following: SNACK, SANDWICH, DESSERT, MEAL.");
                return;
            }

            // Create Food object and add it using the controller
            Food food = new Food(price, points, name, type);
            System.out.println("Saving food: " + food);

            controller.addFood(food); // Add food via controller
            System.out.println("Food added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid numeric values for Points and Price.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    /**
     * Deletes an existing food item from the system by prompting the user for the food's ID.
     * If the food item is found, it is removed from the system; otherwise, an appropriate message is displayed.
     * Handles invalid ID input gracefully.
     *
     * @param scanner The Scanner used for user input.
     */
    private void deleteFood(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the Food to delete: ");
            String idInput = scanner.nextLine().trim();

            // Validate input for empty string
            if (idInput.isEmpty()) {
                System.out.println("Food ID cannot be empty.");
                return;
            }

            // Parse ID and validate numeric input
            int id = Integer.parseInt(idInput);

            // Retrieve the food item to delete
            Food foodToDelete = controller.getFoodById(id);
            if (foodToDelete != null) {
                controller.deleteFood(foodToDelete); // Delete food item
                System.out.println("Food with ID " + id + " has been deleted.");
            } else {
                System.out.println("Food with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void updateFood(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the food to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Food existingFood = controller.getFoodById(id);
            if (existingFood == null) {
                System.out.println("Food with ID " + id + " not found.");
                return;
            }

            // Prompt for Age
            System.out.print("Enter new price(or press Enter to keep " + existingFood.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            int price;
            if (priceInput.isEmpty()) {
                price = existingFood.getPrice(); // Keep the existing age
            } else {
                price = Integer.parseInt(priceInput); // Use the new age
            }

            System.out.print("Enter new number of points(or press Enter to keep " + existingFood.getPoints() + ")points: ");
            String pointsInput = scanner.nextLine();
            int points;
            if (pointsInput.isEmpty()) {
                points = existingFood.getPoints(); // Keep the existing age
            } else {
                points = Integer.parseInt(pointsInput); // Use the new age
            }

            // Prompt for Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingFood.getName() + "'): ");
            String nameInput = scanner.nextLine();
            String name;
            if (nameInput.isEmpty()) {
                name = existingFood.getName(); // Keep the existing name
            } else {
                name = nameInput; // Use the new name
            }

            System.out.print("Enter FoodType (SNACK/SANDWICH/DESSERT/MEAL) (or press Enter to keep '" + existingFood.getFoodType() + "'): ");
            String typeInput = scanner.nextLine();
            FoodType type;
            try {
                type = FoodType.valueOf(typeInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid food type (SNACK, SANDWICH, DESSERT, MEAL).");
                return;
            }



            // Update the Admin
            Food updatedFood = new Food(price, points,name, type);
            controller.updateFood(updatedFood);
            System.out.println("Food updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for price or points.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //coffee management
    private void addCoffee(Scanner scanner) {
        try {
            System.out.println("Enter price:");
            int price = Integer.parseInt(scanner.nextLine()); // Parse price

            System.out.print("Enter Coffee points:");
            int points = Integer.parseInt(scanner.nextLine()); // Parse points

            System.out.print("Enter Coffee name:");
            String name = scanner.nextLine(); // Get name

            System.out.print("Enter Milk Type (WHOLE/SKIM/SOY/ALMOND/OAT/NONE): ");
            String milkTypeInput = scanner.nextLine();
            MilkType milkType;

            // Parse the milk type
            if ("WHOLE".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.WHOLE;
            } else if ("SKIM".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.SKIM;
            } else if ("SOY".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.SOY;
            } else if ("ALMOND".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.ALMOND;
            } else if ("OAT".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.OAT;
            } else if ("NONE".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.NONE;
            } else {
                System.out.println("Invalid input. Please enter a valid milk type.");
                return;// Exit the method if milk type is invalid
            }

            System.out.println("Does it contain caffeine?");
            boolean containsCaffeine = Boolean.parseBoolean(scanner.nextLine()); // Parse caffeine info

            // Create Coffee object and pass to controller
            Coffee coffee = new Coffee(price, points, name, containsCaffeine, milkType);
            controller.addCoffee(coffee); // Call controller method to add coffee

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid numeric values for ID, price, and points.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid milk type input! Please enter one of the following: WHOLE, SKIM, SOY, ALMOND, OAT, NONE.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing coffee item from the system by prompting the user for the coffee's ID.
     * If the coffee item is found, it is removed from the system; otherwise, an appropriate message is displayed.
     * Handles invalid ID input gracefully.
     *
     * @param scanner The Scanner used for user input.
     */
    private void deleteCoffee(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the Coffee to delete: ");
            String idInput = scanner.nextLine().trim();

            // Validate input for empty string
            if (idInput.isEmpty()) {
                System.out.println("Coffee ID cannot be empty.");
                return;
            }

            // Parse ID and validate numeric input
            int id = Integer.parseInt(idInput);

            // Retrieve the coffee item to delete
            Coffee coffeeToDelete = controller.getCoffeeById(id);
            if (coffeeToDelete != null) {
                controller.deleteCoffee(coffeeToDelete); // Delete coffee item
                System.out.println("Coffee with ID " + id + " has been deleted.");
            } else {
                System.out.println("Coffee with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void updateCoffee(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the coffee to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Retrieve the existing Admin
            Coffee existingCoffee = controller.getCoffeeById(id);
            if (existingCoffee == null) {
                System.out.println("Coffee with ID " + id + " not found.");
                return;
            }


            System.out.print("Enter new price(or press Enter to keep " + existingCoffee.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            int price;
            if (priceInput.isEmpty()) {
                price = existingCoffee.getPrice(); // Keep the existing age
            } else {
                price = Integer.parseInt(priceInput); // Use the new age
            }

            System.out.print("Enter new number of points(or press Enter to keep " + existingCoffee.getPoints() + ")points: ");
            String pointsInput = scanner.nextLine();
            int points;
            if (pointsInput.isEmpty()) {
                points = existingCoffee.getPoints(); // Keep the existing age
            } else {
                points = Integer.parseInt(pointsInput); // Use the new age
            }

            // Prompt for Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingCoffee.getName() + "'): ");
            String nameInput = scanner.nextLine();
            String name;
            if (nameInput.isEmpty()) {
                name = existingCoffee.getName(); // Keep the existing name
            } else {
                name = nameInput; // Use the new name
            }

            System.out.print("Does it have caffeine? true/false (or press Enter to keep '" + existingCoffee.getHasCaffeine() + "'): ");
            String hasCaffeine = scanner.nextLine();
            boolean containsCaffeine = Boolean.parseBoolean(hasCaffeine);

            System.out.print("Enter new MilkType (or press Enter to keep '" + existingCoffee.getMilkType() + "'): ");
            String milkTypeInput = scanner.nextLine();
            MilkType milkType = (MilkType) existingCoffee.getMilkType();
            if ("WHOLE".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.WHOLE;
            } else if ("SKIM".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.SKIM;
            } else if ("SOY".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.SOY;
            } else if ("ALMOND".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.ALMOND;
            } else if ("OAT".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.OAT;
            } else if ("NONE".equalsIgnoreCase(milkTypeInput)) {
                milkType = MilkType.NONE;
            } else {
                System.out.println("Invalid input. Please enter a valid milk type.");
                return;
            }


            Coffee updatedCoffee = new Coffee(id,price,points,name,containsCaffeine,milkType);
            controller.updateCoffee(updatedCoffee);
            System.out.println("Coffee updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for price or points.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void viewOrders(){
        controller.viewOrders();
    }

    public void viewOffers(){
        controller.listAllOffers();
    }



    public List<Integer> offerFood(Scanner scanner) {
        List<Integer> foods = new ArrayList<>();
        while(true){
            controller.listAllFoods();
            System.out.println("What food would you like to add to the offer? Enter the ID or press enter if you would like to stop adding");
            String id = scanner.nextLine();

            // Break if the user presses enter without input
            if(id.isEmpty()) {
                break;
            }
            try {
                // Try to parse the entered ID to an integer
                Integer intId = Integer.parseInt(id);

                // Check if the food exists in the list
                if(controller.getFoodById(intId) != null){
                    foods.add(intId);
                } else {
                    System.out.println("Invalid ID");
                }
            } catch (NumberFormatException e) {
                // Handle case where the entered ID is not a valid integer
                System.out.println("Invalid input. Please enter a valid numeric food ID.");
            }
        }
        return foods;
    }

    public List<Integer> offerCoffee(Scanner scanner) {
        List<Integer> coffees = new ArrayList<>();
        while(true){
            controller.listAllCoffees();
            System.out.println("What coffee would you like to add to the offer? Enter the ID or press enter if you would like to stop adding");
            String id = scanner.nextLine();

            // Break the loop if the user presses enter without entering an ID
            if(id.isEmpty()) {
                break;
            }

            try {
                // Try to parse the entered ID to an integer
                Integer intId = Integer.parseInt(id);

                // Check if the coffee exists in the list
                if(controller.getCoffeeById(intId) != null){
                    coffees.add(intId);
                } else {
                    System.out.println("Invalid ID");
                }
            } catch (NumberFormatException e) {
                // Handle case where the entered ID is not a valid integer
                System.out.println("Invalid input. Please enter a valid numeric coffee ID.");
            }
        }
        return coffees;
    }

    /**
     * Deletes an offer based on the offer ID provided by the user.
     * Prompts the user to enter the ID of the offer to delete. If the offer exists,
     * it is deleted. If the offer ID does not exist, an error message is displayed.
     *
     * @param scanner The Scanner object used to read user input.
     */
    private void deleteOffer(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the Offer to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Offer offerToDelete = controller.getOfferById(id);

            if (offerToDelete != null) {
                controller.deleteOffer(offerToDelete);  // Pass the Admin object to delete
                System.out.println("Offer with ID " + id + " has been deleted.");
            } else {
                System.out.println("Offer with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
        }
    }





}
