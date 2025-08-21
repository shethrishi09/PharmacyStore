package Seller;

import Admin.*;
import Pharmacy_System.*;
import java.util.*;
import java.io.*;
import java.sql.*;

public class SellerData {
    static Scanner sc = new Scanner(System.in);

    public static void sellerLogin(PharmacySystem ph) throws Exception {
        String input;
        int choice = 0;
        do {
            System.out.println("------------------- Seller Portal -------------------");
            System.out.println("1. Seller Login");
            System.out.println("2. New Seller Signup");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            input = sc.next();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please Enter A Valid Number.");
                continue;
            }

            switch (choice) {
                case 1:
                    loginSeller(ph);
                    break;
                case 2:
                    sellerSignup(ph);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (choice != 3);
    }

    // -> Seller Signup...
    private static void sellerSignup(PharmacySystem ph) throws Exception {
        sc.nextLine();
        System.out.print("Enter seller username: ");
        String username = sc.nextLine();
        System.out.print("Enter seller password: ");
        String password = sc.nextLine();
        System.out.print("Enter seller name: ");
        String name = sc.nextLine();
        System.out.print("Enter seller email: ");
        String email = sc.nextLine();
        while (!email.endsWith("@gmail.com")) {
            System.out.println("Invalid email. Please enter a valid Gmail address.");
            System.out.print("Enter email: ");
            email = sc.next();
        }
        System.out.print("Enter seller phone: ");
        String phone = sc.nextLine();
        while (phone.length() != 10) {
            System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
            System.out.print("Enter phone number: ");
            phone = sc.next();
        }

        // Check if seller already exists
        File file = new File("sellers.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(username)) {
                System.out.println("Seller already exists.");
                return;
            }
        }

        // Add seller to file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(username + "," + password + "," + name + "," + email + "," + phone + "\n");
        }

        ph.createSellerTable(name, username, password, email, phone);
        System.out.println("Seller signed up successfully.");
        System.out.println("Next Login Please And Use Feature..");
    }

    // -> Seller Login...
    private static void loginSeller(PharmacySystem ph) throws Exception {
        sc.nextLine();
        System.out.print("Enter seller username: ");
        String username = sc.nextLine();
        System.out.print("Enter seller password: ");
        String password = sc.nextLine();

        File file = new File("sellers.txt");
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                System.out.println("Seller logged in successfully.");
                sellerDashboard(ph);
                bufferedReader.close();
                return;
            }
        }

        System.out.println("Invalid seller username or password.");
        bufferedReader.close();
    }

    private static void sellerDashboard(PharmacySystem ph) throws Exception {
        String input;
        int choice = 0;
        do {
            System.out.println("============== Seller Portal ==============");
            System.out.println("-- Welcome To The Authorized Seller Section --");
            System.out.println("1. Add New Medicine"); // Add New Medicines.
            System.out.println("2. Update Medicine"); // Update Medicine By Id
            System.out.println("3. Add Into Stock"); // Particular Medicine's Stock will be added By id.
            System.out.println("4. Update Stock"); // Using id also
            System.out.println("5. View Stock"); // View All Medicines
            System.out.println("6. Logout");
            System.out.println("--------------------");
            System.out.print("Enter your choice: ");
            input = sc.next();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Valid Number.");
                continue;
            }

            switch (choice) {
                case 1:
                    AdminData.addMed(ph);
                    break;
                case 2:
                    AdminData.updateMed(ph);
                    break;
                case 3:
                    addStock(ph);
                    break;
                case 4:
                    updateStock(ph);
                    break;
                case 5:
                    ph.viewStock();
                    break;
                case 6:
                    System.out.println("Logging out. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }

    // --> AddStock....
    private static void addStock(PharmacySystem ph) throws Exception {
        System.out.print("Enter medicine ID to add stock: ");
        int medicineId = sc.nextInt();
        System.out.print("Enter quantity to add: ");
        int quantity = sc.nextInt();
        ph.addStock(medicineId, quantity);
        System.out.println("Stock Will Be Inserted...");
    }

    // --> Update added Stock...
    private static void updateStock(PharmacySystem ph) throws SQLException {
        System.out.print("Enter medicine ID to update stock: ");
        int medicineId = sc.nextInt();
        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        ph.updateStock(medicineId, quantity);
        System.out.println("Stock Will Be Updated Of Medicine " + medicineId + "...");
    }
}
