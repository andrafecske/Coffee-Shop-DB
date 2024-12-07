package org.example.Presentation;

import org.example.Controller.CoffeeShopController;
import org.example.Utils.Role;
import org.example.model.Admin;

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
                    //manageClients(scanner);
                    break;

                case "3":
                    //manageFood(scanner);
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
                    //updateAdmin(scanner);
                    break;

                case "4":
                    //deleteAdmin(scanner);
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

}
