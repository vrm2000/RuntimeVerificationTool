package Operadores;

import Main.Node;

public class Node_IMPLIES extends Node {

	public Node_IMPLIES(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo IMPLIES y devuelvo " + (!lftbool || rgtbool));
		return !lftbool || rgtbool;
	}

}
