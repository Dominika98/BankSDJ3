package DatabaseServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Client.ClientInterface;
import Server.Account;
import utility.observer.AbstractRemoteSubject;

import java.io.Serializable;

public interface BranchOffice extends  Remote {
	public void responceClient(double amount) throws RemoteException;
	public boolean validate(double amount, int accountNo) throws RemoteException;
	public void withdrawMoney(double amount, int accountNo, ClientInterface c) throws RemoteException;
	public void createAccount(int number, double money) throws RemoteException;
	public void receiveAccount(Account a) throws RemoteException;
	public void insertMoney(double amount, int accountNo, ClientInterface c) throws RemoteException;
	public void addObserver(ClientInterface o) throws RemoteException;
	public void deleteObserver(ClientInterface o) throws RemoteException;
	public void notifyObserver(int num) throws RemoteException;
}
