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

    private HashMap<Integer, HashMap<Integer, InstantElement>> instant;
    private String label;

    public Instant(String label) {
	instant = new HashMap<Integer, HashMap<Integer, InstantElement>>();
	this.label = label;
    }

    public void addInteraction(int source, int target, int quantity, float frequency) {
	HashMap<Integer, InstantElement> rowValue = instant.get(source);

	if (rowValue == null) {
	    rowValue = new HashMap<Integer, InstantElement>();
	}

	rowValue.put(target, new InstantElement(quantity, frequency));

	instant.put(source, rowValue);
    }

    public String getLabel() {
	return label;
    }

//    public float getInteraction(int source, int target) {
//	Integer quantity = 0;
//	Float frequency = (float) 0.0;
//
//	HashMap<Integer, InstantElement> rowValue = instant.get(source);
//
//	if (rowValue != null) {
//	    quantity =  rowValue.get(target).quantity;
//	    frequency = rowValue.get(target).frequency;
//
//	    if (frequency == null) {
//		frequency = (float) 0.0;
//	    }
//	}
//
//	return frequency.floatValue();
//    }

    public ArrayList<InteractionElement> getAllInteractions() {
	ArrayList<InteractionElement> allInteractions = new ArrayList<InteractionElement>();

	for (Integer source : instant.keySet()) {
	    HashMap<Integer, InstantElement> rowValue = instant.get(source);

	    for (Integer target : rowValue.keySet()) {
		int quantity = rowValue.get(target).quantity;
		float frequency = rowValue.get(target).frequency;

		InteractionElement ie = new InteractionElement(source, target, quantity, frequency);
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
	    HashMap<Integer, InstantElement> row = instant.get(rowKey);

	    for (Integer columnKey : row.keySet()) {
		Integer quantity = row.get(columnKey).quantity;
		Float frequency = row.get(columnKey).frequency;

		string += rowKey + " -> " + columnKey + ": (" + quantity + ") " + frequency + "\n";
	    }
	}

	return string;
    }
}
