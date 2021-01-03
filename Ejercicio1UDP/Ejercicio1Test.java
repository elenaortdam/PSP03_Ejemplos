package Ejercicio1UDP;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ejercicio1Test {

	private final ServidorUDP servidorUDP = new ServidorUDP();

	@Test
	public void test121() {
		Assertions.assertEquals("a11",
								servidorUDP.generadorSeries(12, 1));
	}

	@Test
	public void test64() {
		Assertions.assertEquals("ab1",
								servidorUDP.generadorSeries(6, 4));
	}

	@Test
	public void test710() {
		Assertions.assertEquals("abcdefg",
								servidorUDP.generadorSeries(7, 10));
	}

	@Test
	public void test157() {
		Assertions.assertEquals("a2",
								servidorUDP.generadorSeries(15, 7));
	}

	@Test
	public void test155() {
		Assertions.assertEquals("abcde2",
								servidorUDP.generadorSeries(15, 5));
	}
}
