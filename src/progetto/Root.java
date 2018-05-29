package progetto;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Set;

public class Root implements Rooter{
	
	private final static int portR = 1200;
	private final static int portS = 2400;
	private final static int portC = 3600;
	
	private HashMap<String, String> ClientInfo; //Username, Servername
	private HashMap<String, Forum> ServerInfo;	//Servername, Server
	
	public Root() throws RemoteException {
		System.out.println("inizio costruttore root");
		ClientInfo = new HashMap<>();
		ServerInfo = new HashMap<>();
		Rooter stub = (Rooter) UnicastRemoteObject.exportObject(this, 0);
		Registry r = null;
		try {
			r = LocateRegistry.createRegistry(portR);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portR);
		}
		r.rebind("Rooter", stub);
		System.out.println("creato il rooter");
	}

	@Override
	public HashMap<String, Forum> updateServerList(String servername, Forum stub)throws RemoteException {
		System.out.println("un server sta richiedendo la lista");
		HashMap<String, Forum> nuova = ServerInfo;
		ServerInfo.put(servername, stub);
		System.out.println("la lista è: ");
		System.out.println(ServerInfo.keySet());
		return nuova;
	}

	@Override
	public boolean checkSName(String servername) throws RemoteException {
		return ServerInfo.containsKey(servername);
	}
	
	public boolean checkUName(String username) throws RemoteException {
		return ClientInfo.containsKey(username);
	}

	public static void main(String[] args) throws RemoteException {
		new Root();
	}

	@Override
	public void updateClientInfo(String username, String servername) throws RemoteException {
		ClientInfo.put(username, servername);
	}

	@Override
	public boolean isOnline(String username) throws RemoteException {
		Forum server = getClientServer(username);
		return server.isOnline(username);
	}

	@Override
	public Forum getClientServer(String username) throws RemoteException {
		return ServerInfo.get(ClientInfo.get(username));
	}

	@Override
	public Set<String> getServerList() throws RemoteException {
		return ServerInfo.keySet();
	}
	
	
}
