package progetto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Pub_Sub implements Client {
	
	private final static int portR = 1200;
	private final static int portS = 2400;
	private final static int portC = 3600;
	private Forum server;
	private String username;

	public Pub_Sub(String user, String hostR) throws RemoteException, NotBoundException {
		this.username = user;
		Client stub = (Forum) UnicastRemoteObject.exportObject(this, 0);
		Registry r = null;
		try {
			r = LocateRegistry.createRegistry(portC);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portC);
		}
		r.rebind(this.username, stub);
		System.out.println("si è creato il client, ora si collega al root");
		Registry registry = LocateRegistry.getRegistry(hostR, portR); //trova il registro
		Rooter rooter = (Rooter) registry.lookup("Rooter");
		
		
		if(rooter.checkUName(this.username) && rooter.isOnline(this.username)) {
			do{
				System.out.println("L'utente " + this.username + " è già online, impossibile collegarsi di nuovo. Inserisci nuovo nome: ");
				Scanner rd = new Scanner(System.in);
				this.username = rd.next();
			}while(rooter.checkUName(this.username) && rooter.isOnline(this.username));
		}
		if(rooter.checkUName(this.username) && !(rooter.isOnline(this.username))) {
			this.server = rooter.getClientServer(this.username);
			this.server.goOnline(this.username, stub);
			System.out.println("Connessione di " + this.username + " al server riuscita.");
		} else {
			System.out.println("Benvenuto " + this.username + ", a quale dei seguenti server vuoi iscriverti?");
			Set<String> serverNames = rooter.getServerList();
			System.out.println(serverNames);
			String nomeserver;
			do {
				Scanner rd = new Scanner(System.in);
				nomeserver = rd.next();
				if(serverNames.contains(nomeserver)) rooter.updateClientInfo(this.username, nomeserver);
				else System.out.println("Non esise il server " + nomeserver + ", riprova.");
			}while(!(serverNames.contains(nomeserver)));
			this.server = rooter.getClientServer(this.username);
			this.server.goOnline(this.username, stub);
			System.out.println("Iscrizione di " + this.username + " a " + nomeserver + " riuscita.");
		}
	}

	@Override
	public void ReqConnection(String user, String host) throws RemoteException, UnknownHostException, NotBoundException {
		int response = 1;
		try {
			while (response == 1) {
				response = server.SReqConnection(username, InetAddress.getLocalHost().getHostAddress());
				switch (response) {
					case (0):
						System.out.println("Connection between " + username + " and " + server + ": DONE.");
					case (1):
						System.err.println("Connection between " + username + " and " + server + ": FAILED.");
						System.err.println("Username " + '"' + username + '"' + " already exists. Try again: ");
						Scanner rd = new Scanner(System.in);
						username = rd.next();
					default:
						System.err.println("--UNEXPECTED ERROR IN REQCONNECTION--");
				}
			}
		} catch (RemoteException e) {
			System.err.println("Connection between " + username + " and " + server + ": FAILED WITH EXCEPTION.");
			e.printStackTrace();
		}
	}

	@Override
	public void ReqDisconnection(String user) throws RemoteException {
		this.server.SReqDisconnection(user);
		System.exit(0);
	}

	@Override
	public void RSTopic(String topic) throws RemoteException {
		int a = server.SRSTopic(topic);
		if (a == 0) { 
			topiclist.add(topic);
			System.out.println("Iscrizione di " + username + " al topic " + topic);
			return;
		} else if (a == 1) {
			System.err.println("Iscrizione di " + username + " al topic " + topic + "non riuscita: già esistente.");
			return;
		}
		System.err.println("--UNEXPECTED ERROR IN RSTOPIC--");
	}

	@Override
	public void RUSTopic(String topic) throws RemoteException {
		int a = server.SRUSTopic(topic);
		if (a == 0) {
			topiclist.remove(topic);
			System.out.println("Disiscrizione di " + username + " al topic " + topic);
			return;
		} else if (a == 1) {
			System.err.println("Disiscrizione di " + username + " al topic " + topic + "non riuscita: non era iscritto.");
			return;
		}
		System.err.println("--UNEXPECTED ERROR IN RUSTOPIC--");
	}

	@Override
	public void Publish(String msg, String topic) throws RemoteException {
		int a = server.SPublish(msg, topic);
		if (a == 0) return;
		else if (a == 1) {
			System.err.println(username + ", non puoi pubblicare sul topic " + topic + "non essendo iscritto.");
			return;
		}
		System.err.println("--UNEXPECTED ERROR IN PUBLISH--");
	}
	
	public void clientPrint(String msg) throws RemoteException {
		System.out.println(msg);
	}
	
}
