package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ReturnBookPage extends JFrame {
    private static final String pathToBorrowedBooks = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\BorrowedBooks.csv";
    private static final String pathToAvailableBooks = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Books.csv";
    private static final String pathToTransactionRecords = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Transaction.csv";
    private static final double LATE_FEE_PER_DAY = 5.0; // Rs. 5 per day as late fee

    private String loggedInEmail; // This will hold the logged-in user's email

    public ReturnBookPage() {
        this.loggedInEmail = loggedInEmail; // Set the logged-in user's email

        setTitle("Return Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Return Book", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable booksTable = new JTable();
        booksTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book Title", "Author", "Borrow Date"}
        ));
        JScrollPane scrollPane = new JScrollPane(booksTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadBorrowedBooksData(booksTable);

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e -> returnBook(booksTable));
        returnPanel.add(returnButton);

        mainPanel.add(returnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadBorrowedBooksData(JTable booksTable) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToBorrowedBooks))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length >= 4) {
                    String email = bookDetails[2].trim();
                    if (email.equals(loggedInEmail)) { // Filter by logged-in user's email
                        model.addRow(new Object[]{
                                bookDetails[0].trim(),
                                bookDetails[1].trim(),
                                bookDetails[3].trim() // Display borrow date
                        });
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading borrowed book details. Please check the file path.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void returnBook(JTable booksTable) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookTitle = (String) model.getValueAt(selectedRow, 0);
        String author = (String) model.getValueAt(selectedRow, 1);
        String borrowDateStr = (String) model.getValueAt(selectedRow, 2);

        LocalDateTime borrowDate = LocalDateTime.parse(borrowDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long daysLate = calculateLateDays(borrowDate);
        double fee = calculateLateFee(daysLate);

        String message = "Book Title: " + bookTitle + "\n" +
                "Late Days: " + daysLate + "\n" +
                "Late Fee: Rs. " + fee;
        int confirm = JOptionPane.showConfirmDialog(this, message + "\nDo you want to return this book?", "Confirm Return", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            addToAvailableBooks(bookTitle, author);
            removeFromBorrowedBooks(bookTitle, loggedInEmail);
            logTransaction(bookTitle, loggedInEmail, fee);

            JOptionPane.showMessageDialog(this, "Book returned successfully! Late fee: Rs. " + fee, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private long calculateLateDays(LocalDateTime borrowDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return ChronoUnit.DAYS.between(borrowDate, currentDate);
    }

    private double calculateLateFee(long daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * LATE_FEE_PER_DAY;
    }

    private void addToAvailableBooks(String bookTitle, String author) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToAvailableBooks, true))) {
            bw.write(bookTitle + "," + author + ",Available");
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error returning the book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void removeFromBorrowedBooks(String bookTitle, String studentEmail) {
        File inputFile = new File(pathToBorrowedBooks);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (!bookDetails[0].trim().equals(bookTitle.trim()) || !bookDetails[2].trim().equals(studentEmail.trim())) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating borrowed book list. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    private void logTransaction(String bookTitle, String studentEmail, double fee) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToTransactionRecords, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(bookTitle + "," + studentEmail + "," + timestamp + ",Returned,Fee: Rs. " + fee);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error logging transaction. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Simulating a logged-in user with their email
        String loggedInEmail = "student@example.com";

        SwingUtilities.invokeLater(() -> {
            ReturnBookPage returnBookPage = new ReturnBookPage();
            returnBookPage.setVisible(true);
        });
    }
}
