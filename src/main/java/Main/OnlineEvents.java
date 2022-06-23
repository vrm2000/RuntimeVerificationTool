
package Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;

public class OnlineEvents {

	/**
	 * Recrea la recepción dinámica de eventos para la clase Formula. Debe ser
	 * ejecutada tras Formula. Solo muestra contenido que manda a Formula
	 * 
	 * @param args[0]: ruta del fichero con los eventos. Especifica los tipos de
	 *                 eventos y algunas medidas adicionales
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		final int PUERTO = 7777; // puerto
		final String HOST = "localhost"; // host de conexion

		try {
			Socket socket = new Socket(HOST, PUERTO);

			// abrimos canales de salida, el de entrada no es necesario
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			String cadena;
			FileReader f = new FileReader(args[0]); // lector de ficheros
			BufferedReader b = new BufferedReader(f);

			while ((cadena = b.readLine()) != null) { // leemos linea de fichero
				dos.writeUTF(cadena); // la enviamos al servidor (ServerSocket)
				System.out.println("> " + cadena);
			}

			cadena = "quit";
			dos.writeUTF(cadena);
			b.close();
			socket.close();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}

}
