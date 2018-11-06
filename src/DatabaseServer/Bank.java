package DatabaseServer;

import java.awt.List;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.w3c.dom.views.AbstractView;

import Client.ClientInterface;
import Server.Account;
import Server.AccountList;
import Server.AccountListInterface;
import utility.observer.AbstractRemoteSubject;
import utility.observer.RemoteObserver;
import utility.observer.RemoteSubject;

public class Bank implements BranchOffice, Serializable, Remote {
	
	private AccountListInterface accounts;
	private ClientInterface client;
	private Account currentAccount;
	private ClientInterface clientWithdrawingMoney;
	private ArrayList<ClientInterface> observers;
	
	public Bank() throws RemoteException, MalformedURLException, NotBoundException
	{
		accounts=(AccountListInterface) Naming.lookup("rmi://localhost:1099/database");
		System.out.println("Connected to the database.");
		observers= new ArrayList<ClientInterface>();
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
			notifyObserver(accountNo);
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
		notifyObserver(accountNo);
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

	public void addObserver(ClientInterface o) throws RemoteException {
		observers.add(o);
		System.out.println("observer added");
	}

	public void deleteObserver(ClientInterface o) throws RemoteException {
		observers.remove(o);
		
	}

	public void notifyObserver(int num) throws RemoteException
	{
		for(ClientInterface o:observers)
		{
			o.update("Current amount on account number "+num+": "+currentAccount.returnAmount()+"dkk", num);
			System.out.println("Observer with num " + num +" notified");
		}
	}
}
