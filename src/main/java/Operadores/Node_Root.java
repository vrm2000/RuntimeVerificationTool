package Operadores;

import Main.Node;

public class Node_Root extends Node {

	public Node_Root(int id, int total, Node parent) {
		super(id, total, parent);
	}

	@Override
	public boolean evaluarCondicion() {
		return false;
	}

}
