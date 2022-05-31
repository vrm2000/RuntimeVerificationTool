package Operadores;

import Main.Node;

public class Node_NOT extends Node {

	public Node_NOT(int id, int total, Node parent) {
		super(id, total, parent);
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo NOT y devuelvo " + !lftbool);
		return !lftbool;
	}

}
