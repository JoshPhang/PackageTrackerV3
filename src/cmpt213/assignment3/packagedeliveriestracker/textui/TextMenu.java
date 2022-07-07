package cmpt213.assignment3.packagedeliveriestracker.textui;

import cmpt213.assignment3.packagedeliveriestracker.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveriestracker.model.Book;
import cmpt213.assignment3.packagedeliveriestracker.model.Electronic;
import cmpt213.assignment3.packagedeliveriestracker.model.Package;
import cmpt213.assignment3.packagedeliveriestracker.model.Perishable;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A class that displays the Parcel Pilot menu
 * @author Joshua Phang
 */
public class TextMenu {
    final private int INDEX_OFFSET = 1;
    private final static String FILE_PATH = "./list.json";
    final private String[] MENU_OPTIONS = {"List all packages", "Add a package", "Remove a package", "List overdue packages", "List upcoming packages", "Mark package as delivered", "Exit"};
    final private LocalDateTime currDate = LocalDateTime.now();
    final private ArrayList<Package> packageArray;

    /**
     * Constructor that takes in an array of packages to use in the program
     * @param packageArray the array holding a list of packages
     */
    public TextMenu(ArrayList<Package> packageArray) {
        this.packageArray = packageArray;
    }

    /**
     * Prints out the menu title surrounded by "#"s
     * Prints out options that the user can select from
     */
    public void displayMenu() {
        final String MENU_BORDER = "#";
        final String MENU_TITLE = "Parcel Pilot";
        final int TITLE_BORDER = 4;
        final int INDEX_OFFSET = 1;

        while(true) {
            for(int i = 0; i < (MENU_TITLE.length() + TITLE_BORDER); i++) {
                System.out.print(MENU_BORDER);
            }
            System.out.println();
            System.out.println(MENU_BORDER + " " + MENU_TITLE + " " + MENU_BORDER);
            for(int i = 0; i < (MENU_TITLE.length() + TITLE_BORDER); i++) {
                System.out.print(MENU_BORDER);
            }
            System.out.println();
            System.out.println("Today is: " + currDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            for(int i = 0; i < MENU_OPTIONS.length; i++) {
                System.out.println((i + INDEX_OFFSET) + ": " + MENU_OPTIONS[i]);
            }

            switch(readInput()) {
                case 1:
                    //option 1: List all packages
                    listPackages();
                    break;
                case 2:
                    //option 2: Add a package
                    addPackage();
                    break;
                case 3:
                    //option 3: Remove a package
                    removePackage();
                    break;
                case 4:
                    //option 4: List overdue packages
                    listOverduePackages();
                    break;
                case 5:
                    //option 5: List upcoming packages
                    listUpcomingPackages();
                    break;
                case 6:
                    //option 6: Mark packages as delivered
                    markPackageDelivered();
                    break;
                case 7:
                    //option 7: Exit
                    updateFile();
                    System.out.println("\nThanks for using Parcel Pilot! Fly on by again soon!");
                    return;
            }
        }
    }

    /**
     * Asks the user to choose an option
     * Reads in the user's entry
     * @return the user's option choice
     */
    private int readInput() {
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Choose an option by entering 1-7: ");
            int option = input.nextInt();
            if(option < 1 || option > 7) {
                System.out.println("Invalid Selection. Enter a number between 1 and 7");
            }
            else {
                return option;
            }
        }
    }


    /**
     * Option 1.
     * Lists all packages in stored array in descending order (the oldest delivery date first)
     * Displays all package information
     */
    private void listPackages() {
        sortPackageList();
        if(packageArray.isEmpty()) {
            System.out.println("\nNo packages to show.\n");
        }
        else {
            printPackages(packageArray);
        }
    }

