import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Formula {

	public static String f = "! True";
	public static boolean resultado = false;

	public static HashMap<String, List<String>> get_Operador(String f) {
		HashMap<String, List<String>> parts = new HashMap<>();
		parts.put("Operador", new ArrayList<String>());
		parts.put("Operando", new ArrayList<String>());
		Scanner sc = new Scanner(f);
		while (sc.hasNext(f)) {
			String part = sc.next();
			String op = "";

			switch (part) {
			case "!":
				parts.get("Operador").add("!");
				part = "";
				while (sc.hasNext()) {
					part += sc.next();
				}
				parts.get("Operando").add(part);
				break;
			case "(":
				op = "";
				do {
					op += part;
					part = sc.next();
				} while (sc.hasNext() && !part.equals(")"));
				if (sc.hasNext()) {
					sc.next();
				}
				op = op.substring(0, op.length() - 2);
				parts.get("Operando").add(op);
				break;
			default:
				op = part;
				while (sc.hasNext() && !(part.equals("&&") || part.equals("||"))) {
					op += sc.next();
				}
				if (sc.hasNext()) {
					part = sc.next();
					if (part.equals("&&") || part.equals("||")) {
						parts.get("Operador").add(part);
					}
					parts.get("Operando").add(op);
					op = "";
					while (sc.hasNext()) {
						op += sc.next();
					}
					parts.get("Operando").add(op);
				}

			}
		}
		return parts;
	}
	
	public static boolean getResultado(Node raiz) {
		return raiz.evaluarCondicion();
		
	}

	public static void main(String[] args) throws InterruptedException {
		Node node = new Node("",0,3, null);
		Node node1 = new Node("NOT",1,3, node);
		Node node2 = new Node("FALSE",2,3, node1);
		node.setSons(node1, null);
		node1.setSons(node2, null);
		node.start();
		
		node.join();

		System.out.println(getResultado(node));
	}

}
