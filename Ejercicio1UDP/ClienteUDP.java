package Ejercicio1UDP;

import java.io.*;
import java.net.*;

public class ClienteUDP {

	public void enviarMensaje() throws SocketException {
		BufferedReader entradaDatos = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket sSocket = new DatagramSocket();
		boolean canSendMessage = true;
		while (canSendMessage) {
			System.out.println("Introduce el número de términos: ");
			int numeroTerminos = getNumeroEntradaDatos(entradaDatos);
			if (numeroTerminos <= 0) {
				canSendMessage = false;
			}
			if (canSendMessage) {
				System.out.println("Introduce el número máximo de letras de la serie: ");
				int maximoLetras = getNumeroEntradaDatos(entradaDatos);
				if (maximoLetras <= 0) {
					do {
						System.out.println("El número de letras debe ser igual o mayor que 1. Introdúzcalo de nuevo");
						maximoLetras = getNumeroEntradaDatos(entradaDatos);
					} while (maximoLetras <= 0);
				}
				Datos datos = new Datos(numeroTerminos, maximoLetras);
				try {

					// Construye la dirección del socket del receptor
					InetAddress maquina = InetAddress.getByName("localhost");
					int Puerto = 1500;
					// Crea el mensaje
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(bs);
					out.reset();
					out.writeObject(datos);
					DatagramPacket mensaje = new DatagramPacket(bs.toByteArray(), bs.size(), maquina, Puerto);

					// Envía el mensaje
					sSocket.send(mensaje);
					byte[] buffer = new byte[1024];
					//Preparo la respuesta
					DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

					//Recibo la respuesta
					sSocket.receive(peticion);
					System.out.println("Recibo la peticion");

					//Cojo los datos y lo muestro
					String respuesta = new String(peticion.getData());
					System.out.println("\tVALOR=> " + respuesta.trim());

				} catch (UnknownHostException e) {
					System.err.println("Desconocido: " + e.getMessage());
				} catch (SocketException e) {
					System.err.println("Socket: " + e.getMessage());
				} catch (IOException e) {
					System.err.println("E/S: " + e.getMessage());
				}
			}
		}
		// Cierra el socket
		sSocket.close();
	}

	private int getNumeroEntradaDatos(BufferedReader entradaDatos) {
		String entrada;
		Integer numeroEntrada = null;
		do {
			try {
				entrada = entradaDatos.readLine();
				numeroEntrada = Integer.parseInt(entrada);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (numeroEntrada == null);
		return numeroEntrada;
	}

	public static void main(String[] args) throws SocketException {
		ClienteUDP clienteUDP = new ClienteUDP();
		clienteUDP.enviarMensaje();

	}
}
