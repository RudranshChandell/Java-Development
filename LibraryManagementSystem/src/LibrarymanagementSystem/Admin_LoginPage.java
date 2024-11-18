package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Admin_LoginPage extends JFrame {
    private static final String pathtoadmindetails = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Admin Details.csv";
    private static final String pathToLoginRecords = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\LoginRecords.csv"; // Path for login records
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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (checkCredentials(email, password)) {
                    logLoginAttempt(email, "Success");
                    showMenu();
                } else {
                    logLoginAttempt(email, "Failure");
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

    private void logLoginAttempt(String email, String status) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToLoginRecords, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(email + "," + timestamp + "," + status);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMenu() {
        JFrame menuFrame = new JFrame("Admin Menu");
        menuFrame.setSize(400, 400);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        menuFrame.add(mainPanel);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space after the label

        JButton manageBooksButton = new JButton("Manage Books");
        JButton manageStudentsButton = new JButton("Manage Students");
        JButton manageAdminButton = new JButton("Manage Admins");
        JButton loginRecordsButton = new JButton("Login Records");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton studentQueriesButton = new JButton("Student Queries");
        JButton logoutButton = new JButton("Log Out");

        manageBooksButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageStudentsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageAdminButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginRecordsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        transactionHistoryButton.setFont(new Font("Arial", Font.PLAIN, 16));
        studentQueriesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));

        manageBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageStudentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageAdminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginRecordsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentQueriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
            LoginRecordsPage loginRecordsPage = new LoginRecordsPage();
            loginRecordsPage.setVisible(true);
        });

        transactionHistoryButton.addActionListener(e -> {
            // Add functionality to view transaction history
        });

        studentQueriesButton.addActionListener(e -> {
            ShowQueryPage page=new ShowQueryPage();
            page.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            menuFrame.dispose();
            Main main = new Main();
            main.setVisible(true);
        });

        menuPanel.add(manageBooksButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(manageStudentsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(manageAdminButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(loginRecordsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(transactionHistoryButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(studentQueriesButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(logoutButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(menuPanel, gbc);

        menuFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin_LoginPage loginPage = new Admin_LoginPage();
            loginPage.setVisible(true);
        });
    }
}