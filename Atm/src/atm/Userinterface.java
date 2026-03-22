package atm;

import java.util.Scanner;

public class Userinterface {
	private static int status;
	public static void main(String[] args) {
		AtmOperationImpl impl=new AtmOperationImpl();
		Scanner scan=new Scanner(System.in);
		int atmnumber=123456;
		int atmpin=123;
		System.out.println("Enter the atm number");
		int atmnum2=scan.nextInt();
		System.out.println("Enter The Atm Pin");
		int atmpin2=scan.nextInt(); 
		if(atmnumber==atmnum2&& atmpin==atmpin2) {
			while(true) {
		System.out.println("\n 1.View Balance\n 2.Withdraw Amount\n 3.Deposite Amount\n 4.View Ministatement\n 5.exit" );
			System.out.println("Enter Your Choice");
			int ch=scan.nextInt();
			if(ch==1) {
				impl.viewbalance();
			}else if(ch==2) {
				System.out.println("Enter the Withdraw Amount");
				double withdrawamount=scan.nextDouble();
				impl.withdrawAmount(withdrawamount);
			}else if(ch==3) {
				System.out.println("Enter The Dposite Amount");
				double deposieamount=scan.nextDouble();
				impl.depositeAmount(deposieamount);
			}else if(ch==4) {
				impl.viewbalance();
			}else if(ch==5) {
				System.out.println("Collect Your Atm Card\n Thank You");
				System.exit(status);
			}
			}
		}else {
			System.out.println("Entered incorrect atmnumber or pin");
		}
	}

}
