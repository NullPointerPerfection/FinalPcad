package progetto;

import java.rmi.RemoteException;

public interface Forum extends Client {
	
	public Integer SReqConnection(String user, String server) throws RemoteException;
	public Integer SReqDisconnection(String user, Forum server) throws RemoteException;
	public Integer SRSTopic(String topic) throws RemoteException;
	public Integer SRUSTopic(String topic) throws RemoteException;
	public boolean SPublish(String msg, String topic) throws RemoteException;

	/*richiesta connessione di un client
	richiesta disconnessione di un client
	richiesta subscribe di un client su una topic
	richiesta unsubscribe di un client su una topic
	richiesta publish di un client su una topic con conseguente broadcast a tutti subscriber della topic
	creazione topic*/
	
}
