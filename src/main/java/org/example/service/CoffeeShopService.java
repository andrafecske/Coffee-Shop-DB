package org.example.service;

import org.example.Repository.IRepository;
import org.example.model.Admin;

import java.util.List;

public class CoffeeShopService {
    private final IRepository<Admin> adminRepo;

    public CoffeeShopService(IRepository<Admin> adminRepo) {
        this.adminRepo = adminRepo;
    }

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
}
