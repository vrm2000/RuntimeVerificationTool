package Operadores;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.BidiMap;

import Main.Node;

public class Node_Root extends Node {

	public Node_Root(int id, int total) {
		super(id, total);
		pnt = null;
	}

	@Override
	public boolean evaluarCondicion() {
		while (!finished) {

		}
		return lftbool;
	}

	public void setDiccionario(BidiMap<String, String> e) {
		event_type = e;
	}

	public void updateMedidas(Map<String, ArrayList<String>> datos) {
		trazas = datos;
	}

}
