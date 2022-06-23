package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public abstract class Node extends Thread {
	public int id_nodo;
	// Llave que permite al nodo en ejecucion obtener el control
	private static final Lock lock = new ReentrantLock();
	// Array de Condition. Es el encargado de indicar quien esta esperando o en
	// ejecucion
	public static Condition[] conditions;

	public boolean finished = false;// Solo util para Nodo root. Indica fin del cumplimiento de la propiedad

	public Node rgt; // Hijo derecho
	public boolean rgtbool = false; // Solucion aportada por el hijo derecho
	public boolean rgtSent = false; // Indica si el hijo derecho ha mandado su respuesta
	public Node lft; // Hijo izquierdo
	public boolean lftbool = false; // Solucion aportada por el hijo izquierdo
	public boolean lftSent = false; // Indica si el hijo izquierdo ha mandado su respuesta
	public Node pnt; // Padre

	// Diccionario con las trazas del fichero de eventos. Se amplía de forma
	// dinamica
	protected static Map<String, ArrayList<String>> trazas = new HashMap<>();
	// Mapa bidireccional (con la llave puedes obtener el valor y con el valor
	// puedes obtener la llave)
	// Usada para la traduccion de Tipo de evento (STT, STP) a valor que representa
	// el tipo en el fichero de eventos
	protected static BidiMap<String, String> event_type = new DualHashBidiMap<>();

	public List<String> timestamps = new ArrayList<>();
	public boolean events_parent = false; // Indica si el nodo padre es un operador temporal
	public Map<String, List<Integer>> events_indexes; // Almacena los intervalos en los que se encuentra el timestamp
														// indicado
	public Map<String, List<Integer>> tsmps_indexes;
	// Lista de booleanos para los operadores booleanos. Estos operadores centran su
	// evaluacion sobre esta lista que pertenece a su hijo. El hijo debe guardar
	// aqui cuando se cumple la propiedad establecida en los diferentes intervalos
	// de tiempo

	public List<Boolean> condIntervalo;

	/**
	 * Constructor de todos los Operadores, excepto del nodo Root.
	 * 
	 * @param id: identificador de nodo. Debe de ser unico. Sirve para encontrar la
	 *            Condition asociada a cada nodo.
	 */
	public Node(int id) {
		this.id_nodo = id;
		timestamps = new ArrayList<>();
		conditions[id_nodo] = lock.newCondition();
	}

	/**
	 * Constructor del nodo root
	 * 
	 * @param id:    identificador de nodo. Debe de ser unico. Sirve para encontrar
	 *               la Condition asociada a cada nodo.
	 * @param total: indica el total de nodos que tiene el arbol. Necesario para
	 *               construir todas las condition
	 */
	public Node(int id, int total) {
		conditions = new Condition[total];
		this.id_nodo = id;
		conditions[id_nodo] = lock.newCondition();
	}

	/**
	 * Se encarga de establecer las relaciones padre-hijo dentro de nuestro arbol
	 * 
	 * Por defecto, si solo se tiene un hijo, el hijo izquierdo sera el que lo
	 * almacene.
	 * 
	 * @param node1: hijo izquierdo
	 * @param node2: hijo derecho
	 */
	public void setSons(Node node1, Node node2) {
		this.lft = node1;
		this.rgt = node2;

		node1.pnt = this; // Establecemos que somos el padre del nodo que se nos pasa
		if (!timestamps.isEmpty()) { // Si es un operador temporal
			if (node1.timestamps.isEmpty()) { // y el hijo no es un operador temporal
				node1.timestamps = this.timestamps; // Su intervalo sera el mismo que el del nodo actual
			} else {
				// Si el hijo es un operador temporal, indica que debe coger los intervalos
				// teniendo en cuenta los intervalos del padre
				node1.events_parent = true;
			}
		}

		// Repetimos lo anterior para el hijo derecho
		if (node2 != null) {
			node2.pnt = this;
			if (!timestamps.isEmpty()) {
				if (node2.timestamps.isEmpty()) {
					node2.timestamps = this.timestamps;
				} else {
					node2.events_parent = true;
					if (node2.timestamps.size() <= 1) {
						node2.timestamps.add(this.timestamps.get(1));
					}
				}
			}
		}

	}

	// METODO ABSTRACTO DE IMPLEMENTACION EN CADA OPERADOR DESARROLLADO
	public abstract boolean evaluarCondicion();

	/**
	 * Traduce de Tipo de evento (STT, STP,...) a valor numerico que representa
	 * dicho tipo (1, 2, ...). Segun se haya indicado en el fichero de
	 * especificaciones
	 * 
	 * @param e
	 * @return
	 */
	protected String getEvent(String e) {
		return event_type.getKey(e);
	}

	/**
	 * Obtiene los intervalos que estan comprendidos entre los eventos pasados en el
	 * parametro tsmp
	 * 
	 * @param tsmp
	 * @return
	 */
	public Map<String, List<Integer>> getIndex(List<String> tsmp) {
		Map<String, List<Integer>> res = new HashMap<>();
		res.put("i", new ArrayList<Integer>());
		res.put("j", new ArrayList<Integer>());

		// Si el padre sabe los intervalos donde mirar, lo cogemos de el (Node_PHI)
		if (pnt != null && pnt.events_indexes != null) {
			res = pnt.events_indexes;
			tsmps_indexes = pnt.tsmps_indexes;
		} else {
			// Si el padre no tiene los intervalos, miramos en toda la traza
			auxGetIndex(res, 0, trazas.get("EVENTS").size(), tsmp);
		}
		return res;
	}

	/**
	 * 
	 * Este metodo es usado por aquellos operadores temporales que tengan como padre
	 * otro operador temporal. Consiste en buscar dentro de cada intervalo del
	 * padre, los intervalos del nodo actual Por ejemplo, si tenemos que el nodo
	 * actual es un operador temporal del estilo Eventually_PQ [L,H] y que su padre
	 * es otro nodo temporal que evalúa entre [STT, STP], este metodo solo buscara
	 * cuando el nodo actual cumple su intervalo dentro del intervalo del padre. Es
	 * decir, dentro de [STT, STP] cuando encuentra un intervalo [L,H].
	 * 
	 * @param tsmp: timestamps del nodo actual.
	 * @return Diccionario con los intervalos en los que tiene que evaluar
	 */
	public Map<String, List<Integer>> getIndexFromInterval(List<String> tsmp) {
		Map<String, List<Integer>> res = new HashMap<>();
		res.put("i", new ArrayList<Integer>());
		res.put("j", new ArrayList<Integer>());
		// Para cada intervalo del padre, busca dentro los intervalos del nodo actual
		// con el tsmp indicado.
		for (int i = 0; i < pnt.events_indexes.get("i").size(); i++) {
			auxGetIndex(res, pnt.events_indexes.get("i").get(i), pnt.events_indexes.get("j").get(i), tsmp);
		}
		return res;
	}

	/**
	 * Metodo auxiliar que se encarga de encontrar el intervalo dentro de un rango.
	 * 
	 * @param res
	 * @param i
	 * @param limit
	 * @param tsmp
	 */
	private void auxGetIndex(Map<String, List<Integer>> res, int inicio, int limit, List<String> tsmp) {
		int i = inicio;
		Iterator<String> it = trazas.get("EVENTS").iterator();
		Map<String, List<Integer>> aux = new HashMap<>();
		aux.put("i", new ArrayList<>());
		aux.put("j", new ArrayList<>());
		for (int x = 0; x < i; x++) {
			it.next();
		}
		if (timestamps.size() == 1) { // estamos ante E_P o A_P
			while (it.hasNext() && i <= limit) {
				String event = it.next();
				// Buscamos los indices
				if (event.equalsIgnoreCase(getEvent(tsmp.get(0)))) {
					aux.get("i").add(i);
					aux.get("j").add(i + 1);
				}
				i++;
			}
		} else {
			boolean buscoI = true;
			while (it.hasNext() && i <= limit) {
				String event = it.next();
				// Buscamos los intervalos por pares ij.
				if (buscoI && event.equalsIgnoreCase(getEvent(tsmp.get(0)))) {
					aux.get("i").add(i);
					buscoI = false; // Cuando encuentra un evento i, inicia la busqueda del evento j
				} else if (tsmp.size() > 1 && !buscoI && event.equalsIgnoreCase(getEvent(tsmp.get(1)))) {
					aux.get("j").add(i);
					buscoI = true; // Cuando encuentra el evento j asociado al evento i, vuelve a buscar nuevo
									// intervalo
					if (buscoI && event.equalsIgnoreCase(getEvent(tsmp.get(0)))) { // bucle para eventos repetidos
						aux.get("i").add(i);
						buscoI = false;
					}
				}
				i++;
			}

			if (!buscoI) { // Si un intervalo ha quedado sin cerrar (no se ha llegado a encontrar j)
				aux.get("i").remove(aux.get("i").size() - 1); // Eliminamos el inicio de dicho intervalo (i).
			}

		}
		/*
		 * if (tsmp.size() <= 1) { aux.get("j").add(trazas.get("EVENTS").size() - 1);
		 * buscoI = true; }
		 */

		if (aux.get("i").size() > 0) {
			for (int n = 0; n < aux.get("i").size(); n++) {
				res.get("i").add(aux.get("i").get(n));
				res.get("j").add(aux.get("j").get(n));
			}
		} else {
			res.get("i").add(-1);
			res.get("j").add(-1);
		}
		getTsmpIndexes(res);
	}

	/**
	 * Calcula y almacena los valores de los timestamps con respecto a los indices
	 * de los intervalos calculados previamente
	 * 
	 * @param res
	 */
	private void getTsmpIndexes(Map<String, List<Integer>> res) {
		tsmps_indexes = new HashMap<>();
		tsmps_indexes.put("i", new ArrayList<>());
		tsmps_indexes.put("j", new ArrayList<>());
		int k = 0;
		if (pnt.tsmps_indexes != null) {
			k = pnt.tsmps_indexes.get("i").get(0);
		}
		for (int l = 0; l < res.get("i").size(); l++) {
			int i = res.get("i").get(l), j = res.get("j").get(l);
			if (i == -1 && j == -1) {
				tsmps_indexes.get("i").add(-1);
				tsmps_indexes.get("j").add(-1);
			} else {
				String inicio = trazas.get("EVENT_TSMP").get(i);
				String fin = trazas.get("EVENT_TSMP").get(j);

				String t = trazas.get("TIME").get(k);
				SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
				Date date, date1, date2;
				try {
					date = parser.parse(t);
					date1 = parser.parse(inicio);
					date2 = parser.parse(fin);
					while (date.before(date1) && !date.after(date2) && k < trazas.get("TIME").size()) {
						k++;
						t = trazas.get("TIME").get(k);
						date = parser.parse(t);
					}

					if (k < trazas.get("TIME").size()) {
						tsmps_indexes.get("i").add(k);
					} else {
						System.err.println("No se ha encontrado ninguna medida entre los timestamps especificados");
						System.exit(1);
					}
					while (date.before(date2) && k < trazas.get("TIME").size()) {
						k++;
						t = trazas.get("TIME").get(k);
						date = parser.parse(t);
					}
					k--;

					if (k < trazas.get("TIME").size() - 1) {
						tsmps_indexes.get("j").add(k);
					} else {
						System.err.println("No se ha encontrado ninguna medida entre los timestamps especificados");
						System.exit(1);
					}

				} catch (ParseException e) {
					System.err.println(
							"Error en el parsing de timestamps de las trazas especificadas. Compruebe su sintaxis");
					System.exit(1);
				}
			}
		}
	}

	/**
	 * Ejecucion de la hebra controlada por un lock con condiciones
	 */
	@Override
	public void run() {
		lock.lock();
		try {
			if (lft != null) {
				// Si tiene hijo izquierdo
				if (!timestamps.isEmpty()) { // y es un nodo temporal
					if (events_parent) { // Comprobamos si su padre es un nodo temporal
						condIntervalo = new ArrayList<>();
						// Calculamos los intervalos del nodo actual que esten dentro de los intervalos
						// del padre
						events_indexes = getIndexFromInterval(timestamps);
					} else {
						// Calculamos los intervalos del nodo actual
						events_indexes = getIndex(timestamps);
					}
				}
				lft.start(); // Iniciamos la ejecucion del hijo
				while (!lftSent) { // Esperamos hasta que el hijo indica que ha terminado su ejecucion
					conditions[id_nodo].await();
				}
			}
			if (rgt != null) {
				// Si tiene hijo derecho
				if (!timestamps.isEmpty()) { // y es un nodo temporal
					if (events_parent) { // Comprobamos si su padre es un nodo temporal
						condIntervalo = new ArrayList<>();
						// Calculamos los intervalos del nodo actual que esten dentro de los intervalos
						// del padre
						events_indexes = getIndexFromInterval(timestamps);
					} else {
						// Calculamos los inetrvalos del nodo actual
						events_indexes = getIndex(timestamps);
					}
				}
				rgt.start(); // Iniciamos la ejecucion del hijo derecho
				while (!rgtSent) { // Esperamos hasta que el hijo indique que ha finalizado su ejecucion
					conditions[id_nodo].await();
				}
			}
			if (pnt != null && (lft == null || lftSent || rgtSent)) {
				// Si tiene padre y es una hoja o le han enviado la informacion ambos hijos
				if (pnt.lft.equals(this)) { // Si el nodo actual es el hijo izquierdo del padre del nodo actual
					pnt.lftbool = evaluarCondicion(); // Inicia su evaluacion
					pnt.lftSent = true; // Indica al padre que ha terminado su ejecucion
					if (pnt != null)
						conditions[pnt.id_nodo].signal(); // Despierta al padre

				} else { // Si no es el hijo izquierdo (es el derecho), hacemos lo mismo
					pnt.rgtbool = evaluarCondicion();
					pnt.rgtSent = true;
					if (pnt != null)
						conditions[pnt.id_nodo].signal();
				}
			}
			if (pnt == null) { // Si el nodo actual es el nodo raiz
				finished = true; // indica que el sistema ha terminado de evaluar su propiedad
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			lock.unlock(); // Por ultimo, liberamos el token para que otro nodo pueda obtener el control
		}
	}
}
