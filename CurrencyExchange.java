
//October 2016

import java.util.Scanner;

public class CurrencyExchange {

	private static double balance = 0;

	/**
	 * Get the current balance of the account
	 */
	public static double getBalance() {
		return balance;
	}

	/**
	 * Update the balance of current account, will do a roundup to 2 significant
	 * digits
	 *
	 * @return if update succeed, will return true. If failed, return false
	 */
	private static boolean updateBalance(double newBalance) {

		balance = Math.round(newBalance * 100) / 100.0;
		if (balance >= 0) {
			return true;
		} else
			return false;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to Currency Exchange 2.0\n");
		printConversionTable();

		while (true) {
			int menuInput = mainMenuOptionSelector(input);

			// Shows different options with different menu inputs
			switch (menuInput) {

			// Shows balance
			case 1: {
				System.out.println("Your current balance is: " + getBalance());
			}
				break;

			// Allows user to deposit
			case 2: {
				int currencyInput = currencyMenuOptionSelector(input);

				System.out.println("Enter deposit amount: ");
				double amount = input.nextDouble();

				if (amount <= 0) {
					System.out.println("Logging Error");
				}

				else {
					deposit(amount, currencyInput);

					System.out.println(logTransaction(amount, currencyInput, true) + "\n");

				}

			}
				break;

			// Allows user to withdraw
			case 3: {

				int currencyInput = currencyMenuOptionSelector(input);

				System.out.println("Enter withdraw amount : ");
				double amount = input.nextDouble();

				if (amount <= 0) {
					System.out.println("Logging Error\n");

				}

				if (amount > getBalance()) {
					System.out.println("Error: Insufficient funds. Please try again.\n");

				}

				else if (getBalance() < 0) {
					System.out.println("Error: Insufficient funds. Please try again.\n");

				}

				else {

					boolean valid = withdraw(amount, currencyInput);

					if (valid == true) {
						System.out.println(logTransaction(amount, currencyInput, false) + "\n");
					}
				}

				break;
			}

			// Allows user to withdraw remaining amount and exit
			case 4: {
				if (getBalance() == 0) {
					System.out.println("Your remaining balance is 0.0 U.S Dollars.");
				} else {
					System.out.println("You successfully withdrew " + getBalance() + " U.S Dollars.");
				}
				System.out.println("Goodbye");
				System.exit(0);

			}
				break;
			default:
				break;

			}

		}

	}
	
	public static boolean deposit(double amount, int currencyType) {

		boolean result = true;

		if (currencyType >= 1 && currencyType <= 11) {

			// Checks if amount input is valid
			if (amount <= 0) {
				result = false;
			}


			// Only convert and update balance if amount is valid
			else {

				double convertedAmount = convertCurrency(amount, currencyType, true);
				balance += convertedAmount;

				updateBalance(balance);

				result = true;

			}

		}

		else {
			System.out.println("Logging Error");
			result = false;
		}

		return result;
	}

	public static boolean withdraw(double amount, int currencyType) {

		boolean result = true;

		if (currencyType >= 1 && currencyType <= 11) {
			// No convenience fee if USD
			if (currencyType == 1) {
				if (amount <= 0) {
					System.out.println("Logging Error");
					result = false;
				}

				else if (balance < amount) {
					System.out.println("Error: Insufficient funds. Please try again.\n");
					result = false;
				}

				else {
					balance -= amount;
					result = true;
				}
			}

			else {
				double convertedAmount = convertCurrency(amount, currencyType, true);
				double amountFee = convertedAmount * 1.005;

				if (amountFee <= 0) {
					System.out.println("Logging Error");
					result = false;
				}

				else if (balance < amountFee) {
					System.out.println("Error: Insufficient funds. Please try again.\n");
					result = false;
				}

				else {
					balance -= amountFee;
					result = true;
				}

			}
		}

		else {
			result = false;
		}

		return result;

	}

	public static double convertCurrency(double amount, int currencyType, boolean isConvertToUSD) {

		double rate = 0;
		// Converts foreign to USD
		switch (currencyType) {
		case 1:
			rate = 1;
			break;
		case 2:
			rate = 0.89;
			break;
		case 3:
			rate = 0.78;
			break;
		case 4:
			rate = 66.53;
			break;
		case 5:
			rate = 1.31;
			break;
		case 6:
			rate = 1.31;
			break;
		case 7:
			rate = 1.37;
			break;
		case 8:
			rate = 0.97;
			break;
		case 9:
			rate = 4.12;
			break;
		case 10:
			rate = 101.64;
			break;
		case 11:
			rate = 6.67;
			break;
		default:
			break;

		}

		double newAmt = 0;
		if (isConvertToUSD == true) {
			newAmt = amount / rate;
		}

		else {
			newAmt = amount * rate;
		}
		return newAmt;
	}

