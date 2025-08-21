package Users;

import java.util.*;

import java.sql.*;

import Admin.*;
import Pharmacy_System.*;

public class UserData {
    static Scanner sc = new Scanner(System.in);
    private static String userName;
    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dburl = "jdbc:mysql://localhost:3306/pharmacystore";
            String dbuser = "root";
            String dbpass = "";
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    // --> User_login.
    public static void userLogin(PharmacySystem ph) throws Exception {
        String input;
        int choice = 0;
        do {
            System.out.println("---------- User Portal ----------");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Logout");
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
                    login(ph);
                    break;
                case 2:
                    signup(ph);
                    break;
                case 3:
                    System.out.println("Logging out. Goodbye!");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }

    // --> 1.Login Method
    private static boolean login(PharmacySystem ph) throws Exception {
        System.out.print("Enter Username: ");
        userName = sc.next();
        System.out.print("Enter Password: ");
        String password = sc.next();

        if (ph.validateUser(userName, password)) {
            System.out.println("Login successful!");
            userDashboard(ph);
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }

    public static String getUserName() {
        return userName;
    }

    // -->2. Sign_up Method
    public static void signup(PharmacySystem ph) {
        Thread signupThread = new Thread(new SignupUser(ph));
        signupThread.start();

        try {
            signupThread.join();
        } catch (InterruptedException e) {
            System.out.println("Signup interrupted: " + e.getMessage());
        }
    }

    // ---> User Dashboard.....
    private static void userDashboard(PharmacySystem ph) throws Exception {
        String input;
        int choice = 0;
        while (true) {
            System.out.println("=================== User Dashboard ===================");
            System.out.println("1. Purchase Medicine");
            System.out.println("2. View Purchase History");
            System.out.println("3. Search Medicine by Name");
            System.out.println("4. Filter Medicines by Category");
            System.out.println("5. View Medicine Details");
            System.out.println("6. View All Available Medicine");
            System.out.println("7. Logout");
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
                    purchaseMedicine(ph);
                    break;
                case 2:
                    viewPurchaseHistory(ph);
                    break;
                case 3:
                    AdminData.searchMed(ph);
                    break;
                case 4:
                    filterMedicinesByCategory(ph);
                    break;
                case 5:
                    viewMedicineDescription(ph);
                    break;
                case 6:
                    AdminData.viewAllAvailableMedicine(ph);
                    break;
                case 7:
                    System.out.println("Logging out. Goodbye!");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // -> Purchase Medicine...
    private static void purchaseMedicine(PharmacySystem ph) throws Exception {
        List<MedicinePurchase> medicinePurchases = new ArrayList<>();

        while (true) {
            System.out.print("Enter medicine ID to purchase: ");
            int medicineId = sc.nextInt();
            sc.nextLine();

            String query = "SELECT * FROM medicines WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, medicineId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int stockQuantity = rs.getInt("stock_quantity");
                if (stockQuantity == 0) {
                    System.out.println("Medicine not available. Please choose another medicine.");
                    continue;
                }

                System.out.print("Enter quantity to purchase: ");
                int quantity = sc.nextInt();
                sc.nextLine();

                if (quantity > stockQuantity) {
                    System.out.println("Not enough stock available. Please enter a quantity less than or equal to "
                            + stockQuantity);
                    continue;
                }

                MedicinePurchase medicinePurchase = new MedicinePurchase(medicineId, quantity);
                medicinePurchases.add(medicinePurchase);

                System.out.print("Do you want to purchase other medicines? (yes/no): ");
                String Other = sc.next();
                if (Other.equalsIgnoreCase("no")) {
                    break;
                }
            } else {
                System.out.println("Medicine not found. Please enter a valid medicine ID.");
            }
        }

        try {
            String userName = getUserName(); // get the username from the login method
            ph.purchaseMedicine(userName, medicinePurchases);
            System.out.println("Medicine purchased successfully!");
        } catch (Exception e) {
            System.out.println("Error purchasing medicine: " + e.getMessage());
        }
    }

    public static void viewPurchaseHistory(PharmacySystem ph) {
        System.out.print("Enter Name : ");
        String name = sc.next();
        try {
            ph.viewPurchaseHistory(name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // --> Filter Medicine By Categories....
    private static void filterMedicinesByCategory(PharmacySystem ph) throws Exception {
        ph.categories();
        sc.nextLine();
        System.out.print("Enter Category : ");
        String category = sc.nextLine();
        try {
            ph.filterMedicinesByCategory(category);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // --> Medicine Details...
    private static void viewMedicineDescription(PharmacySystem ph) throws Exception {
        ph.nameOfMed();
        sc.nextLine();
        System.out.print("Enter Medicine Name : ");
        String medicineName = sc.nextLine();
        ph.viewMedicineDescription(medicineName);
    }

}