    /**
     * Option 2.
     * Function to add a new package to the list of packages
     * Updates package array when all input fields are satisfied
     */
    private void addPackage() {
        String packageType;
        LocalDate packageDate;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        String name;
        String notes;
        double price;
        double weight;

        PackageFactory packageFactory = new PackageFactory();

        Scanner input = new Scanner(System.in);

        boolean validType;
        do {
            System.out.print("Enter the type of the package (book, perishable, electronic): ");
            packageType = input.nextLine().toLowerCase();
            validType = (packageType.equals("book") || packageType.equals("perishable") || packageType.equals("electronic"));
            if(!validType) {
                System.out.println("Error: invalid package type.");
            }
        } while(!validType);

        do {
            System.out.print("Enter the name of the " + packageType + ": ");
            name = input.nextLine();
        } while (name.isEmpty());

        System.out.print("Enter the notes of the " + packageType + ": ");
        notes = input.nextLine();

        do {
            System.out.print("Enter the price of the " + packageType + " (in dollar): ");
            price = input.nextDouble();
            if(price < 0) {
                System.out.println("Error: price cannot be negative.");
            }
        } while(!(price >= 0));

        do {
            System.out.print("Enter the weight of the " + packageType + " (in kg): ");
            weight = input.nextDouble();
            if(weight < 0) {
                System.out.println("Error: weight cannot be negative.");
            }
        } while(!(weight >= 0));

        do {
            System.out.print("Enter the year of the expected delivery date: ");
            year = input.nextInt();
            do {
                System.out.print("Enter the month of the expected delivery date (1-12): ");
                month = input.nextInt();
            } while(!(month >= 1 && month <= 12));

            do {
                System.out.print("Enter the day of the expected delivery date (1-28/29/30/31): ");
                day = input.nextInt();
            } while(!(day >= 1 && day <= 31));
        } while (!(checkValidDate(year,month,day)));

        do {
            System.out.print("Enter the hour of the expected delivery date (0-23): ");
            hour = input.nextInt();
            if(hour < 0 || hour > 23) {
                System.out.println("Error: hour must be between 0-23.");
            }
        } while(!(hour >= 0 && hour <= 23));

        do {
            System.out.print("Enter the minute of the expected delivery date (0-59): ");
            minute = input.nextInt();
            if(minute < 0 || minute > 59) {
                System.out.println("Error: minute must be between 0-59.");
            }
        } while(!(minute >= 0 && minute <= 59));

        packageDate = LocalDate.of(year, month, day);
        LocalDateTime packageDateTime = packageDate.atTime(hour,minute);

        Package newPackage = packageFactory.getPackageType(packageType, name, notes, price, weight, false, packageDateTime);
        packageArray.add(newPackage);

        System.out.println(name + " has been added to the list.\n");
    }

    /**
     * Option 3.
     * Function to remove a package from the current list of packages
     */
    private void removePackage() {
        Scanner input = new Scanner(System.in);

        listPackages();
        if(packageArray.size() == 0) {
            return;
        }
        while(true) {
            System.out.print("Enter the item number you want to remove (0 to cancel): ");
            int item = input.nextInt();

            if(item < 0 || item > packageArray.size()) {
                System.out.println("Invalid item number. Please try again.");
            }
            else if(item == 0){
                return;
            }
            else {
                String tempName = packageArray.get(item - INDEX_OFFSET).getName();
                packageArray.remove(item - INDEX_OFFSET);
                System.out.println(tempName + " has been removed from the list.\n");
                return;
            }
        }
    }

    /**
     * Option 4.
     * Function that prints the list of overdue packages in descending order (the oldest first)
     * First creates a temporary array and then prints it to screen
     */
    private void listOverduePackages() {
        sortPackageList();
        ArrayList<Package> overdueArray = new ArrayList<>();
        for(Package i : packageArray) {
            if(i.getDeliveryDate().isBefore(LocalDateTime.now()) && !i.getDelivered()) {
                overdueArray.add(i);
            }
        }
        if(overdueArray.size() == 0) {
            System.out.println("\nNo overdue packages to show.\n");
            return;
        }
        printPackages(overdueArray);
    }

