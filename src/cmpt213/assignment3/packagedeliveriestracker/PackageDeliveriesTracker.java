package cmpt213.assignment3.packagedeliveriestracker;

import cmpt213.assignment3.packagedeliveriestracker.model.Package;
import cmpt213.assignment3.packagedeliveriestracker.textui.PackageFactory;
import cmpt213.assignment3.packagedeliveriestracker.textui.TextMenu;
import cmpt213.assignment3.packagedeliveriestracker.view.PackageUI;
import com.google.gson.*;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class that is the main application of the Parcel Pilot program
 * Uses the text menu class
 * Stores packages into an array from json file
 * @author Joshua Phang
 */
public class PackageDeliveriesTracker {
    final private ArrayList<Package> packageArray = new ArrayList<>();
    private final static String FILE_PATH = "./list.json";

    /**
     * Main function that prints the display menu and receives user input
     * Based on user input, calls function to handle it
     * @param args the input arguments when program is run
     */
    public static void main(String[] args) {
        PackageDeliveriesTracker user = new PackageDeliveriesTracker();
        user.readJSONtoArray();
        final TextMenu my_menu = new TextMenu(user.packageArray);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PackageUI("package tracker");
            }
        });
//        my_menu.displayMenu();
    }

    /**
     * Function that loads json data to program upon starting.
     * Puts json elements into a json array, then takes each element of the array
     * and puts it into the local package array.
     * If list.json doesn't exist, program lets user know that no data was loaded,
     * and continues to the program.
     */
    private void readJSONtoArray() {
        try {
            JsonElement packageElement = JsonParser.parseReader(new FileReader(FILE_PATH));
            JsonArray jsonPackageArray = packageElement.getAsJsonArray();
            PackageFactory packageFactory = new PackageFactory();

            for(JsonElement i : jsonPackageArray) {
                JsonObject tempObject = i.getAsJsonObject();
                Package newPackage;

                String name = tempObject.get("name").getAsString();
                String notes = tempObject.get("notes").getAsString();
                Double price = tempObject.get("price").getAsDouble();
                Double weight = tempObject.get("weight").getAsDouble();
                Boolean delivered = tempObject.get("delivered").getAsBoolean();
                LocalDateTime deliveryDate = LocalDateTime.parse(tempObject.get("deliveryDate").getAsString());
                if(tempObject.get("type").getAsString().equals("book")) {
                    String author = tempObject.get("author").getAsString();
                    newPackage = packageFactory.readPackage(author, name, notes, price, weight, delivered, deliveryDate);
                } else if(tempObject.get("type").getAsString().equals("perishable")) {
                    LocalDateTime expiry = LocalDateTime.parse(tempObject.get("expiryDate").getAsString());
                    newPackage = packageFactory.readPackage(expiry, name, notes, price, weight, delivered, deliveryDate);
                } else if(tempObject.get("type").getAsString().equals("electronic")) {
                    Double ehf = tempObject.get("ehf").getAsDouble();
                    newPackage = packageFactory.readPackage(ehf, name, notes, price, weight, delivered, deliveryDate);
                }
                else {
                    newPackage = null;
                }

                packageArray.add(newPackage);
            }

        } catch (FileNotFoundException e) {
            System.out.println("list.json not found. No packages have been loaded.\n");
        }
    }
}
