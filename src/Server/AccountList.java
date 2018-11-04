package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import DatabaseServer.BranchOffice;

import java.io.Serializable;

public class AccountList extends UnicastRemoteObject implements AccountListInterface, Serializable {

	private static HashMap<Integer, Account> accounts;
	private int currentAccountNumber;
	
	public AccountList() throws RemoteException
	{
		accounts = new HashMap<Integer, Account>();
	}
	
	public void getAccount(int accountNumber, BranchOffice who) throws RemoteException{
		System.out.println("Account " + accountNumber + " is being looked for...");
		currentAccountNumber = accountNumber;
		who.receiveAccount(accounts.get((Integer)accountNumber));
		System.out.println(accounts.get((Integer)accountNumber));
	}
	public void addAccount(int accountNumber, double amount) throws RemoteException 
	{
		accounts.put(accountNumber, new Account(amount));
		System.out.println("Account " + accountNumber + " created in the database.");
	}
	
	public void check() throws RemoteException
	{
		System.out.println(accounts.get(currentAccountNumber).toString());
	}
	
	public void updateAccount(double amount) throws RemoteException
	{
		accounts.get(currentAccountNumber).setAmount(amount);
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Registry reg= LocateRegistry.createRegistry(1099);
		AccountListInterface rmiServer=new AccountList();
		Naming.rebind("database", rmiServer);
		System.out.println("Starting database server...");
	}
	

}
