package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_AND extends Node {

	public Node_AND(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluarCondicion() {
		String c = "Soy Nodo " + id_nodo + "tengo (Node " + lft.id_nodo + " AND Node " + rgt.id_nodo + ") y devuelvo "
				+ (lftbool && rgtbool);
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		return lftbool && rgtbool;
	}

}
