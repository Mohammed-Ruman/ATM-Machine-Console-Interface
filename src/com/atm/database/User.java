package com.atm.database;

import java.util.List;

public class User {
	private String name;
	private String acc_num;
	private String pin_num;
	private String balance;
	private int atm_card_num;
	private List<Transaction> transactionStatements;
	private Boolean isAccountLocked;
	
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public List<Transaction> getTransactionStatements() {
		return transactionStatements;
	}

	public void setTransactionStatements(List<Transaction> transactionStatements) {
		this.transactionStatements = transactionStatements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(String acc_num) {
		this.acc_num = acc_num;
	}

	public String getPin_num() {
		return pin_num;
	}

	public void setPin_num(String pin_num) {
		this.pin_num = pin_num;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getAtm_card_num() {
		return atm_card_num;
	}

	public void setAtm_card_num(int atm_card_num) {
		this.atm_card_num = atm_card_num;
	}

}
