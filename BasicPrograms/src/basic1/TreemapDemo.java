package basic1;
import java.util.TreeMap;
public class TreemapDemo {
public static void main(String[] args) {
	
	TreeMap<Integer, String>Map=new TreeMap<Integer, String>();
	
	Map.put(150, "Sriraj");
	Map.put(100, "Yash");
	Map.put(98, "Abhi");
	Map.put(75, "John");
	Map.put(50, "Bob");
	
	System.out.println("Student Marks And Names ");
	System.out.println(Map+"\n\t");
	System.out.println("Descending Order");
	System.out.println(Map.descendingMap()+"\n\t");
    
	int highest=Map.lastKey();
    
    System.out.println("Highest Marks:");
    System.out.println(highest+"="+Map.get(highest)+"\n\t");
    
    TreeMap<String, Integer>words=new TreeMap<>();
    
    words.put("Banana", 10);   
     words.put("Apple", 20);
     words.put("Custard Apple", 30);
     words.put("Dragon Fruit", 40);
     words.put("Guava", 10);
     
     System.out.println("Words");
     System.out.println(words+"\n\t");
     System.out.println("Alphabetical Order");
	
     for(String word:words.keySet()) {
		System.out.println(word);
	}
	
}
}
