package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_Eventually_PQ extends Node {

	public Node_Eventually_PQ(int id, String st1, String st2) {
		super(id);
		timestamps.add(st1);
		timestamps.add(st2);
	}

	@Override
	public boolean evaluarCondicion() {
		if (events_indexes.size() == 0) {
			System.err.println(
					"Error. No se ha encontrado ningun intervalo entre los eventos mencionados: " + timestamps);
			System.exit(1);
		}
		int cont = 0, offset = 0;
		boolean condicion = true;
		int k = 0;
		// Comprueba para cada intervalo
		while (k < tsmps_indexes.get("i").size() && condicion) {
			int subtotal = 0;
			// obtiene el numero de intervalos a evaluar
			if (tsmps_indexes.get("i").size() == 1 || lft.events_parent) {
				subtotal = lft.condIntervalo.size(); // Longitud del intervalo del hijo
			} else {
				subtotal = tsmps_indexes.get("j").get(k) - tsmps_indexes.get("i").get(k) + 1;
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
			k = lft.events_parent ? tsmps_indexes.get("i").size() : k + 1;
			cont = 0;
		}
		String c = "Node EVENTUALLY_PQ " + id_nodo + ":" + condicion;
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		if (events_parent) {
			String c1 = "Intervalo de condiciones EVENTUALLY_PQ " + condIntervalo;
			if (Formula.escritorFichero)
				Escritor_Fichero.escritor(c1 + "\n");
			else
				System.out.println(c1 + "\n");
		}
		return condicion;
	}
}
