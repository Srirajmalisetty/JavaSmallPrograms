package basic1;

import java.util.HashMap;
public class HashMapInternalDemo {
    public static void main(String[] args) {
        // Initial capacity 16, load factor 0.75
        HashMap<String, Integer> map = new HashMap<>();
        
        // Step 1: Put first element
        map.put("A", 1);
        // hash("A") = 65
        // index = 65 & 15 = 1 (bucket 1)
        // table[1] = new Node("A", 1)
        
        // Step 2: Put second element (different bucket)
        map.put("B", 2);
        // hash("B") = 66
        // index = 66 & 15 = 2 (bucket 2)
        // table[2] = new Node("B", 2)
        
        // Step 3: Put element in same bucket (collision)
        map.put("Q", 17);  // suppose hash("Q") & 15 = 1 (same as "A")
        // table[1].next = new Node("Q", 17)
        
        // Step 4: Get element
        int value = map.get("Q");  // traverses linked list at bucket 1
        System.out.println(value);
    }
}