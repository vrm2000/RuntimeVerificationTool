
package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.ParseException;

public class OnlineEvents {

	/**
	 * 
	 * Recrea la recepción dinámica de eventos para la clase Formula. Debe ser
	 * ejecutada tras Formula. Solo muestra contenido que manda a Formula
	 * 
	 * @param args[0]: ruta del fichero con los eventos. Especifica los tipos de
	 *                 eventos y algunas medidas adicionales
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		final int PUERTO_SERVIDOR = 7777;
		byte[] buffer = new byte[1024];

		try {
			InetAddress direccionServidor = InetAddress.getByName("localhost");
			DatagramSocket socketUDP = new DatagramSocket();
			String cadena;
			FileReader f = new FileReader(args[0]);
			BufferedReader b = new BufferedReader(f);

			while ((cadena = b.readLine()) != null) {
				System.out.print("> ");
				buffer = cadena.getBytes();
				DatagramPacket pregunta = new DatagramPacket(buffer, cadena.length(), direccionServidor,
						PUERTO_SERVIDOR);
				System.out.println(cadena);
				socketUDP.send(pregunta);
			}
			cadena = "quit";
			buffer = cadena.getBytes();
			DatagramPacket pregunta = new DatagramPacket(buffer, cadena.length(), direccionServidor, PUERTO_SERVIDOR);
			socketUDP.send(pregunta);
			b.close();
			socketUDP.close();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}

}
