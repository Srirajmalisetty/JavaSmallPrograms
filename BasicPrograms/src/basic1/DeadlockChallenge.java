package basic1;

class BankAccountDeadlock {
    private int id;
    private double balance;

    public BankAccountDeadlock(int id, double balance) {
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

    // DEADLOCK PRONE: acquires locks in arbitrary order (source then dest)
    public static void transfer(BankAccountDeadlock from, BankAccountDeadlock to, double amount) {
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() + " locked account " + from.getId());
            try { Thread.sleep(100); } catch (InterruptedException e) {} // simulate work
            synchronized (to) {
                System.out.println(Thread.currentThread().getName() + " locked account " + to.getId());
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Transferred " + amount + " from " + from.getId() + " to " + to.getId());
            }
        }
    }
}

public class DeadlockChallenge {
    public static void main(String[] args) {
        BankAccountDeadlock accA = new BankAccountDeadlock(1, 1000);
        BankAccountDeadlock accB = new BankAccountDeadlock(2, 1000);

        Thread t1 = new Thread(() -> BankAccountDeadlock.transfer(accA, accB, 100), "Thread-1");
        Thread t2 = new Thread(() -> BankAccountDeadlock.transfer(accB, accA, 50),  "Thread-2");

        t1.start();
        t2.start();
    }
}
