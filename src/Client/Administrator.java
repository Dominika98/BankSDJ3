package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import javax.security.auth.kerberos.KerberosKey;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import DatabaseServer.BranchOffice;

public class Administrator extends UnicastRemoteObject implements ClientInterface{

	private BranchOffice bank;
	private static int accountNo=0;
	private Scanner keyboard = new Scanner(System.in);
	
	public Administrator() throws RemoteException {
		try {
			bank = (BranchOffice) Naming.lookup("rmi://localhost:1099/bankServer");
			System.out.println("Connected to branch office.");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createAccount() throws RemoteException
	{
		System.out.println("Your account number is: "+accountNo+"\nHow much money do you want to insert?");
		bank.createAccount(accountNo, keyboard.nextDouble());
		accountNo++;
	}

	public void displayResponce(String msg) throws RemoteException {
		System.out.println(msg);
	}
	
	public static void main(String[] args) throws RemoteException {
		Scanner keyboard = new Scanner(System.in);
		Administrator adm=new Administrator();
		while(true)
		{
			System.out.println("Press 1 to create a new account.");
			if(keyboard.nextDouble()==1)
				adm.createAccount();
		}
	}

	@Override
	public void update(String message, int num) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
