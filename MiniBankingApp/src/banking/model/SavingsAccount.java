package banking.model;

public class SavingsAccount extends Account {

    public SavingsAccount(double balance) {
        super(balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Insufficient funds");
            return false;
        }

        if (balance - amount < 500) {
            System.out.println("Minimum maintaining balnce is 500");
            return false;
        }

        if (amount > 25000) {
            System.out.println("Maximum withdrawal amount for savings is 25,000");
            return false;
        }

        balance = balance - amount;
        return true;
    }

    @Override
    public void deposit(double amount) {
        balance = balance + amount;
    }


}