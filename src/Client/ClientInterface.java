package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface ClientInterface extends Remote{
	public void displayResponce(String msg) throws RemoteException;
	public void update(String message, int num) throws RemoteException;
}
