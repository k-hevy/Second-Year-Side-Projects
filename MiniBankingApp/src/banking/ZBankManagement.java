package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.*;

public class ZBankManagement {
	
	/*Improvements - 
				* banking/
				├── Main.java
				├── DatabaseConnection.java
				│
				├── model/
				│   ├── Customer.java
				│   ├── Account.java
				│   ├── SavingsAccount.java
				│   ├── CurrentAccount.java
				│   └── Transaction.java
				│
				├── service/
				│   ├── AuthService.java
				│   ├── AccountService.java
				│   └── TransactionService.java
				│
				├── dao/
				│   ├── CustomerDAO.java
				│   └── TransactionDAO.java
				│
				└── ui/
    	        └── Menu.java

	 * password Strength checker
	 * name conventions
	 * Hidden password input
	 * login attempts limit
	 * Transfer limit
	 * Adding confirmation prompts 
	 * Handling negative Numebrs
	 * withdrae limit for saving accounnt
	 * when to use Update Select and Auto COmmit
	 * transfering money to ghosts accounts
	 * */
	
	static Connection con = DatabaseConnection.getConnection();
	private static final int NULL = 0;
	static BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
	
	public static boolean createAccount(String name, String passwordCode) {
		
		if (name.isEmpty() || passwordCode.isEmpty()) {
			System.out.println("All fields are required!");
			return false;
		}
		
		try {
			String sql = "INSERT INTO customer(customer_name, balance, password_code) VALUES (?, 1000, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, passwordCode);
			
			int rows = ps.executeUpdate();
			if (rows == 1) {
				System.out.println("Account succesfully created, please login.");
				return true;
			}
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username already exists! Enter a new one");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	} // Creates Account, checks for duplicates Impr: Need password strength checker
	
	public static boolean accountLogin (String name, String passwordCode) {
		
		if (name.isEmpty() || passwordCode.isEmpty()) {
			System.out.println("All fields are required!");
			return false;
		}
		
		boolean loggedIn = true;
		
		while (loggedIn) {
		
			try {
				String sql = "SELECT * FROM customer WHERE customer_name = ? AND password_code = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, passwordCode);
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					
					int senderAccount = rs.getInt("account_number");
					
					System.out.println();
					System.out.println("=".repeat(50));
					System.out.println("     Hello " + rs.getString("customer_name") + " What would you like to do today?");
					System.out.println("=".repeat(50));
					System.out.println();
					
					System.out.println("[1] View Balance (Savings and current)");
					System.out.println("[2] Transfer Money");
					System.out.println("[3] Deposit (Savings and Current)");
					System.out.println("[4] Withdraw (Savings and Current)");
					System.out.println("[5] Transaction History");
					System.out.println("[6] Change Password");
					System.out.println("[7] Delete Account");
					System.out.println("[8] Logout");
					System.out.println();
					System.out.print("Choice: ");
					int ch = Integer.parseInt(sc.readLine());
					
					switch (ch) {
					
						case 1 :
							getBalance(senderAccount);
							pressSpace();
							break;
						case 2 :
							System.out.print("Enter Receiver Account: ");
							int receiverAccount = Integer.parseInt(sc.readLine());
							System.out.print("Enter Transfer Amount: ");
							double amount2 = Double.parseDouble(sc.readLine());
							if (transferMoney(senderAccount, receiverAccount, amount2)) {
								System.out.print("Transaction Succesful!");
							} else {
								System.out.print("Transaction Failed!");
							}
							pressSpace();
							break;
							
						case 3 :
							int ch3 = getDepositOrWithdrawChoice("Deposit");
							System.out.println("Enter amount to deposit: ");
							double amount3 = Double.parseDouble(sc.readLine());
							
							switch (ch3) {
								case 1 : 
									if (depositCurrent(amount3, senderAccount)) { 
										System.out.println("Deposit Succesful"); 
										} else { 
											System.out.println("Deposit Unsuccesful"); 
										} 
									break;
								case 2:  if (depositSavings(amount3, senderAccount)) { 
									System.out.println("Deposit Succesful"); 
									} else { 
										System.out.println("Deposit Unsuccesful"); 
									}
								pressSpace();
								break;
							}
							break;
						case 4 :
							int ch4 = getDepositOrWithdrawChoice("Withdraw");
							System.out.println("Enter Amount to withdraw: ");
							double amount4 = Double.parseDouble(sc.readLine());
							
							switch (ch4) {
								case 1 : if (withdrawCurrent(amount4, senderAccount)) {
									System.out.println("Withdraw Succesful");
								} else {
									System.out.println("Withdraw Unsuccesful");
								}
								break;
								case 2 : if (withdrawSavings(amount4, senderAccount)) {
									System.out.println("Withdraw Succesful");
								} else {
									System.out.println("Withdraw Unsuccesful");
								}
							}
							pressSpace();
							break;
						case 5 :
							if (viewTransactionHistory(senderAccount)) {
								System.out.println("Record viewing succesful");
							} else {
								System.out.println("Record viewing unsuccesful");
							}
							pressSpace();
							break;
						case 6:
							if (changePassword(senderAccount)) {
								System.out.println("Password change Succesful");
							} else {
								System.out.println("Password change Unsuccesful");
							}
							break;
							
						case 7:
							if (deleteAccount(senderAccount)) {
								System.out.println("Account succesfully deleted, logging out");
								loggedIn = false;
							} else {
								System.out.println("Account deletion unsuccesful");
							}
							break;
						case 8:
							loggedIn = false;
							return true;
						default : 
							System.out.println("An error occured");
							
					}
					
				} else {
					return false;
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Please enter only a number!");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return false;
	} // Contains the Selection logic
	
	public static void getBalance(int accountNumber) {
		
		try {
			
			String sql = "SELECT * FROM customer WHERE account_number = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			
			System.out.println();
			System.out.println("=".repeat(50));
			System.out.println();
			System.out.printf("%-12s %-13s %10s %10s", "Account no", "Customer Name", "Balance", "Savings");
			System.out.println();
			
			while (rs.next()) {
				System.out.printf("%-13d %-13s %10.2f %10.2f", rs.getInt("account_number"), rs.getString("customer_name"), rs.getDouble("balance"), rs.getDouble("savings"));
			}
			System.out.println();
			System.out.println();
			System.out.println("=".repeat(50));
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} // Displays Balance as well as account number and name

	public static boolean transferMoney(int senderAccount, int receiverAccount, double amount) {
		
		if (senderAccount == NULL || receiverAccount == NULL || amount == NULL) {
			System.out.println("All fields are required");
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			String checkBalance = "SELECT balance FROM customer WHERE account_number = ?";
			PreparedStatement ps = con.prepareStatement(checkBalance);
			ps.setInt(1, senderAccount);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next() && rs.getInt("balance") < amount) {
				System.out.println("Insufficient Balance Please try again!");
				return false;
			}
			
			String debit = "UPDATE customer SET balance = balance - ? WHERE account_number = ?";
			PreparedStatement psDebit = con.prepareStatement(debit);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2, senderAccount);
			psDebit.executeUpdate();
			
			String credit = "UPDATE customer SET balance = balance + ? WHERE account_number = ?";
			PreparedStatement psCredit = con.prepareStatement(credit);
			psCredit.setDouble(1, amount);
			psCredit.setInt(2, receiverAccount);
			psCredit.executeUpdate();
			
			String history = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, ?, ?, ?)";
			PreparedStatement psHistory = con.prepareStatement(history);
			psHistory.setInt(1, senderAccount);
			psHistory.setInt(2, receiverAccount);
			psHistory.setString(3, "Transfer");
			psHistory.setDouble(4, amount);
			psHistory.executeUpdate();
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
	} // handles money transfers
	
	public static int getDepositOrWithdrawChoice(String text) {
		int ch = 0;
		while(true) {
			try {
				System.out.println("");
				System.out.println("Please select where to" + text);
				System.out.println("[1] Current Balance");
				System.out.println("[2] Savings Balance");
				System.out.println("");
				System.out.print("Choice: ");
				ch = Integer.parseInt(sc.readLine());
				if (ch < 1 || ch > 2) {
					System.out.println("Please Enter a correct choice!");
				}
				return ch;
			} catch (IOException e) {
				System.out.println("I/O error occured");	
			} catch (NumberFormatException e) {
				System.out.println("Please Enter only a number");	
			}
		}
	} // Asks The user to choose savings or current
	
	public static boolean depositCurrent (double amount, int senderAccount) {
		
		if (senderAccount == NULL || amount == NULL) {
			System.out.println("All fields are required");
			return false;
		}
		
		if (amount <= NULL) {
			System.out.println("Please input a valid amount");
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			
			String credit = "UPDATE customer SET balance = balance + ? WHERE account_number = ?";
			PreparedStatement psCredit = con.prepareStatement(credit);
			psCredit.setDouble(1, amount);
			psCredit.setDouble(2, senderAccount);
			psCredit.executeUpdate();
			
			String checkBalance = "SELECT balance FROM customer WHERE account_number = ?";
			PreparedStatement ps = con.prepareStatement(checkBalance);
			ps.setInt(1, senderAccount);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Updated Current Amount :" + rs.getDouble("balance"));
			}
			String history = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, NULL, ?, ?)";
			PreparedStatement psHistory = con.prepareStatement(history);
			psHistory.setInt(1, senderAccount);
			psHistory.setString(2, "Deposit-Current");
			psHistory.setDouble(3, amount);
			psHistory.executeUpdate();
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
	} // deposits in current
	
	public static boolean depositSavings (double amount, int senderAccount) {
		if (senderAccount == NULL || amount == NULL) {
			System.out.println("All fields are required");
			return false;
		}
		

		if (amount <= NULL) {
			System.out.println("Please input a valid amount");
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			
			String credit = "UPDATE customer SET savings = savings + ? WHERE account_number = ?";
			PreparedStatement psCredit = con.prepareStatement(credit);
			psCredit.setDouble(1, amount);
			psCredit.setDouble(2, senderAccount);
			psCredit.executeUpdate();
			
			String checkSavings = "SELECT savings FROM customer WHERE account_number = ?";
			PreparedStatement ps = con.prepareStatement(checkSavings);
			ps.setInt(1, senderAccount);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Updated Savings Amount: " + rs.getDouble("savings"));
			}
			String history = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, NULL, ?, ?)";
			PreparedStatement psHistory = con.prepareStatement(history);
			psHistory.setInt(1, senderAccount);
			psHistory.setString(2, "Deposit-Savings");
			psHistory.setDouble(3, amount);
			psHistory.executeUpdate();
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
	} // deposits in savings
	
	public static boolean withdrawCurrent(double amount, int senderAccount) {
		if (amount == NULL || senderAccount == NULL) {
			System.out.println("Allf fields are required");
			return false;
		}
		

		if (amount <= NULL) {
			System.out.println("Please input a valid amount");
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			String debit = "UPDATE customer SET balance = balance - ? WHERE account_number = ?";
			PreparedStatement psDebit = con.prepareStatement(debit);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2,  senderAccount);
			psDebit.executeUpdate();
			
			String checkBalance = "SELECT balance FROM customer where account_number = ?";
			PreparedStatement ps = con.prepareStatement(checkBalance);
			ps.setInt(1, senderAccount);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				System.out.println("Updated Current Amount: " + rs.getDouble("balance"));
			}
			String history = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, NULL, ?, ?)";
			PreparedStatement psHistory = con.prepareStatement(history);
			psHistory.setInt(1, senderAccount);
			psHistory.setString(2, "Withdraw-Current");
			psHistory.setDouble(3, amount);
			psHistory.executeUpdate();
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
	
