package banking.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import banking.dao.CustomerDAO;
import banking.model.*;
import banking.service.AccountService;

/* 
	 * login attempts limit
	 * Adding confirmation prompts 
	  */

public class Menu {

    static BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
    CustomerDAO customerDAO = new CustomerDAO();
    AccountService accountService = new AccountService();

    public void start() {

        while (true) {
            try {
                System.out.println();
                System.out.println("=".repeat(50));
                System.out.println("             Welcome to BayanBank!   ");
                System.out.println("=".repeat(50));
                System.out.println();
                System.out.println("[1] Create Account");
                System.out.println("[2] Login Account");
                System.out.println("[3] Leave & Exit");
                System.out.println("");
                
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(sc.readLine());

                switch (choice) {
                    case 1 :
                        createAccountMenu();
                        break;
                    case 2 :
                        loginMenu();
                        break;
                    case 3 :
                        System.out.println("Thank you for using BayanBank!!! Goodbye");
                        System.exit(0);
                        break;
                    default :
                    System.out.println("Please enter a valid choice!");
                }
            } catch (IOException e) {
                System.out.println("");
				System.out.println("Please enter a Valid input!!!");
            } catch (NumberFormatException e) {
                System.out.println("");
				System.out.println("Please only enter a number!!!");
            }
        }
    }

    public void createAccountMenu() {
        try {
            System.out.println("\n-------------------------------------\n");
            System.out.println("Welcome new user! Please enter the following");
            System.out.println("");
            System.out.print("Username: ");
            String username = sc.readLine();
            System.out.print("Password: ");
            String password = sc.readLine();

            if (customerDAO.accountCreation(username, password)) {
                System.out.println("Account Creation Succesful");
            } else {
                System.out.println("Account Creation Usuccesful");
            }
        } catch (IOException e) {
            System.out.println("An IO error occured!");
        }
    }

