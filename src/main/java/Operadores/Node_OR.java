package Operadores;

import Main.Node;

public class Node_OR extends Node {

	public Node_OR(int id, int total, Node parent) {
		super(id, total, parent);
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo OR y devuelvo " + (lftbool || rgtbool));
		return lftbool || rgtbool;
	}

}
