package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
import org.example.Utils.FoodType;
import org.example.Utils.MilkType;
import org.example.Utils.Role;
import org.example.model.Admin;
import org.example.model.Client;
import org.example.model.Coffee;
import org.example.model.Food;

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
                    //viewOrders();
                    break;

                case "5":
                    //manageOffers(scanner);
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

    private void addAdmin(Scanner scanner) {
        try {

            System.out.print("Enter Admin Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Admin Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Admin Role (Manager/Client Manager/Product manager): ");
            String roleInput = scanner.nextLine();
            Role role;

            // Check if input matches a valid role
            if ("Manager".equalsIgnoreCase(roleInput)) {
                role = Role.Manager;
            } else if ("ProductManager".equalsIgnoreCase(roleInput)) {
                role = Role.ProductManager;
            } else {
                role = Role.ClientManager;
            }

            // Create a new Admin and add it using the controller
            Admin admin = new Admin(age, name, role);
            controller.addAdmin(admin);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers for ID and age.");
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
            int id = Integer.parseInt(scanner.nextLine());

            // Retrieve the existing Admin
            Admin existingAdmin = controller.getAdminById(id);
            if (existingAdmin == null) {
                System.out.println("Admin with ID " + id + " not found.");
                return;
            }

            // Prompt for Age
            System.out.print("Enter new Age (or press Enter to keep " + existingAdmin.getAge() + "): ");
            String ageInput = scanner.nextLine();
            int age = ageInput.isEmpty() ? existingAdmin.getAge() : Integer.parseInt(ageInput);

            // Prompt for Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingAdmin.getName() + "'): ");
            String nameInput = scanner.nextLine();
            String name = nameInput.isEmpty() ? existingAdmin.getName() : nameInput;

            // Prompt for Role
            System.out.print("Enter new Role (or press Enter to keep " + existingAdmin.getRole() + "): ");
            String roleInput = scanner.nextLine();
            Role role = giveRole(roleInput, existingAdmin.getRole());

            // Update the Admin
            Admin updatedAdmin = new Admin(age, name, role);
            updatedAdmin.setId(id); // Ensure the ID remains the same
            controller.updateAdmin(updatedAdmin);
            System.out.println("Admin updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for age.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role input. Please enter a valid role.");
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
            int id = Integer.parseInt(scanner.nextLine());

            Admin adminToDelete = controller.getAdminById(id);

            if (adminToDelete != null) {
                controller.deleteAdmin(adminToDelete);  // Pass the Admin object to delete
                System.out.println("Admin with ID " + id + " has been deleted.");
            } else {
                System.out.println("Admin with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
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
            System.out.println("Enter Client ID:");
            int id = Integer.parseInt(scanner.nextLine()); // Validate numeric input for ID

            System.out.println("Enter Age:");
            int age = Integer.parseInt(scanner.nextLine()); // Validate numeric input for Age

            System.out.println("Enter Name:");
            String name = scanner.nextLine(); // Read Name input

            Client client = new Client(age, name); // Create Client object
            controller.addClient(client); // Add client using the controller

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric value for ID and Age.");
        } catch (NullPointerException e) {
            System.out.println("Error: Missing data. Please ensure all fields are filled out.");
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
            System.out.print("Enter the ID of the Client to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Retrieve the existing Client
            Client existingClient = controller.getClientById(id);
            if (existingClient == null) {
                System.out.println("Client with ID " + id + " not found.");
                return;
            }

            // Prompt for Age
            System.out.print("Enter new Age (or press Enter to keep " + existingClient.getAge() + "): ");
            String ageInput = scanner.nextLine();
            int age = ageInput.isEmpty() ? existingClient.getAge() : Integer.parseInt(ageInput);

            // Prompt for Name
            System.out.print("Enter new Name (or press Enter to keep '" + existingClient.getName() + "'): ");
            String nameInput = scanner.nextLine();
            String name = nameInput.isEmpty() ? existingClient.getName() : nameInput;

            // Update the Client
            Client updatedClient = new Client(age, name);
            updatedClient.setId(id); // Retain the same ID
            controller.updateClient(updatedClient);
            System.out.println("Client updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for age.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
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
            System.out.print("Enter the ID of the Client to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Client clientToDelete = controller.getClientById(id);

            if (clientToDelete != null) {
                controller.deleteClient(clientToDelete);  // Pass the Admin object to delete
                System.out.println("Client with ID " + id + " has been deleted.");
            } else {
                System.out.println("Client with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
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
            System.out.println("Enter Food Name:");
            String name = scanner.nextLine(); // Get Food Name

            System.out.println("Enter Food Points:");
            int points = Integer.parseInt(scanner.nextLine()); // Parse Points

            System.out.println("Enter Food price:");
            int price = Integer.parseInt(scanner.nextLine()); // Parse Price

            System.out.print("Enter FoodType (SNACK/SANDWICH/DESSERT/MEAL): ");
            String typeInput = scanner.nextLine();
            FoodType type;

            // Parse the food type
            if ("SNACK".equalsIgnoreCase(typeInput)) {
                type = FoodType.SNACK;
            } else if ("SANDWICH".equalsIgnoreCase(typeInput)) {
                type = FoodType.SANDWICH;
            } else if ("DESSERT".equalsIgnoreCase(typeInput)) {
                type = FoodType.DESSERT;
            } else if ("MEAL".equalsIgnoreCase(typeInput)) {
                type = FoodType.MEAL;
            } else {
                System.out.println("Invalid input. Please enter a valid food type.");
                return; // Exit the method if the food type is invalid
            }

            // Create a Food object and pass it to the controller
            Food food = new Food(price, points, name, type);
            System.out.println("Saving food: " + food);

            controller.addFood(food); // Call controller method to add food
            System.out.println("Food added successfully");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric value for ID, Points, and Price.");
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
            int id = Integer.parseInt(scanner.nextLine());

            Food foodToDelete = controller.getFoodById(id);

            if (foodToDelete != null) {
                controller.deleteFood(foodToDelete);  // Pass the Admin object to delete
                System.out.println("Food with ID " + id + " has been deleted.");
            } else {
                System.out.println("Food with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
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
            System.out.println("Enter Coffee ID:");
            int id = Integer.parseInt(scanner.nextLine()); // Parse Coffee ID

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
    private void deleteCoffee(Scanner scanner){
        try {
            System.out.print("Enter the ID of the Coffee to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Coffee coffeeToDelete = controller.getCoffeeById(id);

            if (coffeeToDelete != null) {
                controller.deleteCoffee(coffeeToDelete);  // Pass the Admin object to delete
                System.out.println("Coffee with ID " + id + " has been deleted.");
            } else {
                System.out.println("Coffee with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number for ID.");
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








}
