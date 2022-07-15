package cmpt213.assignment3.packagedeliveries.control;

import cmpt213.assignment3.packagedeliveries.model.Book;
import cmpt213.assignment3.packagedeliveries.model.Electronic;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.model.Perishable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class PackageFactory {

    /**
     * Factory function that takes user input and creates a package object depending on the type
     * @param type the type of the package
     * @param name the name of the package
     * @param notes the notes of the package
     * @param price the price of the package
     * @param weight the weight of the package
     * @param delivered the delivery status of the package
     * @param deliveryDate the delivery date of the package
     * @return an object Book, Perishable, or Electronic depending on the type
     */
    public Package getPackageType(String type, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        Scanner input = new Scanner(System.in);
        if(type.equals("book")) {
            String author;
            do {
                System.out.print("Enter the author of the " + type + ": ");
                author = input.nextLine();
            } while(author.isEmpty());

            return new Book(author, name, notes, price, weight, delivered, deliveryDate);
        } else if(type.equals("perishable")) {
            int year;
            int month;
            int day;
            int hour;
            int minute;

            do {
                System.out.print("Enter the year of the expiry date: ");
                year = input.nextInt();
                System.out.print("Enter the month of the expiry date (1-12): ");
                month = input.nextInt();
                System.out.print("Enter the day of the expiry date (1-28/29/30/31): ");
                day = input.nextInt();
            } while (!(checkValidDate(year,month,day)));

            do {
                System.out.print("Enter the hour of the expiry date (0-23): ");
                hour = input.nextInt();
                if(hour < 0 || hour > 23) {
                    System.out.println("Error: hour must be between 0-23.");
                }
            } while(!(hour >= 0 && hour <= 23));

            do {
                System.out.print("Enter the minute of the expiry date (0-59): ");
                minute = input.nextInt();
                if(minute < 0 || minute > 59) {
                    System.out.println("Error: minute must be between 0-59.");
                }
            } while(!(minute >= 0 && minute <= 59));
            LocalDate expiryDate = LocalDate.of(year, month, day);
            LocalDateTime expiry = expiryDate.atTime(hour,minute);

            return new Perishable(expiry, name, notes, price, weight, delivered, deliveryDate);
        } else if(type.equals("electronic")) {
            double ehf;
            do {
                System.out.print("Enter the environmental handling fee (in dollar): ");
                ehf = input.nextDouble();
                if( ehf < 0) {
                    System.out.println("Error: ehf must be greater than 0.");
                }
            } while(!(ehf >= 0));

            return new Electronic(ehf, name, notes, price, weight, delivered, deliveryDate);
        }
        return null;
    }

    /**
     * Reading a package's values and returning the corresponding subclass
     * @param author the author of the book
     * @param name the name of the book
     * @param notes the notes of the book
     * @param price the price of the book
     * @param weight the weight of the book
     * @param delivered the delivery status of the book
     * @param deliveryDate the delivery date of the book
     * @return a Book object
     */
    public Package readPackage(String author, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        return new Book(author, name, notes, price, weight, delivered, deliveryDate);
    }
    /**
     * Reading a package's values and returning the corresponding subclass
     * @param expiry the expiry date of the perishable
     * @param name the name of the book
     * @param notes the notes of the book
     * @param price the price of the book
     * @param weight the weight of the book
     * @param delivered the delivery status of the book
     * @param deliveryDate the delivery date of the book
     * @return a Perishable object
     */
    public Package readPackage(LocalDateTime expiry, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        return new Perishable(expiry, name, notes, price, weight, delivered, deliveryDate);
    }
    /**
     * Reading a package's values and returning the corresponding subclass
     * @param ehf the ehf of the electronic
     * @param name the name of the book
     * @param notes the notes of the book
     * @param price the price of the book
     * @param weight the weight of the book
     * @param delivered the delivery status of the book
     * @param deliveryDate the delivery date of the book
     * @return an Electronic object
     */
    public Package readPackage(Double ehf, String name, String notes, Double price, Double weight, Boolean delivered, LocalDateTime deliveryDate) {
        return new Electronic(ehf, name, notes, price, weight, delivered, deliveryDate);
    }

    /**
     * Function that checks if a given date is valid or not
     * Uses try-catch to find an exception error.
     * Handles exception by returning false so that the program can continue.
     * @param year the year to check validity
     * @param month the month to check validity
     * @param day the day to check validity
     * @return a boolean value. True if valid, False if invalid
     */
    private Boolean checkValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
        }
        catch (Exception e) {
            System.out.println("Error: this date does not exist.");
            return false;
        }
        return true;
    }
}
