package progetto;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
	
	void ReqConnection() throws RemoteException, UnknownHostException, NotBoundException;
	void ReqDisconnection() throws RemoteException;
	void RSTopic(String topic) throws RemoteException;
	void RUSTopic(String topic) throws RemoteException;
	void Publish(String msg, String topic) throws RemoteException;
	void clientPrint(String msg, String topic, String user) throws RemoteException;
}
