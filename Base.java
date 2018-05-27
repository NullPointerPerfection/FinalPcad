package progetto;

import java.rmi.RemoteException;

public interface Base extends Remote {

	public Integer ReqConnection(String user, String server) throws RemoteException;
}
