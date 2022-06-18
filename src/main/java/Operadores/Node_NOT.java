package Operadores;

import Main.Formula;
import Main.Node;
import Parsing.Escritor_Fichero;

public class Node_NOT extends Node {

	public Node_NOT(int id) {
		super(id);
	}

	@Override
	public boolean evaluarCondicion() {
		String c = "Soy Nodo " + id_nodo + " tengo NOT y devuelvo " + !lftbool;
		if (Formula.escritorFichero)
			Escritor_Fichero.escritor(c);
		else
			System.out.println(c);
		return !lftbool;
	}

}
