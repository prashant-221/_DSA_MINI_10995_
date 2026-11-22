
import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Double> prices = new ArrayList<>();
    static String FILE_NAME = "prices.txt";

    // Add Price
    static void addPrice(Scanner sc) {
        try {
            System.out.print("Enter Price: ");
            double price = Double.parseDouble(sc.nextLine());

            if (price <= 0) {
                System.out.println("Invalid price. Enter a positive number.");
                return;
            }

            prices.add(price);
            System.out.printf("Price added. Day %d: %.2f%n",
                    prices.size(), price);

        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Enter a positive number.");
        }
    }

    // Display Prices
    static void displayPrices() {
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        for (int i = 0; i < prices.size(); i++) {
            System.out.printf("Day %d: %.2f%n",
                    i + 1, prices.get(i));
        }
    }

    // Analysis
    static void analyzePrices() {
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        double high = prices.get(0);
        double low = prices.get(0);
        double sum = 0;
        int highDay = 1, lowDay = 1;

        for (int i = 0; i < prices.size(); i++) {
            double p = prices.get(i);

            if (p > high) {
                high = p;
                highDay = i + 1;
            }

            if (p < low) {
                low = p;
                lowDay = i + 1;
            }

            sum += p;
        }

        System.out.printf("Highest: %.2f (Day %d)%n", high, highDay);
        System.out.printf("Lowest: %.2f (Day %d)%n", low, lowDay);
        System.out.printf("Average: %.2f%n", sum / prices.size());
    }

    // Sort Prices using Bubble Sort
    static void sortPrices() {
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        ArrayList<Double> sorted = new ArrayList<>(prices);

        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - i - 1; j++) {

                if (sorted.get(j) > sorted.get(j + 1)) {
                    double temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }

        System.out.println("Sorted Prices:");
        for (double p : sorted) {
            System.out.printf("%.2f ", p);
        }
        System.out.println();
    }

    // Binary Search
    static void binarySearch(Scanner sc) {
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        System.out.print("Enter price to search: ");

        try {
            double key = Double.parseDouble(sc.nextLine());

            if (key <= 0) {
                System.out.println("Invalid price. Enter a positive number.");
                return;
            }

            ArrayList<Double> sorted = new ArrayList<>(prices);
            Collections.sort(sorted);

            int left = 0;
            int right = sorted.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;

                if (sorted.get(mid) == key) {
                    System.out.println("Price " + key +
                            " found at position " + (mid + 1));
                    return;
                }

                if (sorted.get(mid) < key) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            System.out.println("Price not found.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
        }
    }

    // Load File
    static void loadPrices() {
        try {
            File file = new File(FILE_NAME);

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    try {
                        double p = Double.parseDouble(line);
                        if (p > 0) {
                            prices.add(p);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid file record skipped.");
                    }
                }

                br.close();
            }

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }

    // Save File
    static void savePrices() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));

            for (double p : prices) {
                pw.println(p);
            }

            pw.close();
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // Main Method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        loadPrices();

        while (true) {

            System.out.println("\n===== STOCK PRICE ANALYZER =====");
            System.out.println("1. Add Price");
            System.out.println("2. Display Prices");
            System.out.println("3. Analysis");
            System.out.println("4. Sort Prices");
            System.out.println("5. Binary Search");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {

                case 1:
                    addPrice(sc);
                    break;

                case 2:
                    displayPrices();
                    break;

                case 3:
                    analyzePrices();
                    break;

                case 4:
                    sortPrices();
                    break;

                case 5:
                    binarySearch(sc);
                    break;

                case 6:
                    savePrices();
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
