package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.regex.Pattern;

import models.Item;
import utils.ItemManager;

public class AllItems extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> filterBox;

    private TableRowSorter<DefaultTableModel> sorter;

    private JButton recoverButton;
    private JButton deleteButton;
    private JButton editButton;

    public AllItems() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(242, 245, 250));

        setBorder(
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // TITLE
        JLabel title = new JLabel("All Reported Items");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(35, 50, 80));

        JLabel subtitle = new JLabel(
            "View and manage all lost and found reports."
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

        // SEARCH AND FILTER
        JPanel searchPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 12, 10)
        );

        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );

        searchField = new JTextField(18);
        searchField.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );
        searchField.setPreferredSize(new Dimension(220, 35));

        searchField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(210, 218, 230)
                ),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            )
        );

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );

        filterBox = new JComboBox<>(
            new String[]{"All Items", "Lost", "Found"}
        );

        filterBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 13)
        );

        filterBox.setPreferredSize(new Dimension(130, 35));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(filterLabel);
        searchPanel.add(filterBox);

        JPanel topPanel = new JPanel(
            new BorderLayout(10, 15)
        );

        topPanel.setOpaque(false);
        topPanel.add(headingPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // TABLE
        String[] columns = {
            "Item Name",
            "Category",
            "Type",
            "Location",
            "Date",
            "Status"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(40);
        table.setFont(
            new Font("Segoe UI", Font.PLAIN, 13)
        );

        table.setForeground(new Color(50, 60, 80));

        table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        table.setFillsViewportHeight(true);
        table.setGridColor(new Color(230, 235, 242));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 1));

        // TABLE HEADER
        table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );

        table.getTableHeader().setBackground(
            new Color(35, 50, 80)
        );

        table.getTableHeader().setForeground(Color.WHITE);

        table.getTableHeader().setPreferredSize(
            new Dimension(0, 42)
        );

        table.getTableHeader().setReorderingAllowed(false);

        // ALTERNATING ROW COLORS
        DefaultTableCellRenderer renderer =
            new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
                ) {

                    Component component =
                        super.getTableCellRendererComponent(
                            table, value, isSelected,
                            hasFocus, row, column
                        );

                    if (isSelected) {
                        component.setBackground(
                            new Color(210, 225, 255)
                        );
                        component.setForeground(
                            new Color(30, 50, 90)
                        );
                    } else {
                        component.setBackground(
                            row % 2 == 0
                                ? Color.WHITE
                                : new Color(245, 247, 251)
                        );

                        component.setForeground(
                            new Color(50, 60, 80)
                        );
                    }

                    setBorder(
                        BorderFactory.createEmptyBorder(
                            5, 12, 5, 5
                        )
                    );

                    return component;
                }
            };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel()
                .getColumn(i)
                .setCellRenderer(renderer);
        }

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
            BorderFactory.createLineBorder(
                new Color(220, 225, 235)
            )
        );

        // EDIT BUTTON
        editButton = new JButton("Edit Report");

        styleButton(
            editButton,
            new Color(65, 105, 225)
        );

        editButton.addActionListener(
            e -> editItem()
        );

        // RECOVERY BUTTON
        recoverButton = new JButton("Mark as Recovered");

        styleButton(
            recoverButton,
            new Color(39, 174, 96)
        );

        recoverButton.addActionListener(
            e -> markAsRecovered()
        );

        // DELETE BUTTON
        deleteButton = new JButton("Delete Report");

        styleButton(
            deleteButton,
            new Color(192, 57, 43)
        );

        deleteButton.addActionListener(
            e -> deleteItem()
        );

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 12, 5)
        );

        bottomPanel.setOpaque(false);

        bottomPanel.add(editButton);
        bottomPanel.add(recoverButton);
        bottomPanel.add(deleteButton);

        // SEARCH LISTENER
        searchField.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {

                public void insertUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters();
                }

                public void removeUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters();
                }

                public void changedUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters();
                }
            }
        );

        filterBox.addActionListener(
            e -> applyFilters()
        );

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshItems();
    }

    // BUTTON STYLING
    private void styleButton(
        JButton button,
        Color background
    ) {

        button.setFont(
            new Font("Segoe UI", Font.BOLD, 12)
        );

        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
            new Dimension(155, 40)
        );
    }

    // REFRESH TABLE
    public void refreshItems() {

        model.setRowCount(0);

        for (Item item : ItemManager.getItems()) {

            model.addRow(new Object[]{
                item.getItemName(),
                item.getCategory(),
                item.getType(),
                item.getLocation(),
                item.getDate(),
                item.getStatus()
            });
        }

        applyFilters();
    }

    // EDIT ITEM
    private void editItem() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item first!"
            );

            return;
        }

        int modelRow =
            table.convertRowIndexToModel(selectedRow);

        Item item = ItemManager.getItems().get(modelRow);

        // EDIT FORM
        JTextField nameField =
            new JTextField(item.getItemName());

        JComboBox<String> categoryBox =
            new JComboBox<>(new String[]{
                "Electronics", "Books", "Clothing",
                "Accessories", "Documents", "Other"
            });

        categoryBox.setSelectedItem(item.getCategory());

        JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Lost", "Found"});

        typeBox.setSelectedItem(item.getType());

        JTextField locationField =
            new JTextField(item.getLocation());

        JTextField dateField =
            new JTextField(item.getDate());

        JTextArea descriptionArea =
            new JTextArea(4, 20);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        descriptionArea.setText(item.getDescription());

        JPanel form = new JPanel(
            new GridLayout(0, 2, 10, 12)
        );

        form.add(new JLabel("Item Name:"));
        form.add(nameField);

        form.add(new JLabel("Category:"));
        form.add(categoryBox);

        form.add(new JLabel("Type:"));
        form.add(typeBox);

        form.add(new JLabel("Location:"));
        form.add(locationField);

        form.add(new JLabel("Date:"));
        form.add(dateField);

        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
            this,
            form,
            "Edit Item Report",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String date = dateField.getText().trim();

        if (name.isEmpty()
                || location.isEmpty()
                || date.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Name, location and date are required!",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Create updated item
        Item updatedItem = new Item(
            name,
            categoryBox.getSelectedItem().toString(),
            typeBox.getSelectedItem().toString(),
            location,
            date,
            descriptionArea.getText().trim(),
            item.getStatus()
        );

        // Replace old item
        ItemManager.getItems().set(modelRow, updatedItem);

        // Save permanently
        ItemManager.saveItems();

        refreshItems();

        JOptionPane.showMessageDialog(
            this,
            "Report updated successfully!"
        );
    }

    // MARK AS RECOVERED
    private void markAsRecovered() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item first!"
            );

            return;
        }

        int modelRow =
            table.convertRowIndexToModel(selectedRow);

        Item item = ItemManager.getItems().get(modelRow);

        if (item.getStatus().equalsIgnoreCase("Recovered")) {

            JOptionPane.showMessageDialog(
                this,
                "This item is already recovered!"
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Mark " + item.getItemName() + " as recovered?",
            "Confirm Recovery",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            item.setStatus("Recovered");

            ItemManager.saveItems();

            refreshItems();

            JOptionPane.showMessageDialog(
                this,
                "Item successfully marked as recovered!"
            );
        }
    }

    // DELETE ITEM
    private void deleteItem() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item first!"
            );

            return;
        }

        int modelRow =
            table.convertRowIndexToModel(selectedRow);

        Item item = ItemManager.getItems().get(modelRow);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete "
                + item.getItemName() + "?",
            "Delete Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            ItemManager.getItems().remove(modelRow);

            ItemManager.saveItems();

            refreshItems();

            JOptionPane.showMessageDialog(
                this,
                "Report deleted successfully!"
            );
        }
    }

    // SEARCH AND FILTER
    private void applyFilters() {

        String searchText = searchField.getText().trim();

        String selectedFilter =
            filterBox.getSelectedItem().toString();

        String searchPattern =
            "(?i).*" + Pattern.quote(searchText) + ".*";

        RowFilter<DefaultTableModel, Integer> filter =
            new RowFilter<DefaultTableModel, Integer>() {

                @Override
                public boolean include(
                    Entry<? extends DefaultTableModel,
                    ? extends Integer> entry
                ) {

                    String itemName = entry.getStringValue(0);
                    String itemType = entry.getStringValue(2);

                    boolean matchesSearch =
                        itemName.matches(searchPattern);

                    boolean matchesType =
                        selectedFilter.equals("All Items")
                        || itemType.equalsIgnoreCase(selectedFilter);

                    return matchesSearch && matchesType;
                }
            };

        sorter.setRowFilter(filter);
    }
}