package progetto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
	
	public void ReqConnection() throws RemoteException, UnknownHostException, NotBoundException;
	public void ReqDisconnection() throws RemoteException;
	public void RSTopic(String topic) throws RemoteException;
	public void RUSTopic(String topic) throws RemoteException;
	public void Publish(String msg, String topic, String user, String mittente) throws RemoteException;
	public void clientPrint(String msg, String topic, String user) throws RemoteException;

	/*
	invio richiesta di connessione ad un broker
	invio richiesta di disconnessione
	invio richiesta di subscribe su una topic
	richiesta unsubscribe da una topic
	invio richiesta di publish di un messaggio su una topic
	*/
}
