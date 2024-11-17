package LibrarymanagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class ManageStudents extends JFrame {
    private static final String pathToStudents = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\student.csv";
    private JTable studentsTable;
    private DefaultTableModel tableModel;

    public ManageStudents() {
        setTitle("Manage Students");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table model with the new columns
        tableModel = new DefaultTableModel(new String[]{"Name", "Student ID", "Email", "Phone Number"}, 0);
        studentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentsTable);

        // Load student data from CSV
        loadStudentData();

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        // Layout the components
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadStudentData() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToStudents))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData.length == 4) {
                    tableModel.addRow(studentData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student data.");
        }
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String studentId = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (studentId == null || studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID cannot be empty.");
            return;
        }

        String email = JOptionPane.showInputDialog(this, "Enter Student Email:");
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email cannot be empty.");
            return;
        }

        String phoneNumber = JOptionPane.showInputDialog(this, "Enter Phone Number:");
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone Number cannot be empty.");
            return;
        }

        String[] studentData = {name, studentId, email, phoneNumber};
        tableModel.addRow(studentData);
        saveStudentData(studentData);
    }

    private void updateStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String studentId = (String) tableModel.getValueAt(selectedRow, 1);
            String email = (String) tableModel.getValueAt(selectedRow, 2);
            String phoneNumber = (String) tableModel.getValueAt(selectedRow, 3);

            // Show input dialogs with current values
            name = JOptionPane.showInputDialog(this, "Update Student Name:", name);
            studentId = JOptionPane.showInputDialog(this, "Update Student ID:", studentId);
            email = JOptionPane.showInputDialog(this, "Update Student Email:", email);
            phoneNumber = JOptionPane.showInputDialog(this, "Update Phone Number:", phoneNumber);

            if (name != null && studentId != null && email != null && phoneNumber != null) {
                if (name.isEmpty() || studentId.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled.");
                    return;
                }

                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(studentId, selectedRow, 1);
                tableModel.setValueAt(email, selectedRow, 2);
                tableModel.setValueAt(phoneNumber, selectedRow, 3);
                saveAllStudentsData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to update.");
        }
    }

    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                saveAllStudentsData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
        }
    }

    private void saveStudentData(String[] studentData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToStudents, true))) {
            writer.write(String.join(",", studentData));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving student data.");
        }
    }

    private void saveAllStudentsData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToStudents))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder studentData = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    studentData.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        studentData.append(",");
                    }
                }
                writer.write(studentData.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving all student data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageStudents manageStudents = new ManageStudents();
            manageStudents.setVisible(true);
        });
    }
}