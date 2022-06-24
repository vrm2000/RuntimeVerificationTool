	package Main;

import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import Operadores.Node_AND;
import Operadores.Node_Always_P;
import Operadores.Node_Always_PQ;
import Operadores.Node_Eventually_P;
import Operadores.Node_Eventually_PQ;
import Operadores.Node_IMPLIES;
import Operadores.Node_NOT;
import Operadores.Node_OR;
import Operadores.Node_PHI;
import Operadores.Node_Root;
import Parsing.Escritor_Fichero;
import Parsing.Parsing;

public class Formula {

	final static int PUERTO = 7777; // puerto del socket de conexion
	static byte[] buffer = new byte[1024]; // buffer para el socket
	static Map<String, ArrayList<String>> datos = new HashMap<>();
	static Map<String, ArrayList<String>> eventos = new HashMap<>();
	private static int longitudArbol; // longitud total del arbol a formal
	public static boolean escritorFichero = false; // indica si se va a escribir en fichero de log o no
	/*
	 * El bidimap nos permitira saber que tipo de evento es cada uno cuando estos
	 * esten expresados de forma numerica. La clave sera el numero identificador de
	 * tipo y el segundo el nombre del tipo. Vease, si identificamos al evento stp
	 * como 2 en nuestro fichero, esto nos permite saber que tipo de evento es. Para
	 * definir la relacion entre el numero y el tipo de evento, hay que hacerlo en
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
	 * @param file: fichero con las especificaciones (eltl_property.txt)
	 */
	public static void identificaVariables(String file) {
		String data = "";
		Map<String, String> res = new HashMap<>();
		try {
			File f = new File(file);
			Scanner myReader = new Scanner(f);
			String medidas = "";
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				Scanner sc = new Scanner(data);
				String id = "";
				if (sc.hasNext())
					id = sc.next();
				if (id.equalsIgnoreCase("#define")) {
					String var_name = sc.next(), event_id = "";
					if (var_name.equalsIgnoreCase("MEASURES_FILE")) {
						medidas = sc.next();
						medidas = medidas.substring(1, medidas.length() - 1);
					} else {
						event_id = sc.next();
						res.put(event_id, var_name.toUpperCase());
					}
				}

				if (id.equalsIgnoreCase("#events")) {
					String event_name = sc.next(), event_id = sc.next();
					event_type.put(event_id, event_name);
				}
				sc.close();
			}
			myReader.close();
			if (medidas.isEmpty()) {
				System.err.println("No se ha especificado fichero de medidas en el fichero de especificacion " + file
						+ "\n" + "Agrege la especificacion usando #define MEASURES_FILE <nombre_fichero>");
				System.exit(1);
			}
			leerMedidas(medidas, res);

		} catch (FileNotFoundException e) {
			System.err.println("El fichero " + file + " de especificacion no existe en la ruta especificada");
		}
	}

	private static void leerMedidas(String medidas, Map<String, String> res) {
		Date anterior = null, actual = null;
		SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
		try {
			File f = new File(medidas);
			Scanner myReader = new Scanner(f);
			while (myReader.hasNext()) {
				String data = myReader.nextLine();
				Scanner scmed = new Scanner(data);
				int i = 0;
				while (scmed.hasNext()) {
					String var_name = res.get(Integer.toString(i));
					if (!datos.containsKey(var_name)) {
						datos.put(var_name, new ArrayList<String>());
					}
					String medida = "";
					if (var_name == res.get("0")) {
						medida = scmed.next();
						String dia, mes, year = "";
						try (Scanner trim = new Scanner(medida)) {
							trim.useDelimiter("[-]");
							dia = trim.next();
							mes = trim.next();
							year = trim.next();
							if (year.length() < 4) {
								year = "20" + year;
							}
						}
						medida = dia + "-" + mes + "-" + year + " " + scmed.next();
						if (!medida.contains("."))
							medida = medida.concat(".0"); // añadimos terminacion para poder parsear correctamente
						if (anterior == null) {
							anterior = parser.parse(medida);
						} else {
							actual = parser.parse(medida);
							if (actual.before(anterior)) {
								scmed.close();
								throw new IllegalArgumentException();
							}
							anterior = actual;
						}

					} else {
						medida = scmed.next();
					}
					datos.get(var_name).add(medida);
					i++;
				}
				scmed.close();
			}
			myReader.close();

		} catch (FileNotFoundException | ParseException e) {
			System.err.println("Error en la lectura del fichero de medidas " + medidas
					+ ". Compuebe que el fichero exista en la ruta especificada");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.err.println("Error en el fichero de medidas. No se cumple la propiedad de"
					+ " definicion eLTL 1, donde ti < tf en todos los intervalos." + " En especifico: "
					+ parser.format(anterior).toString() + " y " + parser.format(actual).toString());
			System.exit(1);
		}
	}

	private static String imprimeFichero() {
		longitudArbol = 0;
		StringJoiner sj = new StringJoiner("\n");
		try {
			Scanner sc = new Scanner(new File("arbol.txt"));

			while (sc.hasNextLine()) {
				sj.add(sc.nextLine());
				longitudArbol++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sj.toString();
	}

	/**
	 * Se encargará de construir el arbol de la propiedad. La propiedad debe estar
	 * separada por espacios para una correcta lectura.
	 * 
	 * Podremos encontrar una representacion que se le ha dado al arbol en el
	 * fichero temporal "arbol.txt" que este metodo genera. Aunque si la herramienta
	 * se ejecuta usando el script, este fichero temporal se elimina después de
	 * cada uso
	 */
	public static Node construirArbol(String property) {
		try {
			Parsing.parseado(property); // parseado a la propiedad y creacion del fichero "arbol.txt"
		} catch (FileNotFoundException e) {
			System.err.println("El fichero " + property + " no existe en la ruta especificada");
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Error al leer la propiedad. Verifique su sintaxis");
			System.exit(1);
		}

		String estructura_arbol = imprimeFichero(); // leemos fichero resultado del parsing de la propiedad

		// estructuras necesarias para la creacion del arbol
		Map<Integer, Node> listaNodos = new HashMap<>();
		Map<Integer, List<Integer>> relacionHijos = new HashMap<>();
		List<Integer> hijos = new ArrayList<>();
		List<Integer> hijosCreados = new ArrayList<>();

		// creamos raiz
		Node node = new Node_Root(0, longitudArbol + 1);
		((Node_Root) node).setDiccionario(event_type);

		// creamos estructura del arbol analizando cada linea del resultado del parsing
		try (Scanner sc = new Scanner(estructura_arbol)) {
			while (sc.hasNextLine()) {
				String nodoExpr = sc.nextLine();
				try (Scanner sc1 = new Scanner(nodoExpr)) {
					int id = sc1.nextInt();
					String nombreNodo = sc1.next().toUpperCase();
					switch (nombreNodo) {
					case "PHI":
						String condicion = sc1.nextLine();
						listaNodos.put(id, new Node_PHI(id, condicion));
						break;
					case "ALWAYS_":
						if (sc1.nextInt() == 1) {
							String timestamp = sc1.next();
							listaNodos.put(id, new Node_Always_P(id, timestamp));
						} else {
							String timestamp1 = sc1.next();
							String timestamp2 = sc1.next();
							listaNodos.put(id, new Node_Always_PQ(id, timestamp1, timestamp2));
						}
						sc1.next();
						hijos = new ArrayList<>();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;
					case "EVENTUALLY_":
						if (sc1.nextInt() == 1) {
							String timestamp = sc1.next();
							listaNodos.put(id, new Node_Eventually_P(id, timestamp));
						} else {
							String timestamp1 = sc1.next();
							String timestamp2 = sc1.next();
							listaNodos.put(id, new Node_Eventually_PQ(id, timestamp1, timestamp2));
						}
						sc1.next();
						hijos = new ArrayList<>();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;
					case "NOT":
						listaNodos.put(id, new Node_NOT(id));
						sc1.next();
						hijos = new ArrayList<>();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;
					case "OR":
						listaNodos.put(id, new Node_OR(id));
						hijos = new ArrayList<>();
						sc1.next();
						hijos.add(sc1.nextInt());
						sc1.next();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;
					case "AND":
						listaNodos.put(id, new Node_AND(id));
						hijos = new ArrayList<>();
						sc1.next();
						hijos.add(sc1.nextInt());
						sc1.next();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;
					case "IMPLIES":
						listaNodos.put(id, new Node_IMPLIES(id));
						hijos = new ArrayList<>();
						sc1.next();
						hijos.add(sc1.nextInt());
						sc1.next();
						hijos.add(sc1.nextInt());
						relacionHijos.put(id, hijos);
						break;

					}
				}
				hijosCreados.addAll(hijos);
			}
		}

		int idx_hijo_principal = 0;
		for (int i = 1; i <= longitudArbol; i++) {
			if (!hijosCreados.contains(i)) {
				idx_hijo_principal = i;
			}
		}

		node.setSons(listaNodos.get(idx_hijo_principal), null);

		// asignamos hijos
		for (Integer id : relacionHijos.keySet()) {
			List<Integer> sons = relacionHijos.get(id);
			if (sons.size() == 1) {
				listaNodos.get(id).setSons(listaNodos.get(sons.get(0)), null);
			} else {
				listaNodos.get(id).setSons(listaNodos.get(sons.get(0)), listaNodos.get(sons.get(1)));
			}

		}
		return node; // devolvemos nodo raiz con toda su estructura creada
	}

	private static String[] recopilaInformacion() {
		String[] res = new String[3];
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Introduzca fichero de especificacion de propiedades: ");
			res[0] = sc.next();
			System.out.print("Introduzca fichero con la propiedad: ");
			res[1] = sc.next();
			System.out.print("Indique si desea mostrar informacion por pantalla (N) o en el fichero \"log.txt\" (Y): ");
			res[2] = sc.next();
		}
		return res;
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
	 * El fichero de especificaciones contiene las especificaciones de medidas y de
	 * tipos de eventos. Para indicar que una columna del fichero events es un
	 * atributo, se usa #define [nombre_atributo] [num columna].
	 * 
	 * Para indicar los tipos de eventos que se encuentran en el fichero eventos, es
	 * necesario la existencia de una columna con #define EVENTS [num columna] que
	 * indique la columna que haya que traducir. Los tipos de eventos se indican
	 * como #events [event_name] [id tipo evento]. El identificador de tipo de
	 * evento debera ser valores que se encuentren en la columnna designada como
	 * EVENTS.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		System.out.println("******************************");
		System.out.println("Runtime Verification Tool");
		System.out.println("******************************");

		if (args.length < 3) {
			args = recopilaInformacion();
		}

		identificaVariables(args[0]);

		Node raiz = construirArbol(args[1]); // construye el arbol (por ahora el que esta indicado como
												// ejemplo)
		((Node_Root) raiz).updateMedidas(datos);

		if (!args[2].equals("N")) {
			escritorFichero = true;
			try {
				Escritor_Fichero.fw = new FileWriter("log.txt");
			} catch (IOException e) {
				System.err.println("El fichero log.txt no existe");
			}
		}

		String c1 = "******************************\n" + "Evaluacion de la propiedad: \n\t";
		try (Scanner sc = new Scanner(new File(args[1]))) {
			c1 += sc.nextLine();
		} catch (FileNotFoundException e1) {
			System.err.println("Error de lectura en el archivo " + args[1]);
		}
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c1 + "\n");

		System.out.println(c1 + "\n");

		datos.put("EVENTS", new ArrayList<>());
		datos.put("EVENT_TSMP", new ArrayList<>());
		Date anterior = null, actual = null;
		SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
		try {
			ServerSocket servidor = new ServerSocket(PUERTO);
			String mensaje = "";
			// Llegada de eventos a traves de socket
			System.out.println("Esperando la conexion del cliente...");
			Socket sc = servidor.accept(); // esperamos conexion del cliente
			System.out.println("Cliente conectado. Listo para enviar eventos");
			DataInputStream in = new DataInputStream(sc.getInputStream());
			while (!mensaje.equals("quit")) {
				mensaje = in.readUTF();
				if (!mensaje.equals("quit")) {
					Scanner sx = new Scanner(mensaje);
					String medida = sx.next();
					String dia, mes, year = "";
					try (Scanner trim = new Scanner(medida)) {
						trim.useDelimiter("[-]");
						dia = trim.next();
						mes = trim.next();
						year = trim.next();
						if (year.length() < 4) {
							year = "20" + year;
						}
					}
					medida = dia + "-" + mes + "-" + year + " " + sx.next();
					if (!medida.contains("."))
						medida = medida.concat(".0"); // añadimos terminacion para poder parsear correctamente
					if (anterior == null) {
						anterior = parser.parse(medida);
					} else {
						actual = parser.parse(medida);
						if (actual.before(anterior)) {
							sx.close();
							sc.close();
							servidor.close();
							throw new IllegalArgumentException();
						}
						anterior = actual;
					}
					String ev = sx.next();
					datos.get("EVENT_TSMP").add(medida);
					datos.get("EVENTS").add(ev);
					sx.close();
				}
			}
			sc.close();
			servidor.close();
		} catch (IOException | ParseException ex) {
			System.err.println(ex.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("Error en el fichero de eventos. No se cumple la propiedad de"
					+ " definicion eLTL 1, donde ti < tf en todos los intervalos." + " En especifico: "
					+ parser.format(anterior).toString() + " y " + parser.format(actual).toString());
			System.exit(1);
		}

		try {
			boolean resultado = getResultado(raiz);
			System.out.println("\nResultado de evaluacion de la propiedad: " + resultado + "\n");
			if (escritorFichero) {
				System.out.println("Mas informacion en el ultimo fichero de log correspondiente");
				Desktop fichero = Desktop.getDesktop();
				fichero.open(new File("log.txt"));
				Escritor_Fichero.escritor("Evaluacion de la propiedad: " + resultado);
			}
		} catch (InterruptedException e) {
			System.err.println("Error desconocido en el procesamiento de la propiedad");
		} catch (IOException e) {
			System.err.println("Error al abrir fichero de log");
		}
		if (escritorFichero)
			Escritor_Fichero.cierre();

	}

}
