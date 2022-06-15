package Operadores;

import Main.Node;

public class Node_Always_P extends Node {

	public Node_Always_P(int id, String st1) {
		super(id);
		timestamps.add(st1);
	}

	// Identico a Node_Always_P
	public boolean evaluarCondicion() {
		int cont = 0, offset = 0;
		boolean condicion = true;
		int k = 0;
		while (k < events_indexes.get("i").size()) {
			int subtotal = 0;
			if (events_parent) {
				subtotal = lft.condIntervalo.size();
			} else {
				subtotal = events_indexes.get("j").get(k) - events_indexes.get("i").get(k) + 1;
			}
			while (cont < subtotal && lft.condIntervalo.get(cont + offset)) {
				cont++;
			}
			condicion = condicion && cont >= subtotal;
			offset += subtotal;
			if (events_parent) {
				condIntervalo.add(cont >= subtotal);
			}
			cont = 0;
			k++;
		}
		System.out.println("Node ALWAYS_P " + id_nodo + ":" + condicion);
		if (events_parent) {
			System.out.println("Intervalo de condiciones ALWAYS_P " + condIntervalo);
		}
		System.out.println();
		return condicion;

	}

}
