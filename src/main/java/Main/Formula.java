package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

import Operadores.Node_AND;
import Operadores.Node_False;
import Operadores.Node_NOT;
import Operadores.Node_OR;
import Operadores.Node_Root;
import Operadores.Node_True;

public class Formula {

	final static int PUERTO = 8000;
	static byte[] buffer = new byte[1024];

	public static boolean getResultado(Node raiz) throws InterruptedException {
		raiz.start();
		raiz.join();
		return raiz.evaluarCondicion();

	}

	public static String leerPropiedad(String file) {
		String data = "";
		try {
			File f = new File(file);
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				data += myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Mientras tiene el constructor del ejemplo
	 * 
	 * @param property
	 */
	public static Node construirArbol(String property) {

		// TODO
		Node node = new Node_Root(0, 11, null);
		Node node1 = new Node_AND(1, 11, node);
		Node node2 = new Node_OR(2, 11, node1);
		Node node3 = new Node_NOT(3, 11, node2);
		Node node4 = new Node_False(4, 11, node2);
		Node node5 = new Node_True(5, 11, node3);
		Node node6 = new Node_AND(6, 11, node1);
		Node node7 = new Node_True(7, 11, node6);
		Node node8 = new Node_OR(8, 11, node6);
		Node node9 = new Node_True(9, 11, node8);
		Node node10 = new Node_True(10, 11, node8);

		node.setSons(node1, null);
		node1.setSons(node2, node6);
		node2.setSons(node3, node4);
		node3.setSons(node5, null);
		node6.setSons(node7, node8);
		node8.setSons(node9, node10);

		return node;
	}

	/**
	 * Para crear el arbol de manera artificial es necesario definir en el
	 * constructor de la clase Main.Node: 1) Lo que se va a evaluar (actualmente
	 * puede ser: TRUE, FALSE, NOT, OR, AND) 2) El identificador de nodo (es único y
	 * será originado de forma secuencial por el programa, pero actualmente hay que
	 * añadirlo manualmente) 3) El número total de nodos que va a tener el árbol 4)
	 * El nodo padre del nodo actual. En caso de ser el nodo raiz, indicar null.
	 * 
	 * Para indicar los hijos usar el metodo setSons() de la clase Main.Node. El
	 * primer hijo será el de la izquierda y el segundo que se le indique el de la
	 * derecha. Si solo tiene un hijo, indicar al hijo derecho como null.
	 * 
	 * Se pretende que el árbol se construya preferentemente hacia la izquierda. Si
	 * el nodo no tiene hijos, no hace falta usar el metodo setSons().
	 * 
	 * Es necesaria la existencia del nodo raiz, que tendrá como cadena a evaluar un
	 * String vacio ("").
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// Sentencia lógica a evaluar:
		// (NOT TRUE OR FALSE) AND (TRUE AND (TRUE OR TRUE))
		// Se puede ver el arbol que se formaria en la carpeta src/main/resources
		String propiedad = leerPropiedad(args[0]);
		Node raiz = construirArbol(propiedad);
		try {
			System.out.println("Iniciada la recepcion de eventos");
			DatagramSocket socketUDP = new DatagramSocket(PUERTO);
			String mensaje = "";
			while (!mensaje.equals("quit")) {
				DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
				socketUDP.receive(peticion);
				mensaje = new String(peticion.getData(), 0, peticion.getLength());
				System.out.println(mensaje);
			}
			socketUDP.close();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		System.out.println(getResultado(raiz));

	}

}
