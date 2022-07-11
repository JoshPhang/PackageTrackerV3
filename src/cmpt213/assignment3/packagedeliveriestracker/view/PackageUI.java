package cmpt213.assignment3.packagedeliveriestracker.view;

import cmpt213.assignment3.packagedeliveriestracker.model.Package;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class PackageUI {
    JFrame appFrame;
    JButton allButton;
    JButton overdueButton;
    JButton upcomingButton;
    JPanel selectionPane;
    JPanel displayPane;
    ArrayList<Package> packageArray;

    public PackageUI (String title, ArrayList<Package> packageArray) {
        this.packageArray = packageArray;

        appFrame = new JFrame(title);
        appFrame.setSize(400, 600);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        selectionPane = new JPanel();

        allButton = new JButton("All");
        allButton.setBackground(Color.DARK_GRAY);
        allButton.setForeground(Color.WHITE);
        allButton.addActionListener(e -> {
            displayPane = new JPanel();
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
            displayPane = new JPanel();
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
        });
        selectionPane.add(upcomingButton);

        appFrame.add(selectionPane, BorderLayout.NORTH);
        appFrame.setVisible(true);
    }

    private void allPackagesUI() {
        sortPackageList();
        for(int i = 0; i < packageArray.size(); i++) {
            JTextArea allPackageText = new JTextArea("Package " + (i+1) + ":\n" + packageArray.get(i).toString());
            allPackageText.setEditable(false);
            allPackageText.setLineWrap(true);
            allPackageText.setSize(250,10);
            allPackageText.setBackground(Color.LIGHT_GRAY);
            displayPane.add(allPackageText);
        }
        appFrame.add(displayPane, BorderLayout.CENTER);
    }

    private void overduePackagesUI() {
        sortPackageList();
        for(int i = 0; i < packageArray.size(); i++) {
            if(!packageArray.get(i).getDelivered() && packageArray.get(i).getDeliveryDate().isBefore(LocalDateTime.now())) {
                JTextArea allPackageText = new JTextArea("Package " + (i+1) + ":\n" +
                        packageArray.get(i).toString());
                allPackageText.setEditable(false);
                allPackageText.setLineWrap(true);
                allPackageText.setSize(250,10);
                allPackageText.setBackground(Color.LIGHT_GRAY);
                displayPane.add(allPackageText);
            }
        }
        appFrame.add(displayPane, BorderLayout.CENTER);
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
