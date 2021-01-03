package Ejercicio1UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP {

	public String generadorSeries(int cantidadTerminos, int maximoLetras) {
		String letras = "abcdefghijklmnñopqrstuvwxyz";
		String valor = "";
		int letra = 0;
		int numero = 0;
		for (int i = 0; i < cantidadTerminos; i++) {
			valor = valor.replaceAll("\\d", "");
			valor += letras.charAt(letra);
			letra++;

			if (numero > 0) {
				valor += (numero);
			}
			if (maximoLetras <= letra && ((i + 1) < cantidadTerminos)) {
				letra = 0;
				valor = "";
				numero++;
			}
		}
		return valor;
	}

	public static void main(String[] args) {

		boolean active = true;

		System.out.println("Servidor UDP esperando...");
		try {
			// Crea el socket
			DatagramSocket sSocket = new DatagramSocket(1500);
			// Crea el espacio para los mensajes
			byte[] cadena = new byte[1000];
			DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length);
			while (active) {
				// Recibe y muestra el mensaje
				sSocket.receive(mensaje);
				String datos = new String(mensaje.getData(), 0, mensaje.getLength());
				System.out.println("\tMensaje Recibido: " + datos);
				//TODO: elena ver como convertir el mensaje a objeto
				//TODO: elena Si es 0 cerrar conexion

			}
		} catch (SocketException e) {
			System.err.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("E/S: " + e.getMessage());
		}

	}
}
