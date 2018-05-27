package progetto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	Integer ReqConnection(String user, String server, String host) throws RemoteException;
	Integer ReqDisconnection(String user, Forum server) throws RemoteException;
	Integer RSTopic(String topic) throws RemoteException;
	Integer RUSTopic(String topic) throws RemoteException;
	boolean Publish(String msg, String topic) throws RemoteException;
	/*
	invio richiesta di connessione ad un broker
	invio richiesta di disconnessione
	invio richiesta di subscribe su una topic
	richiesta unsubscribe da una topic
	invio richiesta di publish di un messaggio su una topic
	*/
}
