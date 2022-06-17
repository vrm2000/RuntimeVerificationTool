package Parsing;

import java.io.FileWriter;

public class Escritor_Fichero {
	private static FileWriter fw;

	public Escritor_Fichero() {
		try {
			fw = new FileWriter("arbol.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void escritor(String c) {
		try {
			fw.write(c + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cierre() {
		try {
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
