package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import models.Item;
import utils.ItemManager;

public class MyReports extends JPanel {


private DefaultTableModel model;
private JTable table;
private JTextField searchField;
private JComboBox<String> filterBox;
private TableRowSorter<DefaultTableModel> sorter;

private final Color TEXT = new Color(35, 50, 80);

public MyReports() {

    setLayout(new BorderLayout(15, 20));
    setBackground(new Color(242, 245, 250));

    setBorder(
        BorderFactory.createEmptyBorder(25, 35, 25, 35)
    );

    // Heading
    JLabel title = new JLabel("My Reports");
    title.setFont(new Font("Segoe UI", Font.BOLD, 27));
    title.setForeground(TEXT);

    JLabel subtitle = new JLabel(
        "View and manage all your reported lost and found items."
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

    // Search and filter panel
    JPanel controlPanel = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 12, 12)
    );

    controlPanel.setBackground(Color.WHITE);

    controlPanel.setBorder(
        BorderFactory.createLineBorder(
            new Color(225, 230, 240)
        )
    );

    JLabel searchLabel = new JLabel("Search Item:");
    searchLabel.setFont(
        new Font("Segoe UI", Font.BOLD, 13)
    );

    searchField = new JTextField(18);
    searchField.setFont(
        new Font("Segoe UI", Font.PLAIN, 13)
    );
    searchField.setPreferredSize(
        new Dimension(200, 35)
    );
    searchField.setToolTipText("Search by item name");

    JLabel filterLabel = new JLabel("Filter:");
    filterLabel.setFont(
        new Font("Segoe UI", Font.BOLD, 13)
    );

    filterBox = new JComboBox<>(
        new String[]{"All Reports", "Lost", "Found"}
    );

    filterBox.setFont(
        new Font("Segoe UI", Font.PLAIN, 13)
    );
    filterBox.setPreferredSize(
        new Dimension(140, 35)
    );

    controlPanel.add(searchLabel);
    controlPanel.add(searchField);
    controlPanel.add(filterLabel);
    controlPanel.add(filterBox);

    JPanel topPanel = new JPanel(new BorderLayout(0, 15));
    topPanel.setOpaque(false);

    topPanel.add(headingPanel, BorderLayout.NORTH);
    topPanel.add(controlPanel, BorderLayout.CENTER);

    // Table
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

    table.setRowHeight(42);
    table.setFont(
        new Font("Segoe UI", Font.PLAIN, 13)
    );

    table.setForeground(TEXT);
    table.setBackground(Color.WHITE);
    table.setSelectionBackground(
        new Color(220, 232, 255)
    );
    table.setSelectionForeground(TEXT);

    table.setShowVerticalLines(false);
    table.setGridColor(new Color(235, 238, 245));
    table.setFillsViewportHeight(true);

    table.getTableHeader().setFont(
        new Font("Segoe UI", Font.BOLD, 13)
    );

    table.getTableHeader().setBackground(
        new Color(230, 235, 245)
    );

    table.getTableHeader().setForeground(TEXT);
    table.getTableHeader().setPreferredSize(
        new Dimension(0, 42)
    );

    sorter = new TableRowSorter<>(model);
    table.setRowSorter(sorter);

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBorder(
        BorderFactory.createLineBorder(
            new Color(225, 230, 240)
        )
    );

    scrollPane.getViewport().setBackground(Color.WHITE);

    // Search listener
    searchField.getDocument().addDocumentListener(
        new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                applyFilters();
            }

            public void removeUpdate(DocumentEvent e) {
                applyFilters();
            }

            public void changedUpdate(DocumentEvent e) {
                applyFilters();
            }
        }
    );

    filterBox.addActionListener(
        e -> applyFilters()
    );

    add(topPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    refreshReports();
}

// Load all reports
public void refreshReports() {

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

// Search and filter
private void applyFilters() {

    String search = searchField.getText()
        .trim().toLowerCase();

    String selectedType =
        filterBox.getSelectedItem().toString();

    RowFilter<DefaultTableModel, Integer> filter =
        new RowFilter<DefaultTableModel, Integer>() {

            @Override
            public boolean include(
                Entry<? extends DefaultTableModel,
                ? extends Integer> entry
            ) {

                String name =
                    entry.getStringValue(0).toLowerCase();

                String type =
                    entry.getStringValue(2);

                boolean matchesSearch =
                    name.contains(search);

                boolean matchesType =
                    selectedType.equals("All Reports")
                    || type.equalsIgnoreCase(selectedType);

                return matchesSearch && matchesType;
            }
        };

    sorter.setRowFilter(filter);
}


}
