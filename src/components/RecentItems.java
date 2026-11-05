package components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import models.Item;
import utils.ItemManager;

public class RecentItems extends JPanel {

    private DefaultTableModel model;

    public RecentItems() {

        setLayout(new BorderLayout(10, 15));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 235)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel("Recent Items");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(35, 50, 80));

        String[] columns = {
            "Item Name", "Category", "Type", "Location", "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );

        table.getTableHeader().setBackground(
            new Color(230, 235, 245)
        );

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshItems();
    }

    public void refreshItems() {

        model.setRowCount(0);

        for (Item item : ItemManager.getItems()) {

            model.addRow(new Object[] {
                item.getItemName(),
                item.getCategory(),
                item.getType(),
                item.getLocation(),
                item.getStatus()
            });
        }
    }
}