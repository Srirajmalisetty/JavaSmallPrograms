package basic1;

public class Payment {
	void pay(double Amount) {
		System.out.println("Processing Payment");
	}
		double calculatefee(double Amount) {
			return 0;
		}
	
}
class creditpay extends Payment{
	@Override
	void pay(double Amount) {
		double fee=calculatefee(Amount);
		super.pay(Amount);
		System.out.println("Paid $Amount using Credit card: "+Amount);
		System.out.println("Fee: "+fee);
		System.out.println(Amount+fee);
	}
	double calculatefee(double Amount) {
		return Amount*0.02;
	}
}
class UPI extends Payment{

void pay(double Amount) {
	double fee=calculatefee(Amount);
	super.pay(Amount);
	System.out.println("Paid Amount using UPI: "+Amount );
	System.out.println("Fee: "+fee);
	System.out.println(Amount+fee);
}	
double calculatefee(double Amount) {
	return Amount*0.01;
}
}
class Cash extends Payment{

	void pay(double Amount) {
		
		double fee=calculatefee(Amount);
		super.pay(Amount);
		System.out.println("Paid Amount using Cash: "+Amount);
		System.out.println("Fee: "+fee);
		System.out.println(Amount+fee);	
	}
	double calculatefee(double Amount) {
		return 0;
	}
}
class MAin{
	
	public static void main(String[] args) {
		Payment p1=new creditpay();
		p1.pay(1000);
		Payment p2=new UPI();
		p2.pay(1500);
		Payment p3=new Cash();
		p3.pay(2000);
	}
}