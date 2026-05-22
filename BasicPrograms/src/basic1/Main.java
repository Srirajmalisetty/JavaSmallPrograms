package basic1;
class Address {
 String city;
 int pincode;
 Address(String city, int pincode) {
     this.city = city;
     this.pincode = pincode;
 }
}
class Student implements Cloneable {
 String name;
 Address address;
 Student(String name, Address address) {
     this.name = name;
     this.address = address;
 }

 public Object clone() throws CloneNotSupportedException {
   
     Student s = (Student) super.clone();

     s.address = new Address(this.address.city, this.address.pincode);

     return s;
 }

 void display() {
     System.out.println("Name: " + name +
                        ", City: " + address.city +
                        ", Pincode: " + address.pincode);
 }
}

public class Main {
 public static void main(String[] args) throws Exception {

     Address a1 = new Address("Vijayawada", 520001);
     Student s1 = new Student("John", a1);

     Student s2 = (Student) s1.clone();


     s2.name = "Alex";
     s2.address.city = "Hyderabad";

    
     System.out.println("Original:");
     s1.display();

     System.out.println("\nCloned:");
     s2.display();
 }
}
