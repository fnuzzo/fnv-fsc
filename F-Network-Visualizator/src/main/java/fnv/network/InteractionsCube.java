package fnv.network;

import java.io.Serializable;
import java.util.HashMap;

/* struttura dati che rappresenta tutte le interazioni tra i nodi della rete.
 * Idelamente e' un cubo ma utilizza delle tabelle hash per non sprecare spazio
 * quando non ci sono interazioni tra nodi.
 * Sull'asse z del cubo ci sono tutti gli istanti delle interazioni. Ogni istante
 * contiene una tabella NxN, dove N e' il numero di nodi
 * La tabella che rappresenta un istante memorizza in realta' solo le interazioni
 * realmente avvenute, se una data coppia di nodi non ha avuto interazioni allora
 * la tabella non memorizza niente e viene ritornato il valore 0 per quella data
 * intarezione.
 * NB la tabella non e' triangolare, le interazioni hanno una direzione. 
 */
public class InteractionsCube implements Serializable {

    private HashMap<Integer, Instant> interactionsCube;
    private float maxFrequency = Float.MIN_VALUE;
    private float minFrequency = Float.MAX_VALUE;

    public InteractionsCube() {
	interactionsCube = new HashMap<Integer, Instant>();
    }

    public float getMaxFrequency() {
	return maxFrequency;
    }

    public float getMinFrequency() {
	return minFrequency;
    }

    public void addInteraction(int instantIndex, int source, int target, float frequency, String label) {
	if (source != target) {
	    Instant instant = interactionsCube.get(instantIndex);

	    if (instant == null) {
		instant = new Instant(label);
	    }

	    instant.addInteraction(source, target, frequency);

	    interactionsCube.put(instantIndex, instant);

	    if (frequency > maxFrequency) {
		maxFrequency = frequency;
	    }
	    if (frequency < minFrequency) {
		minFrequency = frequency;
	    }
	}
    }

    public float getInteraction(int instantIndex, int source, int target) {
	float frequency = 0;

	Instant instant = interactionsCube.get(instantIndex);

	if (instant != null) {
	    frequency = instant.getInteraction(source, target);
	}

	return frequency;
    }

    public Instant getInstant(int instantIndex) {
	return interactionsCube.get(instantIndex);
    }

    public int getNumberOfInstants() {
	return interactionsCube.keySet().size();
    }

    @Override
    public String toString() {
	String string = "";

	string += "minFrequency: " + minFrequency + "\n";
	string += "maxFrequency: " + maxFrequency + "\n";

	for (Integer instantKey : interactionsCube.keySet()) {
	    Instant instant = interactionsCube.get(instantKey);

	    string += "instant: " + instantKey + "\n" + instant;
	}

	return string;
    }
    
}
