package Users;

import Pharmacy_System.*;

import java.util.*;
import java.io.*;
import java.sql.*;

class SignupUser implements Runnable {

    private PharmacySystem ph;
    private Scanner sc = new Scanner(System.in);

    public SignupUser(PharmacySystem ph) {
        this.ph = ph;
    }

    @Override
    public void run() {
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter email: ");
        String email = sc.next();
        while (!email.endsWith("@gmail.com")) {
            System.out.println("Invalid email. Please enter a valid Gmail address.");
            System.out.print("Enter email: ");
            email = sc.next();
        }
        System.out.print("Enter phone number: ");
        String phoneNumber = sc.next();
        while (phoneNumber.length() != 10) {
            System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
            System.out.print("Enter phone number: ");
            phoneNumber = sc.next();
        }
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter gender (Male/Female): ");
        String gender = sc.next().trim().toUpperCase();
        while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            System.out.println("Invalid gender. Please enter Male/Female");
            System.out.print("Enter gender (Male/Female): ");
            gender = sc.next().trim().toUpperCase();
        }

        User user = new User(username, password, name, email, phoneNumber);
        UserInfo user1 = new UserInfo();
        user1.createUser(user);
        ph.createUser(username, password, name, email, phoneNumber);
        ph.createUserTable(name, age, gender, phoneNumber, email);

        // Add code to write user details to file...
        writeUserDataToFile(user);

        // Get the user ID from the database...
        int userId = 0;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacystore", "root", "");
                PreparedStatement pst = conn
                        .prepareStatement("SELECT u_id FROM user WHERE u_name = ? AND u_age = ? AND u_gender = ?")) {
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("u_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user ID from database: " + e.getMessage());
        }

        System.out.println("Signup successful! Your user ID is " + userId);

    }

    public void writeUserDataToFile(User user) {
        boolean userAlreadyExists = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) { // all new signup user will
                                                                                        // be
                                                                                        // add here using our code
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(user.getUsername()) &&
                        userData[1].equals(user.getPassword()) &&
                        userData[2].equals(user.getName()) &&
                        userData[3].equals(user.getEmail()) &&
                        userData[4].equals(user.getPhoneNumber())) {
                    userAlreadyExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        if (!userAlreadyExists) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                writer.write(
                        user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getEmail()
                                + "," + user.getPhoneNumber() + "\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
}
