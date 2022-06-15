package Operadores;

import Main.Node;

public class Node_Eventually_PQ extends Node {

	public Node_Eventually_PQ(int id, String st1, String st2) {
		super(id);
		timestamps.add(st1);
		timestamps.add(st2);
	}

	@Override
	public boolean evaluarCondicion() {
		int cont = 0, offset = 0;
		boolean condicion = true;
		int k = 0;
		// Comprueba para cada intervalo
		while (k < events_indexes.get("i").size() && condicion) {
			int subtotal = 0;
			// obtiene el numero de intervalos a evaluar
			if (events_parent) {
				subtotal = lft.condIntervalo.size(); // Longitud del intervalo del hijo
			} else {
				subtotal = events_indexes.get("j").get(k) - events_indexes.get("i").get(k) + 1;
			}
			while (cont < subtotal && !lft.condIntervalo.get(cont + offset)) {
				cont++;
			}
			condicion = condicion && cont < subtotal; // Evalua anterior condicion con la actual
			offset += subtotal; // Nos movemos al siguiente intervalo de eventos si es necesario
			if (events_parent) {
				// En caso de que el padre sea un operador temporal, añadimos la evalucion
				// de cada intervalo en la lista de las evaluaciones de operadores temporales
				condIntervalo.add(cont < subtotal);
			}
			k++;
			cont = 0;
		}
		System.out.println("Node Eventually_PQ " + id_nodo + ":" + condicion);
		if (events_parent) {
			System.out.println("Intervalo de condiciones Eventually_PQ: " + condIntervalo);
		}
		System.out.println();
		return condicion;
	}
}
