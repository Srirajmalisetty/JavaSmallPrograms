package basic1;


class Address{
String city;
int pin;
	public Address(String city,int pin) {
		this.city=city;
		this.pin=pin;
	}
}
public class Student implements Cloneable {
	String name;
	Address address;


public Object clone() throws CloneNotSupportedException{
	Student s=(Student)super.clone();
	s.address=new Address(this.address.city,this.address.pin);
	return s;
}
void display() {
	System.out.println("Name :"+name+"","City: "+address.city+,"Pincode"+address.pin);;
}
}
class Studentsystem{
	public static void main(String[] args) throws Exception {
		Student s1=new Student("Sriraj","Kanigiri");
		Student s2=(Student)s1.clone();
		System.out.println(s1.name);
		System.out.println(s2.name);
	}
}
