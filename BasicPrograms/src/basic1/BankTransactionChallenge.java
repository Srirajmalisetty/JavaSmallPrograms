package basic1;
//BankAccount class with synchronized methods
class BankAccount1 {
 private int accountNumber;
 private double balance;

 public BankAccount1(int accountNumber, double initialBalance) {
     this.accountNumber = accountNumber;
     this.balance = initialBalance;
 }

 public synchronized void deposit(double amount) {
     if (amount <= 0) {
         System.out.println(Thread.currentThread().getName() + " Invalid deposit amount: " + amount);
         return;
     }
     System.out.printf("%s Depositing ₹%.2f (Current: ₹%.2f)%n",
             Thread.currentThread().getName(), amount, balance);
     balance += amount;
     System.out.printf("%s Deposit successful! New balance: ₹%.2f%n",
             Thread.currentThread().getName(), balance);
 }

 public synchronized void withdraw(double amount) {
     if (amount <= 0) {
         System.out.println(Thread.currentThread().getName() + " Invalid withdrawal amount: " + amount);
         return;
     }
     System.out.printf("%s Withdrawing ₹%.2f (Current: ₹%.2f)%n",
             Thread.currentThread().getName(), amount, balance);
     if (amount > balance) {
         System.out.printf("%s Withdrawal failed! Insufficient balance. Available: ₹%.2f%n",
                 Thread.currentThread().getName(), balance);
         return;
     }
     balance -= amount;
     System.out.printf("%s Withdrawal successful! New balance: ₹%.2f%n",
             Thread.currentThread().getName(), balance);
 }

 public synchronized double getBalance() {
     return balance;
 }

 public int getAccountNumber() {
     return accountNumber;
 }
}

//Transaction task (Runnable)
class TransactionTask implements Runnable {
 private BankAccount1 account;
 private String type;      // "deposit" or "withdraw"
 private double amount;
 private long delayMillis;

 public TransactionTask(BankAccount1 account, String type, double amount, long delayMillis) {
     this.account = account;
     this.type = type;
     this.amount = amount;
     this.delayMillis = delayMillis;
 }

 @Override
 public void run() {
     // Simulate processing time
     try {
         Thread.sleep(delayMillis);
     } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
     }

     // Perform transaction
     if (type.equalsIgnoreCase("deposit")) {
         account.deposit(amount);
     } else if (type.equalsIgnoreCase("withdraw")) {
         account.withdraw(amount);
     } else {
         System.out.println(Thread.currentThread().getName() + " Unknown transaction type: " + type);
     }
 }
}

//Main class
public class BankTransactionChallenge {
 public static void main(String[] args) {
     // Create shared account
     BankAccount1 account = new BankAccount1(1001, 10000.0);
     System.out.println("Initial balance: ₹" + account.getBalance());
     System.out.println("====================================\n");

     // Define transactions: (type, amount, delayMs)
     Object[][] transactions = {
         {"withdraw", 2000.0, 100L},
         {"deposit",  500.0,  50L},
         {"withdraw", 8000.0, 120L},
         {"deposit",  1500.0, 80L},
         {"withdraw", 3000.0, 90L},
         {"deposit",  1000.0, 60L},
         {"withdraw", 1000.0, 70L}
     };

     // Create and start threads
     Thread[] threads = new Thread[transactions.length];
     for (int i = 0; i < transactions.length; i++) {
         String type = (String) transactions[i][0];
         double amount = (double) transactions[i][1];
         long delay = (long) transactions[i][2];
         Runnable task = new TransactionTask(account, type, amount, delay);
         threads[i] = new Thread(task, "T" + (i+1));
         threads[i].start();
     }

     // Wait for all threads to finish
     for (Thread t : threads) {
         try {
             t.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

     // Final result
     System.out.println("\n====================================");
     System.out.println("All transactions completed.");
     System.out.printf("Final balance for Account %d: ₹%.2f%n",
             account.getAccountNumber(), account.getBalance());
 }
}