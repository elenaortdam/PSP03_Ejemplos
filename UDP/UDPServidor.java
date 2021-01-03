package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServidor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DatagramSocket cliente = new DatagramSocket(1500);
		System.out.println("Esperando la recepción de paquetes...");
		byte[] recibido = new byte[1024];
		DatagramPacket datagramPacket = new DatagramPacket(recibido, recibido.length);
		cliente.receive(datagramPacket);

		ByteArrayInputStream bais = new ByteArrayInputStream(recibido);
		ObjectInputStream in = new ObjectInputStream(bais);
		Persona persona = (Persona) in.readObject();
		System.out.println("Objeto recibido: " + persona.getNombre() + " " + persona.getEdad());
		in.close();
	}
}
