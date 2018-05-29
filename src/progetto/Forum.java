package progetto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface Forum extends Client {
	
	public Integer SReqConnection(String user, String host) throws RemoteException, NotBoundException;
	public void SReqDisconnection(String user) throws RemoteException;
	public Integer SRSTopic(String topic) throws RemoteException;
	public Integer SRUSTopic(String topic) throws RemoteException;
	public Integer SPublish(String msg, String topic) throws RemoteException;

	
	public void addToServerList(String servername, Forum stub) throws RemoteException;
	public boolean isOnline(String username) throws RemoteException;
	public void goOnline(String username, Client client) throws RemoteException;
	/*richiesta connessione di un client
	richiesta disconnessione di un client
	richiesta subscribe di un client su una topic
	richiesta unsubscribe di un client su una topic
	richiesta publish di un client su una topic con conseguente broadcast a tutti subscriber della topic
	creazione topic*/
	
}
