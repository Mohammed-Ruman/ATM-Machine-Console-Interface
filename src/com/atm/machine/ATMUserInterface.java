package com.atm.machine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.atm.database.Transaction;
import com.atm.database.User;
import com.atm.database.UserDatabase;

public class ATMUserInterface {

	static Scanner scanner = new Scanner(System.in);

	static UserDatabase database = new UserDatabase();
	static User user1_data = database.ghazan_database();
	static User user2_data = database.suhan_database();
	static User user3_data = database.faizan_database();
	
	static List<Transaction> user1_statements = new ArrayList<Transaction>();//[]
	static List<Transaction> user2_statements = new ArrayList<Transaction>();//[]
	static List<Transaction> user3_statements = new ArrayList<Transaction>();//[]
	
	static char ch[] = new char[30];
	static String desc = new String(ch);
	static int count = 0;
	public static void main(String[] args) throws InterruptedException {
	
		while (true) {
			System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
			System.out.println("Please Enter Card Number");
			int card_num = scanner.nextInt();

			Thread.sleep(1000);// to wait

			if (card_num == user1_data.getAtm_card_num()) {
				banking(user1_data);
			} else if (card_num == user2_data.getAtm_card_num()) {
				banking(user2_data);
			} else if (card_num == user3_data.getAtm_card_num()) {
				banking(user3_data);
			} else {
				System.err.println("Please enter a valid card number!");
				main(args);
			}
		}
	}

	private static void banking(User user_data) throws InterruptedException {

		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		System.out.println("Welcome " + user_data.getName());

		System.out.println("Kindly enter 4 digit pin");
		String pin = scanner.next();

		Thread.sleep(1000);

		if(!user_data.getIsAccountLocked()) {
			if (pin.equalsIgnoreCase(user_data.getPin_num())) {
				transactionOptions(user_data);
			} else {
				System.err.println("Enter valid pin!");
				count++;
				if(count==3) {
					System.err.println("Account Locked!!! Please contact Admin to unlock");
					user_data.setIsAccountLocked(true);
					count=0;
				}
			}
		}else{
			System.err.println("Account Locked!!! Please contact shareef to unlock");
		}

	}

	private static void transactionOptions(User user_data) throws InterruptedException {

		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		System.out.println("Select Transactions");
		System.out.println("1.Deposit 	     \t\t\t\t 2.Transfer");
		System.out.println("3.Withdraw 	   	 \t\t\t 4.Balance enquiry");
		System.out.println("5.Mini Statement \t\t\t\t 6.Exit transaction");
		int choice = scanner.nextInt();

		User user = user_data;
		
		switch (choice) {
		case 1:
			user = deposit(user);
			balanceEnquiry(user);
			thankYou(user);
			break;
		case 2:
			transfer(user);
			balanceEnquiry(user);
			thankYou(user);
			break;
		case 3:
			user = withdraw(user);
			balanceEnquiry(user);
			thankYou(user);
			break;
		case 4:
			balanceEnquiry(user_data);
			thankYou(user_data);
			break;
		case 5:
			generateMiniStatement(user_data);
			thankYou(user_data);
			break;
		case 6:
			System.out.println("Thank you for banking with us...");
			break;
		default:
			System.err.println("Unexpected value: " + choice+"\nPlease choose a valid option!");
			break;
		}

	}

