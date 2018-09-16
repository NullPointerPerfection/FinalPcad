package progetto;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class prova {
	
	private static int cont;
	
	private static List<String> BigMom = new ArrayList<>();
	private static List<String> BigMom2 = new ArrayList<>();


	public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException {

		//prova p1 = new prova("a","server1");
		//System.out.println(p1.BigMom.keySet());
		//prova p2 = new prova("1","server1");
	//	System.out.println(p1.BigMom.keySet());
		//System.out.println(p2.BigMom.keySet());

		BigMom2.add("ccc");
		BigMom2.add("aaa");
		//prova c = new prova("a","b");
		//System.out.println(p1.BigMom.keySet());
		//prova b = new prova("1","2");

		PCADBroker server = new PCADBroker("server1", BigMom, 2400);
		PCADBroker server2 = new PCADBroker("server2", BigMom2, 2500, "server1", "localhost", 2400);

       // PCADBroker server2 = new PCADBroker("server2", BigMom2, 2500, "server1", "localhost", 2400);
       // PCADBroker server2 = new PCADBroker("server2", BigMom2, 2500, "server1", "localhost", 2400);


        Pub_Sub client = new Pub_Sub("simo", "localhost", "server1", 1200,2400);
		Pub_Sub client2 = new Pub_Sub("dany", "localhost", "server2", 1300,2500);
		client.ReqConnection();
        client.RSTopic("b");

        client2.ReqConnection();
        client2.RSTopic("b");
        client2.Publish("dany è entrato nel club, benvenuti a tutti", "b", "dany","dany");

	}

}
