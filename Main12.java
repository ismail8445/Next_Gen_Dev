import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private double balance;
    private String pin;
    private List<String> transactions;

    public BankAccount(String accountNumber, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: $" + amount);
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew: $" + amount);
            System.out.println("Successfully withdrew $" + amount);
            return true;
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        } else {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactions.add("Transferred: $" + amount + " to " + recipient.getAccountNumber());
            return true;
        }
        return false;
    }

    public void printMiniStatement() {
        System.out.println("Mini Statement:");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        if (authenticateUser()) {
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transferFunds();
                        break;
                    case 5:
                        changePin();
                        break;
                    case 6:
                        printMiniStatement();
                        break;
                    case 7:
                        System.out.println("Logging out. Thank you for using the ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Change PIN");
        System.out.println("6. Print Mini Statement");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");
    }

    private void checkBalance() {
        System.out.println("Current balance: $" + account.getBalance());
    }

    private void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    private void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    private void transferFunds() {
        System.out.print("Enter recipient account number: ");
        String recipientAccountNumber = scanner.nextLine();
        BankAccount recipient = findAccountByNumber(recipientAccountNumber);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        account.transfer(recipient, amount);
    }

    private void changePin() {
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.nextLine();

        if (account.getPin().equals(currentPin)) {
            System.out.print("Enter new PIN: ");
            String newPin = scanner.nextLine();
            account.setPin(newPin);
            System.out.println("PIN successfully changed.");
        } else {
            System.out.println("Incorrect PIN. Please try again.");
        }
    }

    private void printMiniStatement() {
        account.printMiniStatement();
    }

    private boolean authenticateUser() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin)) {
            return true;
        } else {
            System.out.println("Authentication failed. Invalid account number or PIN.");
            return false;
        }
    }

    private BankAccount findAccountByNumber(String accountNumber) {
        // For simplicity, returning a mock account. In a real-world scenario, you would
        // search the account from a database.
        return new BankAccount(accountNumber, 500.0, "0000");
    }
}

public class Main12 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("12345678", 500.0, "1234");
        ATM atm = new ATM(account);
        atm.start();
    }
}
