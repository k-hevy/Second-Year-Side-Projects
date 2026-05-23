package banking.model;

public class CurrentAccount extends Account {

    public CurrentAccount(double balance) {
        super(balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }

        if (amount > 50000) {
            System.out.println("Maximum withdrawal amount for current is 50,000");
            return false;
        }

        balance = balance - amount;
        return true;
    }

    public void deposit(double amount) {
        balance = balance + amount; 
    }
}