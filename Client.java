package progetto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
	
	public Integer ReqConnection(String user, String server) throws RemoteException;
	public Integer ReqDisconnection(String user, Forum server) throws RemoteException;
	public Integer RSTopic(String topic) throws RemoteException;
	public Integer RUSTopic(String topic) throws RemoteException;
	public boolean Publish(String msg, String topic) throws RemoteException;
	/*
	invio richiesta di connessione ad un broker
	invio richiesta di disconnessione
	invio richiesta di subscribe su una topic
	richiesta unsubscribe da una topic
	invio richiesta di publish di un messaggio su una topic
	*/
}
