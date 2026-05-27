package basic1;

import java.util.ArrayList;
import java.util.Scanner;

// ========== 1. CUSTOM EXCEPTIONS ==========

// Checked Exceptions
class InvalidAccountNumberException extends Exception {
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}

class NegativeAmountException extends Exception {
    public NegativeAmountException(String message) {
        super(message);
    }
    
    public NegativeAmountException(double amount) {
        super("Amount cannot be negative or zero: " + amount);
    }
}

class ExceedTransferLimitException extends Exception {
    public ExceedTransferLimitException(double limit, double attempted) {
        super(String.format("Transfer limit is ₹%.2f per transaction. Attempted: ₹%.2f", limit, attempted));
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(double balance, double requested) {
        super(String.format("Insufficient balance! Available: ₹%.2f, Requested: ₹%.2f", balance, requested));
    }
}

// Unchecked Exceptions
class AccountLockedException extends RuntimeException {
    public AccountLockedException(String message) {
        super(message);
    }
}

class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String email) {
        super("Invalid email format: " + email + ". Email must contain '@' and '.'");
    }
}

// ========== 2. BANK ACCOUNT CLASS ==========

class Banks {
    private int accountNumber;
    private String holderName;
    private String email;
    private double balance;
    private int pin;
    private int failedAttempts;
    private boolean isLocked;
    private ArrayList<String> transactionHistory;
    
    // Constructor
    public Banks(int accountNumber, String holderName, String email, int pin) 
            throws InvalidEmailFormatException {
        validateEmail(email);
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.email = email;
        this.pin = pin;
        this.balance = 0;
        this.failedAttempts = 0;
        this.isLocked = false;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with initial balance: ₹0");
    }
    
    // Validate email format (must contain @ and .)
    private void validateEmail(String email) throws InvalidEmailFormatException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailFormatException(email);
        }
    }
    
    private void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
    
    // Deposit method
    public void deposit(double amount) throws NegativeAmountException {
        if (amount <= 0) {
            throw new NegativeAmountException(amount);
        }
        balance += amount;
        addTransaction("Deposited: ₹" + amount + " | Balance: ₹" + balance);
        System.out.println("✅ Deposited ₹" + amount + " successfully!");
    }
    
    // Withdraw method
    public void withdraw(double amount, int enteredPin) 
            throws NegativeAmountException, InsufficientBalanceException, AccountLockedException, InvalidAccountNumberException {
        
        if (isLocked) {
            throw new AccountLockedException("Account is locked due to multiple failed attempts!");
        }
        
        if (amount <= 0) {
            throw new NegativeAmountException(amount);
        }
        
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
        
        // Validate PIN
        if (enteredPin != pin) {
            failedAttempts++;
            int attemptsLeft = 3 - failedAttempts;
            if (failedAttempts >= 3) {
                isLocked = true;
                throw new AccountLockedException("Account locked! Too many invalid PIN attempts.");
            }
            throw new InvalidAccountNumberException("Invalid PIN! Attempts left: " + attemptsLeft);
        }
        
        // Successful withdrawal
        failedAttempts = 0; // reset on success
        balance -= amount;
        addTransaction("Withdrawn: ₹" + amount + " | Balance: ₹" + balance);
        System.out.println("✅ Withdrawn ₹" + amount + " successfully!");
    }
    
    // Transfer method
    public void transfer(Banks toAccount, double amount, int enteredPin) 
            throws NegativeAmountException, ExceedTransferLimitException, 
                   InsufficientBalanceException, AccountLockedException, InvalidAccountNumberException {
        
        double TRANSFER_LIMIT = 25000;
        
        if (amount > TRANSFER_LIMIT) {
            throw new ExceedTransferLimitException(TRANSFER_LIMIT, amount);
        }
        
        // Use withdraw logic to deduct from this account (includes PIN validation)
        // But we need to differentiate between "withdraw" and "transfer" in transaction history
        if (isLocked) {
            throw new AccountLockedException("Account is locked due to multiple failed attempts!");
        }
        
        if (amount <= 0) {
            throw new NegativeAmountException(amount);
        }
        
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
        
        // Validate PIN
        if (enteredPin != pin) {
            failedAttempts++;
            int attemptsLeft = 3 - failedAttempts;
            if (failedAttempts >= 3) {
                isLocked = true;
                throw new AccountLockedException("Account locked! Too many invalid PIN attempts.");
            }
            throw new InvalidAccountNumberException("Invalid PIN! Attempts left: " + attemptsLeft);
        }
        
        // Perform transfer
        failedAttempts = 0;
        this.balance -= amount;
        toAccount.balance += amount;
        
        addTransaction("Transferred: ₹" + amount + " to Account " + toAccount.getAccountNumber() + 
                       " | Balance: ₹" + balance);
        toAccount.addTransaction("Received: ₹" + amount + " from Account " + this.accountNumber + 
                                 " | Balance: ₹" + toAccount.balance);
        
        System.out.println("✅ Transfer successful!");
        System.out.println("   ₹" + amount + " transferred from " + this.accountNumber + 
                           " to " + toAccount.getAccountNumber());
        System.out.println("   Your new balance: ₹" + balance);
    }
    
    // Check balance (doesn't need PIN for simplicity, but could)
    public double getBalance() {
        return balance;
    }
    
    public boolean checkPin(int enteredPin) {
        return enteredPin == pin;
    }
    
    // Getters
    public int getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public boolean isLocked() { return isLocked; }
    
    // Display account info
    public void displayInfo() {
        System.out.println("Account: " + accountNumber + " | " + holderName + 
                         " | Email: " + email + " | Balance: ₹" + balance);
    }
    
    public void showTransactionHistory() {
        System.out.println("\n=== Transaction History for Account " + accountNumber + " ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = transactionHistory.size() - 1; i >= 0; i--) {
                System.out.println((transactionHistory.size() - i) + ". " + transactionHistory.get(i));
            }
        }
    }
}

