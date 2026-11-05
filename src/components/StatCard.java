package components;

import java.awt.*;
import javax.swing.*;

public class StatCard extends JPanel {

    private JLabel valueLabel;

    public StatCard(String title, String value, Color accentColor) {

        setLayout(new GridLayout(2, 1, 5, 5));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 235)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(90, 100, 120));

        valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 30));
        valueLabel.setForeground(accentColor);

        add(titleLabel);
        add(valueLabel);
    }

    // Update the card value
    public void setValue(String value) {
        valueLabel.setText(value);
    }
}