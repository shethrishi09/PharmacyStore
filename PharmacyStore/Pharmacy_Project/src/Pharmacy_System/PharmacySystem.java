package Pharmacy_System;

import java.util.*;
import java.io.*;
import java.sql.Date;
/*
 * ==========================================================================
 * =========================== Pharmacy System ==============================
 * ==========================================================================
 */
import java.sql.*;
import Users.*;
import Admin.*;
import Payment.*;

public class PharmacySystem {
    private Connection con;
    private Map<String, User> users;
    private Map<String, User> usersMap;
    private Stack<User> lastSignedUpUsers;

    public PharmacySystem() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dburl = "jdbc:mysql://localhost:3306/pharmacystore";
        String dbuser = "root";
        String dbpass = "";
        this.users = new HashMap<>();
        this.usersMap = new HashMap<>();
        this.lastSignedUpUsers = new Stack<>();

        con = DriverManager.getConnection(dburl, dbuser, dbpass);
    }

    // -> Create Medicine Using Sql Query...
    public void addMed(String name, String description, String category, double price, int stockQuantity,
            String manufacturer, String expirationDate) throws Exception {

        String query = "call insertMed(?,?,?,?,?,?,?)";
        CallableStatement cst = con.prepareCall(query);
        cst.setString(1, name);
        cst.setString(2, description);
        cst.setString(3, category);
        cst.setDouble(4, price);
        cst.setInt(5, stockQuantity);
        cst.setString(6, manufacturer);
        cst.setString(7, expirationDate);
        cst.executeUpdate();
        System.out.println("Medicine added successfully.");
    }

    // -> Update Medicine Using Sql Query...
    public void updateMed(int id, String name, String description, String category, double price, int stockQuantity,
            String manufacturer, String expirationDate) throws Exception {
        String query = "UPDATE medicines SET ";
        boolean hasUpdate = false;

        if (name != null) {
            query += "name = ?, ";
            hasUpdate = true;
        }
        if (description != null) {
            query += "description = ?, ";
            hasUpdate = true;
        }
        if (category != null) {
            query += "category = ?, ";
            hasUpdate = true;
        }
        if (price != 0) {
            query += "price = ?, ";
            hasUpdate = true;
        }
        if (stockQuantity != 0) {
            query += "stock_quantity = ?, ";
            hasUpdate = true;
        }
        if (manufacturer != null) {
            query += "manufacturer = ?, ";
            hasUpdate = true;
        }
        if (expirationDate != null) {
            query += "expiration_date = ?, ";
            hasUpdate = true;
        }

        if (hasUpdate) {
            query = query.substring(0, query.length() - 2);
            query += " WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            int index = 1;
            if (name != null) {
                pst.setString(index++, name);
            }
            if (description != null) {
                pst.setString(index++, description);
            }
            if (category != null) {
                pst.setString(index++, category);
            }
            if (price != 0) {
                pst.setDouble(index++, price);
            }
            if (stockQuantity != 0) {
                pst.setInt(index++, stockQuantity);
            }
            if (manufacturer != null) {
                pst.setString(index++, manufacturer);
            }
            if (expirationDate != null) {
                pst.setString(index++, expirationDate);
            }
            pst.setInt(index++, id);
            pst.executeUpdate();
            System.out.println("Medicine updated successfully.");
        } else {
            System.out.println("No updates made.");
        }
    }

    // -> Delete Medicine Using Sql Query...
    public void deleteMed(int id) throws Exception {
        String query = "DELETE FROM medicines WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Medicine deleted successfully.");
    }

    // -> View All Medicine Using Sql Procedure...
    public void viewAllMedicine() throws Exception {
        String query = "call getMedicine()";
        CallableStatement cst = con.prepareCall(query);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println("============================");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Category: " + rs.getString("category"));
            System.out.println("Price: Rs. " + rs.getDouble("price"));
            System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            System.out.println("Manufacturer: " + rs.getString("manufacturer"));
            System.out.println("Expiration Date: " + rs.getString("expiration_date"));
            System.out.println("============================");
        }
    }

    // -> View All Available Medicine Using Sql Query...
    public void viewAllAvailableMedicine() throws Exception {
        String query = "select distinct * from medicines where stock_quantity > 0";
        PreparedStatement cst = con.prepareStatement(query);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println("============================");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Category: " + rs.getString("category"));
            System.out.println("Price: Rs. " + rs.getDouble("price"));
            System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            System.out.println("Manufacturer: " + rs.getString("manufacturer"));
            System.out.println("Expiration Date: " + rs.getString("expiration_date"));
            System.out.println("============================");
        }
    }

    // --> Name Of Medicine Will Be Fetch For Description Of Medicine...
    public void nameOfMed() throws Exception {
        String sql = "select name from medicines";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("=== Available Medicine ===");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
    }

    // --> Id & Name Of Medicine Will Be Fetch For Search Of Medicine...
    public void nameOfMedWithID() throws Exception {
        String sql = "select id,name from medicines where stock_quantity > 0";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("=== Available Medicine ===");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
        }
    }

    // -->Category Of Medicine Will Be Fetch For Display Category Of Medicine...
    public void categories() throws Exception {
        String sql = "select distinct category from medicines";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println("=== Available Category ===");
        while (rs.next()) {
            System.out.println(rs.getString("category"));
        }
    }

    // -> Search Medicine For Sql Query...
    public void searchMed() throws Exception {
        Scanner sc = new Scanner(System.in);
        nameOfMedWithID();
        System.out.print("Enter Medicine Name : ");
        String name = sc.nextLine();
        String query = "SELECT * FROM medicines WHERE name = ? and stock_quantity > 0";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Category: " + rs.getString("category"));
            System.out.println("Price: " + rs.getDouble("price"));
            System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            System.out.println("Manufacturer: " + rs.getString("manufacturer"));
            System.out.println("Expiration Date: " + rs.getString("expiration_date"));
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // -> View All Purchase History For Admin...
    public void viewAllPurchaseHistory() throws Exception {
        String query = "call getPurchase_History()";
        CallableStatement cst = con.prepareCall(query);
        ResultSet rs = cst.executeQuery();

        System.out.println("+----------+----------+------------+----------+----------+---------------------+---------");
        System.out.printf("| %-8s | %-8s | %-10s | %-8s | %-10s | %-20s|\n",
                " ID", "User Name", "Medicine ID", "Quantity", "Total Payment", "Purchase Date");
        System.out.println("+----------+----------+------------+----------+----------+---------------------+---------");

        while (rs.next()) {
            System.out.printf("| %-8d | %-8s | %-10d | %-8d | %-10.2f | %-20s |\n",
                    rs.getInt("pid"),
                    rs.getString("user_name"),
                    rs.getInt("medicine_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_payment"),
                    rs.getString("purchase_date"));
        }
        System.out.printf("| %-8d | %-8s | %-10d | %-8d | %-10.2f | %-20s |\n",
                123, "New User", 456, 10, 100.00, "2022-01-01 00:00:00");

        System.out.println("+----------+----------+------------+----------+----------+---------------------+---------");
    }

    // -> Table Seller Insertion...
    public void createSellerTable(String name, String username, String password, String email, String phoneNumber)
            throws SQLException {

        String query = "INSERT INTO sellers (sname, susername, spassword, semail, sphone_number) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, username);
        pst.setString(3, password);
        pst.setString(4, email);
        pst.setString(5, phoneNumber);
        pst.executeUpdate();
    }

    // -> Purchase Medicine For Sql Query...
    public void purchaseMedicine(String userName, List<MedicinePurchase> medicinePurchases) throws Exception {
        for (MedicinePurchase medicinePurchase : medicinePurchases) {
            int medicineId = medicinePurchase.getMedicineId();
            int quantity = medicinePurchase.getQuantity();

            String query = "SELECT * FROM medicines WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, medicineId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double pricePerUnit = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");
                if (stockQuantity >= quantity) {
                    double totalPrice = pricePerUnit * quantity;
                    totalPrice = Double.parseDouble(String.format("%.2f", totalPrice)); // format totalPrice to 2
                                                                                        // decimal places

                    // update stock quantity in medicines table (1st Query..)
                    query = "UPDATE medicines SET stock_quantity = ? WHERE id = ?";
                    pst = con.prepareStatement(query);
                    pst.setInt(1, stockQuantity - quantity);
                    pst.setInt(2, medicineId);
                    pst.executeUpdate();

                    System.out.println("Your Total Bill Price Is : " + totalPrice);
                    Payment p = new Payment();
                    p.makePayment();

                    // insert into purchase history (2nd Query..)
                    query = "INSERT INTO purchase_history (user_name, medicine_id, quantity, total_payment,purchase_date) VALUES (?, ?, ?, ?,NOW())";
                    pst = con.prepareStatement(query);
                    pst.setString(1, userName);
                    pst.setInt(2, medicineId);
                    pst.setInt(3, quantity);
                    pst.setDouble(4, totalPrice);
                    pst.executeUpdate();

                    // --> Bill File...(code)
                    String baseFileName = userName + "_bill.txt";
                    File f = new File(baseFileName);
                    int counter = 1;
                    while (f.exists()) {
                        f = new File(userName + "_bill_" + counter + ".txt");
                        counter++;
                    }
                    FileWriter fw = new FileWriter(f, true);
                    fw.write("Medicine Purchase Bill\n");
                    fw.write("----------------------------------\n");
                    fw.write("User Name: " + userName + "\n");
                    fw.write("Medicine ID: " + medicineId + "\n");
                    fw.write("Medicine Name: " + rs.getString("name") + "\n");
                    fw.write("Quantity: " + quantity + "\n");
                    fw.write("Price per unit: " + String.format("%.2f", pricePerUnit) + "\n");
                    fw.write("Total Price: " + String.format("%.2f", totalPrice) + "\n");
                    fw.write("Thank You For Visiting.........\n");
                    fw.write("----------------------------------\n");
                    fw.close();

                    System.out.println("Medicine purchased successfully!");
                    System.out.println("Bill saved to file: " + f.getName());

                } else {
                    throw new Exception("Not enough stock available");
                }
            } else {
                throw new Exception("Medicine not found");
            }
        }
    }

    // --> Stock Update...(By Seller)
    public void addStock(int medicineId, int quantity) throws SQLException {
        String query = "UPDATE medicines SET stock_quantity = stock_quantity + ? WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, quantity);
        pst.setInt(2, medicineId);
        pst.executeUpdate();
    }

    public void updateStock(int medicineId, int quantity) throws SQLException {
        String query = "UPDATE medicines SET stock_quantity = ? WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, quantity);
        pst.setInt(2, medicineId);
        pst.executeUpdate();
    }

    public void viewStock() throws SQLException { // here all Available Stock appear.
        String query = "SELECT * FROM medicines where stock_quantity > 0";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("============================");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Category: " + rs.getString("category"));
            System.out.println("Price: " + rs.getDouble("price"));
            System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            System.out.println("Expiration Date: " + rs.getString("expiration_date"));
            System.out.println("============================");
        }
    }

    public void viewAllSellers() throws Exception {
        String sellerQuery = "SELECT * FROM sellers";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sellerQuery);

        System.out.println("+----+---------------+-------------+-------------------+---------------+");
        System.out.printf("| %-2s | %-13s | %-11s | %-18s | %-13s |\n",
                "ID", "Name", "Username", "Email", "Phone Number");
        System.out.println("+----+---------------+-------------+-------------------+---------------+");

        while (rs.next()) {
            System.out.printf("| %-2d | %-13s | %-11s | %-18s | %-13s |\n",
                    rs.getInt("sid"),
                    rs.getString("sname"),
                    rs.getString("susername"),
                    rs.getString("semail"),
                    rs.getString("sphone_number"));
        }
        System.out.println("+----+---------------+-------------+-------------------+---------------+");
    }

    public void viewAllUsers() throws Exception {
        String userQuery = "SELECT * FROM user";
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery(userQuery);

        System.out.println("+----+---------------+-----+--------+-------------------+---------------+");
        System.out.printf("| %-2s | %-15s | %-3s | %-6s | %-18s | %-13s |\n",
                "ID", "Name", "Age", "Gender", "Email", "Phone Number");
        System.out.println("+----+---------------+-----+--------+-------------------+---------------+");

        while (rs1.next()) {
            System.out.printf("| %-2d | %-15s | %-3d | %-6s | %-18s | %-13s |\n",
                    rs1.getInt("u_id"),
                    rs1.getString("u_name"),
                    rs1.getInt("u_age"),
                    rs1.getString("u_gender"),
                    rs1.getString("u_email"),
                    rs1.getString("u_phone_number"));
        }
        System.out.println("+----+---------------+-----+--------+-------------------+---------------+");
    }
    //

    // --> Only User Login Portal

    public boolean containsUser(String username) {
        return usersMap.containsKey(username);
    }

    public boolean validateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void createUser(String username, String password, String name, String email, String phoneNumber) {
        User user = new User(username, password, name, email, phoneNumber);
        users.put(username, user);
        lastSignedUpUsers.push(user);
    }

    public User getLastSignedUpUser() {
        return lastSignedUpUsers.peek();

    }

    public void addUser(User user) {
        lastSignedUpUsers.push(user);
    }

    public void viewLastSignedUpUser() {
        if (!lastSignedUpUsers.isEmpty()) {
            User user = lastSignedUpUsers.peek();
            System.out.println("Last signed up Username: " + user.getName());
            System.out.println("Last signed up Email: " + user.getEmail());
        } else {
            System.out.println("No users have signed up yet.");
        }
    }

    public void removeLastSignedUpUser() {
        if (!lastSignedUpUsers.isEmpty()) {
            User user = lastSignedUpUsers.pop();
            System.out.println("Removed user: " + user.getName() + " (" + user.getEmail() + ")");

            // Remove the user from the database
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacystore", "root", "");
                    PreparedStatement pst = conn.prepareStatement("DELETE FROM user WHERE u_name = ?")) {
                pst.setString(1, user.getName());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error deleting user from database: " + e.getMessage());
            }
        } else {
            System.out.println("No users to remove.");
        }
    }

    // --> we can add a table if not exist name :user to add user data...
    public void createUserTable(String name, int age, String gender, String phoneNumber, String email) {
        // String tableName = name.replaceAll("\\s+", "_").toLowerCase();
        if (con != null) {
            try (PreparedStatement pst = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS user (u_id INT AUTO_INCREMENT PRIMARY KEY, u_name VARCHAR(255), u_age INT, u_gender VARCHAR(10), u_phone_number VARCHAR(15), u_email VARCHAR(255))")) {
                pst.executeUpdate();
                System.out.println("User table created successfully");
            } catch (SQLException e) {
                System.out.println("Error creating user table: " + e.getMessage());
            }
        } else {
            System.out.println("Connection is null");
        }

        if (con != null) {
            try (PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO user (u_name, u_age, u_gender, u_phone_number, u_email) VALUES (?, ?, ?, ?, ?)")) {
                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, gender);
                pst.setString(4, phoneNumber);
                pst.setString(5, email);
                pst.executeUpdate();
                System.out.println("User added to the table successfully");
            } catch (SQLException e) {
                System.out.println("Error adding user to the table: " + e.getMessage());
            }
        } else {
            System.out.println("Connection is null");
        }
    }

    // --User Mehtod..
    // --> View Purchasing History By Name. (name is taken from database...)
    public void viewPurchaseHistory(String userName) throws Exception {
        String query = "select * from purchase_history where user_name=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, userName);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("pid");
            String userNameFromDB = rs.getString("user_name");
            int medicineId = rs.getInt("medicine_id");
            int quantity = rs.getInt("quantity");
            Date purchaseDate = rs.getDate("purchase_date");

            System.out.println("============================");
            System.out.println("Purchase ID: " + id);
            System.out.println("User Name: " + userNameFromDB);
            System.out.println("Medicine Id: " + medicineId);
            System.out.println("Quantity: " + quantity);
            System.out.println("Purchase Date: " + purchaseDate);
            System.out.println("============================");
        }
    }

    // --> View Who Last Purchase Medicine From Store And Also Add In LinkedList....
    public void viewLastPurchaseHistory() throws Exception {
        String query = "SELECT user_name, medicine_id, quantity, purchase_date FROM purchase_history ORDER BY id DESC limit 1";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        LinkedList<PurchaseHistory> purchaseHistoryList = new LinkedList<>();

        while (rs.next()) {
            String name = rs.getString("user_name");
            int medicineId = rs.getInt("medicine_id");
            int quantity = rs.getInt("quantity");
            Date purchaseDate = rs.getDate("purchase_date");
            PurchaseHistory purchaseHistory = new PurchaseHistory(name, medicineId, quantity, purchaseDate);
            purchaseHistoryList.add(purchaseHistory);
        }

        if (!purchaseHistoryList.isEmpty()) {
            PurchaseHistory lastPurchaseHistory = purchaseHistoryList.getLast();
            System.out.println("Last purchase history:");
            System.out.println("Name: " + lastPurchaseHistory.getName());
            System.out.println("Medicine ID: " + lastPurchaseHistory.getMedicineId());
            System.out.println("Quantity: " + lastPurchaseHistory.getQuantity());
            System.out.println("Time: " + lastPurchaseHistory.getPurchaseDate());
        } else {
            System.out.println("No purchase history found.");
        }
    }

    // --> Fetch Medicine from Category...
    public void filterMedicinesByCategory(String category) {
        String fetch = "select * from medicines where category = ?";

        try {
            PreparedStatement pst = con.prepareStatement(fetch);
            pst.setString(1, category);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category1 = rs.getString("category");
                double price = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");
                String manufacturer = rs.getString("manufacturer");
                String expirationDate = rs.getString("expiration_date");

                System.out.println("--------------------------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Description: " + description);
                System.out.println("Category: " + category1);
                System.out.println("Price: " + price);
                System.out.println("Stock Quantity: " + stockQuantity);
                System.out.println("Manufacturer: " + manufacturer);
                System.out.println("Expiration Date: " + expirationDate);
                System.out.println("--------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // --> view medicines description....
    public void viewMedicineDescription(String name) {
        String fetchDetails = "select description,stock_quantity from medicines where name = ?";
        try {
            PreparedStatement pst = con.prepareStatement(fetchDetails);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String description = rs.getString("description");
                int stock = rs.getInt("stock_quantity");
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("Description: " + description);
                System.out.println("Stock Availability : " + stock);
                System.out.println("----------------------------------------------------------------------------");
            } else {
                System.out.println("No medicine found with this name.");

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
