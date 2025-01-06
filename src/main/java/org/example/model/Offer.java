package org.example.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * The {@code Offer} class represents a special offer that includes
 * a list of products and has an associated point cost. Each offer
 * has a unique identifier, represented by {@code offerID}.
 */

@Entity
@Table(name = "offers")
public class Offer implements HasID {

    /**
     * The unique identifier for this offer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    public Integer offerID;

    /**
     * The list of products included in this offer.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "offer_products",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    /**
     * The point cost required to redeem this offer.
     */
    public int pointCost;

    private String name;
    /**
     * Constructs a new {@code Offer} with the specified ID, list of products, and point cost.
     *
     * @param products  the list of products included in the offer
     * @param pointCost the point cost required to redeem this offer
     */
    public Offer(List<Product> products, int pointCost, String name) {
        this.name = name;
        this.products = products;
        this.pointCost = pointCost;
    }

    public Offer() {
    }

    /**
     * Sets a new ID for this offer.
     *
     * @param offerID the new offer ID to set
     */
    public void setOfferID(Integer offerID) {
        this.offerID = offerID;
    }

    /**
     * Retrieves the list of products included in the offer.
     *
     * @return a list of {@code Product} objects in the offer
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets a new list of products for this offer.
     *
     * @param products the new list of products to include in the offer
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Retrieves the point cost required to redeem this offer.
     *
     * @return the point cost of the offer
     */
    public int getPointCost() {
        return pointCost;
    }

    /**
     * Sets a new point cost for this offer.
     *
     * @param pointCost the new point cost to set
     */
    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    /**
     * Returns a string representation of the offer, including its ID, products, and point cost.
     *
     * @return a string representation of the offer
     */
    //@Override
//    public String toString() {
//        StringBuilder productsString = new StringBuilder();
//        for (Product product : products) {
//            productsString.append(product.getName()).append(", ");
//        }
//        productsString.delete(productsString.length() - 1, productsString.length());
//
//        return "offerID=" + offerID +"\n"+ "products:"+ "\n" + productsString + "\n"+ "pointCost = " +pointCost + "\n";
//    }
    @Override
    public String toString() {
        StringBuilder productsString = new StringBuilder();

        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                productsString.append(product.getName()).append(", ");
            }

            // Remove trailing ", " safely
            if (productsString.length() >= 2) {
                productsString.setLength(productsString.length() - 2);
            }
        } else {
            productsString.append("No products available");
        }

        return "offer name: " + name + "\n" +
                "products: \n" + productsString + "\n" +
                "pointCost:" + pointCost + "\n";
    }


    public String clientView(){
        return "name=" + name +"\n" + "point cost=" + pointCost + "\n";
    }
    /**
     * Retrieves the unique identifier of this offer.
     *
     * @return the offer ID
     */
    @Override
    public Integer getId() {
        return offerID;
    }

    @Override
    public void setId(int andIncrement) {
        this.offerID = andIncrement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