	private static String logTransaction(double amount, int currencyType, boolean isDeposit) {

		// Prints out appropriate string according to currency type
		String output = null;
		switch (currencyType) {
		case 1:
			output = "U.S Dollars";
			break;
		case 2:
			output = "Euros";
			break;
		case 3:
			output = "British Pounds";
			break;
		case 4:
			output = "Indian Rupees";
			break;
		case 5:
			output = "Austrailian Dollars";
			break;
		case 6:
			output = "Canadian Dollars";
			break;
		case 7:
			output = "Singapore Dollars";
			break;
		case 8:
			output = "Swiss Francs";
			break;
		case 9:
			output = "Malaysian Ringgits";
			break;
		case 10:
			output = "Japanese Yen";
			break;
		case 11:
			output = "Chinese Yuan Renminbi";
			break;
		default:
			break;

		}

		// Prints out deposit or withdraw result string
		String result;
		if (isDeposit) {
			result = "You have successfully deposited " + amount + " " + output;
		}

		else {

			result = "You have successfully withdrew " + amount + " " + output;

		}
		return result;
	}

	private static int currencyMenuOptionSelector(Scanner input) {
		System.out.println("Please select the currency type: ");
		System.out.println("1 - U.S. Dollar - 1.00\n" + "2 - Euro - 0.89\n" + "3 - British Pound - 0.78\n"
				+ "4 - Indian Rupee - 66.53\n" + "5 - Austrailian Dollar - 1.31\n" + "6 - Canadian Dollar - 1.31\n"
				+ "7 - Singapore Dollar - 1.37\n" + "8 - Swiss Franc - 0.97\n" + "9 - Malaysian Ringgit - 4.12\n"
				+ "10 - Japanese Yen - 101.64\n" + "11 - Chinese Yuan Renminbi - 6.67\n");
		int currencyInput = input.nextInt();

		// Prompts user to reenter input if value is not between 1-11
		while (currencyInput <= 0 || currencyInput >= 12) {
			System.out.println("Input failed validation. Please try again.\n");
			System.out.println("1 - U.S. Dollar - 1.00\n" + "2 - Euro - 0.89\n" + "3 - British Pound - 0.78\n"
					+ "4 - Indian Rupee - 66.53\n" + "5 - Austrailian Dollar - 1.31\n" + "6 - Canadian Dollar - 1.31\n"
					+ "7 - Singapore Dollar - 1.37\n" + "8 - Swiss Franc - 0.97\n" + "9 - Malaysian Ringgit - 4.12\n"
					+ "10 - Japanese Yen - 101.64\n" + "11 - Chinese Yuan Renminbi - 6.67\n");
			currencyInput = input.nextInt();
		}

		return currencyInput;

	}


	private static int mainMenuOptionSelector(Scanner input) {
		System.out.println("Please select an option from the list below:");
		System.out.println("1.) Check the balance of your account");
		System.out.println("2.) Make a deposit");
		System.out.println("3.) Withdraw an amount in a specific currency");
		System.out.println("4.) End your session (and withdraw all remaining currency in U.S Dollars)");
		int menuInput = input.nextInt();

		// Prompts user to reenter input if value is not between 1-4
		while (menuInput <= 0 || menuInput >= 5) {
			System.out.println("Input failed validation. Please try again.\n");
			System.out.println("Please select an option from the list below:");
			System.out.println("1.) Check the balance of your account");
			System.out.println("2.) Make a deposit");
			System.out.println("3.) Withdraw an amount in a specific currency");
			System.out.println("4.) End your session (and withdraw all remaining currency in U.S Dollars)");
			menuInput = input.nextInt();
		}

		return menuInput;
	}


	private static void printConversionTable() {
		System.out.println("Current rates are as follows: \n");
		System.out.println("1 - U.S. Dollar - 1.00\n" + "2 - Euro - 0.89\n" + "3 - British Pound - 0.78\n"
				+ "4 - Indian Rupee - 66.53\n" + "5 - Austrailian Dollar - 1.31\n" + "6 - Canadian Dollar - 1.31\n"
				+ "7 - Singapore Dollar - 1.37\n" + "8 - Swiss Franc - 0.97\n" + "9 - Malaysian Ringgit - 4.12\n"
				+ "10 - Japanese Yen - 101.64\n" + "11 - Chinese Yuan Renminbi - 6.67\n");
	}
}
