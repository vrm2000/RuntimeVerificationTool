package Operadores;

import Main.Node;

public class Node_False extends Node {

	public Node_False(int id, int total, Node parent) {
		super(id, total, parent);
	}

	@Override
	public boolean evaluarCondicion() {
		return false;
	}

}
