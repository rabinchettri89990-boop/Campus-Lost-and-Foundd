package ui;

import javax.swing.*;
import java.awt.*;

import components.StatCard;
import components.RecentItems;
import utils.ItemManager;

public class Dashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel pagePanel;

    private StatCard totalCard;
    private StatCard lostCard;
    private StatCard foundCard;
    private StatCard recoveredCard;

    private RecentItems recentItems;
    private AllItems allItems;
    private MyReports myReports;
    private Statistics statistics;

    public Dashboard() {

        setTitle("Campus Lost & Found Management System");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        pagePanel = new JPanel(cardLayout);

        JPanel dashboardPage = createDashboardPage();

        ReportLostItem lostPage =
                new ReportLostItem(this::refreshDashboard);

        ReportFoundItem foundPage =
                new ReportFoundItem(this::refreshDashboard);

        allItems = new AllItems();
        myReports = new MyReports();
        statistics = new Statistics();

        pagePanel.add(dashboardPage, "Dashboard");
        pagePanel.add(lostPage, "Report Lost Item");
        pagePanel.add(foundPage, "Report Found Item");
        pagePanel.add(allItems, "All Items");
        pagePanel.add(myReports, "My Reports");
        pagePanel.add(statistics, "Statistics");

        Sidebar sidebar = new Sidebar(page -> {

            cardLayout.show(pagePanel, page);

            refreshDashboard();
            allItems.refreshItems();
            myReports.refreshReports();
            statistics.refreshStatistics();
        });

        add(sidebar, BorderLayout.WEST);
        add(new Header(), BorderLayout.NORTH);
        add(pagePanel, BorderLayout.CENTER);

        cardLayout.show(pagePanel, "Dashboard");

        refreshDashboard();
    }

    private JPanel createDashboardPage() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));

        mainPanel.setBackground(new Color(242, 245, 250));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Welcome heading
        JLabel welcome = new JLabel(
                "Welcome to Campus Lost & Found!"
        );

        welcome.setFont(new Font("Segoe UI", Font.BOLD, 27));
        welcome.setForeground(new Color(35, 50, 80));

        JLabel subtitle = new JLabel(
                "Manage lost and found belongings easily."
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(110, 120, 140));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);

        headingPanel.add(welcome);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subtitle);

        // Statistics cards
        JPanel statsPanel = new JPanel(
                new GridLayout(1, 4, 18, 18)
        );

        statsPanel.setOpaque(false);

        totalCard = new StatCard(
                "Total Items", "0", new Color(65, 105, 225)
        );

        lostCard = new StatCard(
                "Lost Items", "0", new Color(230, 126, 34)
        );

        foundCard = new StatCard(
                "Found Items", "0", new Color(39, 174, 96)
        );

        recoveredCard = new StatCard(
                "Recovered Items", "0", new Color(142, 68, 173)
        );

        statsPanel.add(totalCard);
        statsPanel.add(lostCard);
        statsPanel.add(foundCard);
        statsPanel.add(recoveredCard);

        JPanel topSection = new JPanel(
                new BorderLayout(15, 25)
        );

        topSection.setOpaque(false);

        topSection.add(headingPanel, BorderLayout.NORTH);
        topSection.add(statsPanel, BorderLayout.CENTER);

        // Recent items section
        recentItems = new RecentItems();

        JPanel content = new JPanel(
                new BorderLayout(20, 25)
        );

        content.setOpaque(false);

        content.add(topSection, BorderLayout.NORTH);
        content.add(recentItems, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);

        return mainPanel;
    }

    public void refreshDashboard() {

        totalCard.setValue(
                String.valueOf(ItemManager.getTotalItems())
        );

        lostCard.setValue(
                String.valueOf(ItemManager.getLostItems())
        );

        foundCard.setValue(
                String.valueOf(ItemManager.getFoundItems())
        );

        recoveredCard.setValue(
                String.valueOf(ItemManager.getRecoveredItems())
        );

        recentItems.refreshItems();
    }
}