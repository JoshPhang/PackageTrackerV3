package cmpt213.assignment3.packagedeliveriestracker.view;

import cmpt213.assignment3.packagedeliveriestracker.model.Package;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

    public PackageUI (String title, ArrayList<Package> packageArray) {
        this.packageArray = packageArray;
        this.title = title;
        appFrame = new JFrame(title);
        appFrame.setSize(500, 600);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayMainMenu();
    }

    private void displayMainMenu() {
        selectionPane = new JPanel();

        allButton = new JButton("All");
        allButton.setBackground(Color.DARK_GRAY);
        allButton.setForeground(Color.WHITE);
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
        clearDisplayPane();
        sortPackageList();
        for(int i = 0; i < packageArray.size(); i++) {
            JTextArea allPackageText = new JTextArea("Package " + (i + 1) + ":\n" + packageArray.get(i).toString());
            allPackageText.setEditable(false);
            allPackageText.setLineWrap(true);
            allPackageText.setSize(250, 10);
            allPackageText.setOpaque(false);
            allPackageText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            displayPane.add(Box.createVerticalStrut(20));
            displayPane.add(allPackageText);
        }
    }

    private void overduePackagesUI() {
        clearDisplayPane();
        sortPackageList();
        int counter = 1;
        for(Package i : packageArray) {
            if(!i.getDelivered() && i.getDeliveryDate().isBefore(LocalDateTime.now())) {
                JTextArea allPackageText = new JTextArea("Package " + counter + ":\n" +
                        i);
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250,10);
                allPackageText.setOpaque(false);
                allPackageText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                displayPane.add(Box.createVerticalStrut(20));
                displayPane.add(allPackageText);
                counter++;
            }
        }
    }

    private void upcomingPackagesUI() {
        clearDisplayPane();
        sortPackageList();
        int counter = 1;
        for(Package i : packageArray) {
            if(!i.getDelivered() && i.getDeliveryDate().isAfter(LocalDateTime.now())) {
                JTextArea allPackageText = new JTextArea("Package " + counter + ":\n" +
                        i);
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250,10);
                allPackageText.setOpaque(false);
                allPackageText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                displayPane.add(Box.createVerticalStrut(30));
                displayPane.add(allPackageText);
                counter++;
            }
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
        String[] types = {"book", "electronic", "perishable"};
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
        JLabel weightLabel = new JLabel("Price: ");
        JTextField weightInputField = new JTextField();
        weightInputField.setPreferredSize(new Dimension(100,20));
        weightPanel.add(weightLabel);
        weightPanel.add(weightInputField);
        formPanel.add(weightPanel);

        packageTypeOptions.addActionListener(e -> {
            if(packageTypeOptions.getSelectedItem().equals("book")) {

            }
            System.out.println(packageTypeOptions.getSelectedItem());
        });

        formPanel.setBorder(BorderFactory.createEmptyBorder(20,0,150,0));

        addPackageFrame.add(titlePanel, BorderLayout.NORTH);
        addPackageFrame.add(formPanel, BorderLayout.CENTER);
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
}