// ========== 3. BANK CLASS ==========

class Bank {
    private ArrayList<Banks> accounts = new ArrayList<>();
    
    public void addAccount(Banks account) {
        accounts.add(account);
        System.out.println("✅ Account " + account.getAccountNumber() + " created successfully!");
    }
    
    public Banks findAccount(int accountNumber) throws InvalidAccountNumberException {
        for (Banks acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        throw new InvalidAccountNumberException("Account number " + accountNumber + " not found!");
    }
    
    public void displayAllAccounts() {
        System.out.println("\n=== All Accounts ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Banks acc : accounts) {
                acc.displayInfo();
            }
        }
    }
}

// ========== 4. MAIN CLASS ==========

public class CustomExeptionChallenge {
    private static Bank bank = new Bank();
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Pre-create some accounts for testing
        try {
            bank.addAccount(new Banks(101, "John Doe", "john@email.com", 1234));
            bank.addAccount(new Banks(102, "Jane Smith", "jane@email.com", 5678));
            // Add initial balances for demo
            Banks acc101 = bank.findAccount(101);
            acc101.deposit(10000);
            Banks acc102 = bank.findAccount(102);
            acc102.deposit(5000);
        } catch (Exception e) {
            System.out.println("Init error: " + e.getMessage());
        }
        
        while (true) {
            System.out.println("\n=== ONLINE BANKING SYSTEM ===");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Show Transaction History");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");
            
            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next(); // clear buffer
                continue;
            }
            
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    bank.displayAllAccounts();
                    break;
                case 7:
                    showTransactionHistory();
                    break;
                case 8:
                    System.out.println("Thank you for using Online Banking!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option! Please choose 1-8.");
            }
        }
    }
    
    private static void createAccount() {
        System.out.println("\n=== Create New Account ===");
        try {
            System.out.print("Account Number: ");
            int accNo = sc.nextInt();
            System.out.print("Holder Name: ");
            String name = sc.next();
            System.out.print("Email: ");
            String email = sc.next();
            System.out.print("Set PIN (4 digits): ");
            int pin = sc.nextInt();
            
            Banks newAccount = new Banks(accNo, name, email, pin);
            bank.addAccount(newAccount);
            
        } catch (InvalidEmailFormatException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error creating account: " + e.getMessage());
            sc.next(); // clear buffer
        }
    }
    
    private static void depositMoney() {
        System.out.println("\n=== Deposit Money ===");
        try {
            System.out.print("Account Number: ");
            int accNo = sc.nextInt();
            Banks acc = bank.findAccount(accNo);
            
            System.out.print("Enter amount to deposit: ₹");
            double amount = sc.nextDouble();
            
            acc.deposit(amount);
            
        } catch (InvalidAccountNumberException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            sc.next();
        }
    }
    
    private static void withdrawMoney() {
        System.out.println("\n=== Withdraw Money ===");
        try {
            System.out.print("Account Number: ");
            int accNo = sc.nextInt();
            Banks acc = bank.findAccount(accNo);
            
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();
            System.out.print("Enter amount to withdraw: ₹");
            double amount = sc.nextDouble();
            
            acc.withdraw(amount, pin);
            
        } catch (InvalidAccountNumberException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (AccountLockedException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            sc.next();
        }
    }
    
    private static void transferMoney() {
        System.out.println("\n=== Transfer Money ===");
        try {
            System.out.print("From Account Number: ");
            int fromAccNo = sc.nextInt();
            Banks fromAcc = bank.findAccount(fromAccNo);
            
            System.out.print("To Account Number: ");
            int toAccNo = sc.nextInt();
            Banks toAcc = bank.findAccount(toAccNo);
            
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();
            System.out.print("Enter amount to transfer: ₹");
            double amount = sc.nextDouble();
            
            fromAcc.transfer(toAcc, amount, pin);
            
        } catch (InvalidAccountNumberException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (ExceedTransferLimitException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (AccountLockedException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            sc.next();
        }
    }
    
    private static void checkBalance() {
        System.out.println("\n=== Check Balance ===");
        try {
            System.out.print("Account Number: ");
            int accNo = sc.nextInt();
            Banks acc = bank.findAccount(accNo);
            
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();
            
            if (acc.checkPin(pin)) {
                System.out.println("Account: " + accNo);
                System.out.println("Holder: " + acc.getHolderName());
                System.out.println("Current Balance: ₹" + acc.getBalance());
            } else {
                System.out.println("❌ Invalid PIN!");
            }
            
        } catch (InvalidAccountNumberException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            sc.next();
        }
    }
    
    private static void showTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        try {
            System.out.print("Account Number: ");
            int accNo = sc.nextInt();
            Banks acc = bank.findAccount(accNo);
            
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();
            
            if (acc.checkPin(pin)) {
                acc.showTransactionHistory();
            } else {
                System.out.println("❌ Invalid PIN!");
            }
            
        } catch (InvalidAccountNumberException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
            sc.next();
        }
    }
}