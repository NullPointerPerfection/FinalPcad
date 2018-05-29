package progetto;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class PCADBroker implements Forum {

	private final static int portR = 1200;
	private final static int portS = 2400;
	private final static int portC = 3600;
	private String servername;
	private HashMap<String, Forum> ListaServer; //Servername, Server
	private HashMap<String, Client> UtentiOnline; //Username, Client
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		new PCADBroker("Roba", "localhost");
	}
	
	public PCADBroker(String servername, String hostR) throws RemoteException, NotBoundException {
		System.out.println("inizio costuttore server roba");
		this.servername = servername;
		Forum stub = (Forum) UnicastRemoteObject.exportObject(this, 0);
		Registry r = null;
		try {
			r = LocateRegistry.createRegistry(portS);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portS);
		}
		r.rebind(this.servername, stub);
		System.out.println("si è creato il server, ora si collega al root");
		Registry registry = LocateRegistry.getRegistry(hostR, portR); //trova il registro
		Rooter rooter = (Rooter) registry.lookup("Rooter");
		while(rooter.checkSName(this.servername)) {
			System.out.println("Nome server già presente! Inserisci nuovo nome: ");
			Scanner rd = new Scanner(System.in);
			this.servername = rd.next();
		}
		ListaServer = rooter.updateServerList(this.servername, stub);
		System.out.println("lista server = lista del rooter");
		Object[] KeySet = ListaServer.keySet().toArray();
		Forum got;
		System.out.println("prima del for");
		for(int i = 0; i < KeySet.length; i++) {
			got = ListaServer.get(KeySet[i]);
			got.addToServerList(this.servername, stub);
		}
		System.out.println("dopo il for");
	}
	
	public void addToServerList(String servername, Forum stub) {
		ListaServer.put(servername, stub);
	}
	
	
	@Override
	public Integer SReqConnection(String user, String host) throws RemoteException, NotBoundException {
		//if(MyChildren.containsKey(user)) return 1;
		
		//MyChildren.put(user, client);
		return 0;
	}

	@Override
	public void SReqDisconnection(String user) throws RemoteException {
		//il client passerà al server la sua lista di topic
		//il server controollerà dove risiedono i topic
		//in caso di apprtenenza interna si risolve facilemnte
		//se è esterno si chiamerà il metodo di disiscrizione da parte del server sul topic di un altro server
		/*Object[] KeySet = Connections.keySet().toArray();
		List<String> got;
		for(int i = 0; i < KeySet.length; i++) {
			got = Connections.get(KeySet[i]);
			got.remove(user);
			Connections.put((String)KeySet[i], got);
		}
		MyChildren.remove(user);*/
	}

	@Override
	public Integer SRSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SRUSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SPublish(String msg, String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ReqConnection(String user, String host) throws RemoteException {
		
	}

	@Override
	public void ReqDisconnection(String user) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RUSTopic(String topic) throws RemoteException {
		
	}

	@Override
	public void Publish(String msg, String topic) throws RemoteException {

	}

	@Override
	public void clientPrint(String msg) throws RemoteException  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOnline(String username) throws RemoteException {
		return UtentiOnline.containsKey(username);
	}

	@Override
	public void goOnline(String username, Client client) throws RemoteException {
		UtentiOnline.put(username, client);
	}

}
