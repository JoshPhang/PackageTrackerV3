package cmpt213.assignment3.packagedeliveriestracker.view;

import cmpt213.assignment3.packagedeliveriestracker.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveriestracker.model.Book;
import cmpt213.assignment3.packagedeliveriestracker.model.Electronic;
import cmpt213.assignment3.packagedeliveriestracker.model.Package;
import cmpt213.assignment3.packagedeliveriestracker.model.Perishable;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class PackageUI {
    String title;
    JFrame appFrame;
    JButton allButton;
    JButton overdueButton;
    JButton upcomingButton;
    JButton addPackageButton;
    JPanel selectionPane;
    JPanel displayPane = new JPanel();
    JScrollPane scrollPane;
    ArrayList<Package> packageArray;
    private final static String FILE_PATH = "./list.json";

    public PackageUI (String title, ArrayList<Package> packageArray) {
        this.packageArray = packageArray;
        this.title = title;
        appFrame = new JFrame(title);
        appFrame.setSize(600, 600);
        appFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        appFrame.addWindowListener(new WindowListener() {
                                       @Override
                                       public void windowOpened(WindowEvent e) {
                                           //blank
                                       }

                                       @Override
                                       public void windowClosing(WindowEvent e) {
                                           updateFile();
                                           System.exit(1);
                                       }

                                       @Override
                                       public void windowClosed(WindowEvent e) {
                                           //blank
                                       }

                                       @Override
                                       public void windowIconified(WindowEvent e) {
                                           //blank
                                       }

                                       @Override
                                       public void windowDeiconified(WindowEvent e) {
                                           //blank
                                       }

                                       @Override
                                       public void windowActivated(WindowEvent e) {
                                           //blank
                                       }

                                       @Override
                                       public void windowDeactivated(WindowEvent e) {
                                           //blank
                                       }
                                   });

                displayMainMenu();
    }

    private void displayMainMenu() {
        selectionPane = new JPanel();

        allButton = new JButton("All");
        allButton.setBackground(Color.LIGHT_GRAY);
        allButton.setForeground(Color.BLACK);
        allButton.addActionListener(e -> {
            allButton.setBackground(Color.DARK_GRAY);
            allButton.setForeground(Color.WHITE);
            overdueButton.setBackground(Color.LIGHT_GRAY);
            overdueButton.setForeground(Color.BLACK);
            upcomingButton.setBackground(Color.LIGHT_GRAY);
            upcomingButton.setForeground(Color.BLACK);

            allPackagesUI();
        });
        selectionPane.add(allButton);

        overdueButton = new JButton("Overdue");
        overdueButton.setBackground(Color.LIGHT_GRAY);
        overdueButton.setForeground(Color.BLACK);
        overdueButton.addActionListener(e -> {
            overdueButton.setBackground(Color.DARK_GRAY);
            overdueButton.setForeground(Color.WHITE);
            allButton.setBackground(Color.LIGHT_GRAY);
            allButton.setForeground(Color.BLACK);
            upcomingButton.setBackground(Color.LIGHT_GRAY);
            upcomingButton.setForeground(Color.BLACK);

            overduePackagesUI();
        });
        selectionPane.add(overdueButton);

        upcomingButton = new JButton("Upcoming");
        upcomingButton.setBackground(Color.LIGHT_GRAY);
        upcomingButton.setForeground(Color.BLACK);
        upcomingButton.addActionListener(e -> {
            upcomingButton.setBackground(Color.DARK_GRAY);
            upcomingButton.setForeground(Color.WHITE);
            allButton.setBackground(Color.LIGHT_GRAY);
            allButton.setForeground(Color.BLACK);
            overdueButton.setBackground(Color.LIGHT_GRAY);
            overdueButton.setForeground(Color.BLACK);

            upcomingPackagesUI();
        });
        selectionPane.add(upcomingButton);

        addPackageButton = new JButton("Add Package");
        addPackageButton.addActionListener(e -> {
            addPackageUI();
        });

        selectionPane.setBorder(new EmptyBorder(10,10,10,10));
        appFrame.add(selectionPane, BorderLayout.NORTH);
        appFrame.setVisible(true);
        displayPane.setLayout(new BoxLayout(displayPane, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(displayPane);
        scrollPane.setSize(new Dimension(200,300));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(0,50,0,50));

        JPanel buttonContainerPanel = new JPanel();
        buttonContainerPanel.setBorder(new EmptyBorder(20,20,20,20));

        buttonContainerPanel.add(addPackageButton);

        appFrame.add(buttonContainerPanel, BorderLayout.SOUTH);
        appFrame.add(scrollPane, BorderLayout.CENTER);
    }

    private void allPackagesUI() {
        JTextArea allPackageText;
        clearDisplayPane();
        if(packageArray.isEmpty()) {
            allPackageText = new JTextArea("No items to show.");
            allPackageText.setEditable(false);
            allPackageText.setLineWrap(true);
            allPackageText.setSize(250, 10);
            allPackageText.setOpaque(false);

            displayPane.add(allPackageText);
        }
        else {
            sortPackageList();
            for(int i = 0; i < packageArray.size(); i++) {
                JPanel packagePanel = new JPanel();
                allPackageText = new JTextArea("Package " + (i + 1) + ":\n" + packageArray.get(i).toString());
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250, 10);
                allPackageText.setOpaque(false);
                JCheckBox deliveredBox;
                JButton removeButton = new JButton("Remove");

                if(packageArray.get(i).getDelivered()) {
                    deliveredBox = new JCheckBox("Delivered?", true);
                } else {
                    deliveredBox = new JCheckBox("Delivered?", false);
                }


                int finalI = i;
                deliveredBox.addActionListener(e -> {
                    if(deliveredBox.isSelected()) {
                        packageArray.get(finalI).setDelivered(true);
                    }
                    else {
                        packageArray.get(finalI).setDelivered(false);
                    }
                });

                removeButton.addActionListener(e -> {
                    System.out.println(finalI);
                    packageArray.remove(finalI);
                    displayPane.remove(packagePanel);
                    displayPane.updateUI();
                    if(displayPane.getComponentCount() == 0 || displayPane.getComponentCount() == 1) {
                        JTextArea missingText = new JTextArea("No items to show.");
                        missingText.setEditable(false);
                        missingText.setLineWrap(true);
                        missingText.setSize(250,10);
                        missingText.setOpaque(false);
                        displayPane.add(missingText);
                    }
                });

                packagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                packagePanel.add(allPackageText);
                packagePanel.add(deliveredBox);
                packagePanel.add(removeButton);

                displayPane.add(Box.createVerticalStrut(20));
                displayPane.add(packagePanel);
            }
        }
    }

    private void overduePackagesUI() {
        JTextArea allPackageText;
        clearDisplayPane();
        sortPackageList();
        int counter = 1;
        for(Package i : packageArray) {
            if(!i.getDelivered() && i.getDeliveryDate().isBefore(LocalDateTime.now())) {
                JPanel packagePanel = new JPanel();
                JCheckBox deliveredBox;
                JButton removeButton = new JButton("Remove");

                if(i.getDelivered()) {
                    deliveredBox = new JCheckBox("Delivered?", true);
                } else {
                    deliveredBox = new JCheckBox("Delivered?", false);
                }

                allPackageText = new JTextArea("Package " + counter + ":\n" +
                        i);
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250,10);
                allPackageText.setOpaque(false);

                Package finalPackage = i;
                deliveredBox.addActionListener(e -> {
                    if(deliveredBox.isSelected()) {
                        finalPackage.setDelivered(true);
                    }
                    else {
                        finalPackage.setDelivered(false);
                    }
                });

                removeButton.addActionListener(e -> {
                    packageArray.remove(finalPackage);
                    displayPane.remove(packagePanel);
                    displayPane.updateUI();
                    if(displayPane.getComponentCount() == 0 || displayPane.getComponentCount() == 1) {
                        JTextArea missingText = new JTextArea("No items to show.");
                        missingText.setEditable(false);
                        missingText.setLineWrap(true);
                        missingText.setSize(250,10);
                        missingText.setOpaque(false);
                        displayPane.add(missingText);
                    }
                });

                packagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                packagePanel.add(allPackageText);
                packagePanel.add(deliveredBox);
                packagePanel.add(removeButton);

                displayPane.add(Box.createVerticalStrut(20));
                displayPane.add(packagePanel);
                counter++;
            }
        }
        if(displayPane.getComponentCount() == 0) {
            allPackageText = new JTextArea("No items to show.");
            allPackageText.setEditable(false);
            allPackageText.setLineWrap(true);
            allPackageText.setSize(250,10);
            allPackageText.setOpaque(false);
            displayPane.add(allPackageText);
        }
    }

    private void upcomingPackagesUI() {
        JTextArea allPackageText;
        clearDisplayPane();
        sortPackageList();
        int counter = 1;
        for(Package i : packageArray) {
            if(!i.getDelivered() && i.getDeliveryDate().isAfter(LocalDateTime.now())) {
                JPanel packagePanel = new JPanel();
                JCheckBox deliveredBox;
                JButton removeButton = new JButton("Remove");

                if(i.getDelivered()) {
                    deliveredBox = new JCheckBox("Delivered?", true);
                } else {
                    deliveredBox = new JCheckBox("Delivered?", false);
                }

                allPackageText = new JTextArea("Package " + counter + ":\n" +
                        i);
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250,10);
                allPackageText.setOpaque(false);

                Package finalPackage = i;
                deliveredBox.addActionListener(e -> {
                    if(deliveredBox.isSelected()) {
                        finalPackage.setDelivered(true);
                    }
                    else {
                        finalPackage.setDelivered(false);
                    }
                });

                removeButton.addActionListener(e -> {
                    packageArray.remove(finalPackage);
                    displayPane.remove(packagePanel);
                    displayPane.updateUI();
                    if(displayPane.getComponentCount() == 0 || displayPane.getComponentCount() == 1) {
                        JTextArea missingText = new JTextArea("No items to show.");
                        missingText.setEditable(false);
                        missingText.setLineWrap(true);
                        missingText.setSize(250,10);
                        missingText.setOpaque(false);
                        displayPane.add(missingText);
                    }
                });

                packagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                packagePanel.add(allPackageText);
                packagePanel.add(deliveredBox);
                packagePanel.add(removeButton);

                displayPane.add(Box.createVerticalStrut(30));
                displayPane.add(packagePanel);
                counter++;
            }
        }
        if(displayPane.getComponentCount() == 0) {
            allPackageText = new JTextArea("No items to show.");
            allPackageText.setEditable(false);
            allPackageText.setLineWrap(true);
            allPackageText.setSize(250,10);
            allPackageText.setOpaque(false);
            displayPane.add(allPackageText);
        }
    }

    private void addPackageUI() {
        JPanel titlePanel = new JPanel();
        JFrame addPackageFrame = new JFrame("Add Package");
        addPackageFrame.setSize(500, 600);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Add Package");
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titleLabel.setBorder(new EmptyBorder(10,10,10,10));
        titlePanel.add(titleLabel);

        JPanel typePanel = new JPanel();
        String[] types = {"--","book", "electronic", "perishable"};
        JComboBox<String> packageTypeOptions = new JComboBox<String>(types);
        JLabel typeLabel = new JLabel("Type: ");
        typePanel.setPreferredSize(new Dimension(100,20));
        typePanel.add(typeLabel);
        typePanel.add(packageTypeOptions);
        formPanel.add(typePanel);

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameInputField = new JTextField();
        nameInputField.setPreferredSize(new Dimension(100,20));
        namePanel.add(nameLabel);
        namePanel.add(nameInputField);
        formPanel.add(namePanel);

        JPanel notesPanel = new JPanel();
        JLabel notesLabel = new JLabel("Notes: ");
        JTextField notesInputField = new JTextField();
        notesInputField.setPreferredSize(new Dimension(100,20));
        notesPanel.add(notesLabel);
        notesPanel.add(notesInputField);
        formPanel.add(notesPanel);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("Price: ");
        JTextField priceInputField = new JTextField();
        priceInputField.setPreferredSize(new Dimension(100,20));
        pricePanel.add(priceLabel);
        pricePanel.add(priceInputField);
        formPanel.add(pricePanel);

        JPanel weightPanel = new JPanel();
        JLabel weightLabel = new JLabel("Weight: ");
        JTextField weightInputField = new JTextField();
        weightInputField.setPreferredSize(new Dimension(100,20));
        weightPanel.add(weightLabel);
        weightPanel.add(weightInputField);
        formPanel.add(weightPanel);

        JPanel deliveryDatePanel = new JPanel();
        JLabel deliveryDateLabel = new JLabel("Estimated Delivery Date: ");
        DateTimePicker deliveryDatePicker = new DateTimePicker();
        deliveryDatePanel.add(deliveryDateLabel);
        deliveryDatePanel.add(deliveryDatePicker);
        formPanel.add(deliveryDatePanel);

        JPanel specificPanel = new JPanel();
        JLabel specificLabel = new JLabel();
        JTextField specificInputField = new JTextField();
        specificInputField.setPreferredSize(new Dimension(100,20));
        DateTimePicker expiryDate = new DateTimePicker();
        packageTypeOptions.addActionListener(e -> {
        specificPanel.removeAll();
            if(packageTypeOptions.getSelectedItem().equals("book")) {
                specificLabel.setText("Author: ");
                specificPanel.add(specificLabel);
                specificPanel.add(specificInputField);
            }
            else if(packageTypeOptions.getSelectedItem().equals("electronic")) {
                specificLabel.setText("EHF: ");
                specificPanel.add(specificLabel);
                specificPanel.add(specificInputField);
            }
            else if(packageTypeOptions.getSelectedItem().equals("perishable")) {
                specificLabel.setText("Expiry Date: ");
                specificPanel.add(specificLabel);
                specificPanel.add(expiryDate);
            }
            formPanel.add(specificPanel);
            formPanel.updateUI();
        });

        formPanel.setBorder(BorderFactory.createEmptyBorder(20,0,80,0));
        JPanel buttonPanel = new JPanel();
        JButton createPackageButton = new JButton("Create");
        createPackageButton.addActionListener(e -> {
            Boolean success = true;
            if(packageTypeOptions.getSelectedItem().equals("--")) {
                success = false;
                typeLabel.setText("Type: ***Please select a package type***");
            }
            else {
                typeLabel.setText("Type: ");
            }

            if(nameInputField.getText().isBlank()) {
                success = false;
                nameLabel.setText("Name: ***Please enter a name***");
            }
            else {
                nameLabel.setText("Name: ");
            }

            try {
                priceLabel.setText("Price: ");
                Double.parseDouble(priceInputField.getText());
            } catch (Exception error) {
                success = false;
                priceLabel.setText("Price: ***Invalid price***");
            }

            try {
                weightLabel.setText("Weight: ");
                Double.parseDouble(weightInputField.getText());
            } catch (Exception error) {
                success = false;
                weightLabel.setText("Weight: ***Invalid weight***");
            }

            if(deliveryDatePicker.getDatePicker().toString().isBlank()) {
                success = false;
                deliveryDateLabel.setText("Estimated Delivery Date: ***Invalid Date***");
            } else if(deliveryDatePicker.getTimePicker().toString().isBlank()) {
                success = false;
                deliveryDateLabel.setText("Estimated Delivery Date: ***Invalid Time***");
            }
            else {
                deliveryDateLabel.setText("Estimated Delivery Date: ");
            }

            if(packageTypeOptions.getSelectedItem().equals("book")) {
                if(specificInputField.getText().isBlank()){
                    success = false;
                    specificLabel.setText("Author: ***Invalid author***");
                }
                else {
                    specificLabel.setText("Author: ");
                }
            } else if(packageTypeOptions.getSelectedItem().equals("electronic")) {
                try {
                    specificLabel.setText("EHF: ");
                    Double.parseDouble(specificInputField.getText());
                } catch (Exception error) {
                    success = false;
                    specificLabel.setText("EHF: ***Invalid EHF***");
                }
            } else if(packageTypeOptions.getSelectedItem().equals("perishable")) {
                if(expiryDate.getDatePicker().toString().isBlank()){
                    success = false;
                    specificLabel.setText("Expiry Date: ***Invalid expiry date***");
                } else if(expiryDate.getTimePicker().toString().isBlank()) {
                    success = false;
                    specificLabel.setText("Expiry Date: ***Invalid expiry time***");
                }
                else {
                    specificLabel.setText("Expiry Date: ");
                }
            }
            if(success) {
                Package newPackage;
                String name = nameInputField.getText();
                String notes = notesInputField.getText();
                Double price = Double.parseDouble(priceInputField.getText());
                Double weight = Double.parseDouble(weightInputField.getText());
                Boolean delivered = false;
                LocalDateTime deliveryDate = deliveryDatePicker.getDateTimePermissive();
                if(packageTypeOptions.getSelectedItem().equals("book")) {
                    String author = specificInputField.getText();
                    newPackage = new Book(author, name, notes, price, weight, delivered, deliveryDate);
                } else if(packageTypeOptions.getSelectedItem().equals("electronic")) {
                    Double ehf = Double.parseDouble(specificInputField.getText());
                    newPackage = new Electronic(ehf, name, notes, price, weight, delivered, deliveryDate);
                } else {
                    LocalDateTime expiry = expiryDate.getDateTimePermissive();
                    newPackage = new Perishable(expiry, name, notes, price, weight, delivered, deliveryDate);
                }
                packageArray.add(newPackage);
                addPackageFrame.dispose();
            }
        });

        JButton cancelPackageButton = new JButton("Cancel");
        cancelPackageButton.addActionListener(e -> {
            addPackageFrame.dispose();
        });

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
        buttonPanel.add(createPackageButton);
        buttonPanel.add(cancelPackageButton);

        addPackageFrame.add(titlePanel, BorderLayout.NORTH);
        addPackageFrame.add(formPanel, BorderLayout.CENTER);
        addPackageFrame.add(buttonPanel, BorderLayout.SOUTH);
        addPackageFrame.setVisible(true);
    }

    private void clearDisplayPane() {
        displayPane.removeAll();
        displayPane.repaint();
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
}
