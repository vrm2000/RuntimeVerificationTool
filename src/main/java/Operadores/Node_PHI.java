package Operadores;

import java.util.ArrayList;
import java.util.Scanner;

import Main.Node;

public class Node_PHI extends Node {

	private String phi;

	public Node_PHI(int id, String phi) {
		super(id);
		this.phi = phi;
		condIntervalo = new ArrayList<>();
	}

	@Override
	public boolean evaluarCondicion() {
		Scanner sc = new Scanner(phi);
		if (sc.hasNext()) {
			String prop = sc.next();
			if (trazas.containsKey(prop) || prop.equalsIgnoreCase("false") || prop.equalsIgnoreCase("true")) {
				events_indexes = getIndex(timestamps); // Obtiene los intervalos
			} else {
				System.err.println("Aprende a escribir, novato");
				sc.close();
				return false; // Deberia lanzar error
			}
			if (events_indexes.get("i").get(0) < 0) {
				sc.close();
				return false; // Deberia lanzar error
			}

			Double comp = 0.0;
			String op = "";
			Double comp2 = 0.0;
			if (trazas.containsKey(prop)) {
				op = sc.next();
				comp2 = Double.parseDouble(sc.next());
			}
			for (int k = 0; k < events_indexes.get("i").size(); k++) { // Para cada intervalo
				System.out.println("	> Intervalo " + k + ":");
				int cont = 0;
				// Obtenemos inicio y fin del intervalo que esta siendo evaluado
				int j = events_indexes.get("j").get(k), i = events_indexes.get("i").get(k);
				while (cont < j - i + 1) { // Evalua dentro del intervalo si se cumple Phi
					if (prop.equalsIgnoreCase("true")) { // Si PHI es true, todo sera true
						condIntervalo.add(true);
					} else if (prop.equalsIgnoreCase("false")) { // Si PHI es false, todo sera false
						condIntervalo.add(false);
					} else { // Evalua condicion del tipo [Atributo] [Operador booleano] [Valor]
						comp = Double.parseDouble(trazas.get(prop).get(i + cont));
						switch (op) {
						case ">":
							condIntervalo.add(comp > comp2);
							break;
						case "<":
							condIntervalo.add(comp < comp2);
							break;
						case ">=":
							condIntervalo.add(comp >= comp2);
							break;
						case "<=":
							condIntervalo.add(comp <= comp2);
							break;
						case "!=":
							condIntervalo.add(Double.compare(comp, comp2) != 0);
							break;
						case "==":
							condIntervalo.add(Double.compare(comp, comp2) == 0);
							break;
						}
					}
					System.out.println(
							"		>>Node PHI " + (cont + i) + ": " + condIntervalo.get(condIntervalo.size() - 1));
					cont++;
				}
			}
		}
		sc.close();
		return true; // Nadie va a hacer uso de esto, todos evaluaran condIntervalo
	}

}