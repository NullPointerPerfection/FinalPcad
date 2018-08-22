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
	private final static int portC = 2500;//3600
	private String servername;
	private Forum amicoserver; //    l'eventuale server a cui si iscrive

    private HashMap<String, String> topic = new HashMap<String, String>(); // topic, server
    private HashMap<String, List<String>> ListaTopic = new HashMap<String, List<String>>(); //topic, lista utenti
    private HashMap<String, Client> ListaClient = new HashMap<String, Client>(); //nomeUtente, oggetto


	//public static void main(String[] args) throws RemoteException, NotBoundException { new PCADBroker("Roba", "localhost"); }

	public PCADBroker(String servername, HashMap<String, String> t, int port) throws RemoteException, NotBoundException {
		System.out.println("inizio costuttore server roba");
		this.servername = servername;
		Forum stub = (Forum) UnicastRemoteObject.exportObject(this, 0);
		Registry r = null;
        portS=port;
		try {
			r = LocateRegistry.createRegistry(portS);
		} catch (RemoteException e) {
			r = LocateRegistry.getRegistry(portS);
		}
		r.rebind(this.servername, stub);
		topic=t;
		System.out.println("si � creato il server");
	}

	public PCADBroker(String servername, HashMap<String, String> t, int port, String nomeamico, String hostamico, int portA) throws RemoteException, NotBoundException, UnknownHostException {
		this(servername,t, port);
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

	@Override
	public Integer SRSTopic(String user, String topic) throws RemoteException {
		if(amicoserver != null) updatelist();
		if(ListaClient.containsKey(user)){ //se client iscritto
			if(this.topic.containsKey(topic)) {//se esiste il topic
                if(this.topic.get(topic).equals(servername))//se è proprio
			         if (ListaTopic.get(topic).add(user)) //iscrivilo
                         return 0;
                this.RSTopic(topic);//il server si iscrive al topic dell serveramico
                return 0;
            }else{//se non c'è il topic crealo
			        this.topic.put(topic, servername);//iscrivi il client
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
		    if(this.topic.get(topic).equals(servername)) {
                ListaTopic.get(topic).remove(user);
                return 0;
            }else{
		        amicoserver.SRUSTopic(servername, topic);
                ListaTopic.get(topic).remove(user);
                return 1;
            }
    }

	@Override
	public Integer SPublish(String msg, String topic) throws RemoteException {
		if(this.topic.get(topic).equals(servername)) //caso in cui è un mio topic
		{
			List<String> ls = ListaTopic.get(topic); //lista utenti iscritti
			for(int i=0; i<ls.size(); i++) {
				if (ListaClient.get(ls.get(i)).listen_topic(topic))//se è in ascolto
					ListaClient.get(ls.get(i)).clientPrint(msg, topic);
			}
			return 0;
		}//se è in un altro server
		amicoserver.Publish(msg, topic);// in teoria se lho pensata bene il serveramico userà la clientprint(del nostro server) per inviare la notifica... a quel punto nella nostra clientprint faremo in modo di chiamarla su tutti i nostri client iscritti
		return null;
	}

    @Override
    public HashMap<String, String> listaserveramico() throws RemoteException {
        HashMap<String,String> s = new HashMap<String,String>();

		topic.forEach((k, v) -> {
			if(v.equals(servername)) s.put(k,v);
		});
		return s;
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
		updatelist();
	}

	private void updatelist() throws RemoteException {
		HashMap<String, String> l = amicoserver.listaserveramico();
		l.forEach((k, v) -> {
			if(!topic.containsKey(k)){
				topic.put(k,v);
				ListaTopic.put(k, new ArrayList<String>());
			}
		});
	}

    @Override
    public void ReqDisconnection() throws RemoteException {
		amicoserver.SReqDisconnection(servername);
		amicoserver=null;

		topic.forEach((k, v) -> {
			if (!v.equals(servername)) {
				topic.remove(k);
				List<String> s = ListaTopic.get(k);
				for (int i = 0; i < s.size(); i++) {
					try {
						ListaClient.get(s.get(i)).RUSTopic(k);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void RSTopic(String topic) throws RemoteException {
		if(amicoserver.SRSTopic(servername, topic).equals(0))
			System.out.println("il server " + servername + " si è iscritto al topic " + topic);

	}//RICONTROLLARE SRSTOPIC DOPO AVER LETTO IL COMMENTO SOTTOSTANTE
//il client usa il metodo rstopic per volersi iscrivere, quindi usa il metodo del server srstopic per poterlo fare. se il server non è in grado di iscriverlo chiamerà a sua volta il suo metodo rstopic per iscriversi a quello del serveramico che userà il metodo srtsopic per farlo
	@Override
	public void RUSTopic(String topic) throws RemoteException {

	}

	@Override
	public void Publish(String msg, String topic) throws RemoteException {

	}

	@Override
	public void clientPrint(String msg, String topic) throws RemoteException  {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean listen_topic(String topic) throws RemoteException {
		return false;
	}
}
