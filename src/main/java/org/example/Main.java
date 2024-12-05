package org.example;

import org.example.Utils.Role;
import org.example.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        //Admin person = new Admin(14,"Ion", Role.Manager);

        // opening a session
        Session session = factory.openSession();
        // Transaction is a java object used to give the
        // instructions to database
        Transaction tx=session.beginTransaction();
        // we use save to provide the object to push in
        // database table
        Admin admin1 = new Admin();
        admin1.setName("admin1");
        session.saveOrUpdate(admin1);
        admin1.setRole(Role.ClientManager);
        session.saveOrUpdate(admin1);

        // commit is a transaction function used to push
        // some changes to database with reference to hql
        // query
        tx.commit();
        session.close();
    }
}