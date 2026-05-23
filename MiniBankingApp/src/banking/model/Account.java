package banking.model;

public abstract class  Account {
    protected double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public abstract boolean withdraw(double amount);
    public abstract void deposit(double amount);
    public double getBalance() {
        return balance;
    }
}