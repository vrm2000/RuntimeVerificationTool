package Parsing;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class Parsing {
	public static PrintStream out;

	public static void parseado(String argv) {
		try {
			Reader in = new InputStreamReader(System.in);
			in = new FileReader(argv);
			parser p = new parser(new Yylex(in));
			Object result = p.parse().value;
			Escritor_Fichero.cierre();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
