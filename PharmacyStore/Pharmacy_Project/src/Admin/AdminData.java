package Admin;

import Pharmacy_System.*;
import Users.*;
import java.util.*;
import java.sql.*;

public class AdminData {
    private static Scanner sc = new Scanner(System.in);
    Connection con;

    public static boolean adminLogin(PharmacySystem ph) throws Exception {
        int count = 0;
        while (true) {
            System.out.print("Enter admin username: ");
            String username = sc.next();
            System.out.print("Enter admin password: ");
            String password = sc.next();

            if ((username.equalsIgnoreCase("Krish") && password.equalsIgnoreCase("Krish@152"))
                    || (username.equalsIgnoreCase("Smit") && password.equalsIgnoreCase("Smit@178"))
                    || (username.equalsIgnoreCase("Kunj") && password.equalsIgnoreCase("Kunj@153"))
                    || (username.equalsIgnoreCase("Manthan") && password.equalsIgnoreCase("Manthan@177"))
                    || (username.equalsIgnoreCase("Rishi") && password.equalsIgnoreCase("Rishi@165"))) {
                System.out.println(username + " logged in successfully.");
                adminDashboard(ph);
                return true;
            } else {
                int c = 3 - count - 1;
                System.out.println("Invalid admin credentials. Please try again. Attempts Remaining - " + c);
                count++;
                if (count == 3) {
                    System.out.println(
                            "You have exceeded the maximum number of attempts.");
                    return false;
                }
            }
        }
    }

    // -> Admin Dashboard...
    public static void adminDashboard(PharmacySystem ph) throws Exception {
        String input;
        int choice = 0;
        do {
            System.out.println("=================== Admin ===================");
            System.out.println("-- Welcome To The Authorized Admin Section --");
            System.out.println("1. Add Medicine"); // add medicine using sql procedure.
            System.out.println("2. Update Medicine / Any Component"); // update medicine using sql query.
            System.out.println("3. Delete Medicine"); // delete medicine using sql query.
            System.out.println("4. View All Medicine"); // view medicine using sql procedure.
            System.out.println("5. Search Medicine By Name"); // view medicine by id using sql query.
            System.out.println("6. View All PurchaseHistory"); // view all purchase_history using sql query.
            System.out.println("7. Search Particular PurchaseHistory By Name"); // view History using sql query.
            System.out.println("8. View Last Purchase History"); // last purchase history using sql query
                                                                 // also use orderby in query)
            System.out.println("9. View All Seller"); // seller using sql query.
            System.out.println("10. View All Users."); // users using sql query.
            System.out.println("11. View Last Signed Up User"); // view who last signup user using stack.
            System.out.println("12. Remove Last Signed Up User"); // delete last signup user using stack.
            System.out.println("13. Add User");
            System.out.println("14. Exit");
            System.out.println("-----------------------------");
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
                    addMed(ph);
                    break;
                case 2:
                    updateMed(ph);
                    break;
                case 3:
                    deleteMed(ph);
                    break;
                case 4:
                    viewAllMedicine(ph);
                    break;
                case 5:
                    searchMed(ph);
                case 6:
                    ph.viewAllPurchaseHistory();
                    break;
                case 7:
                    UserData.viewPurchaseHistory(ph);
                    break;
                case 8:
                    ph.viewLastPurchaseHistory();
                    break;
                case 9:
                    ph.viewAllSellers();
                    break;
                case 10:
                    ph.viewAllUsers();
                    break;
                case 11:
                    ph.viewLastSignedUpUser();
                    break;
                case 12:
                    ph.removeLastSignedUpUser();
                    break;
                case 13:
                    UserData.signup(ph);
                    break;
                case 14:
                    System.out.println("Exiting admin portal");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 14);
    }

    // -> Create Medicine...
    public static void addMed(PharmacySystem ph) throws Exception {
        try {
            sc.nextLine();
            System.out.print("Enter medicine name: ");
            String name = sc.nextLine();
            System.out.print("Enter medicine description: ");
            String description = sc.nextLine();
            System.out.print("Enter medicine category: ");
            String category = sc.nextLine();
            System.out.print("Enter medicine price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter medicine stock quantity: ");
            int stockQuantity = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter medicine manufacturer: ");
            String manufacturer = sc.nextLine();
            System.out.print("Enter medicine expiration date (yyyy-mm-dd): ");
            String expirationDate = sc.nextLine();

            ph.addMed(name, description, category, price, stockQuantity, manufacturer, expirationDate);
        } catch (Exception e) {
            System.out.println("please enter valid input");

            sc.next();
        }
    }

    // -> Update Medicine...
    public static void updateMed(PharmacySystem ph) throws Exception {
        try {
            sc.nextLine();
            System.out.print("Enter medicine ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new medicine name (or press enter to skip): ");
            String name = sc.nextLine();
            if (name.isEmpty()) {
                name = null;
            }

            System.out.print("Enter new medicine description (or press enter to skip): ");
            String description = sc.nextLine();
            if (description.isEmpty()) {
                description = null;
            }

            System.out.print("Enter new medicine category (or press enter to skip): ");
            String category = sc.nextLine();
            if (category.isEmpty()) {
                category = null;
            }

            System.out.print("Enter new medicine price (or press enter to skip): ");
            String priceStr = sc.next();
            double price = priceStr.isEmpty() ? 0 : Double.parseDouble(priceStr);
            sc.nextLine();

            System.out.print("Enter new medicine stock quantity (or press enter to skip): ");
            String stockQuantityStr = sc.next();
            int stockQuantity = stockQuantityStr.isEmpty() ? 0 : Integer.parseInt(stockQuantityStr);
            sc.nextLine();

            System.out.print("Enter new medicine manufacturer (or press enter to skip): ");
            String manufacturer = sc.nextLine();
            if (manufacturer.isEmpty()) {
                manufacturer = null;
            }

            System.out.print("Enter new medicine expiration date (yyyy-mm-dd) (or press enter to skip): ");
            String expirationDate = sc.nextLine();
            if (expirationDate.isEmpty()) {
                expirationDate = null;
            }

            ph.updateMed(id, name, description, category, price, stockQuantity, manufacturer, expirationDate);
        } catch (Exception e) {
            System.out.println("please enter valid input");
            sc.next();
        }
    }

    // -> Delete Medicine...
    private static void deleteMed(PharmacySystem ph) throws Exception {
        System.out.print("Enter medicine ID to delete: ");
        int id = sc.nextInt();
        ph.deleteMed(id);
    }

    // -> View All Medicine...
    public static void viewAllMedicine(PharmacySystem ph) throws Exception {
        ph.viewAllMedicine();
    }

    public static void viewAllAvailableMedicine(PharmacySystem ph) throws Exception {
        ph.viewAllAvailableMedicine();
    }

    // -> Search Medicine...
    public static void searchMed(PharmacySystem ph) throws Exception {
        ph.searchMed();
    }

}
