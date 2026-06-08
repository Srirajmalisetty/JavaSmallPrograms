package basic1;
class BankAccountFixed {
    private int id;
    private double balance;

    public BankAccountFixed(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() { return id; }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) balance -= amount;
        else throw new IllegalArgumentException("Insufficient funds");
    }

    // FIXED: always lock lower ID first, then higher ID
    public static void transfer(BankAccountFixed from, BankAccountFixed to, double amount) {
        BankAccountFixed firstLock = from.getId() < to.getId() ? from : to;
        BankAccountFixed secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                // Now perform the actual transfer (from -> to)
                if (from == firstLock && to == secondLock) {
                    from.withdraw(amount);
                    to.deposit(amount);
                } else {
                    // The order of parameters might be swapped: we need to deduct from the correct one
                    // Actually our variable 'from' and 'to' are the original arguments.
                    // If firstLock == to and secondLock == from, we must reverse the operation.
                    // Safer: use the original from/to references, but we already hold both locks.
                    // So:
                    from.withdraw(amount);
                    to.deposit(amount);
                }
                System.out.println(Thread.currentThread().getName() + " transferred " + amount +
                        " from " + from.getId() + " to " + to.getId());
            }
        }
    }

    public double getBalance() { return balance; 
	}
}

public class FixedDeadlockChallenge {
    public static void main(String[] args) throws InterruptedException {
        BankAccountFixed accA = new BankAccountFixed(1, 1000);
        BankAccountFixed accB = new BankAccountFixed(2, 1000);

        Thread t1 = new Thread(() -> BankAccountFixed.transfer(accA, accB, 100), "Thread-1");
        Thread t2 = new Thread(() -> BankAccountFixed.transfer(accB, accA, 50),  "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final balance A: " + accA.getBalance());
        System.out.println("Final balance B: " + accB.getBalance());
    }
}