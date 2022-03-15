package com.atm.database;

import java.util.Date;

public class Transaction {

	private Date date;

	private String amount;
	
	private String currentBalance;
	
	private String description;
	
	private Boolean debit;
	
	private Boolean credit;
	
	public Boolean getDebit() {
		return debit;
	}

	public void setDebit(Boolean debit) {
		this.debit = debit;
	}

	public Boolean getCredit() {
		return credit;
	}

	public void setCredit(Boolean credit) {
		this.credit = credit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
