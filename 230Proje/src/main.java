
public class main {
public static void main(String[] args) {
	
		 Hyp86 assembly=new Hyp86("");
		 assembly.mov("ax", "*");
		 int number = Integer.parseInt("12F4",16);
		 assembly.mov("ax", "32h");
		 assembly.mov("ax", "22d");
		 
//registerda ters tutuluyo aray tarz�na g�re
		// yani sa� taraf 0 sol taraf 15 regde
		// biz ax=ah:al yapal�m
		// al indexi daha y�ksek olucak ama yapcak bi�i yok

		String second = "[123]";
		second = second.substring(0, second.length());

		System.out.println(second+", "  + second.length());
	}

}
