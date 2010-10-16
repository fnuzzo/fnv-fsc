package network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InteractionCubeTest {
	private InteractionCube interactionCube;

	@Before
	public void setup() {
		interactionCube = new InteractionCube();
	}

	@Test
	public void InteractionCube() {
		/* valori per riempire la struttura dati */
		Integer v001 = 0;
		Integer v001b = 1;
		Integer v023 = 2;
		Integer v101 = 3;
		Integer v110 = 4;
		Integer v223 = 5;
		Integer v232 = 6;
		Integer v303 = 7;
		Integer v422 = 8;
		Integer v421 = 9;
		Integer v531 = 10;
		/* valori presi dalla struttura dati */
		Integer rv001;
		Integer rv023;
		Integer rv101;
		Integer rv110;
		Integer rv223;
		Integer rv232;
		Integer rv303;
		Integer rv422;
		Integer rv421;
		Integer rv531;
		
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
		
		assertEquals(String.format("expected: %d, actual: %d", v001, rv001), v001b, rv001);
		assertEquals(String.format("expected: %d, actual: %d", v023, rv023), v023, rv023);
		assertEquals(String.format("expected: %d, actual: %d", v101, rv101), v101, rv101);
		assertEquals(String.format("expected: %d, actual: %d", v110, rv110), v110, rv110);
		assertEquals(String.format("expected: %d, actual: %d", v223, rv223), v223, rv223);
		assertEquals(String.format("expected: %d, actual: %d", v232, rv232), v232, rv232);
		assertEquals(String.format("expected: %d, actual: %d", v303, rv303), v303, rv303);
		/* sorgente e destinazione sono la stessa, percio' non aggiunge niente, il valore deve sempre essere 0 */
		assertEquals(String.format("expected: %d, actual: %d", new Integer(0), rv422), new Integer(0), rv422);
		assertEquals(String.format("expected: %d, actual: %d", v421, rv421), v421, rv421);
		assertEquals(String.format("expected: %d, actual: %d", v531, rv531), v531, rv531);
	}
}
