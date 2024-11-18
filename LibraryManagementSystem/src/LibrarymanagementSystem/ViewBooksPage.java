package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewBooksPage extends JFrame {
    private static final String pathToBooksDetails = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Books.csv";

    public ViewBooksPage() {
        setTitle("View Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Available Books", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable booksTable = new JTable();
        booksTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book Title", "Author", "Availability"}
        ));
        JScrollPane scrollPane = new JScrollPane(booksTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadBooksData(booksTable);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadBooksData(JTable booksTable) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToBooksDetails))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length >= 3) {
                    model.addRow(new Object[]{bookDetails[0].trim(), bookDetails[1].trim(), bookDetails[2].trim()});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading book details. Please check the file path.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewBooksPage viewBooksPage = new ViewBooksPage();
            viewBooksPage.setVisible(true);
        });
    }
}
