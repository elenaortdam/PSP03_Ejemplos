package Ejercicio2TCP;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClienteTCP {

	private final String HOST = "localhost";
	private final int PUERTO = 2000;

	public void enviarMensaje() throws IOException {

		System.out.println("PROGRAMA CLIENTE INICIADO");
		boolean programaTerminado = false;
		while (!programaTerminado) {
			boolean canSendMessage = true;
			String numeroCliente = "";
			Socket cliente = null;
			cliente = new Socket(HOST, PUERTO);
			InputStream aux = cliente.getInputStream();
			try {
				DataInputStream respuestaServidor = new DataInputStream(aux);
				numeroCliente = respuestaServidor.readUTF();
				System.out.println("Soy el cliente: " + numeroCliente);
				System.out.println("==========================");
			} catch (SocketException e) {
				programaTerminado = true;
				canSendMessage = false;
			}

			while (canSendMessage) {
				System.out.println("Introduce el número de términos: ");
				BufferedReader entradaDatos = new BufferedReader(new InputStreamReader(System.in));
				int numeroTerminos = getNumeroEntradaDatos(entradaDatos);
				if (numeroTerminos <= 0) {
					System.out.println("Fin de envíos del cliente " + numeroCliente);
					Datos datos = new Datos(numeroTerminos, 0);
					ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
					//Se envía el objeto
					out.writeObject(datos);
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
					ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
					//Se envía el objeto
					out.writeObject(datos);
					DataInputStream flujo_entrada = new DataInputStream(aux);
//					if(flujo_entrada.readUTF().equalsIgnoreCase("Finished")){
//						programaTerminado = true;
//						canSendMessage = false;
//					}else {
					System.out.println("\tVALOR=> " + flujo_entrada.readUTF());
//					}
				}
			}
			cliente.close();
		}
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ClienteTCP clienteTCP = new ClienteTCP();
		clienteTCP.enviarMensaje();
	}
}
