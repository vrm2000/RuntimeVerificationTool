public class Formula {

	public static String f = "! True";
	public static boolean resultado = false;
	
	public static boolean getResultado(Node raiz) throws InterruptedException {
		raiz.start();
		raiz.join();
		return raiz.evaluarCondicion();
		
	}
	
	/**
	 * Para crear el arbol de manera artificial es necesario definir en el constructor
	 * de la clase Node:
	 * 1) Lo que se va a evaluar (actualmente puede ser: TRUE, FALSE, NOT, OR, AND)
	 * 2) El identificador de nodo (es único y será originado de forma secuencial por
	 * 	  el programa, pero actualmente hay que añadirlo manualmente)
	 * 3) El número total de nodos que va a tener el árbol
	 * 4) El nodo padre del nodo actual. En caso de ser el nodo raiz, indicar null.
	 * 
	 * Para indicar los hijos usar el metodo setSons() de la clase Node. El primer hijo
	 * será el de la izquierda y el segundo que se le indique el de la derecha. Si solo
	 * tiene un hijo, indicar al hijo derecho como null.
	 *  
	 * Se pretende que el árbol se construya preferentemente hacia la izquierda.
	 * Si el nodo no tiene hijos, no hace falta usar el metodo setSons().
	 * 
	 * Es necesaria la existencia del nodo raiz, que tendrá como cadena a evaluar un 
	 * String vacio ("").
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		// Sentencia lógica a evaluar:
		// (NOT TRUE OR FALSE) AND (TRUE AND (TRUE OR TRUE))
		// Se puede ver el arbol que se formaria en la carpeta src/main/resources
		
		Node node = new Node("",0,11, null);
		Node node1 = new Node("AND",1,11, node);
		Node node2 = new Node("OR",2,11, node1);
		Node node3 =  new Node("NOT",3,11, node2);
		Node node4 =  new Node("FALSE",4,11, node2);
		Node node5 =  new Node("TRUE",5,11, node3);
		Node node6 =  new Node("AND",6,11, node1);
		Node node7 =  new Node("TRUE",7,11, node6);
		Node node8 =  new Node("OR",8,11, node6);
		Node node9 =  new Node("TRUE",9,11, node8);
		Node node10 =  new Node("TRUE",10,11, node8);
		
		node.setSons(node1, null);
		node1.setSons(node2, node6);
		node2.setSons(node3, node4);
		node3.setSons(node5, null);
		node6.setSons(node7, node8);
		node8.setSons(node9, node10);


		System.out.println(getResultado(node));
	}

}
