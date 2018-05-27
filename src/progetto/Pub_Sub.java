package progetto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Pub_Sub implements Client {
	private final int portS = 1120;
	private final int portC = 1230;
	Forum server;
	String username;
	List<String> topic;

	public Pub_Sub(String user, String server, String host) {
		topic = new ArrayList<>();
		username = user;
		int response = -1;
		try {
			while (response != 0) {
				response = ReqConnection(username, server, host);
				switch (response) {
					case (0):
						System.out.println("Connection between " + username + " and " + server + ": DONE.");
						Registry registry = LocateRegistry.getRegistry(host, portS);
						this.server = (Forum) registry.lookup(server);
					case (1):
						System.out.println("Connection between " + username + " and " + server + ": FAILED.");
						System.out.println("Username " + '"' + username + '"' + " already exists. Try again: ");
						Scanner rd = new Scanner(System.in);
						username = rd.next();
					default:
						System.out.println("Unknown??");
				}
			}
			//creare oggetto remoto client, creare il registro
		} catch (RemoteException e) {
			System.out.println("Connection between " + username + " and " + server + ": FAILED WITH EXCEPTION.");
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer ReqConnection(String user, String server, String host) throws RemoteException {

		return null;
	}

	@Override
	public Integer ReqDisconnection(String user, Forum server) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer RSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer RUSTopic(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Publish(String msg, String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


}
