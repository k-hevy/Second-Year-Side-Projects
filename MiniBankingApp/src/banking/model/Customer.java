package banking.model;

public class Customer {
	
	private int accountNumber;
	private String customerName;
	private String passwordCode;
	private double current;
	private double savings;
	
	public Customer (int accountNumber, String customerName, String passwordCode, double currentBalance, double savingsBalance) {
		this.accountNumber = accountNumber;
		this.customerName = customerName;
		this.passwordCode = passwordCode;
		this.current = currentBalance;
		this.savings = savingsBalance;
	}

	public void setCurrent(double amount) {
		this.current = amount;
	}

	public void setSavings(double amount) {
		this.savings = amount;
	}
	
	public int getAccountNumber () {
		return accountNumber;
	}
	
	public String getCustomerName () {
		return customerName;
	}
	
	public String getPasswordCode () {
		return passwordCode;
	}
	
	public double getCurrentBalance () {
		return current;
	}
	
	public double getSavingsBalance () {
		return savings;
	}

}
