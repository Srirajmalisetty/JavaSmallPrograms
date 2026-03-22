package atm;

public class Atm {
	private double balance;
	private double depositeAmount;
	private double withdrawAmount;
	public Atm() {
		super();
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getDepositeAmount() {
		return depositeAmount;
	}
	public void setDepositeAmount(double depositeAmount) {
		this.depositeAmount = depositeAmount;
	}
	public double getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String toString() {
		return "Atm [balance=" + balance + ", depositeAmount=" + depositeAmount + ", withdrawAmount=" + withdrawAmount
				+ "]";
	}
	

}
