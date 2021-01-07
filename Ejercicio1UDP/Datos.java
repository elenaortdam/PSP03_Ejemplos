package Ejercicio1UDP;

import java.io.Serializable;

public class Datos implements Serializable {

	private int numeroTerminos;
	private int maximoLetras;

	public Datos(int numeroTerminos, int maximoLetras) {
		this.numeroTerminos = numeroTerminos;
		this.maximoLetras = maximoLetras;
	}

	public int getNumeroTerminos() {
		return numeroTerminos;
	}

	public void setNumeroTerminos(int numeroTerminos) {
		this.numeroTerminos = numeroTerminos;
	}

	public int getMaximoLetras() {
		return maximoLetras;
	}

	public void setMaximoLetras(int maximoLetras) {
		this.maximoLetras = maximoLetras;
	}
}
