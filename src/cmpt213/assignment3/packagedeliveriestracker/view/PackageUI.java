package cmpt213.assignment3.packagedeliveriestracker.view;

import javax.swing.*;
import java.awt.*;

public class PackageUI {
    JFrame appFrame;
    JTextField questionTextField;
    JButton askButton;
    JLabel answerLabel;
    String answer = "--";

    public PackageUI (String title) {
        appFrame = new JFrame(title);
        appFrame.setSize(400, 600);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        questionTextField = new JTextField(50);
        appFrame.add(questionTextField, BorderLayout.NORTH);

        answerLabel = new JLabel(answer);
        appFrame.add(answerLabel);

        askButton = new JButton("Ask me");
        appFrame.add(askButton, BorderLayout.SOUTH);

        appFrame.setVisible(true);
    }
}
