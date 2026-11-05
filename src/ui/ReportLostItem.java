package ui;

import javax.swing.*;
import java.awt.*;

import models.Item;
import utils.ItemManager;

public class ReportLostItem extends JPanel {

    private JTextField itemNameField;
    private JTextField locationField;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryBox;

    private Runnable onItemAdded;

    private final Color PRIMARY = new Color(45, 100, 200);
    private final Color TEXT = new Color(35, 50, 80);

    public ReportLostItem(Runnable onItemAdded) {

        this.onItemAdded = onItemAdded;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(242, 245, 250));

        setBorder(
            BorderFactory.createEmptyBorder(25, 40, 25, 40)
        );

        // Heading
        JLabel title = new JLabel("Report Lost Item");

        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
            "Provide details about the item you have lost."
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(120, 130, 145));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
            new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setOpaque(false);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 230, 240)
                ),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
            )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Item name
        itemNameField = new JTextField(20);
        styleField(itemNameField);

        addField(
            formPanel, gbc, 0,
            "Item Name *", itemNameField
        );

        // Category
        String[] categories = {
            "Electronics",
            "Books",
            "Clothing",
            "Accessories",
            "Documents",
            "Other"
        };

        categoryBox = new JComboBox<>(categories);

        categoryBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );

        categoryBox.setPreferredSize(
            new Dimension(250, 38)
        );

        addField(
            formPanel, gbc, 1,
            "Category *", categoryBox
        );

        // Location
        locationField = new JTextField(20);
        styleField(locationField);

        addField(
            formPanel, gbc, 2,
            "Last Seen Location *", locationField
        );

        // Date
        dateField = new JTextField(20);
        styleField(dateField);

        dateField.setToolTipText(
            "Example: 04-11-2026"
        );

        addField(
            formPanel, gbc, 3,
            "Date Lost *", dateField
        );

        // Description
        descriptionArea = new JTextArea(4, 20);

        descriptionArea.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        descriptionArea.setBorder(
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );

        JScrollPane descriptionScroll =
            new JScrollPane(descriptionArea);

        descriptionScroll.setPreferredSize(
            new Dimension(250, 100)
        );

        addField(
            formPanel, gbc, 4,
            "Description", descriptionScroll
        );

        // Submit button
        JButton submitButton = new JButton("Submit Lost Report");

        submitButton.setFont(
            new Font("Segoe UI", Font.BOLD, 14)
        );

        submitButton.setBackground(PRIMARY);
        submitButton.setForeground(Color.WHITE);

        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);

        submitButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        submitButton.setPreferredSize(
            new Dimension(200, 42)
        );

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;

        formPanel.add(submitButton, gbc);

        submitButton.addActionListener(
            e -> submitReport()
        );

        // Main layout
        add(headingPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    // Style text fields
    private void styleField(JTextField field) {

        field.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );

        field.setPreferredSize(
            new Dimension(250, 38)
        );

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(210, 218, 230)
                ),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            )
        );
    }

    // Submit report
    private void submitReport() {

        String name = itemNameField.getText().trim();
        String location = locationField.getText().trim();
        String date = dateField.getText().trim();

        String category =
            categoryBox.getSelectedItem().toString();

        String description =
            descriptionArea.getText().trim();

        // Validation
        if (name.isEmpty()
                || location.isEmpty()
                || date.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please fill in all required fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Item item = new Item(
            name,
            category,
            "Lost",
            location,
            date,
            description,
            "Missing"
        );

        ItemManager.addItem(item);

        if (onItemAdded != null) {
            onItemAdded.run();
        }

        JOptionPane.showMessageDialog(
            this,
            "Lost item report submitted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Clear form
        itemNameField.setText("");
        locationField.setText("");
        dateField.setText("");
        descriptionArea.setText("");
        categoryBox.setSelectedIndex(0);
    }

    // Add form fields
    private void addField(
        JPanel panel,
        GridBagConstraints gbc,
        int row,
        String label,
        JComponent component
    ) {

        JLabel fieldLabel = new JLabel(label);

        fieldLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );

        fieldLabel.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;

        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        panel.add(component, gbc);
    }
}