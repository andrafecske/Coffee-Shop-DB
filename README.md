# ☕ Coffee Shop Loyalty Program

## 📝 Overview

This project is a **Coffee Shop Loyalty Program** designed to manage customer loyalty and streamline coffee shop operations. It allows **administrators** to manage clients, products, and offers while enabling **clients** to earn and redeem points through purchases of food and coffee. 

## ✨ Main Features

### **Client Functionality**
- **View Menu:** Browse available food and coffee items.
- **Order Management:** Create, update, and delete orders for food and drinks.
- **Points System:** Earn points based on purchases, view accumulated points, and claim offers.
- **Offer Redemption:** Redeem points for exclusive coffee shop offers.
- **Order Filtering and Sorting:** 
  - Sort orders by price.
  - Filter food items by type (e.g., SNACK, SANDWICH, DESSERT, MEAL).

### **Admin Functionality**
- **User Management:** Add and manage administrators and clients.
- **Product Management:** Handle food and coffee inventory.
- **Order Oversight:** View all client orders placed in the system.
- **Offer Management:** Create and manage offers redeemable by clients.
- **Role-Based Access:** Different admin roles like Manager, Product Manager, and Client Manager have specific permissions.

## 🔧 Functional Details

### **Points System**
- Points accumulate based on the total value of client purchases.
- Clients can use accumulated points to claim offers.

### **Product Customization**
- **Coffee Items:** Attributes include caffeine content, milk type.
- **Food Items:** Attributes include type.

### **Order Management**
- Clients can add, update, and delete orders.
- Points are calculated and added to the client’s loyalty card after each order.

## 🛠️ Complex Methods

### **Admin Login and Role-Based Navigation**
- **Method:** `loginAsAdmin(scanner)`
  - Handles admin authentication using email and password.
  - Routes admins to specific management areas based on their roles:
    - **Manager:** Full access to client, product, and offer management.
    - **Product Manager:** Restricted to managing food and coffee items.
    - **Client Manager:** Restricted to managing client accounts.

### **Offer Redemption**
- **Method:** `addOfferOrder(offerID, id)`
  - Verifies client eligibility for offers based on accumulated points.
  - Deducts the required points and records the redemption.

### **Filtering and Sorting**
- **Methods:**
  - `filterFoodsByType(type)`: Returns food items matching a specified type.
  - `sortClientOrdersByPrice(id)`: Sorts a client's orders based on total price.

## 📋 Usage

### **System Navigation**
1. **Login System:**
   - Login as Admin or Client.
   - Create a new account if you’re a client.
2. **Admin Dashboard:**
   - Manage clients, food, offers, and view all orders.
3. **Client Dashboard:**
   - Explore menu, place orders, manage points, and claim offers.

### **Sample Interaction**
- A client orders a customized coffee and a sandwich.
- Points are calculated based on the order value and added to the client’s loyalty card.
- Once enough points are accumulated, the client redeems them for a free dessert.

## 🧑‍💻 Technologies Used
- **Java:** Core language for system implementation.
- **Scanner:** Used for console-based interaction.
- **Object-Oriented Design:** Enables scalability and modularity of the system.

## 🎯 Future Enhancements
- Integration with a GUI for improved user experience.

<img width="369" alt="image" src="https://github.com/user-attachments/assets/fe2bdb27-c814-4c31-8ba1-c638c11b5892" />



