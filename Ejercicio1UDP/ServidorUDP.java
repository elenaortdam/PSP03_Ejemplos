package Ejercicio1UDP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorUDP {

	private void recibirMensajes() {
		boolean active = true;

		try {
			// Crea el socket
			DatagramSocket sSocket = new DatagramSocket(1500);
			System.out.println("Servidor UDP esperando...");

			byte[] recibido = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(recibido, recibido.length);
			while (active) {
				sSocket.receive(datagramPacket);

				ByteArrayInputStream bais = new ByteArrayInputStream(recibido);
				ObjectInputStream in = new ObjectInputStream(bais);
				Datos dato = (Datos) in.readObject();
				System.out.println("Consultando TERMINO: " + dato.getNumeroTerminos()
										   + ", de " + dato.getMaximoLetras() + " letras");
				if (dato.getNumeroTerminos() <= 0) {
					active = false;
				}
				int puerto = datagramPacket.getPort();
				InetAddress maquina = datagramPacket.getAddress();
				String respuesta = generadorSeries(dato.getNumeroTerminos(),
												   dato.getMaximoLetras());
				byte[] bytes = respuesta.getBytes();

				//Envio la información
				System.out.println("Envio la informacion del cliente");
				DatagramPacket mensaje = new DatagramPacket(bytes, bytes.length, maquina, puerto);
				// Envía la respuesta
				sSocket.send(mensaje);
				in.close();
			}
			sSocket.close();
		} catch (SocketException e) {
			System.err.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("E/S: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

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

		ServidorUDP servidorUDP = new ServidorUDP();
		servidorUDP.recibirMensajes();
	}
}
