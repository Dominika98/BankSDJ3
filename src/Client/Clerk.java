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

public class Clerk implements ClientInterface, Serializable, RemoteObserver<Integer> {
	
	private int clientAccountNo;
	private BranchOffice bank;

	public Clerk() throws RemoteException
	{
		try {
			bank = (BranchOffice) Naming.lookup("rmi://localhost:1099/bankServer");
			System.out.println("Connected to branch office.");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bank.addObserver(this);
	}
	
	public void setAccountNo(int no)
	{
		clientAccountNo = no;
	}
	
	public void withdrawMoney(double amount) throws RemoteException
	{
		bank.withdrawMoney(amount, clientAccountNo, this);
	}
	
	public void insertMoney(double amount) throws RemoteException
	{
		bank.insertMoney(amount, clientAccountNo, this);
	}
	
	public static void main(String[] args) throws RemoteException {
		Clerk c = new Clerk();
		Scanner keyboard = new Scanner(System.in);
		while(true)
		{
			System.out.println("Enter client's account number:");
			c.setAccountNo(keyboard.nextInt());
			System.out.println("Enter a choice:");
			System.out.println("Press 1 to insert money.");
			System.out.println("Press 2 to withdraw money.");
			System.out.println("Press 0 to handle a different client.");
			int choice = keyboard.nextInt();
			while(choice!=0)
			{
				
					switch(choice)
					{
						case 1:
							System.out.println("Enter amount:");
							double amountToInsert = keyboard.nextDouble();
							c.insertMoney(amountToInsert);
							break;
						case 2:
							System.out.println("Enter amount:");
							double amountToWithdraw = keyboard.nextDouble();
							c.withdrawMoney(amountToWithdraw);
							break;
						default:
							System.out.println("Wrong number, try again.");
							break;
					}
					System.out.println("Enter a choice:");
					System.out.println("Press 1 to insert money.");
					System.out.println("Press 2 to withdraw money.");
					System.out.println("Press 0 to handle a different client.");
					choice = keyboard.nextInt();
			}
		}
	}

	@Override
	public void displayResponce(String msg) throws RemoteException {
		System.out.println(msg);
		
	}

	public void update(RemoteSubject<Integer> subject, Integer updateMsg) throws RemoteException {
		if(updateMsg == clientAccountNo)
		{
			System.out.println("Hello there :D");
		}
		
	}
}