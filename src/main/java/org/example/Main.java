package org.example;

import org.example.Controller.CoffeeShopController;
import org.example.Presentation.AdminUI;
import org.example.Presentation.ClientUI;
import org.example.Presentation.UI;
import org.example.Repository.DBRepo;
import org.example.Repository.FileRepository;
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

import java.io.File;
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
        DBRepo<Client> clientDBRepo = new DBRepo<>(factory, Client.class);
        DBRepo<Food> foodDBRepo = new DBRepo<>(factory, Food.class);
        DBRepo<Coffee> coffeeDBRepo = new DBRepo<>(factory, Coffee.class);
        DBRepo<Order> orderDBRepo = new DBRepo<>(factory, Order.class);
        DBRepo<Offer> offerDBRepo = new DBRepo<>(factory, Offer.class);
        DBRepo<OfferOrder> offerOrderDBRepo = new DBRepo<>(factory, OfferOrder.class);

        InMemoryRepository<Admin> adminInMemoryRepository= new InMemoryRepository<>();
        InMemoryRepository<Client> clientInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Food> foodInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Coffee> coffeeInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Order> orderInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<Offer> offerInMemoryRepository = new InMemoryRepository<>();
        InMemoryRepository<OfferOrder> offerOrderInMemoryRepository = new InMemoryRepository<>();

        FileRepository<Admin> adminFileRepo = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Admins.txt");
        FileRepository<Client> clientFileRepo = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Clients.txt");
        FileRepository<Food> foodFileRepo = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Foods.txt");
        FileRepository<Coffee> coffeeFileRepo = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Coffees.txt");
        FileRepository<Order> orderFileRepository = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Orders.txt");
        FileRepository<Offer> offerFileRepository = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\Offers.txt");
        FileRepository<OfferOrder> offerOrderFileRepository = new FileRepository<>("C:\\Coding\\facultate\\MAP\\proiect MAP BD\\MAP-BD\\src\\main\\java\\org\\example\\inFileRepo\\OfferOrders.txt");


        //CoffeeShopService coffeeShopService2 = new CoffeeShopService(adminInMemoryRepository, clientInMemoryRepository, foodInMemoryRepository, coffeeInMemoryRepository, orderInMemoryRepository, offerInMemoryRepository, offerOrderInMemoryRepository);




        //CoffeeShopService coffeeShopService = new CoffeeShopService(adminRepository, clientDBRepo, foodDBRepo, coffeeDBRepo, orderDBRepo, offerDBRepo, offerOrderDBRepo);
        //CoffeeShopController coffeeShopController = new CoffeeShopController(coffeeShopService);

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                 Choose repository type:
                 1.file repository
                 2.database repository
                 3.in memory repository
                """);

        String option = scanner.nextLine();
        switch (option) {
            case "1":
                CoffeeShopService coffeeShopServiceFile = new CoffeeShopService(adminFileRepo, clientFileRepo, foodFileRepo, coffeeFileRepo, orderFileRepository, offerFileRepository, offerOrderFileRepository);
                CoffeeShopController coffeeShopControllerFile = new CoffeeShopController(coffeeShopServiceFile);
                AdminUI adminUI = new AdminUI(coffeeShopControllerFile, scanner);
                ClientUI clientUI = new ClientUI(coffeeShopControllerFile, scanner);
                UI mainUI = new UI(coffeeShopControllerFile, adminUI, clientUI);
                Admin admin3 = new Admin(32, "Vanessa", Role.Manager, "vane@gmail.com", "yuhu");


                mainUI.start();
                break;
            case "2":
                CoffeeShopService coffeeShopServiceDB = new CoffeeShopService(adminRepository, clientDBRepo, foodDBRepo, coffeeDBRepo, orderDBRepo, offerDBRepo, offerOrderDBRepo);
                CoffeeShopController coffeeShopControllerDB = new CoffeeShopController(coffeeShopServiceDB);
                AdminUI adminUIDB = new AdminUI(coffeeShopControllerDB, scanner);
                ClientUI clientUIDB = new ClientUI(coffeeShopControllerDB, scanner);
                UI mainUIDB = new UI(coffeeShopControllerDB, adminUIDB, clientUIDB);
                mainUIDB.start();
                break;
            case "3":
                CoffeeShopService coffeeShopService2 = new CoffeeShopService(adminInMemoryRepository, clientInMemoryRepository, foodInMemoryRepository, coffeeInMemoryRepository, orderInMemoryRepository, offerInMemoryRepository, offerOrderInMemoryRepository);
                CoffeeShopController coffeeShopController2 = new CoffeeShopController(coffeeShopService2);
                AdminUI adminUI2 = new AdminUI(coffeeShopController2, scanner);
                ClientUI clientUI2 = new ClientUI(coffeeShopController2, scanner);
                UI mainUI2 = new UI(coffeeShopController2, adminUI2, clientUI2);

                //initializing repo
                Admin admin1 = new Admin(32, "Manuela", Role.Manager, "ma@gmail.com", "yuhu");
                Client client = new Client(20, "Mirabela", "mira@gmail.com", "1234");
                Client client1 = new Client(34, "Iulia", "i@gmail.com", "1234");
                Food food = new Food(20, 30, "strawberry cupcake", FoodType.MEAL);
                Food food1 = new Food(25, 40, "blueberry muffin", FoodType.DESSERT);
                Food food2 = new Food(35, 50, "chicken sandwich", FoodType.MEAL);
                Food food3 = new Food(15, 25, "cheese crisps", FoodType.SNACK);
                Food food4 = new Food(20, 35, "chocolate brownie", FoodType.DESSERT);
                Food food5 = new Food(30, 45, "turkey wrap", FoodType.MEAL);


                Coffee coffee = new Coffee(18, 38, "chai latte", false, MilkType.ALMOND);
                Coffee coffee1 = new Coffee(20, 40, "mocha", true, MilkType.WHOLE);
                Coffee coffee2 = new Coffee(22, 42, "caramel macchiato", true, MilkType.SOY);
                Coffee coffee3 = new Coffee(19, 39, "espresso", false, MilkType.NONE);
                Coffee coffee4 = new Coffee(21, 41, "vanilla latte", true, MilkType.OAT);
                Coffee coffee5 = new Coffee(23, 43, "americano", true, MilkType.NONE);



                coffeeShopController2.addAdmin(admin1);
                coffeeShopController2.addClient(client);
                coffeeShopController2.addClient(client1);
                coffeeShopController2.addFood(food);
                coffeeShopController2.addFood(food1);
                coffeeShopController2.addFood(food2);
                coffeeShopController2.addFood(food3);
                coffeeShopController2.addFood(food4);
                coffeeShopController2.addCoffee(coffee);
                coffeeShopController2.addCoffee(coffee1);
                coffeeShopController2.addCoffee(coffee2);
                coffeeShopController2.addCoffee(coffee3);
                coffeeShopController2.addCoffee(coffee4);
                coffeeShopController2.addCoffee(coffee5);

                mainUI2.start();
                break;

        }


        scanner.close();



        // commit is a transaction function used to push
        // some changes to database with reference to hql
        // query
        tx.commit();
        session.close();
        factory.close();
    }


}