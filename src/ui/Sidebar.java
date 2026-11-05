package ui;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class Sidebar extends JPanel {

    private JButton activeButton;

    private final Color SIDEBAR_COLOR = new Color(22, 35, 65);
    private final Color HOVER_COLOR = new Color(43, 62, 100);
    private final Color ACTIVE_COLOR = new Color(65, 105, 225);

    public Sidebar(Consumer<String> pageListener) {

        setPreferredSize(new Dimension(235, 700));
        setBackground(SIDEBAR_COLOR);
        setLayout(new BorderLayout());

        // Logo section
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_COLOR);
        logoPanel.setPreferredSize(new Dimension(235, 110));
        logoPanel.setLayout(new GridBagLayout());

        JLabel logo = new JLabel("CAMPUS FIND");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 23));

        logoPanel.add(logo);

        add(logoPanel, BorderLayout.NORTH);

        // Navigation section
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(SIDEBAR_COLOR);
        menuPanel.setLayout(new GridLayout(7, 1, 0, 8));

        menuPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        );

        String[] menuItems = {
            "Dashboard",
            "Report Lost Item",
            "Report Found Item",
            "All Items",
            "My Reports",
            "Statistics",
            "Exit"
        };

        for (String item : menuItems) {

            JButton button = new JButton(item);

            button.setForeground(Color.WHITE);
            button.setBackground(SIDEBAR_COLOR);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));

            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);

            button.setBorder(
                BorderFactory.createEmptyBorder(12, 18, 12, 10)
            );

            button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
            );

            // Hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (button != activeButton) {
                        button.setBackground(HOVER_COLOR);
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (button != activeButton) {
                        button.setBackground(SIDEBAR_COLOR);
                    }
                }
            });

            button.addActionListener(e -> {

                if (item.equals("Exit")) {

                    int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to exit?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }

                } else {

                    // Update active button
                    if (activeButton != null) {
                        activeButton.setBackground(SIDEBAR_COLOR);
                    }

                    activeButton = button;
                    activeButton.setBackground(ACTIVE_COLOR);

                    pageListener.accept(item);
                }
            });

            menuPanel.add(button);

            // Dashboard active by default
            if (item.equals("Dashboard")) {
                activeButton = button;
                button.setBackground(ACTIVE_COLOR);
            }
        }

        add(menuPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
            "  Campus Lost & Found System  ",
            JLabel.CENTER
        );

        footer.setForeground(new Color(170, 180, 200));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footer.setBorder(
            BorderFactory.createEmptyBorder(15, 5, 15, 5)
        );

        add(footer, BorderLayout.SOUTH);
    }
}