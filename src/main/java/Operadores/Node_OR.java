package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_OR extends Node {

	public Node_OR(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		String c = "Soy Nodo " + id_nodo + " tengo (Node " + lft.id_nodo + " OR Node " + rgt.id_nodo + ") y devuelvo "
				+ (lftbool || rgtbool);
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		return lftbool || rgtbool;
	}

}
