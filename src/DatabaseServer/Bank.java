package DatabaseServer;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.w3c.dom.views.AbstractView;

import Client.ClientInterface;
import Server.Account;
import Server.AccountList;
import Server.AccountListInterface;
import utility.observer.AbstractRemoteSubject;
import utility.observer.RemoteSubject;

public class Bank extends BranchOffice implements Serializable, Remote, RemoteSubject<Integer> {
	
	private AccountListInterface accounts;
	private ClientInterface client;
	private Account currentAccount;
	private ClientInterface clientWithdrawingMoney;
	
	public Bank() throws RemoteException, MalformedURLException, NotBoundException
	{
		accounts=(AccountListInterface) Naming.lookup("rmi://localhost:1099/database");
		System.out.println("Connected to the database.");
	}

	public boolean validate(double amount, int accountNo) throws RemoteException {
		accounts.getAccount(accountNo, this);
		System.out.println("poop "+currentAccount);
		if(currentAccount.returnAmount() >= amount)
			return true;
		else
			return false;
	}
	
	public void receiveAccount(Account a) throws RemoteException
	{
		currentAccount = a;
	}

	
	public void withdrawMoney(double amount, int accountNo, ClientInterface c) throws RemoteException 
	{
		clientWithdrawingMoney = c;
		if(validate(amount, accountNo))
		{
			currentAccount.withdrawMoney(amount);
			accounts.updateAccount(currentAccount.returnAmount());
			System.out.println(currentAccount.toString());
			accounts.check();
			responceClient(amount);
			notifyObservers(accountNo);
		}
		else
			responceClient(0);
			
	}
	
	public void insertMoney(double amount, int accountNo, ClientInterface c) throws RemoteException 
	{
		clientWithdrawingMoney = c;
		accounts.getAccount(accountNo, this);
		currentAccount.insertMoney(amount);
		accounts.updateAccount(currentAccount.returnAmount());
		System.out.println(currentAccount.toString());
		accounts.check();
		clientWithdrawingMoney.displayResponce("You have inserted "+amount+"dkk");
		notifyObservers(accountNo);
	}

	@Override
	public void responceClient(double amount) throws RemoteException
	{
		String msg;
		if(amount!=0)
			msg="You have withdrawn "+amount+"dkk.";
		else
			msg="You don't have enough money on account!";
		clientWithdrawingMoney.displayResponce(msg);
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		Bank client=new Bank();
		UnicastRemoteObject.exportObject(client, 1090);
		BranchOffice rmiServer=client;
		Naming.rebind("bankServer", rmiServer);
		System.out.println("Starting bank server...");
	}

	@Override
	public void createAccount(int number, double money) throws RemoteException 
	{
		accounts.addAccount(number, money);
		
	}
	
}
