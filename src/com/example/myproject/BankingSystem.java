package com.example.myproject;
import java.sql.*;
import java.util.Scanner;

// Account class
class Account {
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

// DAO (Data Access Object) class
class AccountDAO {
    private Connection connection;

    public AccountDAO() {
        try {
            // Establishing connection to MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "2628");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create operation
    public void createAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (account_number,account_type, balance) VALUES (?, ?, ?)");
            statement.setString(1, account.getAccountNumber());
            statement.setString(2, account.getAccountType());
            statement.setDouble(3, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read operation
    public Account getAccount(String accountNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String accountType = resultSet.getString("account_type");
                double balance = resultSet.getDouble("balance");
                return new Account(accountNumber, accountType, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update operation
    public void updateBalance(String accountNumber, double newBalance) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE account_number = ?");
            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete operation
    public void deleteAccount(String accountNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE account_number = ?");
            statement.setString(1, accountNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Main class
public class BankingSystem {
    public static void main(String[] args) {
        // Creating Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Creating DAO object
        AccountDAO accountDAO = new AccountDAO();

        while (true) {
            // Display menu options
            System.out.println("\nMenu:");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Update Balance");
            System.out.println("4. Delete Account");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Read user choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Create operation
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter account type (Savings/Checking): ");
                    String accountType = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    Account account = new Account(accountNumber, accountType, balance);
                    accountDAO.createAccount(account);
                    System.out.println("Account created successfully.");
                    break;
                case 2:
                    // Read operation
                    System.out.print("Enter account number: ");
                    String viewAccountNumber = scanner.nextLine();
                    Account viewAccount = accountDAO.getAccount(viewAccountNumber);
                    if (viewAccount != null) {
                        System.out.println("Account Number: " + viewAccount.getAccountNumber());
                        System.out.println("Account Type: " + viewAccount.getAccountType());
                        System.out.println("Balance: $" + viewAccount.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    // Update operation
                    System.out.print("Enter account number: ");
                    String updateAccountNumber = scanner.nextLine();
                    System.out.print("Enter new balance: ");
                    double newBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    accountDAO.updateBalance(updateAccountNumber, newBalance);
                    System.out.println("Balance updated successfully.");
                    break;
                case 4:
                    // Delete operation
                    System.out.print("Enter account number: ");
                    String deleteAccountNumber = scanner.nextLine();
                    accountDAO.deleteAccount(deleteAccountNumber);
                    System.out.println("Account deleted successfully.");
                    break;
                case 5:
                    // Exit the program
                    System.out.println("Exiting...");
                    scanner.close(); // Close Scanner before exiting
                    accountDAO.closeConnection(); // Close database connection
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
