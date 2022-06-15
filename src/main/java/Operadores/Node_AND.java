package Operadores;

import Main.Node;

public class Node_AND extends Node {

	public Node_AND(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(
				" tengo (Node " + lft.id_nodo + " AND Node " + rgt.id_nodo + ") y devuelvo " + (lftbool && rgtbool));
		return lftbool && rgtbool;
	}

}
