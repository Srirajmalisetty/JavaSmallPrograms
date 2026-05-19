package basic1;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Random;

public class ListChallengeSolution {
    public static void main(String[] args) {
        
        ArrayList<Integer> arrayList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            arrayList.add(rand.nextInt(100) + 1); // 1 to 100
        }
       
        LinkedList<Integer> linkedList = new LinkedList<>(arrayList);
        
        System.out.println("Original ArrayList: " + arrayList);
        System.out.println("Original LinkedList: " + linkedList);
        
       
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) % 2 == 0) {  
                arrayList.remove(i);
                i--;  
            }
        }
        
      
        Iterator<Integer> it = linkedList.iterator();
        while (it.hasNext()) {
            int num = it.next();
            if (num % 2 != 0) {   // odd
                it.remove();
            }
        }
        
      
        System.out.println("\nAfter removing even numbers (ArrayList): " + arrayList);
        System.out.println("After removing odd numbers (LinkedList): " + linkedList);
    }
}