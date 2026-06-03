package basic1;

public class ThreadLifeCycleDemo {
    
    public static void main(String[] args) throws InterruptedException {
        
        // Create a target object for synchronization
        final Object lock = new Object();
        
        // Thread A: sleeps, then waits on lock
        Thread tA = new Thread(() -> {
            try {
                Thread.sleep(500); // TIMED_WAITING
                synchronized (lock) {
                    lock.wait(); // WAITING
                }
            } catch (InterruptedException e) {}
        });
        
        // Thread B: holds lock and then sleeps
        Thread tB = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(2000); // holds lock, then TIMED_WAITING
                } catch (InterruptedException e) {}
            }
        });
        
        // Thread C: normal run and finish
        Thread tC = new Thread(() -> {
            System.out.println("Thread C running");
        });
        
        // 1. NEW state
        System.out.println("After creation:");
        System.out.println("tA: " + tA.getState()); // NEW
        System.out.println("tB: " + tB.getState()); // NEW
        System.out.println("tC: " + tC.getState()); // NEW
        
        // 2. Start tA and tB
        tA.start();
        tB.start();
        Thread.sleep(100); // give time to start
        
        System.out.println("\nAfter start and short sleep:");
        System.out.println("tA: " + tA.getState()); // TIMED_WAITING (sleeping)
        System.out.println("tB: " + tB.getState()); // RUNNABLE (will acquire lock soon)
        
        // 3. Start tC and join to see TERMINATED
        tC.start();
        tC.join(); // wait for tC to finish
        System.out.println("\nAfter tC finishes:");
        System.out.println("tC: " + tC.getState()); // TERMINATED
        
        // 4. Wait for tB to acquire lock and sleep, then tA will be BLOCKED
        Thread.sleep(500); // let tB get lock and sleep inside sync block
        System.out.println("\nAfter tB holds lock and sleeps:");
        System.out.println("tB: " + tB.getState()); // TIMED_WAITING (sleep inside sync)
        System.out.println("tA: " + tA.getState()); // BLOCKED (waiting for lock)
        
        // 5. Interrupt tA to wake it from wait (but tA is BLOCKED, so will wait until lock)
        tA.interrupt();
        System.out.println("\nAfter interrupting tA:");
        System.out.println("tA (still blocked, interrupt flag set): " + tA.getState());
        
        // Let tB finish its sleep and release lock
        Thread.sleep(2000);
        System.out.println("\nAfter tB releases lock:");
        System.out.println("tB: " + tB.getState()); // TERMINATED
        System.out.println("tA: " + tA.getState()); // RUNNABLE (acquired lock, then it was waiting, but we interrupted – will throw InterruptedException)
        
        // Wait for tA to finish
        tA.join();
        System.out.println("\nFinal state tA: " + tA.getState()); // TERMINATED
    }
}