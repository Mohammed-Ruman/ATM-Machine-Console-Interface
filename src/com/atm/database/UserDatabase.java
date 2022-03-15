package com.atm.database;

public class UserDatabase {

	public User ghazan_database() {
		User user1 = new User();
		user1.setName("Ghazanfer");
		user1.setAcc_num("1234567890");
		user1.setBalance("0");
		user1.setPin_num("1234");
		user1.setAtm_card_num(12);
		user1.setIsAccountLocked(false);
		return user1;
	}

	public User shareef_database() {
		User user2 = new User();
		user2.setName("Shareef");
		user2.setAcc_num("0987654321");
		user2.setBalance("0");
		user2.setPin_num("4321");
		user2.setAtm_card_num(7);
		user2.setIsAccountLocked(false);		
		return user2;
	}
	
	public User faizan_database() {
		User user3 = new User();
		user3.setName("Faizan");
		user3.setAcc_num("1111222233");
		user3.setBalance("0");
		user3.setPin_num("2345");
		user3.setAtm_card_num(9);
		user3.setIsAccountLocked(false);
		return user3;
	}
}
