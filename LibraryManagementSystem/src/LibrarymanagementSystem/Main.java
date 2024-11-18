package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends JFrame {
    private JTextArea textArea;
    private final String introText = "Welcome to the Library Management System!\n"
            + "This system allows you to manage books, members, and transactions.\n"
            + "This Code is made by Rudransh Chandel and Raushan Raj ....\n"
            + "Please wait while we direct you to the login form..."
            ;
    private int currentIndex = 0;
    private Timer timer;
    private JLabel clockLabel;

    public Main() {
        setTitle("Library Management System - Intro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Create a clock label
        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(clockLabel, BorderLayout.NORTH);

        // Timer to update the text area
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < introText.length()) {
                    textArea.append(String.valueOf(introText.charAt(currentIndex)));
                    currentIndex++;
                } else {
                    timer.stop(); // Stop the timer when all text is displayed
                    textArea.append("\nLoading......");
                    // Show the login form after a short delay
                    Timer delayTimer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showLoginForm(); // Show login form after the delay
                        }
                    });
                    delayTimer.setRepeats(false); // Only run once
                    delayTimer.start();
                }
            }
        });
        timer.start(); // Start the animation

        // Timer to update the clock every second
        Timer clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        clockTimer.start(); // Start the clock timer

        // Add key listener to stop animation on Enter key press
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timer.stop(); // Stop the animation timer
                    textArea.append("\nLoading......"); // Optional: Indicate loading
                    showLoginForm(); // Show the login form immediately
                }
            }
        });

        // Request focus for the text area to capture key events
        textArea.requestFocusInWindow();
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        clockLabel.setText(currentTime);
    }

    private void showLoginForm() {
        // Create login form frame
        JFrame loginFrame = new JFrame("Login Form");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        // Create a panel for the login form
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons for Student and Admin
        JButton studentButton = new JButton("Student");
        JButton adminButton = new JButton("Admin");

        // Add action listeners for the buttons
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(loginFrame, "Student Login Selected.");
                // Here you can add functionality for student login
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instantiate and show the Admin_LoginPage
                Admin_LoginPage adminLoginPage = new Admin_LoginPage();
                adminLoginPage.setVisible(true);
                loginFrame.dispose(); // Close the login frame
            }
        });

        // Add buttons to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(studentButton, gbc);

        gbc.gridx = 1;
        panel.add(adminButton, gbc);

        loginFrame.add(panel);
        loginFrame.setVisible(true); // Show the login form
        this.dispose(); // Close the intro frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main introFrame = new Main();
            introFrame.setVisible(true);
        });
    }
}