	private static User withdraw(User user) throws InterruptedException {
		System.out.println("|++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		
		System.out.println("Enter Amount...");
		double amount = scanner.nextDouble();
		
		System.out.println("Withdraw is in progress...");
		
		double updatedAmount = Double.parseDouble(user.getBalance()) - amount;
		BigDecimal bigDecimal = new BigDecimal(updatedAmount);
		user.setBalance(bigDecimal.toString());
		
		Thread.sleep(1000);
		
		System.out.println("Withdraw Success!");
		
		Transaction transaction = new Transaction();
		desc = 	String.format("%-30s", "Withdraw");
		
		transaction.setDescription(desc);//Deposit          
		transaction.setCredit(false);
		transaction.setDebit(true);
		createStatement(user, amount, transaction);
		return user;	
	}

	private static void generateMiniStatement(User user_data) {
		desc = String.format("%-30s", "Description");
		System.out.println("|++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		System.out.println("Date\t\t\t\t"+desc+"Debit\t\t\tCredit\t\t\tAvailable Balance");

		if(user_data.getName().equalsIgnoreCase(user1_data.getName())) {
			generateUserStatement(user1_statements);
		}else if(user_data.getName().equalsIgnoreCase(user2_data.getName())) {
			generateUserStatement(user2_statements);
		}else if(user_data.getName().equalsIgnoreCase(user3_data.getName())) {
			generateUserStatement(user3_statements);
		}
		System.out.println("|++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
	}

	private static void generateUserStatement(List<Transaction> user_statements) {
		String debit = null;
		String credit = null;
		for (Transaction transaction : user_statements) {
			if(transaction.getCredit()) {
				debit = "-";
				credit = transaction.getAmount();
			}else if(transaction.getDebit()) {
				debit = transaction.getAmount();
				credit = "-";
			}
			System.out.println(transaction.getDate()+"\t"+transaction.getDescription()+debit+"\t\t\t"+credit+"\t\t\t"+transaction.getCurrentBalance());
		}		
	}

	private static void transfer(User user) {
		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		List<User> users = new ArrayList<User>();
		
		System.out.println("Please enter account number to be transferred...");
		String acc_num = scanner.next();
				
		if (acc_num.equalsIgnoreCase(user1_data.getAcc_num())) {
			users = transferToUser(user, user1_data);
			user1_data = users.get(1);
			updateUser(user, users);
		} else if (acc_num.equalsIgnoreCase(user2_data.getAcc_num())) {
			users = transferToUser(user, user2_data);
			user2_data = users.get(1);
			updateUser(user, users);
		} else if (acc_num.equalsIgnoreCase(user3_data.getAcc_num())) {
			users = transferToUser(user, user3_data);
			user3_data = users.get(1);
			updateUser(user, users);
		} else {
			System.err.println("Enter a valid account number!");
		}
	}

	private static void updateUser(User user, List<User> users) {
		if(user.getName().equalsIgnoreCase(user1_data.getName())) {
			user1_data = users.get(0);
		}else if(user.getName().equalsIgnoreCase(user2_data.getName())){
			user2_data = users.get(0);
		}else if(user.getName().equalsIgnoreCase(user3_data.getName())) {
			user3_data = users.get(0);
		}
	}

	private static List<User> transferToUser(User fromUser, User toUser) {
		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		
		List<User> users = new ArrayList<User>();//[list1,list2,....]
		
		System.out.println("Enter amount to be transferred...");
		double amount = scanner.nextDouble();

		double fromUserBalance = Double.parseDouble(fromUser.getBalance()) - amount;
		double toUserBalance = Double.parseDouble(toUser.getBalance()) + amount;

		BigDecimal fromBigDecimal = new BigDecimal(fromUserBalance);
		BigDecimal toBigDecimal = new BigDecimal(toUserBalance);

		User updateFromUser = fromUser;
		User updateToUser = toUser;

		if (fromUserBalance < 0) {
			System.err.println("Insufficient balance!");
			
			users.add(fromUser);
			users.add(toUser);
		} else {
			updateFromUser.setBalance(fromBigDecimal.toString());
			updateToUser.setBalance(toBigDecimal.toString());
			System.out.println("Amount transferred successfully!!!");
			
			Transaction transaction = new Transaction();
			desc = 	String.format("%-30s", "Transferred to "+toUser.getName());
			
			transaction.setDescription(desc);
			transaction.setDebit(true);
			transaction.setCredit(false);
			createStatement(updateFromUser, amount, transaction);//debit

			Transaction transaction2 = new Transaction();
			desc = 	String.format("%-30s", "Transferred from "+fromUser.getName());

			transaction2.setDescription(desc);
			transaction2.setDebit(false);
			transaction2.setCredit(true);
			createStatement(toUser, amount, transaction2);//credit
			
			users.add(updateFromUser);//0
			users.add(updateToUser);//1
		}
		return users;
	}

	private static void thankYou(User user_data) throws InterruptedException {
		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		
		System.out.println("1.Back to main menu\n2.Exit transaction");
		int choice = scanner.nextInt();
		if (choice == 1) {
			transactionOptions(user_data);
		} else if (choice == 2) {
			System.out.println("Thank you for banking with us...");
		} else {
			System.out.println("Enter valid option!!");
		}
	}

	private static void balanceEnquiry(User user_data) throws InterruptedException {

		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		Thread.sleep(1000);
		System.out.println("Total available balance is : " + user_data.getBalance());	

	}

	private static User deposit(User user_data) throws InterruptedException {

		System.out.println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
		Thread.sleep(1000);
		User user = user_data;
		System.out.println("Please enter amount to be deposited");
		double amount = scanner.nextDouble();
		
		System.out.println("Deposit is in progress...");
		
		double updatedAmount = Double.parseDouble(user.getBalance()) + amount;
		BigDecimal bigDecimal = new BigDecimal(updatedAmount);
		user.setBalance(bigDecimal.toString());
		
		Thread.sleep(1000);
		
		System.out.println("Amount deposited Successfully!");
		
		Transaction transaction = new Transaction();
		desc = 	String.format("%-30s", "Deposit");
		
		transaction.setDescription(desc);//Deposit          
		transaction.setCredit(true);
		transaction.setDebit(false);
		createStatement(user, amount, transaction);
		return user;		

	}

	private static void createStatement(User user_data, double amount, Transaction txn) {
		Transaction transaction = txn;
		BigDecimal bigDecimal = new BigDecimal(amount);

		transaction.setDate(new Date());
		transaction.setAmount(bigDecimal.toString());
		transaction.setCurrentBalance(user_data.getBalance());
		transaction.setUser(user_data);
		
		sendStatementToDatabase(transaction);
	}

	private static void sendStatementToDatabase(Transaction transaction) {
		if(transaction.getUser().getName().equalsIgnoreCase(user1_data.getName())) {
			user1_statements.add(transaction);
		}else if(transaction.getUser().getName().equalsIgnoreCase(user2_data.getName())){
			user2_statements.add(transaction);
		}else if(transaction.getUser().getName().equalsIgnoreCase(user3_data.getName())) {
			user3_statements.add(transaction);
		}
	}
}
