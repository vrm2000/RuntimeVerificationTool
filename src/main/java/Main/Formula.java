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
import Operadores.Node_Always_P;
import Operadores.Node_Eventually_PQ;
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
		System.out.println("Inicio de evaluacion de propiedad:");
		raiz.start();
		raiz.join();
		return raiz.evaluarCondicion();

	}

	/**
	 * Construye los diccionarios tanto de los atributos del fichero eventos como de
	 * los tipos de eventos.
	 * 
	 * @param file: fichero con la especificaciones (eltl_property.txt)
	 */
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
				sc.close();
			}

			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Se encargará de construir el arbol de la propiedad
	 * 
	 * Mientras tiene el constructor del ejemplo
	 * 
	 * 
	 */
	public static Node construirArbol() {
		// El arbol de ejemplo debe dar TRUE.
		Node node = new Node_Root(0, 6);
		((Node_Root) node).setDiccionario(event_type);

		Node node1 = new Node_AND(1);
		Node node2 = new Node_Always_P(2, "stt");
		Node node3 = new Node_Eventually_PQ(3, "l", "h");
		Node node4 = new Node_PHI(4, "RX_DATA > 10");
		Node node5 = new Node_PHI(5, "RX_DATA == 30");

		node.setSons(node1, null);
		node1.setSons(node2, node3);
		node2.setSons(node4, null);
		node3.setSons(node5, null);

		return node;
	}

	/**
	 * Para crear el arbol de manera artificial es necesario definir en el
	 * constructor de la clase Main.Node:
	 * 
	 * Si el nodo es Root: necesita su identificador y el total de nodos del arbol
	 * 
	 * Si el nodo es un nodo PHI: necesita identificador y expresion PHI a evaluar
	 * (actualmente del tipo [atributo] [operador booleano] [Valor])
	 * 
	 * Si el nodo es un operador temporal: identificador, primer timestamp, segundo
	 * timestamp(si define ambos)
	 * 
	 * Para indicar los hijos usar el metodo setSons() de la clase Main.Node. El
	 * primer hijo será el de la izquierda y el segundo que se le indique el de la
	 * derecha. Si solo tiene un hijo, indicar al hijo derecho como null.
	 * 
	 * Se pretende que el árbol se construya preferentemente hacia la izquierda. Si
	 * el nodo no tiene hijos, no hace falta usar el metodo setSons().
	 * 
	 * "eltl_property.txt" contiene las especificaciones de medidas y de tipos de
	 * eventos. Para indicar que una columna del fichero events es un atributo, se
	 * usa #define [nombre_atributo] [num columna].
	 * 
	 * Para indicar los tipos de eventos que se encuentran en el fichero eventos, es
	 * necesario la existencia de una columna con #define EVENTS [num columna] que
	 * indique la columna que haya que traducir. Los tipos de eventos se indican
	 * como #events [event_name] [id tipo evento]. El identificador de tipo de
	 * evento debera ser valores que se encuentren en la columnna designada como
	 * EVENTS.
	 * 
	 * 
	 * IMPORTANTE:
	 * 
	 * NECESARIO EJECUTAR Formula AÑADIENDO COMO ARGUMENTO LA RUTA DEL FICHERO
	 * "events_0.txt" NECESARIO EJECUTAR OnlineEvents AÑADIENDO COMO ARGUMENTO LA
	 * RUTA DEL FICHERO "eltl_property.txt" NECESARIO EJECUTAR Formula PREVIAMENTE A
	 * OnlineEvents
	 * 
	 * 
	 * @param args[0]: ruta del fichero "events_0.txt". Especifica los eventos, asi
	 *                 como diferentes parametros o medidas del evento.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Map<String, String> variables = identificaVariables(args[0]);
		Node raiz = construirArbol(); // construye el arbol (por ahora el que esta indicado como ejemplo)
		try {
			DatagramSocket socketUDP = new DatagramSocket(PUERTO);
			String mensaje = "";
			// Llegada de eventos a traves de socket
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
					sc.close();
				}
			}

			socketUDP.close();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		System.out.println("Evaluacion de la propiedad: " + getResultado(raiz));

	}

}
