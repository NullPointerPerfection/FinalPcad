package progetto;

public class prova {
	
	private static int cont;
	private int a;
	
	public prova(int s) {
		cont = s;
	}

	public static void main(String[] args) {
		prova p1 = new prova(1);
		System.out.println(p1.cont);
		prova p2 = new prova(2);
		System.out.println(p1.cont);
	}

}
