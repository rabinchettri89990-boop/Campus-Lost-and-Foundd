package ui;

import java.awt.*;
import javax.swing.*;

public class Header extends JPanel {

    public Header() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(850, 85));

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(
                0, 0, 1, 0, new Color(225, 230, 240)
            ),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        // Left section
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel title = new JLabel("Dashboard");

        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(new Color(35, 50, 80));

        JLabel subtitle = new JLabel(
            "Campus Lost & Found Management System"
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(130, 140, 155));

        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(3));
        leftPanel.add(subtitle);

        // Right section
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, Student");

        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(75, 90, 115));

        rightPanel.add(userLabel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }
}