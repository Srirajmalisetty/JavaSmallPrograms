package basic1;

import java.util.Scanner;
import java.util.ArrayList;

// 1. Custom Checked Exceptions
class InvalidPinException extends Exception {
    public InvalidPinException(String message) {
        super(message);
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// 2. Custom Unchecked Exceptions
class DailyLimitExceededException extends RuntimeException {
    public DailyLimitExceededException(String message) {
        super(message);
    }
}

class AccountBlockedException extends RuntimeException {
    public AccountBlockedException(String message) {
        super(message);
    }
}

// 3. BankAccount Class
class BankAccount {
    private double balance = 10000;
    private final int correctPin = 1234;
    private int pinAttempts = 0;
    private boolean isBlocked = false;
    private double dailyWithdrawn = 0;
    private final double DAILY_LIMIT = 50000;
    private ArrayList<String> transactionHistory = new ArrayList<>();
    
    // Method to validate PIN (throws checked exception)
    public void validatePin(int enteredPin) throws InvalidPinException {
        if (isBlocked) {
            throw new AccountBlockedException("Account is blocked due to multiple invalid attempts!");
        }
        
        if (enteredPin == correctPin) {
            pinAttempts = 0;  // Reset attempts on successful PIN entry
            System.out.println("PIN verified!");
        } else {
            pinAttempts++;
            int attemptsLeft = 3 - pinAttempts;
            
            if (pinAttempts >= 3) {
                isBlocked = true;
                throw new AccountBlockedException("Account blocked! Too many invalid attempts.");
            }
            
            throw new InvalidPinException("Invalid PIN! Attempts left: " + attemptsLeft);
        }
    }
    
    // Method to withdraw (throws checked exceptions)
    public void withdraw(double amount) 
            throws InsufficientBalanceException, InvalidPinException {
        
        // Check if account is blocked
        if (isBlocked) {
            throw new AccountBlockedException("Account is blocked. Cannot perform withdrawal.");
        }
        
        // Check balance
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance! Available: ₹" + balance);
        }
        
        // Check daily limit
        if (amount + dailyWithdrawn > DAILY_LIMIT) {
            throw new DailyLimitExceededException("Daily limit exceeded! Maximum ₹" + DAILY_LIMIT + " per day. Already withdrawn: ₹" + dailyWithdrawn);
        }
        
        // All validations passed - perform withdrawal
        balance -= amount;
        dailyWithdrawn += amount;
        
        // Add to transaction history
        String transaction = "Withdrawn: ₹" + amount + " | Balance: ₹" + balance;
        transactionHistory.add(transaction);
        
        System.out.println("✅ Withdrawal successful!");
        System.out.println("   Amount: ₹" + amount);
        System.out.println("   Remaining balance: ₹" + balance);
        System.out.println("   Today's withdrawals: ₹" + dailyWithdrawn + " / ₹" + DAILY_LIMIT);
    }
    
    // Method to check balance
    public void checkBalance() {
        System.out.println("Current balance: ₹" + balance);
    }
    
    // Method to show transaction history
    public void showTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = transactionHistory.size() - 1; i >= 0; i--) {
                System.out.println((transactionHistory.size() - i) + ". " + transactionHistory.get(i));
            }
        }
    }
    
    // Method to reset daily limit (for demo purposes)
    public void resetDailyLimit() {
        dailyWithdrawn = 0;
        System.out.println("Daily limit reset!");
    }
    
    // Getter for blocked status
    public boolean isBlocked() {
        return isBlocked;
    }
}

// 4. Main ATM Class
public class ThrowsChallenge {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=".repeat(40));
        System.out.println("      WELCOME TO ATM SYSTEM");
        System.out.println("=".repeat(40));
        
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Withdraw Money");
            System.out.println("2. Check Balance");
            System.out.println("3. Transaction History");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next(); // clear invalid input
                continue;
            }
            
            switch (choice) {
                case 1:  // Withdraw
                    if (account.isBlocked()) {
                        System.out.println("❌ Account is blocked! Cannot perform transactions.");
                        break;
                    }
                    
                    try {
                        // PIN validation
                        System.out.print("Enter PIN: ");
                        int pin = sc.nextInt();
                        account.validatePin(pin);
                        
                        // Withdrawal amount
                        System.out.print("Enter amount to withdraw: ₹");
                        double amount = sc.nextDouble();
                        
                        if (amount <= 0) {
                            System.out.println("❌ Invalid amount! Please enter positive amount.");
                            break;
                        }
                        
                        account.withdraw(amount);
                        
                    } catch (InvalidPinException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (InsufficientBalanceException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (DailyLimitExceededException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (AccountBlockedException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Invalid input! Please enter valid amount.");
                        sc.next(); // clear invalid input
                    }
                    break;
                    
                case 2:  // Check Balance
                    if (account.isBlocked()) {
                        System.out.println("❌ Account is blocked! Cannot check balance.");
                    } else {
                        account.checkBalance();
                    }
                    break;
                    
                case 3:  // Transaction History
                    account.showTransactionHistory();
                    break;
                    
                case 4:  // Exit
                    exit = true;
                    System.out.println("Thank you for using ATM! Have a great day!");
                    break;
                    
                default:
                    System.out.println("❌ Invalid option! Please choose 1-4.");
            }
        }
        
        sc.close();
    }
}