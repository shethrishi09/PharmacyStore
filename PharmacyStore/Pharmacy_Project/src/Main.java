import Admin.*;
import Pharmacy_System.*;
import Seller.*;
import Users.*;
import java.sql.*;

import java.util.*;

/*
 * ==========================================================================
 * ========================== Main Class ====================================
 * ==========================================================================
 */
public class Main {

    Connection con;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PharmacySystem ph = new PharmacySystem();
        UserInfo u = new UserInfo();
        List<User> users = u.getAllUsers();
        for (User user : users) {
            ph.createUser(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
                    user.getPhoneNumber());
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dburl = "jdbc:mysql://localhost:3306/pharmacystore";
        String user = "root";
        String pass = "";
        Connection con = DriverManager.getConnection(dburl, user, pass);

        if (con != null) {
            System.out.println("--------------------------------------------");
            System.out.println("-------------- Pharmacy Store --------------");
            System.out.println("--------------------------------------------");

            String input;
            int choice;
            while (true) {
                do {
                    try {
                        System.out.println("----------------");
                        System.out.println("1. Admin Portal");
                        System.out.println("2. Seller Portal");
                        System.out.println("3. User Portal");
                        System.out.println("4. Exit");
                        System.out.println("----------------");
                        System.out.print("Enter your choice: ");
                        input = sc.next();

                        choice = Integer.parseInt(input);

                        if (choice == 1) {
                            AdminData.adminLogin(ph);
                        } else if (choice == 2) {
                            SellerData.sellerLogin(ph);
                        } else if (choice == 3) {
                            UserData.userLogin(ph);
                        } else if (choice == 4) {
                            System.out.println("Thank You For Visiting Our Pharmacy Store.");
                            System.exit(0);
                            break;
                        } else {
                            System.out.println("Invalid choice");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please Enter A Valid Number.");
                    }
                } while (true);
            }
        } else {
            System.out.println("Failed to connect to the database");
        }
        sc.close();
    }
}
