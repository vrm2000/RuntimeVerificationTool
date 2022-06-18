package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_IMPLIES extends Node {

	public Node_IMPLIES(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		String c = "Soy Nodo " + id_nodo + " tengo IMPLIES y devuelvo " + (!lftbool || rgtbool);
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		return !lftbool || rgtbool;
	}

}
