package banking.dao;

import java.sql.*;
import banking.DatabaseConnection;
import banking.model.Account;
import banking.model.CurrentAccount;
import banking.model.Customer;
import banking.model.SavingsAccount;

public class CustomerDAO {

    Connection con = DatabaseConnection.getConnection();

    public Customer accountLogin (String username, String password) {

        try {

            String sql = "SELECT * FROM customer WHERE customer_name = ? AND password_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("account_number"),
                    rs.getString("customer_name"),
                    rs.getString("password_code"),
                    rs.getDouble("current"),
                    rs.getDouble("savings")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean accountCreation (String username, String password) {
        try {

            if (username.length() < 8) {
                System.out.println("Name should atleast have 8 characters or more");
                return false;
            } else if (password.length() < 8) {
                System.out.println("password should atleast have 8 characters or more");
                return false;
            } else if (!password.matches(".*[a-z].*")) {
                System.out.println("password should have atleast 1 lowercase letter");
                return false;
            } else if (!password.matches(".*[A-Z].*")) {
                System.out.println("password should have atleast 1 uppercase letter");
                return false;
            } else if (!password.matches(".*[0-9].*")) {
                System.out.println("password should have atleast 1 number");
                return false;
            }

            String sql = "INSERT INTO customer (customer_name, current, savings, password_code) VALUES (?, 1000, 1500, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username );
            ps.setString(2, password );

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("That account already Exist!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Customer getCustomer(int accountNumber) {
        try {

            String sql = "SELECT * FROM customer WHERE account_number = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("account_number"),
                    rs.getString("customer_name"),
                    rs.getString("password_code"),
                    rs.getDouble("current"),
                    rs.getDouble("savings")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean transferMoney(int senderAccount, int receiverAccount, double amount) {

        try {

			con.setAutoCommit(false);
			
			String debit = "UPDATE customer SET current = current - ? WHERE account_number = ?";
			PreparedStatement psDebit = con.prepareStatement(debit);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2, senderAccount);
			psDebit.executeUpdate();
			
			String credit = "UPDATE customer SET current = current + ? WHERE account_number = ?";
			PreparedStatement psCredit = con.prepareStatement(credit);
			psCredit.setDouble(1, amount);
			psCredit.setInt(2, receiverAccount);
			psCredit.executeUpdate();
            
			con.commit();
			return true;
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}

        return false;
    }

    public boolean depositMoney(Customer customer, Account account, double amount) {

		try {

            String credit = "";
			con.setAutoCommit(false);
			
            if (account instanceof CurrentAccount) {
                credit = "UPDATE customer SET current = current + ? WHERE account_number = ?";
            } else if (account instanceof SavingsAccount) {
                credit = "UPDATE customer SET savings = savings + ? WHERE account_number = ?";
            }

			PreparedStatement psCredit = con.prepareStatement(credit);
			psCredit.setDouble(1, amount);
			psCredit.setDouble(2, customer.getAccountNumber());
			psCredit.executeUpdate();

			con.commit();
			return true;
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}

        return false;

    }

    public boolean withdrawMoney(Customer customer, Account account, double amount) {
        try {

            String debit = "";
			con.setAutoCommit(false);
			
            if (account instanceof CurrentAccount) {
                debit = "UPDATE customer SET current = current - ? WHERE account_number = ?";
            } else if (account instanceof SavingsAccount) {
                debit = "UPDATE customer SET savings = savings - ? WHERE account_number = ?";
            }

			PreparedStatement psDebit = con.prepareStatement(debit);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2,  customer.getAccountNumber());
			psDebit.executeUpdate();
			
			con.commit();
			return true;
		} catch (Exception e) {
			try { 
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace(); 
			}
			e.printStackTrace();
		}
        return false;
    }

    public void viewTransactionHistory(Customer customer) {
        try {
            String sql = "SELECT * FROM transactions WHERE sender_account = ? OR receiver_account = ? ORDER BY transaction_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customer.getAccountNumber());
            ps.setInt(2, customer.getAccountNumber());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println(
                    " Type" + rs.getString("type") + 
                        " | Amount: " + rs.getDouble("amount") +
                        " | Sender: " + rs.getInt("sender_account") +
                        " | Receiver: " + rs.getInt("receiver_account") +
                        " | Date: " + rs.getTimestamp("transaction_date"));
            }
			} catch (Exception e) {
				e.printStackTrace();		
			}
        }

    public boolean changePassword (Customer customer, String newPassword) {

        try {

            if (newPassword.length() < 8) {
                System.out.println("password should atleast have 8 characters or more");
                return false;
            } else if (!newPassword.matches(".*[a-z].*")) {
                System.out.println("password should have atleast 1 lowercase letter");
                return false;
            } else if (!newPassword.matches(".*[A-Z].*")) {
                System.out.println("password should have atleast 1 uppercase letter");
                return false;
            } else if (!newPassword.matches(".*[0-9].*")) {
                System.out.println("password should have atleast 1 number");
                return false;
            } else if (newPassword.equals(customer.getPasswordCode())) {
                System.out.println("new cannot equal to old password");
                return false;
            }

            String update = "UPDATE customer SET password_code = ? WHERE account_number = ?";
			PreparedStatement psUpdate = con.prepareStatement(update);
			psUpdate.setString(1, newPassword);
			psUpdate.setInt(2, customer.getAccountNumber());
			
			int rows = psUpdate.executeUpdate();
			
			if (rows == 1) {
				return true;
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}

        return false;

    }

    public boolean deleteAccount(Customer customer) {
        try {
            String delete = "DELETE FROM customer WHERE account_number = ?";
			PreparedStatement deletePs = con.prepareStatement(delete);
			deletePs.setInt(1, customer.getAccountNumber());
			
			int rows = deletePs.executeUpdate();
			if (rows == 1) {
				return true;
			}
            con.commit();
			return false;
    
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}

        return false;
    }

}
