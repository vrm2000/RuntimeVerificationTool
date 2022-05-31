package Operadores;

import Main.Node;

public class Node_AND extends Node {

	public Node_AND(int id, int total, Node parent) {
		super(id, total, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo AND y devuelvo " + (lftbool && rgtbool));
		return lftbool && rgtbool;
	}

}
