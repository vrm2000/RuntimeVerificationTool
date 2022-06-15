package Operadores;

import Main.Node;

public class Node_NOT extends Node {

	public Node_NOT(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		System.out.print("Soy Nodo " + id_nodo);
		System.out.println(" tengo NOT y devuelvo " + !lftbool);
		return !lftbool;
	}

}
