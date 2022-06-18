package Parsing;

import java.io.FileWriter;

public class Escritor_Fichero {
	public static FileWriter fw;

	public Escritor_Fichero(String ruta) {
		try {
			fw = new FileWriter(ruta);
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
