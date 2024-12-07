package org.example.model;
import jakarta.persistence.*;
import org.example.Utils.FoodType;

import java.util.List;

/**
 * The {@code Food} class represents a food product, inheriting from the {@code Product} class.
 * It includes additional properties specific to food items, such as the food type.
 */
@Entity
public class Food extends Product {

    /**
     * The type of food, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private FoodType FoodType;



    /**
     * Constructs a new {@code Food} product with the specified ID, price, points, name, and food type.
     * @param price     the price of the food product
     * @param points    the reward points associated with the food product
     * @param name      the name of the food product
     * @param foodType  the type of food (e.g., appetizer, main course, dessert)
     */
    public Food( int price, int points, String name, org.example.Utils.FoodType foodType) {
        super(price, points, name);
        this.FoodType = foodType;
    }

    public Food() {
        super();
    }

    /**
     * Retrieves the type of food.
     *
     * @return the food type as an {@code Enum}
     */
    public FoodType getFoodType() {
        return FoodType;
    }

    /**
     * Sets a new food type for this food item.
     *
     * @param foodType the new food type to set
     */
    public void setFoodType(FoodType foodType) {
        this.FoodType = foodType;
    }

    /**
     * Returns a string representation of the food product, including its ID, points, price, name, and food type.
     *
     * @return a string representation of the food product
     */
    @Override
    public String toString() {
        return
                "ID=" + getProductID()+
                        ", points=" + points +
                        ", price=" + price +
                        ", name='" + name + '\'' +
                        ", FoodType=" + FoodType +
                        '\n';
    }

}
