package progetto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public interface Forum extends Client {
	
	Integer SReqConnection(String user, String host, int portC) throws RemoteException, NotBoundException;
	void SReqDisconnection(String user) throws RemoteException;
	Integer SRSTopic(String user, String topic) throws RemoteException;
	Integer SRUSTopic(String user, String topic) throws RemoteException;
	Integer SPublish(String msg, String topic, String user, String mittente) throws RemoteException;
	List<String> listaserveramico() throws RemoteException;

	void Publish(String msg, String topic, String user, String mittente) throws RemoteException;


		String myname() throws RemoteException;
}
