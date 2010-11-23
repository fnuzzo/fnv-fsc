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
		float v001 = (float)0.0;
		float v001b = (float)1.0;
		float v023 = (float)2.0;
		float v101 = (float)3.0;
		float v110 = (float)4.0;
		float v223 = (float)5.0;
		float v232 = (float)6.0;
		float v303 = (float)7.0;
		float v422 = (float)8.0;
		float v421 = (float)9.0;
		float v531 = (float)10.0;
		/* valori presi dalla struttura dati */
		float rv001;
		float rv023;
		float rv101;
		float rv110;
		float rv223;
		float rv232;
		float rv303;
		float rv422;
		float rv421;
		float rv531;
		
		interactionCube.addInteraction(0, 0, 1, v001, "0");
		interactionCube.addInteraction(0, 0, 1, v001b, "0");
		interactionCube.addInteraction(0, 2, 3, v023, "0");
		interactionCube.addInteraction(1, 0, 1, v101, "1");
		interactionCube.addInteraction(1, 1, 0, v110, "1");
		interactionCube.addInteraction(2, 2, 3, v223, "2");
		interactionCube.addInteraction(2, 3, 2, v232, "2");
		interactionCube.addInteraction(3, 0, 3, v303, "3");
		interactionCube.addInteraction(4, 2, 2, v422, "4");
		interactionCube.addInteraction(4, 2, 1, v421, "4");
		interactionCube.addInteraction(5, 3, 1, v531, "5");
		
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
