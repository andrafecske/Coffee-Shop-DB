package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
import org.example.Exceptions.BusinessLogicException;
import org.example.Exceptions.EntityNotFoundException;
import org.example.Exceptions.ValidationException;
import org.example.Utils.Role;
import org.example.model.Admin;
import org.example.model.Client;
import org.hibernate.annotations.Check;

import java.util.Scanner;

public class UI {
    private final CoffeeShopController coffeeShopController;
    private final AdminUI adminUI;
    private final ClientUI clientUI;

    public UI(CoffeeShopController coffeeShopController, AdminUI adminUI, ClientUI clientUI) {
        this.coffeeShopController = coffeeShopController;
        this.adminUI = adminUI;
        this.clientUI = clientUI;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueSession = true;

        while (continueSession) {
            System.out.println("Coffee Shop Login System");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Client");
            System.out.println("3. Create account");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    loginAsAdmin(scanner);  // Attempt to log in
                    break;

                case "2":
                    loginAsClient(scanner);
                    break;
                case "3":
                    createClient(scanner);
                    break;
                case "4":
                    continueSession = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void loginAsAdmin(Scanner scanner) {
        try {
            System.out.println("Enter Admin Email: ");
            String email = scanner.nextLine();

            System.out.println("Enter Password: ");
            String password = scanner.nextLine();

            Admin admin = coffeeShopController.getAdminByEmail(email);

            if (admin != null && admin.getPassword().equals(password)) {
                System.out.println("Welcome, Admin " + admin.getName() + "!");
                if (admin.getRole().equals(Role.Manager))
                    adminUI.start();
                else if (admin.getRole().equals(Role.ProductManager))
                    adminUI.startFoodManager();
                else if (admin.getRole().equals(Role.ClientManager))
                    adminUI.startClientManager();

                System.out.println("You have been logged out.");
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage()); // Friendly error for missing admin
        } catch (NullPointerException e) {
            System.out.println("Invalid admin data found. Please contact support.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during admin login. Please try again.");
            e.printStackTrace();
        }
    }



    private void loginAsClient(Scanner scanner) {
        try {
            System.out.println("Enter Client Email: ");
            String email = scanner.nextLine();

            System.out.println("Enter Password: ");
            String password = scanner.nextLine();

            Client client = coffeeShopController.getClientByEmail(email);

            if (client != null && client.getPassword().equals(password)) {
                System.out.println("Welcome, Client " + client.getName() + "!");
                clientUI.start(client.getId()); // Run the Client-specific UI operations

                System.out.println("You have been logged out.");
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage()); // Friendly error for missing client
        } catch (NullPointerException e) {
            System.out.println("Invalid client data found. Please contact support.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during client login. Please try again.");
            e.printStackTrace();
        }
    }


    private void createClient(Scanner scanner) {
        try {
            System.out.println("Enter Client age: ");
            int age = Integer.parseInt(scanner.nextLine());  // Read and parse age

            System.out.println("Enter Client name: ");
            String name = scanner.nextLine();

            System.out.println("Enter Client email: ");
            String email = scanner.nextLine();

            System.out.println("Enter Client password: ");
            String password = scanner.nextLine();

            Client newClient = new Client(age, name, email, password);

            // Attempt to add the client
            try {
                coffeeShopController.addClient(newClient);
                System.out.println("Client created successfully.");
            } catch (ValidationException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (BusinessLogicException e) {
                System.out.println("Business Logic Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            // Handle case when the user doesn't enter a valid number for age
            System.out.println("Invalid age entered. Please enter a valid number.");
        }

        // After the operation (either success or failure), go back to the main menu
        System.out.println("Returning to the main menu...");
    }





}




