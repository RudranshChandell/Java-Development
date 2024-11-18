package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BorrowBookPage extends JFrame {
    private static final String pathToAvailableBooks = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\books.csv";
    private static final String pathToBorrowedBooks = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\BorrowedBooks.csv";
    private static final String pathToTransactionRecords = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Transaction.csv";

    private JTextField studentEmailField;

    public BorrowBookPage() {
        setTitle("Borrow Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Borrow Book", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable booksTable = new JTable();
        booksTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book Title", "Author", "Availability"}
        ));
        JScrollPane scrollPane = new JScrollPane(booksTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadAvailableBooksData(booksTable);

        JPanel borrowPanel = new JPanel();
        borrowPanel.setLayout(new BoxLayout(borrowPanel, BoxLayout.Y_AXIS));

        JLabel emailLabel = new JLabel("Student Email:");
        studentEmailField = new JTextField();
        borrowPanel.add(emailLabel);
        borrowPanel.add(studentEmailField);

        JButton borrowButton = new JButton("Borrow Book");
        borrowButton.addActionListener(e -> borrowBook(booksTable));
        borrowPanel.add(borrowButton);

        mainPanel.add(borrowPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadAvailableBooksData(JTable booksTable) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToAvailableBooks))) {
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

    private void borrowBook(JTable booksTable) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to borrow.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookTitle = (String) model.getValueAt(selectedRow, 0);
        String author = (String) model.getValueAt(selectedRow, 1);
        String availability = (String) model.getValueAt(selectedRow, 2);

        if (availability.equals("Not Available")) {
            JOptionPane.showMessageDialog(this, "This book is currently not available for borrowing.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String studentEmail = studentEmailField.getText();

        // Validate Gmail email address format
        if (studentEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the student's email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate that the email is a Gmail address
        if (!studentEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Gmail address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        addToBorrowedBooks(bookTitle, author, studentEmail);
        removeFromAvailableBooks(bookTitle);
        logTransaction(bookTitle, studentEmail);

        JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addToBorrowedBooks(String bookTitle, String author, String studentEmail) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToBorrowedBooks, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(bookTitle + "," + author + "," + studentEmail + "," + timestamp);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error borrowing book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void removeFromAvailableBooks(String bookTitle) {
        File inputFile = new File(pathToAvailableBooks);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (!bookDetails[0].trim().equals(bookTitle.trim())) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating book list. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    private void logTransaction(String bookTitle, String studentEmail) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToTransactionRecords, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(bookTitle + "," + studentEmail + "," + timestamp);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error logging transaction. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BorrowBookPage borrowBookPage = new BorrowBookPage();
            borrowBookPage.setVisible(true);
        });
    }
}
