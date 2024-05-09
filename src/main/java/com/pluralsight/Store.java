package com.pluralsight;

import com.pluralsight.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        loadInventory("products.csv", inventory);

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    totalAmount = calculateTotal(cart);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.trim().split("\\|");
                if (data.length == 3) {
                    Product product = new Product(data[0], data[1], Double.parseDouble(data[2]));
                    inventory.add(product);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("\nAVAILABLE PRODUCTS ARE:");
        for (Product item : inventory) {
            System.out.println(item.getId() + ") " + item.getName() + " - $" + item.getPrice());
        }
        System.out.println("Enter the Product ID or Name to add to cart, or 'back' to return:");

        while (true) {
            String entry = scanner.nextLine();
            if ("back".equalsIgnoreCase(entry)) {
                break;
            } else {
                Product product = findProductById(entry, inventory);
                if (product == null) {
                    product = findProductByName(entry, inventory);
                }
                if (product != null) {
                    cart.add(product);
                    System.out.println("Product added to cart: " + product.getName() + ". Add another or 'back' to return.");
                } else {
                    System.out.println("Product not found. Try again or 'back' to return");
                }
            }
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Products in your cart:");
        for (Product item : cart) {
            System.out.println(item.getId() + " " + item.getName() + " $" + item.getPrice());
        }
        System.out.println("Total: $" + totalAmount);

        System.out.println("Do you want to proceed to checkout? (Yes/No)");
        String response = scanner.nextLine();
        if ("Yes".equalsIgnoreCase(response)) {
            checkOut(cart, totalAmount, scanner);
        } else {
            System.out.println("Returning to the main menu.");
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty, nothing to check out.");
            return;
        }

        System.out.println("Proceeding to checkout...");
        System.out.println("Do you want to confirm your purchase? (Yes/No)");
        String confirmation = scanner.nextLine();
        if ("Yes".equalsIgnoreCase(confirmation)) {
            System.out.println("Thank you for your purchase!");
            System.out.println("Total Amount: $" + totalAmount);
            cart.clear();
        } else {
            System.out.println("Checkout canceled.");
        }
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product item : inventory) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public static Product findProductByName(String name, ArrayList<Product> inventory) {
        for (Product item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }


            public static double calculateTotal (ArrayList < Product > cart) {
                double total = 0.0;
                for (Product item : cart) {
                    total += item.getPrice();
                }
                return total;
            }
        }

