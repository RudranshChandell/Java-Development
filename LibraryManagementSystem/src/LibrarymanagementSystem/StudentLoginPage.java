package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentLoginPage extends JFrame {
    private static final String PATH_TO_STUDENT_DETAILS = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Student_Id.csv";
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StudentLoginPage() {
        setTitle("Student Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

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

        JButton signupButton = new JButton("Sign Up");
        gbc.gridy = 3;
        panel.add(signupButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    showStudentMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student_SignupPage signupPage = new Student_SignupPage();
                signupPage.setVisible(true);
                dispose(); // Close the login page
            }
        });

        add(panel);
    }

    private boolean authenticate(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_STUDENT_DETAILS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2) {
                    if (credentials[0].trim().equals(username.trim()) && credentials[1].trim().equals(password.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showStudentMenu() {
        JFrame menuFrame = new JFrame("Student Menu");
        menuFrame.setSize(400, 400);
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
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel menuLabel = new JLabel("Student Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton viewBooksButton = new JButton("View Books");
        JButton borrowBooksButton = new JButton("Borrow Books");
        JButton returnBooksButton = new JButton("Return Books");
        JButton logoutButton = new JButton("Log Out");

        viewBooksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        borrowBooksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        returnBooksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));

        viewBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        borrowBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners for buttons
        viewBooksButton.addActionListener(e -> {
            // Add functionality to view books
        });

        borrowBooksButton.addActionListener(e -> {
            // Add functionality to borrow books
        });

        returnBooksButton.addActionListener(e -> {
            // Add functionality to return books
        });

        logoutButton.addActionListener(e -> {
            menuFrame.dispose();
            StudentLoginPage loginPage = new StudentLoginPage();
            loginPage.setVisible(true);
        });

        menuPanel.add(viewBooksButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(borrowBooksButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(returnBooksButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(logoutButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(menuPanel, gbc);

        menuFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentLoginPage loginPage = new StudentLoginPage();
            loginPage.setVisible(true);
        });
    }
}