package UDP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente {
	public static void main(String[] args) throws IOException {
		DatagramSocket sSocket = new DatagramSocket();
		// Construye la dirección del socket del receptor
		InetAddress maquina = InetAddress.getByName("localhost");
		int Puerto = 1500;
		Persona persona = new Persona("Helena", 23);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bs);
		out.writeObject(persona);
		DatagramPacket mensaje = new DatagramPacket(bs.toByteArray(), bs.size(), maquina, Puerto);
		// Envía el mensaje
		System.out.println("Enviando mensaje...");
		sSocket.send(mensaje);
		out.close();

	}
}
