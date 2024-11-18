package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangePasswordPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private static final String PATH_TO_STUDENT_DETAILS = "C:\\Java Project\\LibraryManagementSystem\\src\\LibrarymanagementSystem\\Student_Id.csv"; // Path to student details

    public ChangePasswordPage() {
        setTitle("Change Password");
        setSize(400, 300);
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

        JLabel oldPasswordLabel = new JLabel("Old Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(oldPasswordLabel, gbc);

        oldPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(oldPasswordField, gbc);

        JLabel newPasswordLabel = new JLabel("New Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(newPasswordField, gbc);

        JButton changePasswordButton = new JButton("Change Password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(changePasswordButton, gbc);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                if (changePassword(username, oldPassword, newPassword)) {
                    JOptionPane.showMessageDialog(null, "Password changed successfully!");
                    dispose(); // Close the change password page
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or old password. Please try again.");
                }
            }
        });

        add(panel);
    }

    private boolean changePassword(String username, String oldPassword, String newPassword) {
        boolean isChanged = false;
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_STUDENT_DETAILS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2) {
                    if (credentials[0].trim().equals(username.trim()) && credentials[1].trim().equals(oldPassword.trim())) {
                        updatedContent.append(username).append(",").append(newPassword).append("\n");
                        isChanged = true; // Mark as changed
                    } else {
                        updatedContent.append(line).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isChanged) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_TO_STUDENT_DETAILS))) {
                bw.write(updatedContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isChanged;
    }

}