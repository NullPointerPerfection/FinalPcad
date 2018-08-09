package progetto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class PCADBroker implements Forum {

	private final static int portS = 2400;
	private final static int portC = 3600;
	private String servername;
	private Forum amicoserver; //    l'eventuale server a cui si iscrive

    private HashMap<String, String> topic = new HashMap<String, String>(); // topic, server
    private HashMap<String, List<String>> ListaTopic = new HashMap<String, List<String>>(); //topic, lista utenti
    private HashMap<String, Client> ListaClient = new HashMap<String, Client>(); //nomeUtente, oggetto


	//public static void main(String[] args) throws RemoteException, NotBoundException { new PCADBroker("Roba", "localhost"); }

	public PCADBroker(String servername, HashMap<String, String> t) throws RemoteException, NotBoundException {
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
		topic=t;
		System.out.println("si � creato il server");
	}

	public PCADBroker(String servername, List<String> t, String nomeamico, String hostamico, int portA) throws RemoteException, NotBoundException, UnknownHostException {
		this(servername,t);
		Registry registry = LocateRegistry.getRegistry(hostamico, portA); //trova il registro
		amicoserver = (Forum) registry.lookup(nomeamico);
		ReqConnection(); //il server si iscrive ad un altro server
	}


	@Override
	public Integer SReqConnection(String user, String host) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, portC); //trova il registro
        Client stub = (Client) registry.lookup(user);

        if(ListaClient.containsKey(user)) return 1;
        else{
            ListaClient.put(user, stub);
            return 0;
        }
	}

	@Override
	public void SReqDisconnection(String user) throws RemoteException {

        if(!ListaClient.containsKey(user)) return;
        ListaClient.remove(user);

        ListaTopic.forEach((k, v) -> {
            if(v.contains(user)) v.remove(user);
        });
	}

	@Override //è da modificare... nel caso sia un topic di amicoserver il server deve iscriversi al posto del client
	public Integer SRSTopic(String user, String topic) throws RemoteException {
		if(ListaClient.containsKey(user)){ //se client iscritto
			if(this.topic.containsKey(topic)) {//se esiste il topic
                if (ListaTopic.get(topic).add(user)) //iscrivilo
                    return 0;
            }else{//se non c'è il topic crealo
			   if( this.topic.put(topic, (qualcosa))){//iscrivi il client
			       List<String> l = new ArrayList<>();
			       l.add(user);
			       ListaTopic.put(topic, l);
                   return 0;
               }
            }
		}
		return 1;
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
    public List<String> listaserveramico() throws RemoteException {
        return null;
    }

    @Override
	public void ReqConnection() throws RemoteException, UnknownHostException, NotBoundException {
		//fare controllo se esiste serveramico
        if(amicoserver == null)
            return;
        if(amicoserver.SReqConnection(servername, InetAddress.getLocalHost().getHostAddress()) == 0)
            System.out.println("il server si è registrato su un altro server");
        else
            System.out.println("non si è riuscito ad registrare");

	}

	private void updatelist() throws RemoteException {
	    amicoserver.listaserveramico();
    }

    @Override
    public void ReqDisconnection() throws RemoteException {

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
/*
	@Override
	public boolean isOnline(String username) throws RemoteException {
		return UtentiOnline.containsKey(username);
	}

	@Override
	public void goOnline(String username, Client client) throws RemoteException {
		UtentiOnline.put(username, client);
	}
*/
}
