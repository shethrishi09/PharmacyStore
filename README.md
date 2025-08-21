# Pharmacy Store Management System (Java + SQL + JDBC)

This is a **Java project** for managing a **Pharmacy Store**, built with **JDBC** for database connectivity.  
It demonstrates how Java applications can interact with SQL databases to perform day-to-day pharmacy management tasks.

## 📌 Features
- Manage medicines (add, update, delete, search)  
- Manage customers and their orders  
- Track stock availability  
- Generate bills and calculate totals  
- Database-driven application using JDBC  

## 🛠️ Technologies Used
- **Java (JDK 8 or higher)**  
- **MySQL / MariaDB**  
- **JDBC (Java Database Connectivity)**  

## 🗂️ Data Structures Used
- **ArrayList** → to store lists of medicines, customers, and orders in memory  
- **HashMap** → for mapping medicine IDs to their details quickly  
- **LinkedList** → for handling dynamic records like order queues (if implemented)  
- **Classes & Objects (OOP)** → to represent Medicines, Customers, and Orders  

## 🚀 How to Run

Follow these steps to run the project:

1. **Setup Database**
   - Import the provided SQL file (e.g., `pharmacy.sql`) into your MySQL database:
     ```bash
     mysql -u root -p < pharmacy.sql
     ```
   - Ensure your MySQL server is running.

2. **Configure Database Connection**
   - Open the Java files (e.g., `DatabaseConnection.java`) and update the database connection details  
     (username, password, URL) if required.

3. **Compile Java Files**
   - From the project folder, compile the Java source files:
     ```bash
     javac *.java
     ```

4. **Run the Project**
   - Start the application from the main class (e.g., `App.java` or `PharmacyMain.java`):
     ```bash
     java App
     ```

## 📖 Notes
- Replace `App.java` with your project’s main entry point if it has a different name.  
- Database file should be imported before running the application.  
- This project is created for **educational purposes** to demonstrate Java + JDBC concepts.
