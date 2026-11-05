package ui;

import javax.swing.*;
import java.awt.*;

import utils.ItemManager;

public class Statistics extends JPanel {


private JLabel totalLabel;
private JLabel lostLabel;
private JLabel foundLabel;
private JLabel recoveredLabel;
private JLabel pendingLabel;

private JPanel chartPanel;

private final Color TEXT = new Color(35, 50, 80);

public Statistics() {

    setLayout(new BorderLayout(20, 20));
    setBackground(new Color(242, 245, 250));

    setBorder(
        BorderFactory.createEmptyBorder(25, 35, 25, 35)
    );

    // Heading
    JLabel title = new JLabel("Statistics & Reports");
    title.setFont(new Font("Segoe UI", Font.BOLD, 27));
    title.setForeground(TEXT);

    JLabel subtitle = new JLabel(
        "Overview of lost, found and recovered campus items."
    );

    subtitle.setFont(
        new Font("Segoe UI", Font.PLAIN, 13)
    );
    subtitle.setForeground(new Color(120, 130, 145));

    JPanel headingPanel = new JPanel();
    headingPanel.setLayout(
        new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
    );
    headingPanel.setOpaque(false);

    headingPanel.add(title);
    headingPanel.add(Box.createVerticalStrut(5));
    headingPanel.add(subtitle);

    // Statistics cards
    JPanel cards = new JPanel(
        new GridLayout(2, 3, 15, 15)
    );
    cards.setOpaque(false);

    totalLabel = addCard(
        cards, "TOTAL ITEMS", new Color(65, 105, 225)
    );

    lostLabel = addCard(
        cards, "LOST ITEMS", new Color(230, 126, 34)
    );

    foundLabel = addCard(
        cards, "FOUND ITEMS", new Color(39, 174, 96)
    );

    recoveredLabel = addCard(
        cards, "RECOVERED ITEMS", new Color(142, 68, 173)
    );

    pendingLabel = addCard(
        cards, "PENDING ITEMS", new Color(220, 70, 70)
    );

    // Chart
    chartPanel = new JPanel() {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            int lost = ItemManager.getLostItems();
            int found = ItemManager.getFoundItems();

            int total = lost + found;

            int chartWidth = Math.max(
                100, getWidth() - 60
            );

            int x = 25;
            int y = 85;
            int height = 28;

            // Chart heading
            g2.setColor(TEXT);
            g2.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
            );

            g2.drawString("Lost vs Found Items", 25, 35);

            g2.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
            );

            g2.setColor(new Color(130, 140, 155));

            g2.drawString(
                "Comparison of reported items",
                25, 55
            );

            // Background bar
            g2.setColor(new Color(235, 238, 245));

            g2.fillRoundRect(
                x, y, chartWidth, height, 12, 12
            );

            // Calculate proportions
            int lostWidth = total == 0 ? 0 :
                (int) (
                    chartWidth * (lost / (double) total)
                );

            int foundWidth = chartWidth - lostWidth;

            // Lost section
            if (lostWidth > 0) {

                g2.setColor(
                    new Color(230, 126, 34)
                );

                g2.fillRoundRect(
                    x, y, lostWidth, height, 12, 12
                );
            }

            // Found section
            if (foundWidth > 0) {

                g2.setColor(
                    new Color(39, 174, 96)
                );

                g2.fillRoundRect(
                    x + lostWidth, y,
                    foundWidth, height, 12, 12
                );
            }

            // Legend
            g2.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
            );

            g2.setColor(
                new Color(230, 126, 34)
            );

            g2.fillOval(27, 145, 12, 12);

            g2.setColor(TEXT);
            g2.drawString("Lost: " + lost, 47, 156);

            g2.setColor(
                new Color(39, 174, 96)
            );

            g2.fillOval(170, 145, 12, 12);

            g2.setColor(TEXT);
            g2.drawString("Found: " + found, 190, 156);

            g2.dispose();
        }
    };

    chartPanel.setBackground(Color.WHITE);

    chartPanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(225, 230, 240)
            ),
            BorderFactory.createEmptyBorder(
                10, 10, 10, 10
            )
        )
    );

    // Add chart to sixth card position
    cards.add(chartPanel);

    JPanel contentPanel = new JPanel(
        new BorderLayout(0, 20)
    );

    contentPanel.setOpaque(false);

    contentPanel.add(headingPanel, BorderLayout.NORTH);
    contentPanel.add(cards, BorderLayout.CENTER);

    add(contentPanel, BorderLayout.CENTER);

    refreshStatistics();
}

// Create statistic card
private JLabel addCard(
    JPanel panel,
    String title,
    Color accent
) {

    JPanel card = new JPanel(
        new GridLayout(2, 1, 5, 5)
    );

    card.setBackground(Color.WHITE);

    card.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(
                0, 4, 0, 0, accent
            ),
            BorderFactory.createEmptyBorder(
                15, 15, 15, 15
            )
        )
    );

    JLabel heading = new JLabel(title);

    heading.setFont(
        new Font("Segoe UI", Font.BOLD, 12)
    );

    heading.setForeground(
        new Color(120, 130, 145)
    );

    JLabel value = new JLabel("0");

    value.setFont(
        new Font("Segoe UI", Font.BOLD, 30)
    );

    value.setForeground(accent);

    card.add(heading);
    card.add(value);

    panel.add(card);

    return value;
}

// Refresh statistics
public void refreshStatistics() {

    int total = ItemManager.getTotalItems();
    int lost = ItemManager.getLostItems();
    int found = ItemManager.getFoundItems();
    int recovered = ItemManager.getRecoveredItems();

    totalLabel.setText(String.valueOf(total));
    lostLabel.setText(String.valueOf(lost));
    foundLabel.setText(String.valueOf(found));
    recoveredLabel.setText(String.valueOf(recovered));
    pendingLabel.setText(
        String.valueOf(Math.max(0, total - recovered))
    );

    chartPanel.repaint();
}


}
