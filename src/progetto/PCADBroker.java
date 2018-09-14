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

	private int portS;//le porte sono così per i test (bisognerà fare in modo di passare la porta richiesta quando serve)
	private String servername;
	private Forum amicoserver; //    l'eventuale server a cui si iscrive
	//private String amiconame;

	private List<String> mytopic;
	private List<String> ereditati;

    //private HashMap<String, String> topic = new HashMap<String, String>(); // topic, server

	private List<String> utentionline = new ArrayList<>();
    private HashMap<String, List<String>> ListaTopic = new HashMap<String, List<String>>(); //topic, lista utenti
    private HashMap<String, Client> ListaClient = new HashMap<String, Client>(); //nomeUtente, oggetto


	public PCADBroker(String servername, List<String> t, int port) throws RemoteException, NotBoundException {
		System.out.println("inizio costuttore server roba");
		this.servername = servername;
		Forum stub = (Forum) UnicastRemoteObject.exportObject(this, 0);
		Registry r;
        portS=port;
		try {
			r = LocateRegistry.createRegistry(portS);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portS);
		}
		r.rebind(this.servername, stub);
		mytopic=t;
		System.out.println("si � creato il server");
	}

	public PCADBroker(String servername, List<String> t, int port, String nomeamico, String hostamico, int portA) throws RemoteException, NotBoundException, UnknownHostException {
		this(servername, t, port);
		Registry registry = LocateRegistry.getRegistry(hostamico, portA); //trova il registro
		amicoserver = (Forum) registry.lookup(nomeamico);
		updatelist();
		ReqConnection(); //il server si iscrive ad un altro server
	}


	@Override
	public Integer SReqConnection(String user, String host, int portC) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, portC); //trova il registro
        Client stub = (Client) registry.lookup(user);
			synchronized (this) {
				if (ListaClient.containsKey(user)) return 1;
				ListaClient.put(user, stub);
				utentionline.add(user);
			}
        return 0;
	}

	@Override//non dovrebbe servire il blocco perchè dalla connessione prevedo già che se c'è un user quello sarà univoco (se non è presente nella lista ok, ma se è presente sicuramente saranno diversi)
	public void SReqDisconnection(String user) throws RemoteException {
        if(!ListaClient.containsKey(user)) return;
        utentionline.remove(user);
	}

	@Override
	public Integer SRSTopic(String user, String topic) throws RemoteException {
		if(amicoserver != null) updatelist();
		if(ListaClient.containsKey(user)){ //se client iscritto
			synchronized (this) {
				if (mytopic.contains(topic)) {//se è proprio
					ListaTopic.get(topic).add(user); //iscrivilo
					return 0;
				} else if(ereditati.contains(topic)){ //quest'ultimo pezzo è ancora da decidere come gestire la concorrenza (secondo me mettere l'intero metodo syn non è performante)
					RSTopic(topic);//il server si iscrive al topic dell serveramico
					ListaTopic.get(topic).add(user);
					return 0;
				} //se non c'è il topic crealo
				mytopic.add(topic);//iscrivi il client
				List<String> l = new ArrayList<>();
				l.add(user);
				ListaTopic.put(topic, l);
				return 0;
			}
		}
		return 1;
	}

	@Override
	public Integer SRUSTopic(String user, String topic) throws RemoteException {
		if (ListaTopic.containsKey(topic)){
			ListaTopic.get(topic).remove(user);
			if(ereditati.contains(topic) && ListaTopic.get(topic).isEmpty()){
				RUSTopic(topic);
			}
		return 0;
	}
	return 1;
    }

	@Override
	public Integer SPublish(String msg, String topic) throws RemoteException {
		if(mytopic.contains(topic)) {
			List<String> ls = ListaTopic.get(topic); //lista utenti iscritti
			for(int i=0; i<ls.size(); i++) ListaClient.get(ls.get(i)).clientPrint(msg, topic);
			return 0;
		}else if(ereditati.contains(topic)) {
			Publish(msg, topic);
			return 0;
		} return 1;
	}


    @Override
	public void ReqConnection() throws RemoteException, UnknownHostException, NotBoundException {
		//fare controllo se esiste serveramico
        if(amicoserver == null)
            return;
        if(amicoserver.SReqConnection(servername, InetAddress.getLocalHost().getHostAddress(), portS) == 0)
            System.out.println("il server si è registrato su un altro server");
        else
            System.out.println("non si è riuscito ad registrare");
		updatelist();
	}

	@Override
	public List<String> listaserveramico() throws RemoteException {
		return mytopic;
	}

	private synchronized void updatelist() throws RemoteException {
		ereditati = amicoserver.listaserveramico();
	}

    @Override
    public void ReqDisconnection() throws RemoteException {
		if(amicoserver != null) {
			amicoserver.SReqDisconnection(servername);
			amicoserver = null;
		}
	}

	@Override
	public void RSTopic(String topic) throws RemoteException {
		if(amicoserver.SRSTopic(servername, topic).equals(0))
			System.out.println("il server " + servername + " si è iscritto al topic " + topic);
	}

	@Override
	public void RUSTopic(String topic) throws RemoteException {
		amicoserver.SRUSTopic(servername, topic);
	}

	@Override
	public void Publish(String msg, String topic) throws RemoteException {
		amicoserver.Publish(msg, topic);
		// in teoria se lho pensata bene il serveramico userà la clientprint(del nostro server) per inviare la notifica...
		// a quel punto nella nostra clientprint faremo in modo di chiamarla su tutti i nostri client iscritti
	}

	@Override
	public void clientPrint(String msg, String topic) throws RemoteException {
		List<String> s = ListaTopic.get(topic);
		for (int i = 0; i < s.size(); i++) ListaClient.get(s.get(i)).clientPrint(msg, topic);
	}
}
