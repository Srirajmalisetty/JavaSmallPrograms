package basic1;
import java.util.InputMismatchException;
import java.util.Scanner;
public class TryCatchDemo {
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
try {	
	System.out.println("Enter Numerator");
	int a=sc.nextInt();
	System.out.println("Enter Denominator");
	int b=sc.nextInt();
	System.out.println("\nResult");
	float result=a/b;
	System.out.println("Result: "+result);
}catch (ArithmeticException e) {
	System.out.println("Arithmetic Caught :"+e);	
}catch (InputMismatchException e) {
	System.out.println("Error: Enter Valid Integers");
}catch (Exception e) {
	System.out.println("Unexpexted Error"+e.getMessage());
}finally {
	if(sc !=null) {
		sc.close();
		System.out.println("Scanner Closed");
	}
}
}
}
