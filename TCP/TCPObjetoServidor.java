package TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPObjetoServidor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		int puerto = 2000;
		System.out.println("Esperando al cliente...");
		ServerSocket server = new ServerSocket(puerto);
		Socket cliente = server.accept();
		ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());

		Persona persona = new Persona("Helena", 23);
		out.reset();
		out.writeObject(persona);

		System.out.println("Envio" + persona.getNombre() + " " + persona.getEdad());
		ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
		Persona dato = (Persona) in.readObject();
		System.out.println("Recibo" + dato.getNombre() + " " + dato.getEdad());
		out.close();
		in.close();
		cliente.close();
		server.close();
	}
}