    /**
     * Option 5.
     * Function that prints the list of upcoming packages in descending order (the oldest first)
     * First creates a temporary array and then prints it to screen
     */
    private void listUpcomingPackages() {
        sortPackageList();
        ArrayList<Package> upcomingArray = new ArrayList<>();
        for(Package i : packageArray) {
            if(i.getDeliveryDate().isAfter(LocalDateTime.now()) && !i.getDelivered()) {
                upcomingArray.add(i);
            }
        }
        if(upcomingArray.size() == 0) {
            System.out.println("\nNo upcoming packages to show.\n");
            return;
        }
        printPackages(upcomingArray);
    }

    /**
     * Option 6.
     * Function that changes a package's delivery status to delivered
     * First prints array of all undelivered packages, then takes user selection to mark package as delivered
     */
    private void markPackageDelivered() {
        sortPackageList();
        ArrayList<Package> undeliveredArray = new ArrayList<>();
        for(Package i : packageArray) {
            if(!i.getDelivered()) {
                undeliveredArray.add(i);
            }
        }
        if(undeliveredArray.size() == 0) {
            System.out.println("\nNo undelivered packages to show.\n");
            return;
        }
        printPackages(undeliveredArray);

        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Enter the item number you want to mark (0 to cancel): ");
            int item = input.nextInt();

            if (item < 0 || item > undeliveredArray.size()) {
                System.out.println("Invalid item number. Please try again.");
            } else if (item == 0) {
                return;
            }
            else {
                undeliveredArray.get(item - INDEX_OFFSET).markDelivered();
                System.out.println(undeliveredArray.get(item - INDEX_OFFSET).getName() + " has been delivered.\n");
                return;
            }
        }
    }

    /**
     * Function that sorts the list of packages in descending order.
     * Utilizes bubble sort algorithm to sort packages
     */
    private void sortPackageList() {
        //bubble sort
        for(int i = 0; i < packageArray.size() - 1; i++) {
            for(int j = 0; j < (packageArray.size() - i - 1); j++) {
                if(packageArray.get(j).getDeliveryDate().isAfter(packageArray.get(j+1).getDeliveryDate())) {
                    Collections.swap(packageArray,j,j+1);
                }
            }
        }
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

    /**
     * Function that utilizes gson to update list.json when program ends
     * GsonBuilder code taken from Assignment1_Description.pdf
     * If list.json doesn't exist upon exiting the program, it will create it.
     */
    private void updateFile() {
        RuntimeTypeAdapterFactory<Package> packageAdapterFactory = RuntimeTypeAdapterFactory.of(Package.class)
                .registerSubtype(Book.class, "book")
                .registerSubtype(Perishable.class, "perishable")
                .registerSubtype(Electronic.class, "electronic");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        new TypeAdapter<LocalDateTime>() {
                            @Override
                            public void write(JsonWriter jsonWriter,
                                              LocalDateTime localDateTime) throws IOException {
                                jsonWriter.value(localDateTime.toString());
                            }
                            @Override
                            public LocalDateTime read(JsonReader jsonReader) throws IOException {
                                return LocalDateTime.parse(jsonReader.nextString());
                            }
                        })
                .registerTypeAdapterFactory(packageAdapterFactory)
                .setPrettyPrinting()
                .create();

        try {
            FileWriter file = new FileWriter(FILE_PATH);
            file.append("[");
            for(int index = 0; index < packageArray.size(); index++) {
                file.append(gson.toJson(packageArray.get(index), Package.class));
                if (index + 1 != packageArray.size()) {
                    file.append(",");
                }
            }
            file.append("]");
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper function that prints all elements of an ArrayList of packages
     * @param packageList the list that holds the package information
     */
    private void printPackages(ArrayList<Package> packageList) {
        System.out.println();
        int counter = 1;
        for(Package i : packageList) {
            System.out.println("Package #" + counter);
            System.out.println(i.toString() + '\n');
            counter++;
        }
    }
}
