package progetto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;

public interface Rooter extends Remote{

	public HashMap<String, Forum> updateServerList(String servername, Forum stub) throws RemoteException;
	public boolean checkSName (String servername) throws RemoteException;
	public boolean checkUName (String username) throws RemoteException;
	public void updateClientInfo(String username, String servername) throws RemoteException;
	public boolean isOnline(String username) throws RemoteException;
	public Forum getClientServer(String username) throws RemoteException;
	public Set<String> getServerList() throws RemoteException;
}
