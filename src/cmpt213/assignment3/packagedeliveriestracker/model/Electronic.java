package cmpt213.assignment3.packagedeliveriestracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Electronic extends Package {
    final private double ehf;

    /**
     * Constructor for Book class
     * @param ehf the ehf of the electronic
     * @param name the inherited name of the Package
     * @param notes the inherited notes of the Package
     * @param price the inherited price of the Package
     * @param weight the inherited weight of the Package
     * @param delivered the inherited delivery status of the Package
     * @param deliveryDate the inherited delivery date of the Package
     */
    public Electronic(double ehf, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        super(name, notes, price, weight, delivered, deliveryDate);
        this.ehf = ehf;
    }

    /**
     * Overloaded function that puts the package info into a readable text format
     * @return the string value of package info
     */
    @Override
    public String toString() {
        return("Package Type: Electronic\n" +
                "Package: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Price: $" + price + "\n" +
                "Weight: " + weight + "kg\n" +
                "Expected Delivery Date: " + deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                "Environmental Handling Fee: $" + ehf);
    }
}
