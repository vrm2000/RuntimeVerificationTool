package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_Always_P extends Node {

	public Node_Always_P(int id, String st1) {
		super(id);
		timestamps.add(st1);
	}

	// Identico a Node_Always_P
	public boolean evaluarCondicion() {
		if (events_indexes.size() == 0) {
			System.err.println(
					"Error. No se ha encontrado ningun intervalo entre los eventos mencionados: " + timestamps);
			System.exit(1);
		}

		int cont = 0, offset = 0;
		boolean condicion = true;
		int k = 0;

		while (k < tsmps_indexes.get("i").size()) {
			int subtotal = 0;
			if (tsmps_indexes.get("i").size() == 1) {
				subtotal = lft.condIntervalo.size();
			} else {
				subtotal = tsmps_indexes.get("j").get(k) - tsmps_indexes.get("i").get(k) + 1;
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
		String c = "Node ALWAYS_P " + id_nodo + ":" + condicion;
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		if (events_parent) {
			String c1 = "Intervalo de condiciones ALWAYS_P " + condIntervalo;
			if (Formula.escritorFichero)
				Escritor_Fichero.escritor(c1+"\n");
			else
				System.out.println(c1+"\n");
		}
		return condicion;

	}

}
