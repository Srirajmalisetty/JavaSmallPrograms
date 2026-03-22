package atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AtmOperationImpl implements AtmInterface {

	Atm atm=new Atm();
	Map<Double,String>ministmt=new HashMap<>();
	public void viewbalance() {
		System.out.println("Available Balance"+atm.getBalance());
		
	}

	
	public void withdrawAmount(double withdrawAmount) {
		if(withdrawAmount%500==0) {
			if(withdrawAmount<=atm.getBalance()) {
				System.out.println("collect the cash"+withdrawAmount);
				atm.setBalance(atm.getBalance()-withdrawAmount);
			ministmt.put(withdrawAmount, "Amount Withdrawed");
			viewbalance();
			}else {
				System.out.println("Insufficint Balance");
			}
		}else {
			System.out.println("Enter amount in terms of 500 only");
		}
		
	}

	
	public void depositeAmount(double depositeAmount) {
		System.out.println("Deposited Amount is"+depositeAmount);
		atm.setBalance(atm.getBalance()+depositeAmount);
		ministmt.put(depositeAmount, "Deposite Successfully");
		viewbalance();
		
		
	}

	
	public void viewMiniStatement() {
		Set<Double> set=ministmt.keySet();
		for(Double d:set) {
			System.out.println(d+"="+ministmt.get(d));
		}
		
	}

}
