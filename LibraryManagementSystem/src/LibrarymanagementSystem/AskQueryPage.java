package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AskQueryPage extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextArea queryTextArea;
    private static final String PATH_TO_QUERIES = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Queries.csv"; // Path to queries

    public AskQueryPage() {
        setTitle("Ask Query");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        JLabel queryLabel = new JLabel("Query:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(queryLabel, gbc);

        queryTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(queryTextArea);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(scrollPane, gbc);

        JButton submitButton = new JButton("Submit Query");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String query = queryTextArea.getText();
                if (!username.isEmpty() && !email.isEmpty() && !query.isEmpty()) {
                    submitQuery(username, email, query);
                    JOptionPane.showMessageDialog(null, "Query submitted successfully!");
                    dispose(); // Close the query page
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        add(panel);
    }

    private void submitQuery(String username, String email, String query) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_TO_QUERIES, true))) {
            bw.write(username + "," + email + "," + query);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}