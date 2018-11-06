package Client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import DatabaseServer.BranchOffice;
import utility.observer.RemoteObserver;
import utility.observer.RemoteSubject;

public class Client extends UnicastRemoteObject implements ClientInterface, Serializable {
	private int accountNo;
	private BranchOffice bank;

	public Client(int no) throws RemoteException {
		accountNo = no;
		try {
			bank = (BranchOffice) Naming.lookup("rmi://localhost:1099/bankServer");
			System.out.println("Connected to branch office.");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void withdrawMoney(double amount) throws RemoteException {
		bank.withdrawMoney(amount, accountNo, this);
	}
	
	public void addMe() throws RemoteException
	{
		bank.addObserver((ClientInterface)this);
	}

	public void displayResponce(String msg) throws RemoteException{
		System.out.println(msg);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter your account number");
		int number = keyboard.nextInt();
		Client client = new Client(number);
		client.addMe();
		while(true)
		{
			System.out.println("Do you want to withdraw money? Insert y or n");
			if(keyboard.nextLine().equals("y"))
			{
				System.out.println("How much money do you want to withdraw?:");
				double amount = keyboard.nextDouble();
				client.withdrawMoney(amount);
			}
		}
	}

	@Override
	public void update(String message, int num) throws RemoteException {
		System.out.println(num);
		if(num == accountNo)
		{
			System.out.println(message);
		}
		
	}

}
