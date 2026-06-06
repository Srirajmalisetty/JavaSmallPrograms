package basic1;

class TicketCounter {
    private int availableSeats = 10;
    
    public synchronized boolean bookTicket(String passengerName, int seats) {
        if (seats <= 0) {
            System.out.println(passengerName + " invalid seat count.");
            return false;
        }
        if (seats <= availableSeats) {
            availableSeats -= seats;
            System.out.println(passengerName + " booked " + seats + " seat(s). Remaining: " + availableSeats);
            return true;
        } else {
            System.out.println(passengerName + " failed. Only " + availableSeats + " seats left.");
            return false;
        }
    }
    
    public synchronized int getAvailableSeats() {
        return availableSeats;
    }
}

public class SynchronizedChallenge {
    public static void main(String[] args) {
        TicketCounter counter = new TicketCounter();
        
        Runnable bookingTask = () -> {
            String name = Thread.currentThread().getName();
            int seats = (int)(Math.random() * 3) + 1; // 1 to 3 seats
            counter.bookTicket(name, seats);
        };
        
        Thread t1 = new Thread(bookingTask, "Alice");
        Thread t2 = new Thread(bookingTask, "Bob");
        Thread t3 = new Thread(bookingTask, "Charlie");
        Thread t4 = new Thread(bookingTask, "Diana");
        Thread t5 = new Thread(bookingTask, "Eve");
        
        t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
        
        // Wait for all to finish
        try {
            t1.join(); t2.join(); t3.join(); t4.join(); t5.join();
        } catch (InterruptedException e) {}
        
        System.out.println("Final available seats: " + counter.getAvailableSeats());
    }
}