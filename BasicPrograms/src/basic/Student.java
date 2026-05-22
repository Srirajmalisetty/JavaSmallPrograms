package basic;
import java.util.*;
public class Student {
	String name;
	int age;
	int marks;
	void display() {
		System.out.println("Student Nmae:"+name);
		System.out.println("Student Age:"+age);
		System.out.println("Student Marks:"+marks);
	}

	public static void main(String[] args) 
	{
		Student s =new Student();
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter Student Name");
		s.name=sc.nextLine();
		System.out.println("Enter Student Age");
		s.age=sc.nextInt();
		System.out.println("Enter Student Marks");
		s.marks=sc.nextInt();
		s.display();
		sc.close();

	}

}
