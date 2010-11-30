/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fnv.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author enrico
 */
public class Instant implements Serializable {

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
	Float frequency = (float) 0.0;

	HashMap<Integer, Float> rowValue = instant.get(source);

	if (rowValue != null) {
	    frequency = rowValue.get(target);

	    if (frequency == null) {
		frequency = (float) 0.0;
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
