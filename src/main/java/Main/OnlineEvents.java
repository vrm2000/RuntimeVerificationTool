package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class OnlineEvents {
 
    public static void main(String[] args) {
        final int PUERTO_SERVIDOR = 8000;
        byte[] buffer = new byte[1024];
 
        try {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();
            Scanner sc = new Scanner(System.in);
            
            String msg = "";
            
            while(!msg.equals("quit")) {
            	System.out.print("> ");
            	msg = sc.nextLine();
                buffer = msg.getBytes();
                
                DatagramPacket pregunta = new DatagramPacket(buffer, msg.length(), direccionServidor, PUERTO_SERVIDOR);
                socketUDP.send(pregunta);
                /*DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
     
                //Recibo la respuesta
                socketUDP.receive(peticion);
                System.out.println("Recibo la peticion");
     
                //Cojo los datos y lo muestro
                mensaje = new String(peticion.getData());
                System.out.println(mensaje); */
            }
            //cierro el socket
            socketUDP.close();
 
        } catch (IOException ex) {
        	System.err.println(ex.getMessage());
        } 

    }
 
}
