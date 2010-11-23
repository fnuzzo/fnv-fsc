package fnv.network;

import java.util.ArrayList;
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
public class InteractionsCube {

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

    public InteractionElement[] getAllInteractions(int instantIndex) {
	Instant instant = interactionsCube.get(instantIndex);
	if (instant == null) {
	    return new InteractionElement[0];
	}

	ArrayList<InteractionElement> allInteractions = instant.getAllInteractions();

	return allInteractions.toArray(new InteractionElement[0]);
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

    private class Instant {

	private HashMap<Integer, HashMap<Integer, Float>> instant;
	private String label;

	public Instant(String label) {
	    instant = new HashMap<Integer, HashMap<Integer, Float>>();
	    this.label = label;
	}

	public void addInteraction(int source, int target, float frequency) {
	    HashMap<Integer, Float> rowValue = instant.get(source);

	    if (rowValue == null) {
		rowValue = new HashMap<Integer, Float>();
	    }

	    rowValue.put(target, frequency);

	    instant.put(source, rowValue);
	}

	public String getLabel() {
	    return label;
	}

	public float getInteraction(int source, int target) {
	    Float frequency = (float)0.0;

	    HashMap<Integer, Float> rowValue = instant.get(source);

	    if (rowValue != null) {
		frequency = rowValue.get(target);

		if (frequency == null) {
		    frequency = (float)0.0;
		}
	    }

	    return frequency.floatValue();
	}

	public ArrayList<InteractionElement> getAllInteractions() {
	    ArrayList<InteractionElement> allInteractions = new ArrayList<InteractionElement>();

	    for (Integer source : instant.keySet()) {
		HashMap<Integer, Float> rowValue = instant.get(source);

		for (Integer target : rowValue.keySet()) {
		    float frequency = rowValue.get(target);

		    InteractionElement ie = new InteractionElement(source, target, frequency);
		    allInteractions.add(ie);
		}
	    }

	    return allInteractions;
	}

	@Override
	public String toString() {
	    String string = "";

	    string += "label: " + label + "\n";

	    for (Integer rowKey : instant.keySet()) {
		HashMap<Integer, Float> row = instant.get(rowKey);

		for (Integer columnKey : row.keySet()) {
		    Float frequency = row.get(columnKey);

		    string += rowKey + " -> " + columnKey + ": " + frequency + "\n";
		}
	    }

	    return string;
	}
    }
}
