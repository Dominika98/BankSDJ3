package DatabaseServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Client.ClientInterface;
import Server.Account;
import utility.observer.AbstractRemoteSubject;

import java.io.Serializable;

public abstract class BranchOffice extends AbstractRemoteSubject<Integer> implements Remote {
	public abstract boolean validate(double amount, int accountNo) throws RemoteException;
	public abstract void withdrawMoney(double amount, int accountNo, ClientInterface c) throws RemoteException;
	public abstract void responceClient(double amount) throws RemoteException;
	public abstract void createAccount(int number, double money) throws RemoteException;
	public abstract void receiveAccount(Account a) throws RemoteException;
	public abstract void insertMoney(double amount, int accountNo, ClientInterface c) throws RemoteException;
}
