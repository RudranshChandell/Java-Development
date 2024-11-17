package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class ManageBooks extends JFrame {
    private static final String pathToBooks = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\books.csv";
    private JTable booksTable;
    private DefaultTableModel tableModel;

    public ManageBooks() {
        setTitle("Manage Books");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table model with the new columns
        tableModel = new DefaultTableModel(new String[]{"Title", "ISBN", "Author", "Number of Pages", "Price"}, 0);
        booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        // Load book data from CSV
        loadBookData();

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());

        // Layout the components
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadBookData() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToBooks))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 5) {
                    tableModel.addRow(bookData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading book data.");
        }
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter Book Title:");
        if (title == null || title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.");
            return;
        }

        String isbn = JOptionPane.showInputDialog(this, "Enter Book ISBN:");
        if (isbn == null || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN cannot be empty.");
            return;
        }

        String author = JOptionPane.showInputDialog(this, "Enter Book Author:");
        if (author == null || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Author cannot be empty.");
            return;
        }

        String numberOfPagesStr = JOptionPane.showInputDialog(this, "Enter Number of Pages:");
        if (numberOfPagesStr == null || numberOfPagesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Number of Pages cannot be empty.");
            return;
        }

        String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");
        if (priceStr == null || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price cannot be empty.");
            return;
        }

        // Validate number of pages and price
        int numberOfPages;
        double price;
        try {
            numberOfPages = Integer.parseInt(numberOfPagesStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Number of Pages and Price.");
            return;
        }

        String[] bookData = {title, isbn, author, String.valueOf(numberOfPages), String.valueOf(price)};
        tableModel.addRow(bookData);
        saveBookData(bookData);
    }

    private void updateBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = (String) tableModel.getValueAt(selectedRow, 0);
            String isbn = (String) tableModel.getValueAt(selectedRow, 1);
            String author = (String) tableModel.getValueAt(selectedRow, 2);
            String numberOfPages = (String) tableModel.getValueAt(selectedRow, 3);
            String price = (String) tableModel.getValueAt(selectedRow, 4);

            // Show input dialogs with current values
            title = JOptionPane.showInputDialog (this, "Update Book Title:", title);
            isbn = JOptionPane.showInputDialog(this, "Update Book ISBN:", isbn);
            author = JOptionPane.showInputDialog(this, "Update Book Author:", author);
            numberOfPages = JOptionPane.showInputDialog(this, "Update Number of Pages:", numberOfPages);
            price = JOptionPane.showInputDialog(this, "Update Price:", price);

            if (title != null && isbn != null && author != null && numberOfPages != null && price != null) {
                if (title.isEmpty() || isbn.isEmpty() || author.isEmpty() || numberOfPages.isEmpty() || price.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled.");
                    return;
                }

                // Validate number of pages and price
                int pages;
                double bookPrice;
                try {
                    pages = Integer.parseInt(numberOfPages);
                    bookPrice = Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for Number of Pages and Price.");
                    return;
                }

                tableModel.setValueAt(title, selectedRow, 0);
                tableModel.setValueAt(isbn, selectedRow, 1);
                tableModel.setValueAt(author, selectedRow, 2);
                tableModel.setValueAt(String.valueOf(pages), selectedRow, 3);
                tableModel.setValueAt(String.valueOf(bookPrice), selectedRow, 4);
                saveAllBooksData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to update.");
        }
    }

    private void deleteBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                saveAllBooksData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
        }
    }

    private void saveBookData(String[] bookData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToBooks, true))) {
            writer.write(String.join(",", bookData));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving book data.");
        }
    }

    private void saveAllBooksData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToBooks))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder bookData = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    bookData.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        bookData.append(",");
                    }
                }
                writer.write(bookData.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving all book data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageBooks manageBooks = new ManageBooks();
            manageBooks.setVisible(true);
        });
    }
}