package TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPObjetoCliente {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String host = "localhost";
		int puerto = 2000;
		System.out.println("PROGRAMA CLIENTE INICIADO");
		Socket cliente = new Socket(host, puerto);

		ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
		Persona dato = (Persona) perEnt.readObject();
		System.out.println("Recibido: " + dato.getNombre() + " " + dato.getEdad());

//		Modifico los datos de la persona
		dato.setNombre("Estrella");
		dato.setEdad(22);

		ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
		//Se envía el objeto
		out.writeObject(dato);
		System.out.println("Envío: " + dato.getNombre() + " " + dato.getEdad());
		perEnt.close();
		out.close();
		cliente.close();
	}
}
