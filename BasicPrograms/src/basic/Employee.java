package basic;

public class Employee {
	int id;
	String name;
	double salary;
	
				Employee(){
				 id=12;
				 name="Sriraj" ;
				 salary=10000;
				}
				Employee(int i,String n,double s){	
					id=i;
					name=n;
					salary=s;
					
				}
				void display() {
					System.out.println("Employee Name:"+name);
					System.out.println("Employee ID:"+id);
					System.out.println("Employee Salary"+salary);
				}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee e=new Employee();
		e.display();
		Employee e1=new Employee(123,"Sriraj",10000);
		e1.display();
	}

}
