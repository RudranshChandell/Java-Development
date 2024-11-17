package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student_SignupPage extends JFrame {
    private static final String pathtostudentdetails = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Student_Id.csv";
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField rollNumberField;
    private JTextField courseField;
    private JProgressBar progressBar;
    private JLabel clockLabel; // Clock label

    public Student_SignupPage() {
        setTitle("Student Signup Page");
        setSize(400, 450); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout to position the clock

        // Create panel for the signup form
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Name label and text field
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Email label and text field
        JLabel emailLabel = new JLabel("Email (Gmail):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password (min 8 chars):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Roll number label and text field
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(rollNumberLabel, gbc);

        rollNumberField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(rollNumberField, gbc);

        // Course label and text field
        JLabel courseLabel = new JLabel("Course:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(courseLabel, gbc);

        courseField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(courseField, gbc);

        // Signup button
        JButton signupButton = new JButton("Signup");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(signupButton, gbc);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        gbc.gridy = 6;
        panel.add(progressBar, gbc);

        // Add action listener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String rollNumber = rollNumberField.getText();
                String course = courseField.getText();

                // Validate inputs
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Gmail address.");
                    return;
                }

                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.");
                    return;
                }

                if (rollNumber.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                    return;
                }

                if (registerUser(name, email, password, rollNumber, course)) {
                    JOptionPane.showMessageDialog(null, "Signup Successful!");
                    // Redirect to the login page
                    StudentLoginPage loginPage = new StudentLoginPage();
                    loginPage.setVisible(true);
                    dispose(); // Close the signup page
                } else {
                    JOptionPane.showMessageDialog(null, "Signup Failed. Please try again.");
                }
            }
        });

        // Add document listeners to update progress bar
        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateProgressBar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProgressBar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateProgressBar();
            }
        };

        nameField.getDocument().addDocumentListener(docListener);
        emailField.getDocument().addDocumentListener(docListener);
        passwordField.getDocument().addDocumentListener(docListener);
        rollNumberField.getDocument().addDocumentListener(docListener);
        courseField.getDocument().addDocumentListener(docListener);

        // Create and add the clock label
        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(clockLabel, BorderLayout.NORTH); // Add clock to the top of the frame

        // Timer to update the clock every second
        Timer clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        clockTimer.start(); // Start the clock timer

        add(panel, BorderLayout.CENTER); // Add the signup panel to the center
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        clockLabel.setText(currentTime);
    }

    private void updateProgressBar() {
        int filledFields = 0;
        if (!nameField.getText().isEmpty()) filledFields++;
        if (!emailField.getText().isEmpty()) filledFields++;
        if (passwordField.getPassword().length > 0) filledFields++;
        if (!rollNumberField.getText().isEmpty()) filledFields++;
        if (!courseField.getText().isEmpty()) filledFields++;

        int progress = (filledFields * 100) / 5; // 5 fields total
        progressBar.setValue(progress);
    }

    private boolean registerUser(String name, String email, String password, String rollNumber, String course) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathtostudentdetails, true))) {
            bw.write(name + "," + email + "," + password + "," + rollNumber + "," + course);
            bw.newLine();
            return true; // Successful registration
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Failed registration
        }
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8; // Check for at least 8 characters
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Student_SignupPage signupPage = new Student_SignupPage();
            signupPage.setVisible(true);
        });
    }
}
