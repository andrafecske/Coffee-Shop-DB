package org.example;

import org.example.Controller.CoffeeShopController;
import org.example.Presentation.AdminUI;
import org.example.Presentation.ClientUI;
import org.example.Presentation.UI;
import org.example.Repository.DBRepo;
import org.example.Repository.InMemoryRepository;
import org.example.Utils.FoodType;
import org.example.Utils.MilkType;
import org.example.Utils.Role;
import org.example.model.*;
import org.example.service.CoffeeShopService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
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
        DBRepo<Client> clientDBRepo = new DBRepo<>(factory, Client.class);
        DBRepo<Food> foodDBRepo = new DBRepo<>(factory, Food.class);
        DBRepo<Coffee> coffeeDBRepo = new DBRepo<>(factory, Coffee.class);
        DBRepo<Order> orderDBRepo = new DBRepo<>(factory, Order.class);
        DBRepo<Offer> offerDBRepo = new DBRepo<>(factory, Offer.class);
        DBRepo<OfferOrder> offerOrderDBRepo = new DBRepo<>(factory, OfferOrder.class);

        CoffeeShopService coffeeShopService = new CoffeeShopService(adminRepository, clientDBRepo, foodDBRepo, coffeeDBRepo, orderDBRepo, offerDBRepo, offerOrderDBRepo);
        CoffeeShopController coffeeShopController = new CoffeeShopController(coffeeShopService);

        Scanner scanner = new Scanner(System.in);

        AdminUI adminUI = new AdminUI(coffeeShopController, scanner);
        ClientUI clientUI = new ClientUI(coffeeShopController, scanner);

        UI mainUI = new UI(coffeeShopController, adminUI, clientUI);
        mainUI.start();


//       Client client = new Client(23, "tanti");
//       coffeeShopController.addClient(client);

//        Food food = new Food(40, 57, "Burger", FoodType.MEAL);
//        coffeeShopController.addFood(food);
//
//        Food food1 = new Food(30, 60, "Velvet Cake", FoodType.DESSERT);
//        coffeeShopController.addFood(food1);
//
//        Coffee coffee = new Coffee(20, 25, "Matcha", false, MilkType.OAT);
//        coffeeShopController.addCoffee(coffee);


        //Admin admin = new Admin(30, "Alexandra", Role.Manager);
      //  coffeeShopController.addAdmin(admin);




        scanner.close();









        // commit is a transaction function used to push
        // some changes to database with reference to hql
        // query
        tx.commit();
        session.close();
        factory.close();
    }
}