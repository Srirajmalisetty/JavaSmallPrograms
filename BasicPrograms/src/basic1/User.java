package basic1;

import java.util.Scanner;

class User {
private String username;
private String password;
private double balance;
public void setUsername(String username) {
	this.username = username;
}
public void setPassword(String password) {
	if(password.length()>=6) 
	{this.password = password;
	System.out.println("Password set successfully");
}else {
	System.out.println("Password  must be atleast 6 Characters");
}
	}
void deposite(double amount) {
	if(amount>0) {
		balance+=amount;
		System.out.println("Deposite Amount: "+amount);
	}
}
void withdraw(double amount) {
	if(amount<=balance&&amount>0){
	balance-=amount;	
	System.out.println("Withdraw Amount: "+amount);
	}
}
public void showpassword() {
	if(password !=null) {
		System.out.println("Password is ******");
	}else {
		System.out.println("Password is not set");
	}
}
public void setBalance(double balance) {
	this.balance = balance;
}
public String getUsername() {
	return username;}
public double getBalance() {
	return balance;
}
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	User u=new User();
	u.setUsername("Sriraj");
	System.out.println("Username"+u.getUsername());
	System.out.println("Enter Password");
	String p=sc.nextLine();
	u.setPassword(p);
	System.out.println("Enter Deposite Amount");
	double a=sc.nextDouble();
	u.deposite(a);
	System.out.println("_______________________________________");
	System.out.println("Enter Withdraw Amount");
	double w=sc.nextDouble();
	u.withdraw(w);
	System.out.println("_______________________________________");
	System.out.println("User Name: "+u.getUsername());
	System.out.println("Balance: "+u.getBalance());
	sc.close();
	
}
}
