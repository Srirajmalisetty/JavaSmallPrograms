package basic1;

abstract class Vehicle {
	abstract void start();
	abstract void stop();
}
interface fueltype{
	void fuel();
}
class car extends Vehicle implements fueltype{
	void start() {
		System.out.println("Car starts with key");
	}	
	void stop() {
		System.out.println("Car Stops");	
	}
	public void fuel() {
	   System.out.println("Car is diesel type");
	}
}
class bike extends Vehicle implements fueltype{
	void start() {
		System.out.println("Bike starts with key");		
	}
	void stop() {
		System.out.println("Bike stops");
	}
	public void fuel() {
		System.out.println("Bike is a Petrol");
		
	}
}
class Plane{
	public static void main(String[] args) {
		car v=new car();
	      v.start();
	      v.stop();
	      v.fuel();
	      
	      
		
	}
}