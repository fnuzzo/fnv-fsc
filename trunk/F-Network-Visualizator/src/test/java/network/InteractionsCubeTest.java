package network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fnv.network.InteractionsCube;

public class InteractionsCubeTest {
	private InteractionsCube interactionCube;

	@Before
	public void setup() {
		interactionCube = new InteractionsCube();
	}

	@Test
	public void InteractionCube() {
		/* valori per riempire la struttura dati */
		double v001 = 0.0;
		double v001b = 1.0;
		double v023 = 2.0;
		double v101 = 3.0;
		double v110 = 4.0;
		double v223 = 5.0;
		double v232 = 6.0;
		double v303 = 7.0;
		double v422 = 8.0;
		double v421 = 9.0;
		double v531 = 10.0;
		/* valori presi dalla struttura dati */
		double rv001;
		double rv023;
		double rv101;
		double rv110;
		double rv223;
		double rv232;
		double rv303;
		double rv422;
		double rv421;
		double rv531;
		
		interactionCube.addInteraction(0, 0, 1, v001);
		interactionCube.addInteraction(0, 0, 1, v001b);
		interactionCube.addInteraction(0, 2, 3, v023);
		interactionCube.addInteraction(1, 0, 1, v101);
		interactionCube.addInteraction(1, 1, 0, v110);
		interactionCube.addInteraction(2, 2, 3, v223);
		interactionCube.addInteraction(2, 3, 2, v232);
		interactionCube.addInteraction(3, 0, 3, v303);
		interactionCube.addInteraction(4, 2, 2, v422);
		interactionCube.addInteraction(4, 2, 1, v421);
		interactionCube.addInteraction(5, 3, 1, v531);
		
		rv001 = interactionCube.getInteraction(0, 0, 1);
		rv023 = interactionCube.getInteraction(0, 2, 3);
		rv101 = interactionCube.getInteraction(1, 0, 1);
		rv110 = interactionCube.getInteraction(1, 1, 0);
		rv223 = interactionCube.getInteraction(2, 2, 3);
		rv232 = interactionCube.getInteraction(2, 3, 2);
		rv303 = interactionCube.getInteraction(3, 0, 3);
		rv422 = interactionCube.getInteraction(4, 2, 2);
		rv421 = interactionCube.getInteraction(4, 2, 1);
		rv531 = interactionCube.getInteraction(5, 3, 1);
		
		assertEquals(String.format("expected: %f, actual: %f", v001, rv001), v001b, rv001);
		assertEquals(String.format("expected: %f, actual: %f", v023, rv023), v023, rv023);
		assertEquals(String.format("expected: %f, actual: %f", v101, rv101), v101, rv101);
		assertEquals(String.format("expected: %f, actual: %f", v110, rv110), v110, rv110);
		assertEquals(String.format("expected: %f, actual: %f", v223, rv223), v223, rv223);
		assertEquals(String.format("expected: %f, actual: %f", v232, rv232), v232, rv232);
		assertEquals(String.format("expected: %f, actual: %f", v303, rv303), v303, rv303);
		/* sorgente e destinazione sono la stessa, percio' non aggiunge niente, il valore deve sempre essere 0 */
		assertEquals(String.format("expected: %f, actual: %f", 0.0, rv422), 0.0, rv422);
		assertEquals(String.format("expected: %f, actual: %f", v421, rv421), v421, rv421);
		assertEquals(String.format("expected: %f, actual: %f", v531, rv531), v531, rv531);
	}
}
