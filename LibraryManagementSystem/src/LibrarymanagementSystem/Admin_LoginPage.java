package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Admin_LoginPage extends JFrame {
    private static final String pathtoadmindetails = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Admin Details.csv";
    private JTextField emailField;
    private JPasswordField passwordField;

    public Admin_LoginPage() {
        setTitle("Admin Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        // Removed the signup button and its action listener

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (checkCredentials(email, password)) {
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });

        add(panel);
    }

    private boolean checkCredentials(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathtoadmindetails))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 3) {
                    if (credentials[1].trim().equals(email.trim()) && credentials[2].trim().equals(password.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showMenu() {
        JFrame menuFrame = new JFrame("Admin Menu");
        menuFrame.setSize(400, 400); // Adjust size as needed
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

        // Create a main panel with GridBagLayout to center the content
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Add the main panel to the frame
        menuFrame.add(mainPanel);

        // Create a panel for the menu buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // Set BoxLayout for vertical arrangement
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space after the label

        JButton manageBooksButton = new JButton("Manage Books");
        JButton manageStudentsButton = new JButton("Manage Students");
        JButton manageAdminButton = new JButton("Manage Admins");
        JButton loginRecordsButton = new JButton("Login Records");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton studentQueriesButton = new JButton("Student Queries");
        JButton logoutButton = new JButton("Log Out"); // Add Log Out button

        manageBooksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageStudentsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageAdminButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginRecordsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        transactionHistoryButton.setFont(new Font("Arial", Font.PLAIN, 16));
        studentQueriesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for Log Out button

        // Center the buttons
        manageBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageStudentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageAdminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginRecordsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentQueriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center Log Out button

        // Add action listeners for buttons
        manageBooksButton.addActionListener(e -> {
            ManageBooks manageBooks = new ManageBooks();
            manageBooks.setVisible(true);
        });

        manageStudentsButton.addActionListener(e -> {
            ManageStudents manageStudents = new ManageStudents();
            manageStudents.setVisible(true);
        });

        manageAdminButton.addActionListener(e -> {
            ManageAdmin manageAdmin = new ManageAdmin();
            manageAdmin.setVisible(true);
        });

        loginRecordsButton.addActionListener(e -> {
            // Add functionality to view login records
        });

        transactionHistoryButton.addActionListener(e -> {
            // Add functionality to view transaction history
        });

        studentQueriesButton.addActionListener(e -> {
            // Add functionality to handle student queries
        });

        // Add action listener for Log Out button
        logoutButton.addActionListener(e -> {
            menuFrame.dispose(); // Close the menu frame
            Main main = new Main(); // Create a new login page
            main.setVisible(true); // Show the login page
        });

        // Add buttons to the panel
        menuPanel.add(manageBooksButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        menuPanel.add(manageStudentsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        menuPanel.add(manageAdminButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        menuPanel.add(loginRecordsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        menuPanel.add(transactionHistoryButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        menuPanel.add(studentQueriesButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space before Log Out button
        menuPanel.add(logoutButton); // Add Log Out button to the panel

        // Center the menuPanel in the mainPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(menuPanel, gbc);

        menuFrame.setVisible(true); // Show the menu frame
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin_LoginPage loginPage = new Admin_LoginPage();
            loginPage.setVisible(true);
        });
    }
}