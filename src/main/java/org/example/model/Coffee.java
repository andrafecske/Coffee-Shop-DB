package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.example.Utils.MilkType;
import org.example.model.Product;

/**
 * The {@code Coffee} class represents a coffee product, inheriting from the {@code Product} class.
 * It includes additional properties specific to coffee, such as caffeine content and milk type.
 */
@Entity
@Table(name = "coffees")
public class Coffee extends Product {

    /**
     * Indicates whether the coffee contains caffeine.
     */
    private Boolean hasCaffeine;

    /**
     * The type of milk used in the coffee.
     */
    @Enumerated(EnumType.STRING)
    private org.example.Utils.MilkType MilkType;

    /**
     * Constructs a new {@code Coffee} product with the specified ID, price, points, name,
     * caffeine content, and milk type.
     *
     * @param productID   the unique identifier for the coffee product
     * @param price       the price of the coffee product
     * @param points      the reward points associated with the coffee product
     * @param name        the name of the coffee product
     * @param hasCaffeine indicates if the coffee contains caffeine
     * @param milkType    the type of milk used in the coffee
     */
    public Coffee(Integer productID, int price, int points, String name, boolean hasCaffeine, MilkType milkType) {
        super(price, points, name);
        this.hasCaffeine = hasCaffeine;
        this.MilkType = (org.example.Utils.MilkType) milkType;
    }

    public Coffee() {
        super();
    }

    /**
     * Retrieves the type of milk used in the coffee.
     *
     * @return the milk type as an {@code Enum}
     */
    public MilkType getMilkType() {
        return MilkType;
    }

    /**
     * Sets a new milk type for the coffee.
     *
     * @param milkType the new milk type to set
     */
    public void setMilkType(MilkType milkType) {
        this.MilkType = (org.example.Utils.MilkType) milkType;
    }

    /**
     * Checks if the coffee contains caffeine.
     *
     * @return {@code true} if the coffee contains caffeine, {@code false} otherwise
     */
    public Boolean getHasCaffeine() {
        return hasCaffeine;
    }

    /**
     * Sets the caffeine content of the coffee.
     *
     * @param hasCaffeine {@code true} if the coffee should contain caffeine, {@code false} otherwise
     */
    public void setHasCaffeine(Boolean hasCaffeine) {
        this.hasCaffeine = hasCaffeine;
    }

    /**
     * Returns a string representation of the coffee product, including caffeine content, milk type,
     * and inherited attributes such as ID, points, name, and price.
     *
     * @return a string representation of the coffee product
     */
    @Override
    public String toString() {
        return
                "hasCaffeine=" + hasCaffeine +
                        ", MilkType=" + MilkType +
                        ", ID=" + getProductID() +
                        ", points=" + points +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        '\n';
    }
}
