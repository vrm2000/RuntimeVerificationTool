package Operadores;

import Main.Node;

public class Node_OR extends Node {

	public Node_OR(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(
				" tengo (Node " + lft.id_nodo + " OR Node " + rgt.id_nodo + ") y devuelvo " + (lftbool || rgtbool));
		return lftbool || rgtbool;
	}

}
