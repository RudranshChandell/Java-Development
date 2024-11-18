package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShowAdminPage extends JFrame {
    private static final String pathToAdminDetails = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Admin Details.csv";

    public ShowAdminPage() {
        setTitle("Admin Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display admin details
        JTable adminTable = new JTable();
        adminTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Name", "Email", "Role"}
        ));
        JScrollPane scrollPane = new JScrollPane(adminTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Load admin data into the table
        loadAdminData(adminTable);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadAdminData(JTable adminTable) {
        DefaultTableModel model = (DefaultTableModel) adminTable.getModel();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToAdminDetails))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] adminDetails = line.split(",");
                if (adminDetails.length >= 3) {
                    // Assuming CSV format: Name,Email,Role
                    model.addRow(new Object[]{adminDetails[0].trim(), adminDetails[1].trim(), adminDetails[2].trim()});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading admin details. Please check the file path.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShowAdminPage adminPage = new ShowAdminPage();
            adminPage.setVisible(true);
        });
    }
}
