package org.example.Controller;

import org.example.Utils.Role;
import org.example.model.Admin;
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
        coffeeShopService.addAdmin(admin);
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
}
