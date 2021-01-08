package Ejercicio2TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

	private final int PUERTO = 2000;
	private final int MINIMO_CLIENTES = 1;
	private final int MAXIMO_CLIENTES = 5;

	public void recibirMensajes() {
		try {
			BufferedReader entradaDatos = new BufferedReader(new InputStreamReader(System.in));
			ServerSocket skServidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado...");
			int numeroClientes = comprobarNumero(entradaDatos);
			boolean cambiarCliente = false;
			for (int i = 0; i < numeroClientes; i++) {
				int numeroCliente = i + 1;
				System.out.println("Esperando al cliente: " + numeroCliente);
				Socket sCliente = skServidor.accept();
				OutputStream aux = sCliente.getOutputStream();
				DataOutputStream salida = new DataOutputStream(aux);
				salida.writeUTF(String.valueOf(numeroCliente));
				while (!cambiarCliente) {

					ObjectInputStream in = new ObjectInputStream(sCliente.getInputStream());
					Datos dato = (Datos) in.readObject();
					if (dato.getNumeroTerminos() == 0) {
						System.out.printf("EL CLIENTE %s HA FINALIZADO\n", numeroCliente);
						cambiarCliente = true;
//						in.close();
					} else {
						System.out.printf("\tCliente %s solicita el término %s para una serie de %s\n",
										  numeroCliente, dato.getNumeroTerminos(),
										  dato.getMaximoLetras());
						String respuesta = generadorSeries(dato.getNumeroTerminos(), dato.getMaximoLetras());
						System.out.println("\tServidor responde con " + respuesta);
						salida.writeUTF(respuesta);
					}
				}
//				if(i + 1 == numeroClientes){
//					salida.writeUTF("Finish");
//				}
				cambiarCliente = false;
			}
			System.out.printf("NUMERO DE CONEXIONES %s, NO  SE ADMITEN MÁS CONEXIONES\n", numeroClientes);
			System.out.println("SERVIDOR FINALIZADO...");

			skServidor.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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

	private int comprobarNumero(BufferedReader entradaDatos) {
		String entrada;
		Integer numeroEntrada = null;
		do {
			System.out.print("Introduce el número de clientes: ");
			try {
				entrada = entradaDatos.readLine();
				numeroEntrada = Integer.parseInt(entrada);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (numeroEntrada == null
					|| numeroEntrada < MINIMO_CLIENTES || numeroEntrada > MAXIMO_CLIENTES) {
				System.out.println("Introduce un número de clientes del 1 al 5");
			}
		} while (numeroEntrada == null
				|| numeroEntrada < MINIMO_CLIENTES || numeroEntrada > MAXIMO_CLIENTES);
		return numeroEntrada;
	}

	public static void main(String[] args) {
		ServidorTCP servidor = new ServidorTCP();
		servidor.recibirMensajes();
	}
}
