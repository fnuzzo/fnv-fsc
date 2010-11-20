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

    public InteractionsCube() {
	interactionsCube = new HashMap<Integer, Instant>();
    }

    public void addInteraction(int instantIndex, int source, int target, double frequency) {
	if (source != target) {
	    Instant instant = interactionsCube.get(instantIndex);

	    if (instant == null) {
		instant = new Instant();
	    }

	    instant.addInteraction(source, target, frequency);

	    interactionsCube.put(instantIndex, instant);
	}
    }

    public double getInteraction(int instantIndex, int source, int target) {
	double frequency = 0;

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

	for (Integer instantKey : interactionsCube.keySet()) {
	    Instant instant = interactionsCube.get(instantKey);

	    string += "instant: " + instantKey + "\n" + instant;
	}

	return string;
    }

    private class Instant {

	private HashMap<Integer, HashMap<Integer, Double>> instant;

	public Instant() {
	    instant = new HashMap<Integer, HashMap<Integer, Double>>();
	}

	public void addInteraction(int source, int target, double frequency) {
	    HashMap<Integer, Double> rowValue = instant.get(source);

	    if (rowValue == null) {
		rowValue = new HashMap<Integer, Double>();
	    }

	    rowValue.put(target, frequency);

	    instant.put(source, rowValue);
	}

	public double getInteraction(int source, int target) {
	    Double frequency = 0.0;

	    HashMap<Integer, Double> rowValue = instant.get(source);

	    if (rowValue != null) {
		frequency = rowValue.get(target);

		if (frequency == null) {
		    frequency = 0.0;
		}
	    }

	    return frequency.doubleValue();
	}

	public ArrayList<InteractionElement> getAllInteractions() {
	    ArrayList<InteractionElement> allInteractions = new ArrayList<InteractionElement>();

	    for (Integer source : instant.keySet()) {
		HashMap<Integer, Double> rowValue = instant.get(source);

		for (Integer target : rowValue.keySet()) {
		    double frequency = rowValue.get(target);

		    InteractionElement ie = new InteractionElement(source, target, frequency);
		    allInteractions.add(ie);
		}
	    }

	    return allInteractions;
	}

	@Override
	public String toString() {
	    String string = "";

	    for (Integer rowKey : instant.keySet()) {
		HashMap<Integer, Double> row = instant.get(rowKey);

		for (Integer columnKey : row.keySet()) {
		    Double frequency = row.get(columnKey);

		    string += rowKey + " -> " + columnKey + ": " + frequency + "\n";
		}
	    }

	    return string;
	}
    }
}
