package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShowQueryPage extends JFrame {
    private static final String pathToQueries = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Queries.csv";

    public ShowQueryPage() {
        setTitle("Student Queries");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Student Queries", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea queryArea = new JTextArea();
        queryArea.setEditable(false);
        queryArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(queryArea);

        // Load queries from the file
        loadQueries(queryArea);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadQueries(JTextArea queryArea) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToQueries))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] queryDetails = line.split(",");
                if (queryDetails.length >= 3) {
                    content.append("Name: ").append(queryDetails[0].trim())
                            .append("\nEmail: ").append(queryDetails[1].trim())
                            .append("\nQuery: ").append(queryDetails[2].trim())
                            .append("\n------------------------------\n");
                }
            }
            queryArea.setText(content.toString());
        } catch (IOException e) {
            queryArea.setText("Error loading queries. Please check the file path.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShowQueryPage queryPage = new ShowQueryPage();
            queryPage.setVisible(true);
        });
    }
}
