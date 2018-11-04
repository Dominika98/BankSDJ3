package Server;

import java.io.Serializable;

public class Account implements Serializable {
	
	private double money;
	
	public Account(double money) {
		this.money=money;
	}
	public double returnAmount() {
		return money;
	}
	
	public void setAmount(double amount)
	{
		money = amount;
	}
	
	public void withdrawMoney(double amount) {
		money-=amount;
	}
	
	public void insertMoney(double amount) {
		money+=amount;
	}
	
	public String toString()
	{
		return "Amount: " + returnAmount();
	}
	
	
}
