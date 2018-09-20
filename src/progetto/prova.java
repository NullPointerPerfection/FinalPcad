package progetto;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class prova {
	
	private static int cont;
	
	private static List<String> Troot = new ArrayList<>();
	private static List<String> Ta = new ArrayList<>();
	private static List<String> Tb = new ArrayList<>();
	private static List<String> Tc = new ArrayList<>();
	private static List<String> Tz = new ArrayList<>();


	public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException {

		//prova p1 = new prova("a","server1");
		//System.out.println(p1.BigMom.keySet());
		//prova p2 = new prova("1","server1");
	//	System.out.println(p1.BigMom.keySet());
		//System.out.println(p2.BigMom.keySet());

		//BigMom2.add("ccc");
		//BigMom2.add("aaa");
		//prova c = new prova("a","b");
		//System.out.println(p1.BigMom.keySet());
		//prova b = new prova("1","2");

/*		PCADBroker server = new PCADBroker("server1", BigMom, 2400);
		PCADBroker server2 = new PCADBroker("server2", BigMom2, 2500, "server1", "localhost", 2400);

       PCADBroker ser = new PCADBroker("ser", BigMom2, 2600, "server2", "localhost", 2500);
       PCADBroker s = new PCADBroker("s", BigMom2, 2700, "ser", "localhost", 2600);


        Pub_Sub client = new Pub_Sub("simo", "localhost", "server1", 1200,2400);
		Pub_Sub client2 = new Pub_Sub("dany", "localhost", "s", 1300,2700);
		client.ReqConnection();
        client.RSTopic("b");

        client2.ReqConnection();
        client2.RSTopic("b");
        client2.Publish("dany è entrato nel club, benvenuti a tutti", "b", "dany","dany");*/
		Troot.add("cucina");
		Troot.add("calcio");
		Troot.add("giochi");
		Troot.add("manga");
		Tz.add("serietv");
		Ta.add("cucito");
		Ta.add("ballo");
		Ta.add("anime");
		Ta.add("pc");
		Tb.add("basket");
		Tc.add("tennis");



		PCADBroker root = new PCADBroker("root", Troot, 2400);
		PCADBroker a= new PCADBroker("a", Ta, 2500, "root", "localhost", 2400);

		PCADBroker b = new PCADBroker("b", Tb, 2600, "a", "localhost", 2500);
		PCADBroker c = new PCADBroker("c", Tc, 2700, "a", "localhost", 2500);
		PCADBroker z= new PCADBroker("z", Tz, 2800, "root", "localhost", 2400);


		Pub_Sub client1 = new Pub_Sub("1", "localhost", "root", 1200,2400);
		Pub_Sub client2 = new Pub_Sub("2", "localhost", "root", 1300,2400);
		Pub_Sub client3 = new Pub_Sub("3", "localhost", "a", 1400,2500);
		Pub_Sub client4 = new Pub_Sub("4", "localhost", "a", 1500,2500);
		Pub_Sub client5 = new Pub_Sub("5", "localhost", "b", 1600,2600);
		Pub_Sub client6 = new Pub_Sub("6", "localhost", "b", 1700,2600);
		Pub_Sub client7 = new Pub_Sub("7", "localhost", "c", 1800,2700);
		Pub_Sub client8 = new Pub_Sub("8", "localhost", "c", 1900,2700);
		Pub_Sub client9 = new Pub_Sub("9", "localhost", "z", 2000,2800);
		Pub_Sub client10 = new Pub_Sub("10", "localhost", "z", 2100,2800);


		client1.ReqConnection();
		client2.ReqConnection();
		client3.ReqConnection();
		client4.ReqConnection();
		client5.ReqConnection();
		client6.ReqConnection();
		client7.ReqConnection();
		client8.ReqConnection();
		client9.ReqConnection();
		client10.ReqConnection();

		client1.RSTopic("cucina");
		client2.RSTopic("cucina");
		client3.RSTopic("cucina");
		client4.RSTopic("cucina");
		client5.RSTopic("cucina");
		client6.RSTopic("cucina");
		client7.RSTopic("cucina");
		client8.RSTopic("cucina");
		client9.RSTopic("cucina");
		client10.RSTopic("cucina");
		client1.RSTopic("calcio");
		client2.RSTopic("calcio");
		client1.RSTopic("giochi");
		client2.RSTopic("giochi");
		client9.RSTopic("giochi");
		client10.RSTopic("giochi");
		client1.RSTopic("manga");
		client2.RSTopic("manga");
		client3.RSTopic("manga");
		client4.RSTopic("manga");
		client5.RSTopic("manga");
		client6.RSTopic("manga");
		client7.RSTopic("manga");
		client8.RSTopic("manga");
		client9.RSTopic("serietv");
		client10.RSTopic("serietv");
		client3.RSTopic("cucito");
		client4.RSTopic("cucito");
		client5.RSTopic("cucito");
		client6.RSTopic("cucito");
		client7.RSTopic("cucito");
		client8.RSTopic("cucito");
		client4.RSTopic("ballo");
		client3.RSTopic("ballo");
		client4.RSTopic("anime");
		client5.RSTopic("anime");
		client6.RSTopic("anime");
		client3.RSTopic("anime");
		client3.RSTopic("pc");
		client4.RSTopic("pc");
		client7.RSTopic("pc");
		client8.RSTopic("pc");
		client5.RSTopic("basket");
		client6.RSTopic("basket");
		client7.RSTopic("tennis");
		client8.RSTopic("tennis");


		client8.RSTopic("tennis");
		client8.RSTopic("cucito");
		client9.RSTopic("basket");
		client10.RSTopic("basket");
		client4.RSTopic("scherma");
		client6.RSTopic("scherma");
		client7.RSTopic("scherma");
		client1.RSTopic("pallavolo");
		client5.RSTopic("pallavolo");
		client8.RSTopic("pallavolo");
		client10.RSTopic("pallavolo");
		client3.RSTopic("pallavolo");

		client6.ReqDisconnection();
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("\\", "calcio", "2","2");
		client10.Publish("!", "giochi", "10","10");
		client9.Publish("£", "manga", "9","9");
		client9.Publish("$", "serietv", "9","9");
		client8.Publish("4-", "manga", "8","8");
		client3.Publish("%", "cucina", "3","3");
		client6.Publish("&", "calcio", "6","6");
		client6.ReqConnection();
		client6.Publish("&", "calcio", "6","6");
		client4.Publish("-7", "cucito", "4","4");
		client4.Publish("//", "serietv", "4","4");
		client4.Publish("()", "ballo", "4","4");
		client3.Publish("=", "pc", "3","3");
		client6.Publish("?", "anime", "6","6");
		client6.Publish("^", "basket", "6","6");
		client7.Publish("*", "tennis", "7","7");
		client7.Publish("'", "basket", "7","7");
		client9.Publish("-k", "basket", "9","9");
		client1.Publish("..", "pallavolo", "1","1");
		client5.Publish("g-", "pallavolo", "5","5");
		client7.Publish("-dd", "scherma", "7","7");
	/*	client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
		client2.Publish("-", "cucina", "2","2");
*/


		//client2.Publish("dany è entrato nel club, benvenuti a tutti", "b", "dany","dany");


	}

}
