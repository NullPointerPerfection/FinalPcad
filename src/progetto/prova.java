package progetto;

import java.util.HashMap;

public class prova {
	
	private static int cont;
	
	private static HashMap<String, String> BigMom = new HashMap<>();
	
	public prova(String s, String v) {
		BigMom.put(s, v);
	}

	public static void main(String[] args) {
		prova p1 = new prova("a","b");
		System.out.println(p1.BigMom.keySet());
		prova p2 = new prova("1","2");
		System.out.println(p1.BigMom.keySet());
		System.out.println(p2.BigMom.keySet());
		
		//creo root (classe semplice[potrei farlo anche remoto e implementare i metodi di inserimento di topic nella lista, e la restituzione del nome del server in cui è salvato un topic]) mi dichiaro un thread pool di dimensione variabile minima di 5
		//mi dichiaro una lista di topic/server
		
		//e se io dentro al client mi salvo il server root, da cui chiamo un metodo per sapere il nome del server che mi serve
		//e poi faccio come già previsto
	}

}
