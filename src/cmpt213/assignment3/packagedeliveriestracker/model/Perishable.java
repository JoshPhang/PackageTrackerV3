package cmpt213.assignment3.packagedeliveriestracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Perishable extends Package {
    final private LocalDateTime expiryDate;

    /**
     * Constructor for Book class
     * @param expiryDate the expiry date of the perishable
     * @param name the inherited name of the Package
     * @param notes the inherited notes of the Package
     * @param price the inherited price of the Package
     * @param weight the inherited weight of the Package
     * @param delivered the inherited delivery status of the Package
     * @param deliveryDate the inherited delivery date of the Package
     */
    public Perishable(LocalDateTime expiryDate, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        super(name, notes, price, weight, delivered, deliveryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * Overloaded function that puts the package info into a readable text format
     * @return the string value of package info
     */
    @Override
    public String toString() {
        return("Package Type: Perishable\n" +
                "Package: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Price: $" + price + "\n" +
                "Weight: " + weight + "kg\n" +
                "Expected Delivery Date: " + deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                "Delivered? " + deliveredString(delivered) + "\n" +
                "Expiry Date: " + expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
