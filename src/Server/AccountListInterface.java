package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import DatabaseServer.BranchOffice;

import java.io.Serializable;

public interface AccountListInterface extends Remote{
	
	public void getAccount(int accountNumber, BranchOffice who) throws RemoteException;
	public void addAccount(int accountNumber, double amount) throws RemoteException;
	public void check() throws RemoteException;
	public void updateAccount(double amount) throws RemoteException;
	
}
