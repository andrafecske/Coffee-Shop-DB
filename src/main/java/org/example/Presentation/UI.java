package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
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
            System.out.println("1. Login as Admin or Client");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    login(scanner);  // Attempt to log in
                    break;

                case "2":
                    continueSession = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void login(Scanner scanner) {
        try {
            System.out.println("Enter Name:");
            String name = scanner.nextLine();

            System.out.println("Enter ID:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());  // Parse ID to integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a numeric ID.");
                return;
            }

            // Check for Admin login
            Admin admin = coffeeShopController.getAdminById(id);
            if (admin != null && admin.getName().equalsIgnoreCase(name)) {
                System.out.println("Welcome, Admin " + name + "!");
                if (admin.getRole().equals(Role.Manager))
                    adminUI.start();  // Run the Admin-specific UI operations
                else if (admin.getRole().equals(Role.ProductManager))
                    adminUI.startFoodManager();
                else if (admin.getRole().equals(Role.ClientManager))
                    adminUI.startClientManager();

                System.out.println("You have been logged out.");
                return;
            }

            //check client login
            Client client = coffeeShopController.getClientById(id);
            if (client != null && client.getName().equalsIgnoreCase(name)) {
                System.out.println("Welcome, Client " + name + "!");
                clientUI.start(id);  // Run the Client-specific UI operations

                System.out.println("You have been logged out.");
                return;
            }

            System.out.println("Login failed. No admin or client found with the provided name and ID.");
        } catch (Exception e) {
            System.out.println("An error occurred during login. Please try again.");
            e.printStackTrace();
        }
    }
}




