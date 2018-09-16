package progetto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Pub_Sub implements Client {
	
	//private final static int portR = 1200;
	//private final static int portS = 2400;
	private int portC;
	private Forum server;
	private String username;

	public Pub_Sub(String user, String hostS, String nameS, int portC, int portS) throws RemoteException, NotBoundException {
		System.setProperty("java.security.policy","file:./sec.policy");
		System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname","localhost");

		this.username = user;
		Client stub = (Client) UnicastRemoteObject.exportObject(this, 0);
		this.portC=portC;
		Registry r;
		try {
			r = LocateRegistry.createRegistry(portC);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portC);
		}
		r.rebind(this.username, stub);
		System.out.println("si � creato il client");

		Registry registry = LocateRegistry.getRegistry(hostS, portS); //trova il registro
		server = (Forum) registry.lookup(nameS);
		System.out.println("fine costruttorec");
	}

	public String myname(){
		return username;
	}

	@Override
	public void ReqConnection() throws RemoteException, UnknownHostException, NotBoundException {
		int response = 1;
		try {
			while (response == 1) {
				response = server.SReqConnection(username, InetAddress.getLocalHost().getHostAddress(), portC);
				System.out.println(response);
				switch (response) {
					case (0):
						System.out.println("Connection between " + username + " and " + server.myname() + ": DONE.");
						break;
					case (1):
						System.err.println("Connection between " + username + " and " + server.myname() + ": FAILED.");
						System.err.println("Username " + '"' + username + '"' + " already exists. Try again: ");
						Scanner rd = new Scanner(System.in);
						username = rd.next();
						break;
					default:
						System.err.println("--UNEXPECTED ERROR IN REQCONNECTION--");
						break;
				}
			}
		} catch (RemoteException e) {
			System.err.println("Connection between " + username + " and " + server + ": FAILED WITH EXCEPTION.");
			e.printStackTrace();
		}
	}

	@Override
	public void ReqDisconnection() throws RemoteException {
		this.server.SReqDisconnection(username);
		System.exit(0);
	}

	@Override
	public void RSTopic(String topic) throws RemoteException {
		int a = server.SRSTopic(username, topic);
		if (a ==  0) {
			System.out.println("Iscrizione di " + username + " al topic " + topic);
			return;
		} else if (a == 1) {
			System.err.println("Iscrizione di " + username + " al topic " + topic + "non riuscita");
			return;
		}
		System.err.println("--UNEXPECTED ERROR IN RSTOPIC--");
	}

	@Override
	public void RUSTopic(String topic) throws RemoteException {
		int a = server.SRUSTopic(username, topic);
		if (a == 0) {
			System.out.println("Disiscrizione di " + username + " al topic " + topic);
			return;
		} else if (a == 1) {
			System.err.println("Disiscrizione di " + username + " al topic " + topic + "non riuscita: non era iscritto.");
			return;
		}
		System.err.println("--UNEXPECTED ERROR IN RUSTOPIC--");
	}

	@Override
	public void Publish(String msg, String topic, String user, String mittente) throws RemoteException {
		int a = server.SPublish(msg, topic, username, username);
		if (a == 0) return;
		else if (a == 1) {
			System.err.println(username + ", non puoi pubblicare sul topic " + topic + "non essendo iscritto.");
			return;
		}
		else if ( a == 2) {
            System.err.println(username + ", non puoi pubblicare sul topic " + topic + ": non esiste.");
            return;
        }
		System.err.println("--UNEXPECTED ERROR IN PUBLISH--");
	}

	public void clientPrint(String msg, String topic, String user) throws RemoteException {
		System.out.println(username+":  "+ topic + " -> " + user + ": " + msg);
	}
}
