package banking.service;

import banking.dao.TransactionDAO;
import banking.model.Account;
import banking.model.CurrentAccount;
import banking.model.SavingsAccount;
import banking.ui.Menu;
import banking.model.Customer;

import banking.dao.CustomerDAO;

public class AccountService {

    TransactionDAO transactionDAO = new TransactionDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    Menu menu = new Menu();

    public boolean transferMoney(Customer customer, int receiverAccount, double amount) {

        Customer receiver = customerDAO.getCustomer(receiverAccount);

        if (menu.askUser("Transfer " + amount + "to :" + receiverAccount) == 'N') {
            return false;
        }

        if (receiver == null) {
            System.out.println("That account does not exist!");
            return false;
        }
        
        if (amount <= 0) {
            System.out.println("Ammount cannot be zero or less!!");
            return false;
        }

        if (customer.getAccountNumber() == receiverAccount) {
            System.out.println("You cannot tarnsfer money to yourself!");
            return false;
        }

        if (customerDAO.transferMoney(customer.getAccountNumber(), receiverAccount, amount)) {
            customer.setCurrent(customer.getCurrentBalance() - amount);
            transactionDAO.addTransaction(customer.getAccountNumber(), receiverAccount, "Transfer", amount);
            return true;
        }
        
        return false;
    }

    public boolean depositMoney (Customer customer, Account account, double amount) {

        if (amount <= 0) {
            System.out.println("Amount cannot be zero or less!!");
            return false;
        }

        if (customer.getAccountNumber() == 0 || amount == 0) {
			System.out.println("All fields are required");
			return false;
		}

        if (amount > 50000) {
			System.out.println("Maximum deposit is limited to 50,000");
			return false;
		}

        if (menu.askUser("Deposit " + amount) == 'N') {
            return false;
        }
		
        if (customerDAO.depositMoney(customer, account, amount)) {
            account.deposit(amount);

            if (account instanceof CurrentAccount) {
                customer.setCurrent(account.getBalance());
                transactionDAO.addTransaction(customer.getAccountNumber(), customer.getAccountNumber(), "Deposit-Current", amount);
            } else if (account instanceof SavingsAccount) {
                customer.setSavings(account.getBalance());
                transactionDAO.addTransaction(customer.getAccountNumber(), customer.getAccountNumber(), "Deposit-Savings", amount);
            }
            
            System.out.println("Updated Balance: " + account.getBalance());
            return true;
        }

        return false;
    }

    public boolean withdrawMoney(Customer customer, Account account, double amount) {

        if (menu.askUser("Withdraw " + amount) == 'N') {
            return false;
        }

        if (customerDAO.withdrawMoney(customer, account, amount)) {
            account.withdraw(amount);

            if (account instanceof CurrentAccount) {
                customer.setCurrent(account.getBalance());
                transactionDAO.addTransaction(customer.getAccountNumber(), customer.getAccountNumber(), "Withdraw-Current", amount);
            } else if (account instanceof SavingsAccount) {
                customer.setSavings(account.getBalance());
                transactionDAO.addTransaction(customer.getAccountNumber(), customer.getAccountNumber(), "Withdraw-Savings", amount);
            }
            
            System.out.println("Updated Balance: " + account.getBalance());
            return true;
        }
        return false;
    }

    public boolean changePassword (Customer customer, String newPassword, String confirmPassword) {

		try {
			
			if (!newPassword.contains(confirmPassword)) {
				System.out.println("Passwords do not match");
				return false;
			}

            if (menu.askUser("change password ") == 'N') {
            return false;
        }
			
			return customerDAO.changePassword(customer, newPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }

    public boolean deleteAccount(Customer customer) {
        try {

            if (customer.getCurrentBalance() > 0 || customer.getSavingsBalance() > 500) {
                System.out.println("Please withdraw all money first");
				return false;
            }
			
            char choice = menu.askUser("Are you sure to delete the account?");
			if (choice == 'N') {
				return false;
			}

            return customerDAO.deleteAccount(customer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;

    }
}