	public static boolean withdrawSavings(double amount, int senderAccount) {
		if (amount == NULL || senderAccount == NULL) {
			System.out.println("Allf fields are required");
			return false;
		}
		

		if (amount <= NULL) {
			System.out.println("Please input a valid amount");
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			String Debit = "UPDATE customer SET savings = savings - ? WHERE account_number = ?";
			PreparedStatement psDebit = con.prepareStatement(Debit);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2, senderAccount);
			psDebit.executeUpdate();
			
			
			String checkBalance = "SELECT savings FROM customer WHERE account_number = ?";
			PreparedStatement ps = con.prepareStatement(checkBalance);
			ps.setInt(1, senderAccount);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				System.out.println("Updated Savings Amount: " + rs.getDouble("savings"));
			}
			String history = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, NULL, ?, ?)";
			PreparedStatement psHistory = con.prepareStatement(history);
			psHistory.setInt(1, senderAccount);
			psHistory.setString(2, "Withdraw-Savings");
			psHistory.setDouble(3, amount);
			psHistory.executeUpdate();
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
	
	public static boolean viewTransactionHistory(int senderAccount) {
			try {
				String sql = "SELECT * FROM transactions WHERE sender_account = ? OR receiver_account = ? ORDER BY transaction_date DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, senderAccount);
				ps.setInt(2, senderAccount);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					System.out.println(
							" Type" + rs.getString("type") + 
							" | Amount: " + rs.getDouble("amount") +
							" | Sender: " + rs.getInt("sender_account") +
							" | Receiver: " + rs.getInt("receiver_account") +
							" | Date: " + rs.getTimestamp("transaction_date")
							);
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();		
			}
		return false;
	}
	
	public static boolean changePassword (int senderAccount) {
		
		try {
			System.out.println("Enter old password");
			String oldPassword = sc.readLine();
			String verify = "SELECT * FROM customer WHERE account_number = ? AND password_code = ?";
			PreparedStatement psVerify = con.prepareStatement(verify);
			psVerify.setInt(1, senderAccount);
			psVerify.setString(2, oldPassword);
			ResultSet rs = psVerify.executeQuery();
			
			if (!rs.next()) {
				System.out.println("Incorrect password");
				return false;
			}
			
			System.out.println("Enter new password: ");
			String newPassword = sc.readLine();
			System.out.println("Enter new password: ");
			String confirmPassword = sc.readLine();
			
			if (!newPassword.contains(confirmPassword)) {
				System.out.println("Passwords do not match");
				return false;
			}
			
			String update = "UPDATE customer SET password_code = ? WHERE account_number = ?";
			PreparedStatement psUpdate = con.prepareStatement(update);
			psUpdate.setString(1, newPassword);
			psUpdate.setInt(2, senderAccount);
			
			int rows = psUpdate.executeUpdate();
			
			if (rows == 1) {
				System.out.println("Password succesfully changed");
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAccount (int senderAccount) {
		try {
			
			String check = "SELECT balance, savings FROM customer WHERE account_number = ?";
			PreparedStatement psCheck = con.prepareStatement(check);
			psCheck.setInt(1, senderAccount);
			ResultSet rs = psCheck.executeQuery();
			
			if (rs.next()) {
				if (rs.getDouble("balance") > 0 || rs.getDouble("savings") > 0) {
					System.out.println("Please withdraw all money first");
					return false;
				}
			}
			
			char choice = askUser("Are you sure to delete the account?");
			
			if (choice == 'N') {
				return false;
			}
			
			
			String delete = "DELETE FROM customer WHERE account_number = ?";
			PreparedStatement deletePs = con.prepareStatement(delete);
			deletePs.setInt(1, senderAccount);
			
			
			int rows = deletePs.executeUpdate();
			System.out.println("Account deletion Starting2");
			if (rows == 1) {
				System.out.println("Account succesfully deleted");
				return true;
			}
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void pressSpace() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String space = "";
		do {
			try {
				System.out.print("Press Enter to return to main: ");
				space = sc.readLine();
			} catch (IOException e) {
				System.out.println("A I/O error occured");
			}
		} while (!space.isBlank());
		
	} // Asks the user to press space to return to the main menu
	
	public static char askUser(String question) {
		while (true) {
			try {
			System.out.println(question + " [Y/N]");
			System.out.print("Choice: ");
			char choice = Character.toUpperCase(sc.readLine().charAt(0));
			
			if (choice == 'Y' || choice == 'N') {
				return choice;
			}
			System.out.println("Please Enter only Yes or No");
			
			} catch (NumberFormatException e) {
				System.out.println("Enter only a Yes Or No");
			} catch (IOException e) {
				System.out.println("A IO error occurerd");
			}
		}
	} // receives a question and return only Y/N

}
