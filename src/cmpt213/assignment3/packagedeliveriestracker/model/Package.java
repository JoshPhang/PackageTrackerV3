package cmpt213.assignment3.packagedeliveriestracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class storing the info of a package
 * @author Joshua Phang
 */
public class Package {
    final protected String name;
    final protected String notes;
    final protected Double price;
    final protected Double weight;
    protected Boolean delivered;
    final protected LocalDateTime deliveryDate;


    /**
     * Constructor for PackageInfo class
     * @param name the name of the package
     * @param notes the notes of the package (optional)
     * @param price the price of the package ($)
     * @param weight the weight of the package (kg)
     * @param delivered the delivery status of the package
     * @param deliveryDate expected package delivery date
     */
    public Package(String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.delivered = delivered;
        this.deliveryDate = deliveryDate;
    }

    /**
     * Overloaded function that puts the package info into a readable text format
     * @return the string value of package info
     */
    @Override
    public String toString() {
        return("Package: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Price: $" + price + "\n" +
                "Weight: " + weight + "kg\n" +
                "Expected Delivery Date: " + deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    /**
     * Getter function to get expected package delivery date
     * @return the package delivery date
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Getter function to get the name of the package
     * @return the package name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter function to get delivery status of the package
     * @return the delivery status
     */
    public Boolean getDelivered() {
        return delivered;
    }

    /**
     * Setter function to mark package as delivered
     */
    public void markDelivered() {
        delivered = true;
    }

    public void setDelivered(Boolean delivered) { this.delivered = delivered; }

    /**
     * Function to return "yes" or "no" depending on true or false
     * @param delivered the boolean value to be converted
     * @return a string: "yes" if true, "no" if false
     */
    protected String deliveredString(boolean delivered) {
        if(delivered) {
            return "Yes";
        }
        else {
            return "No";
        }
    }

}
