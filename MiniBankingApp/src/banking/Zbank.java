package banking;

import java.io.*;

public class Zbank {
	
	public static void main(String kean[]) {
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String name;
		String passwordCode;
		int ch;
		boolean error = false;
		
		do {
		
		System.out.println();
		System.out.println("=".repeat(50));
		System.out.println("             Welcome to BayanBank!   ");
		System.out.println("=".repeat(50));
		System.out.println();
		System.out.println("[1] Create Account");
		System.out.println("[2] Login Account");
		System.out.println("[3] Leave & Exit");
		System.out.println("");
			
			try {
			System.out.print("Enter choice: ");
			ch = Integer.parseInt(sc.readLine());
			
				switch (ch) {
				
					case 1 :
						System.out.println("\n-------------------------------------\n");
						System.out.println("Welcome new user! Please enter the following");
						System.out.println("");
						System.out.print("Username: ");
						name = sc.readLine();
						System.out.print("Password: ");
						passwordCode = sc.readLine();
						
						if (ZBankManagement.createAccount(name, passwordCode)) {
							System.out.println("You can now login from the main menu");
						}
						error = true;
						break;
					
					case 2 : 
						System.out.println("\n-------------------------------------\n");
						System.out.println("Welcome back! Please enter the following");
						System.out.println("");
						System.out.print("Username: ");
						name = sc.readLine();
						System.out.print("Password: ");
						passwordCode = sc.readLine();
						if (!ZBankManagement.accountLogin(name, passwordCode)) {
							System.out.println("Invalid Username/password, please try again");
						}
						error = true;
						break;
						
					case 3 :
						System.out.println("\n-------------------------------------\n");
						System.out.println("Thank you for using BayanBank!!! Goodbye");
						System.exit(0);
						break;
						
					default :
						System.out.println("");
						System.out.println("Invalid Input, Please try again");
						error = true;
				}
			
			} catch (IOException e) {
				System.out.println("");
				System.out.println("Please enter a Valid input!!!");
				error = true;
			} catch (NumberFormatException e) {
				System.out.println("");
				System.out.println("Please only enter a number!!!");
				error = true;
			}
		
		} while (error);
		
	}

}
