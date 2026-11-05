package utils;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

import models.Item;

public class ItemManager {

    private static ArrayList<Item> items = new ArrayList<>();

    private static final Path DATA_FILE = getDataFile();

    // Find the data folder
    private static Path getDataFile() {

        Path current = Paths.get("").toAbsolutePath();

        if (current.getFileName().toString().equalsIgnoreCase("SRC")) {
            current = current.getParent();
        }

        return current.resolve("data").resolve("items.dat");
    }

    // Load saved items when application starts
    static {
        loadItems();
    }

    // Add a new item
    public static void addItem(Item item) {
        items.add(item);
        saveItems();
    }

    // Get all items
    public static ArrayList<Item> getItems() {
        return items;
    }

    // Save items to file
    public static void saveItems() {

        try {
            Files.createDirectories(DATA_FILE.getParent());

            ObjectOutputStream output =
                new ObjectOutputStream(
                    new FileOutputStream(DATA_FILE.toFile())
                );

            output.writeObject(items);
            output.close();

            System.out.println("Items saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving items: " + e.getMessage());
        }
    }

    // Load items from file
    @SuppressWarnings("unchecked")
    public static void loadItems() {

        if (!Files.exists(DATA_FILE)) {
            System.out.println("No previous records found.");
            return;
        }

        try {
            ObjectInputStream input =
                new ObjectInputStream(
                    new FileInputStream(DATA_FILE.toFile())
                );

            items = (ArrayList<Item>) input.readObject();

            input.close();

            System.out.println("Items loaded successfully!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
    }

    public static int getTotalItems() {
        return items.size();
    }

    public static int getLostItems() {

        int count = 0;

        for (Item item : items) {
            if (item.getType().equalsIgnoreCase("Lost")) {
                count++;
            }
        }

        return count;
    }

    public static int getFoundItems() {

        int count = 0;

        for (Item item : items) {
            if (item.getType().equalsIgnoreCase("Found")) {
                count++;
            }
        }

        return count;
    }

    public static int getRecoveredItems() {

        int count = 0;

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Recovered")) {
                count++;
            }
        }

        return count;
    }
}