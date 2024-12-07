package org.example;

import org.example.Controller.CoffeeShopController;
import org.example.Presentation.AdminUI;
import org.example.Presentation.UI;
import org.example.Repository.DBRepo;
import org.example.Repository.InMemoryRepository;
import org.example.Utils.Role;
import org.example.model.Admin;
import org.example.service.CoffeeShopService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        //Admin person = new Admin(14,"Ion", Role.Manager);

        // opening a session
        Session session = factory.openSession();
        // Transaction is a java object used to give the
        // instructions to database
        Transaction tx=session.beginTransaction();
        // we use save to provide the object to push in
        // database table

        DBRepo<Admin> adminRepository = new DBRepo<>(factory, Admin.class);
        InMemoryRepository<Admin> adminInMemoryRepository= new InMemoryRepository<Admin>();

        CoffeeShopService coffeeShopService = new CoffeeShopService(adminRepository);
        CoffeeShopController coffeeShopController = new CoffeeShopController(coffeeShopService);

        Admin admin1 = new Admin(12, "iulia", Role.ClientManager);
        coffeeShopController.addAdmin(admin1);
        Scanner scanner = new Scanner(System.in);

        AdminUI adminUI = new AdminUI(coffeeShopController, scanner);

        UI mainUI = new UI(coffeeShopController, adminUI);
        mainUI.start();

        scanner.close();





        // commit is a transaction function used to push
        // some changes to database with reference to hql
        // query
        tx.commit();
        session.close();
        factory.close();
    }
}