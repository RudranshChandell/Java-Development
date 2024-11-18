package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginRecordsPage extends JFrame {
    private static final String pathToLoginRecords = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\LoginRecords.csv"; // Update this path as necessary

    public LoginRecordsPage() {
        setTitle("Login Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Timestamp");
        model.addColumn(" Status");

        // Load login records from CSV file
        List<String[]> records = loadLoginRecords();
        for (String[] record : records) {
            model.addRow(record);
        }

        // Create a JTable with the model
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create a button to close the window
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }

    private List<String[]> loadLoginRecords() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToLoginRecords))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}