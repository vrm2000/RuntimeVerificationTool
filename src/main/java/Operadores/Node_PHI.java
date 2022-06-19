package Operadores;

import java.util.ArrayList;
import java.util.Scanner;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

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
			if (trazas.containsKey(prop.toUpperCase()) || prop.equalsIgnoreCase("false")
					|| prop.equalsIgnoreCase("true")) {
				events_indexes = getIndex(timestamps); // Obtiene los intervalos
			} else {
				System.err.println("Atributo " + prop + " no definido en el fichero de especificaciones");
				System.err.println("Los atributos disponibles son: " + trazas.keySet().toString());
				sc.close();
				System.exit(1);
			}
			if (events_indexes.get("i").size() == 0) {
				sc.close();
				System.err.println("Error. No se ha encontrado ningun intervalo entre los eventos: " + timestamps);
				System.exit(1);
			}

			Double comp = 0.0;
			String op = "";
			Double comp2 = 0.0;
			if (trazas.containsKey(prop.toUpperCase())) {
				op = sc.next();
				comp2 = Double.parseDouble(sc.next());
			}
			for (int k = 0; k < tsmps_indexes.get("i").size(); k++) { // Para cada intervalo
				String c3 = "Busqueda de intervalo [" + timestamps.get(0) + "," + timestamps.get(1) + "] ";
				if (pnt.events_parent) {
					c3 += "dentro del intervalo " + (k + 1) + " [" + pnt.pnt.timestamps.get(0) + ","
							+ pnt.pnt.timestamps.get(1) + "] ";
				}
				if (Formula.escritorFichero)
					Escritor_Fichero.escritor(c3);
				else
					System.out.println(c3);
				// Obtenemos inicio y fin del intervalo que esta siendo evaluado
				int j = tsmps_indexes.get("j").get(k), i = tsmps_indexes.get("i").get(k);
				if (i == -1 && j == -1) {
					String c2 = "	> Intervalo no existente entre [" + timestamps.get(0) + "," + timestamps.get(1)
							+ "]. false";
					if (Formula.escritorFichero)
						Escritor_Fichero.escritor(c2);
					else
						System.out.println(c2);
					condIntervalo.add(false);
				} else {
					String c = "	> Intervalo " + (k + 1) + " [" + tsmps_indexes.get("i").get(k) + "("
							+ trazas.get("TIME").get(tsmps_indexes.get("i").get(k)) + "),"
							+ tsmps_indexes.get("j").get(k) + "("
							+ trazas.get("TIME").get(tsmps_indexes.get("j").get(k)) + ")]. " + prop.toUpperCase() + " "
							+ op + " " + comp2 + ":";
					if (Formula.escritorFichero)
						Escritor_Fichero.escritor(c);
					else
						System.out.println(c);

					int cont = 0;
					while (cont < j - i + 1) { // Evalua dentro del intervalo si se cumple Phi
						if (prop.equalsIgnoreCase("true")) { // Si PHI es true, todo sera true
							condIntervalo.add(true);
						} else if (prop.equalsIgnoreCase("false")) { // Si PHI es false, todo sera false
							condIntervalo.add(false);
						} else { // Evalua condicion del tipo [Atributo] [Operador booleano] [Valor]
							comp = Double.parseDouble(trazas.get(prop.toUpperCase()).get(i + cont));
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
						String c1 = "		>>Node PHI " + (cont + i) + ". Value: " + comp + ". "
								+ condIntervalo.get(condIntervalo.size() - 1);
						if (Formula.escritorFichero)
							Escritor_Fichero.escritor(c1);
						else
							System.out.println(c1);
						cont++;
					}
				}
			}
		}
		sc.close();
		return true; // Nadie va a hacer uso de esto, todos evaluaran condIntervalo
	}

}