    public void loginMenu() {
        try {
            System.out.println("\n-------------------------------------\n");
            System.out.println("Welcome back! Please enter the following");
            System.out.println("");
            System.out.print("Username: ");
            String username = sc.readLine();
            System.out.print("Password: ");
            String password = sc.readLine();

            Customer customer = customerDAO.accountLogin(username, password);

            if (customer != null) {
                accountMenu(customer);
            } else {
                System.out.println("Account login unsuccesful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accountMenu(Customer customer) {
        boolean loggedIn = true;
        while (loggedIn) {
            try {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("     Hello " + customer.getCustomerName() + " What would you like to do today?");
                System.out.println("=".repeat(50) + "\n");

                System.out.println("[1] View Balance (Savings and current)");
                System.out.println("[2] Transfer Money");
                System.out.println("[3] Deposit (Savings and Current)");
                System.out.println("[4] Withdraw (Savings and Current)");
                System.out.println("[5] Transaction History");
                System.out.println("[6] Change Password");
                System.out.println("[7] Delete Account");
                System.out.println("[8] Logout");
                System.out.print("\n" + "Choice: ");
                int choice = Integer.parseInt(sc.readLine());

                switch (choice) {
                    case 1 : viewBalance(customer);
                        break;
                    case 2 : transferMoney(customer);
                        break;
                    case 3 : depositMoney(customer);
                        break;
                    case 4 : withdrawMoney(customer);
                        break;
                    case 5 : viewTransactionHistory(customer);
                        break;
                    case 6 : changePassword(customer);
                        break;
                    case 7 : loggedIn = deleteAccount(customer);
                        break;
                    case 8 : System.out.println("Logging out..."); loggedIn = false;
                        break;
                    default :
                    break;
                }

                System.out.println();
                pressSpace();

            } catch (NumberFormatException e) {
                System.out.println("Please Enter only a number");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void viewBalance(Customer customer) {
        System.out.println("\n" + "=".repeat(50) + "\n");
        System.out.printf("%-12s %-13s %10s %10s", "Account no", "Customer Name", "Balance", "Savings" + "\n");

        System.out.printf("%-13d %-13s %10.2f %10.2f", customer.getAccountNumber(), customer.getCustomerName(), customer.getCurrentBalance(), customer.getSavingsBalance());
        System.out.println("\n" + "\n" +"=".repeat(50) + "\n" );
    }

    public void transferMoney (Customer customer) {
        try {
            System.out.print("Enter Receiver Account: ");
            int receiverAccount = Integer.parseInt(sc.readLine());
            System.out.print("Enter Transfer Amount: ");
            double amount = Double.parseDouble(sc.readLine());

            if (accountService.transferMoney(customer, receiverAccount, amount)) {
                System.out.print("Transaction Succesful!");
            } else {
                System.out.print("Transaction Cancelled!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please Enter only numbers!");
        } catch (IOException e) {
            System.out.println("An IO exception occured!");
        }
    }

    public void depositMoney (Customer customer) {
        try {

            System.out.println("Enter Amount to Deposit: ");
            double amount = Double.parseDouble(sc.readLine());
            
            Account account = null;

            switch (getAccountChoice()) {
                case 1 : 
                    account = new CurrentAccount(customer.getCurrentBalance());
                    break;
                case 2 : 
                    account = new SavingsAccount(customer.getSavingsBalance());
                    break;
            }

            if (accountService.depositMoney(customer, account, amount)) {
                        System.out.print("Deposit Succesful!");
                    } else {
                        System.out.print("Deposit Cancelled!");
                    }

        } catch (NumberFormatException e) {
            System.out.println("Please Enter only numbers!");
        } catch (IOException e) {
            System.out.println("An IO exception occured!");
        }

    }

    public void withdrawMoney (Customer customer) {
        try {
            System.out.println("Enter Amount to withdraw: ");
            double amount = Double.parseDouble(sc.readLine());
            
            Account account = null;

            switch (getAccountChoice()) {
                case 1 : 
                    account = new CurrentAccount(customer.getCurrentBalance());
                    break;
                case 2 : 
                    account = new SavingsAccount(customer.getSavingsBalance());
                    break;
            }

            if (accountService.withdrawMoney(customer, account, amount)) {
                        System.out.print("Withdrawal Succesful!");
                    } else {
                        System.out.print("Withdrawal Cancelled!");
                    }

        } catch (NumberFormatException e) {
            System.out.println("Please Enter only numbers!");
        } catch (IOException e) {
            System.out.println("An IO exception occured!");
        }

    }
    
    public void viewTransactionHistory (Customer customer) {
        customerDAO.viewTransactionHistory(customer);
        System.out.println("Succesfully viewed history");
    }

    public void changePassword (Customer customer) {
        try {
            System.out.println("Enter old password");
            String oldPassword = sc.readLine();

            if (oldPassword.equals(customer.getPasswordCode())) {
                System.out.println("Enter new password: ");
                String newPassword = sc.readLine();
                System.out.println("Enter new password: ");
                String confirmPassword = sc.readLine();
                if (accountService.changePassword(customer, newPassword, confirmPassword)) {
                    System.out.println("password Succesfully Changed");
                } else {
                    System.out.println("password Change Cancelled");
                }
            } else {
                System.out.println("incorrect Password");
            }

        } catch (IOException e) {
            System.out.println("An IO exception occured!");
        }

    }

    public boolean deleteAccount (Customer customer) {
        try {
            if (accountService.deleteAccount(customer)) {
                System.out.print("Account Deletion Succesful!");
                System.out.println("Logging out...");
                return false;
            } else {
                System.out.println("Account Deletion Cancelled");
                return true;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }

    public static int getAccountChoice() {
		int ch = 0;
		while(true) {
			try {
				System.out.println("");
				System.out.println("Please select Account type:");
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
    
    public static void pressSpace() {
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

    public char askUser(String question) {
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
