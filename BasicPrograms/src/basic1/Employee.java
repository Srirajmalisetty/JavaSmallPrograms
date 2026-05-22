package basic1;

public class Employee {
	String name;
	int id;
	Employee(String name,int id){
		this.name=name;
		this.id=id;
	}
		void display() {
			System.out.println("Employee Details");
			System.out.println("Employee Name"+name);
			System.out.println("Employee ID"+id);
		}
}
		class Fulltimeemployee extends Employee{
			double salary;
		public Fulltimeemployee(String name,int id,double salary) {
			super(name,id);
			this.salary=salary;}	
		void display() {
				System.out.println("Full Time Employee");
				super.display();
				System.out.println("Employee Salary"+salary);
				System.out.println("-----------------------------------");
			}
			
		
			
		}
	class Parttimeemployee extends Employee{
		int hoursworked;
		double rateperhour;

		Parttimeemployee(String name, int id,int hoursworked,double rateperhour) {
			super(name, id);
			this.hoursworked=hoursworked;
			this.rateperhour=rateperhour;
		}
		void display() {
			 double salary =hoursworked*rateperhour;
			System.out.println("Part Time Employee");
			super.display();
			System.out.println("Employee Salary"+salary);
			
			System.out.println();
		}
	}
 class hello{
	public static void main(String[] args) {
		Employee e1=new Fulltimeemployee("Sriraj", 123, 10);
		
		e1.display();
		Employee e2=new Parttimeemployee("Sri", 1,  12, 9);
		e2.display();
	}
}