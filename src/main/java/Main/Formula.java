package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import Operadores.Node_AND;
import Operadores.Node_NOT;
import Operadores.Node_OR;
import Operadores.Node_PHI;
import Operadores.Node_Root;

public class Formula {

	final static int PUERTO = 7777;
	static byte[] buffer = new byte[1024];
	static Map<String, ArrayList<String>> datos = new HashMap<>();
	/*
	 * EL bidimap nos permitirá saber que tipo de evento es cada uno cuando estos
	 * esten expresados de forma numerica. La clave será el numero identificador de
	 * tipo y el segundo el nombre del tipo. Vease, si identificamos al evento stp
	 * como 2 en nuestro fichero, esto nos permite saber que tipo de evento es. Para
	 * definir la relación entre el número y el tipo de evento, hay que hacerlo en
	 * el fichero eltl_property.txt
	 */
	static BidiMap<String, String> event_type = new DualHashBidiMap<>();

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

	public static Map<String, String> identificaVariables(String file) {
		String data = "";
		Map<String, String> res = new HashMap<>();
		try {
			File f = new File(file);
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				Scanner sc = new Scanner(data);

				String id = "";
				if (sc.hasNext())
					id = sc.next();
				if (id.equalsIgnoreCase("#define")) {
					String var_name = sc.next(), event_id = sc.next();
					res.put(event_id, var_name);
				}

				if (id.equalsIgnoreCase("#events")) {
					String event_name = sc.next(), event_id = sc.next();
					event_type.put(event_id, event_name);
				}

			}

			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Mientras tiene el constructor del ejemplo
	 * 
	 * @param property
	 */
	public static Node construirArbol() {
		Node node = new Node_Root(0, 11, null);
		Node node1 = new Node_AND(1, 11, node);
		Node node2 = new Node_OR(2, 11, node1);
		Node node3 = new Node_NOT(3, 11, node2);
		Node node4 = new Node_PHI(4, 11, node2, "RX_DATA <= 5");
		Node node5 = new Node_PHI(5, 11, node3, "RX_DATA == 5");
		Node node6 = new Node_AND(6, 11, node1);
		Node node7 = new Node_PHI(7, 11, node6, "RX_DATA != 5");
		Node node8 = new Node_OR(8, 11, node6);
		Node node9 = new Node_PHI(9, 11, node8, "RX_DATA > 5");
		Node node10 = new Node_PHI(10, 11, node8, "RX_DATA == 15");

		node.setSons(node1, null);
		node1.setSons(node2, node6);
		node2.setSons(node3, node4);
		node3.setSons(node5, null);
		node6.setSons(node7, node8);
		node8.setSons(node9, node10);

		node4.setTimestamp("stt", "stp");
		node5.setTimestamp("stt", "stp");
		node7.setTimestamp("stt", "stp");
		node9.setTimestamp("stt", "stp");
		node10.setTimestamp("stt", "stp");

		// Node node = new Node_Root(0, 5, null);
		// Node node1 = new Node_NOT(1, 5, node);
		// Node node2 = new Node_AND(2, 5, node1);
		// Node node3 = new Node_PHI(3, 5, node2, "RX_DATA >= 5");
		// Node node4 = new Node_PHI(4, 5, node2, "RX_DATA < 5");

		// node.setSons(node1, null);
		// node1.setSons(node2, null); /* Le damos los timestamps a los nodos phi. */
		// node2.setSons(node3, node4);
		// node3.setTimestamp("stt", "stp");
		// node4.setTimestamp("stt", "stp");

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
	 * "eltl_property.txt" Especifica
	 * 
	 * IMPORTANTE:
	 * 
	 * NECESARIO EJECUTAR Formula AÑADIENDO COMO ARGUMENTO LA RUTA DEL FICHERO "events_0.txt"
	 * NECESARIO EJECUTAR OnlineEvents AÑADIENDO COMO ARGUMENTO LA RUTA DEL FICHERO "eltl_property.txt"
	 * NECESARIO EJECUTAR Formula PREVIAMENTE A OnlineEvents
	 * 
	 * 
	 * @param args[0]: ruta del fichero "events_0.txt". Especifica los eventos, asi como diferentes
	 * 				   parametros o medidas del evento.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// Sentencia lógica a evaluar:
		// (NOT TRUE OR FALSE) AND (TRUE AND (TRUE OR TRUE))
		// Se puede ver el arbol que se formaria en la carpeta src/main/resources
		// String propiedad = leerPropiedad(args[0]);
		Node raiz = construirArbol();
		Map<String, String> variables = identificaVariables(args[0]);
		((Node_Root) raiz).setDiccionario(event_type);
		try {
			System.out.println("Iniciada la recepcion de eventos");
			DatagramSocket socketUDP = new DatagramSocket(PUERTO);
			String mensaje = "";
			while (!mensaje.equals("quit")) {
				DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
				socketUDP.receive(peticion);
				mensaje = new String(peticion.getData(), 0, peticion.getLength());
				if (!mensaje.equals("quit")) {
					Scanner sc = new Scanner(mensaje);
					int i = 0;
					String dato = "";
					String var_name = "";
					Map<String, String> traza = new HashMap<>();
					while (sc.hasNext()) {
						var_name = variables.get(Integer.toString(i));
						dato = sc.next();
						if (!datos.containsKey(var_name)) {
							datos.put(var_name, new ArrayList<String>());
						}
						datos.get(var_name).add(dato);
						i++;
						traza.put(var_name, dato);
					}
					((Node_Root) raiz).updateEventsTrace(traza);
				}
			}

			socketUDP.close();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		System.out.println("Evaluacion de la propiedad: " + getResultado(raiz));

	}

}
