package Operadores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Main.Node;

public class Node_PHI extends Node {

	private String phi;

	public Node_PHI(int id, int total, Node parent, String phi) {
		super(id, total, parent);
		this.phi = phi;
	}

	@Override
	public boolean evaluarCondicion() {
		Scanner sc = new Scanner(phi);
		boolean cond = false;
		List<Integer> ij = new ArrayList<>();
		if (sc.hasNext()) {
			String prop = sc.next();
			if (trazas.containsKey(prop)) {
				Iterator<String> it = trazas.get("EVENTS").iterator();
				int i = 0;
				while (it.hasNext() && i < trazas.get("EVENTS").size()
						&& !it.next().equalsIgnoreCase(getEvent(timestamps.get(0)))) {
					i++;
				}
				if (i >= trazas.get("EVENTS").size()) {
					System.err.println("Node_PHI: Causado por I. El tamaño importa.");
					return false;
				}
				ij.add(i);
				if (timestamps.size() > 1) {
					int j = 0;
					while (it.hasNext() && j < trazas.get("EVENTS").size()
							&& !it.next().equalsIgnoreCase(getEvent(timestamps.get(1)))) {
						j++;
					}
					ij.add(j);
					if (j >= trazas.get("EVENTS").size()) {
						System.err.println("Node_PHI: Causado por J. El tamaño importa.");
						return false;
					}
				}

			} else {
				System.err.println("Aprende a escribir, novato");
				return false; // Deberia lanzar error
			}

			Double comp = Double.parseDouble(trazas.get(prop).get(ij.get(0)));
			String op = sc.next();
			Double comp2 = Double.parseDouble(sc.next());
			switch (op) {
			case ">":
				cond = comp > comp2;
				break;
			case "<":
				cond = comp < comp2;
				break;
			case ">=":
				cond = comp >= comp2;
				break;
			case "<=":
				cond = comp <= comp2;
				break;
			case "!=":
				cond = comp != comp2;
				break;
			case "==":
				cond = comp == comp2;
				break;
			}
		}
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo PHI (" + phi + ") y devuelvo " + cond);
		return cond;
	}

}