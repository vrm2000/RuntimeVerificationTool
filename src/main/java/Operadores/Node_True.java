package Operadores;

import Main.Node;

public class Node_True extends Node {

	public Node_True(int id, int total, Node parent) {
		super(id, total, parent);
	}

	@Override
	public boolean evaluarCondicion() {
		return true;
	}

}