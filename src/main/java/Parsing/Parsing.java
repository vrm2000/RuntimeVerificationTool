package Parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Reader;

public class Parsing {
	public static PrintStream out;

	public static void parseado(String file) throws FileNotFoundException, Exception {
		Reader in = new FileReader(file); // leemos del archivo pasado como parametro
		parser p = new parser(new Yylex(in)); // creamos objeto parser
		Object result = p.parse().value; // evaluamos expresion
		Escritor_Fichero.cierre();
	}
}
