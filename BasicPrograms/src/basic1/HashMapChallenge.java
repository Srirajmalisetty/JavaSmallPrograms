package basic1;
import java.util.HashMap;
public class HashMapChallenge {
public static void main(String[] args) {
	HashMap<Integer, String>students=new HashMap<>();
	students.put(101, "John");
	students.put(102, "Alice");
	students.put(103, "Bob");
	System.out.println("Students Details");
	System.out.println(students);
	if(students.containsValue("Alice")) {
		System.out.println("Student Found:"+students.get(102));
		
	}else {
		System.out.println("Student Not Found");
	}
	String str="Programming";
	HashMap<Character, Integer>freq=new HashMap<>();
	for(int i=0;i<str.length();i++) {
		char ch=str.charAt(i);
		if(freq.containsKey(ch)) {
			freq.put(ch, freq.get(ch)+1);
		}else {
			freq.put(ch, 1);
		}
	}
	System.out.println("\n Frequency of Characters");
	System.out.println(freq);
	char nonrepeat = 0 ;
	for(int i=0;i<str.length();i++) {
		char ch=str.charAt(i);
		if(freq.get(ch)==1) {
			nonrepeat=ch;
			break;
		}
		
	}
	System.out.println("\n First Non Repative Character:" +nonrepeat);
}
}
