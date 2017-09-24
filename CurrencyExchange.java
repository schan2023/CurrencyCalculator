
//Name: Lam-Pui Chan
//UFID: lamschan996
//Section: 5993
//Instructor: Kyla McMullen
//Date: October 27th, 2016
//Project 3
//Currency Exchange

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

	/****************************************************************
	 * Do not modify anything above this line *
	 *****************************************************************/

	/**
	 * main method, put your business logic and console input here
	 */
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

	/**
	 * deposit the amount of a specific currency to the account
	 *
	 * @param amount
	 *            the amount to be deposited.
	 * @param currencyType
	 *            the currency type will be the same as the type number used in
	 *            the convertCurrency(double, int, boolean) method. An Invalid
	 *            type will result in a deposit failure.
	 * @return if deposit succeed, will return true. If failed, return false
	 */
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

	/**
	 * withdraw the value amount with a specific currency from the account. The
	 * withdraw amount should never exceed the current account balance, or the
	 * withdrawal will fail. If the currency is other than USD, a 0.5%
	 * convenience fee will be charged
	 *
	 * @param amount
	 *            the amount to be withdrawn.
	 * @param currencyType
	 *            the currency type will be the same as the type number used in
	 *            the convertCurrency(double, int, boolean) method. An invalid
	 *            type will result a withdraw failure.
	 * @return if withdraw succeed, will return true. If failed, return false
	 */
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

	/**
	 * Convert the value amount between U.S. dollars and a specific currency.
	 *
	 * @param amount
	 *            The amount of the currency to be converted.
	 * @param currencyType
	 *            The integer currencyType works as follows: 1 for usd (U.S.
	 *            Dollars) 2 for eur (Euros) 3 for bri (British Pounds) 4 for
	 *            inr (Indian Rupees) 5 for aus (Australian Dollars) 6 for cnd
	 *            (Canadian Dollars) 7 for sid (Singapore Dollars) 8 for swf
	 *            (Swiss Francs) 9 for mar (Malaysian Ringgits) 10 for jpy
	 *            (Japanese Yen) 11 for cyr (Chinese Yuan Renminbi)
	 * @param isConvertToUSD
	 *            Tells the direction of the conversion. If the value is true,
	 *            then the conversion is from a foreign currency to USD. If the
	 *            value is false, then the conversion is from USD to the foreign
	 *            currency
	 * @return the converted amount
	 */
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

	/**
	 * Displays message at the end of the withdraw, deposit, and endTransaction
	 * stating how much the user just withdrew/deposited and what type (this
	 * will be used in both features B, C and D of the main menu).
	 *
	 * @param amount
	 *            the amount of currency withdrew/deposited
	 * @param currencyType
	 *            the currency type will be the same as the type number used in
	 *            {@link #convertCurrency(double, int, boolean)}
	 * @param isDeposit
	 *            if true log the deposit transaction, false log the withdraw
	 *            transaction
	 * @return Return the formatted log message as following examples: You
	 *         successfully withdrew 10.0 U.S. Dollars You successfully
	 *         deposited 30.0 Japanese Yen
	 *         <p>
	 *         The invalid input like invalid currencyType or negative amount,
	 *         will return a “Logging Error”
	 */
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

	/**
	 * Prints the currency menu (see output examples), allows the user to make a
	 * selection from available currencies
	 *
	 * @param input
	 *            the Scanner object you created at the beginning of the main
	 *            method. Any value other than the 11 valid valid currency types
	 *            should generate an invalid value prompt. Print the list again
	 *            and prompt user to select a valid value from the list. the
	 *            currency type will be the same as the type number used in
	 *            {@link #convertCurrency(double, int, boolean)}
	 * @return an integer from 1-11 inclusive representing the user’s selection.
	 */
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

	/**
	 * Prints the main menu (see output examples), allows the user to make a
	 * selection from available operations
	 *
	 * @param input
	 *            the Scanner object you created at the beginning of the main
	 *            method. Any value other than the 4 valid selections should
	 *            generate an invalid value prompt. Print the list again and
	 *            prompt user to select a valid value from the list.
	 * @return an integer from 1-4 inclusive representing the user’s selection.
	 */
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

	/**
	 * Prints the conversion table at the start of the program (see the output
	 * examples).
	 */
	private static void printConversionTable() {
		System.out.println("Current rates are as follows: \n");
		System.out.println("1 - U.S. Dollar - 1.00\n" + "2 - Euro - 0.89\n" + "3 - British Pound - 0.78\n"
				+ "4 - Indian Rupee - 66.53\n" + "5 - Austrailian Dollar - 1.31\n" + "6 - Canadian Dollar - 1.31\n"
				+ "7 - Singapore Dollar - 1.37\n" + "8 - Swiss Franc - 0.97\n" + "9 - Malaysian Ringgit - 4.12\n"
				+ "10 - Japanese Yen - 101.64\n" + "11 - Chinese Yuan Renminbi - 6.67\n");
	}
}