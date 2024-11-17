package LibrarymanagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAdmin extends JFrame {
    private JTextField adminEmailField;
    private JPasswordField adminPasswordField;

    public ManageAdmin() {
        setTitle("Manage Admins");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Admin Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        adminEmailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(adminEmailField, gbc);

        JLabel passwordLabel = new JLabel("Admin Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        adminPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(adminPasswordField, gbc);

        JButton addButton = new JButton("Add Admin");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        JButton removeButton = new JButton("Remove Admin");
        gbc.gridy = 3;
        panel.add(removeButton, gbc);

        JButton viewButton = new JButton("View Admins");
        gbc.gridy = 4;
        panel.add(viewButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = adminEmailField.getText();
                String password = new String(adminPasswordField.getPassword());
                // Add logic to add admin to the database or file
                JOptionPane.showMessageDialog(null, "Admin added: " + email);
                adminEmailField.setText("");
                adminPasswordField.setText("");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = adminEmailField.getText();
                // Add logic to remove admin from the database or file
                JOptionPane.showMessageDialog(null, "Admin removed: " + email);
                adminEmailField.setText("");
                adminPasswordField.setText("");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to view all admins
                JOptionPane.showMessageDialog(null, "Displaying all admins...");
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageAdmin manageAdmin = new ManageAdmin();
            manageAdmin.setVisible(true);
        });
    }
}