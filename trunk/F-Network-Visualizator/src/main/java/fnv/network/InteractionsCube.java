package fnv.network;

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
	
	public void addInteraction(int instantIndex, int source, int destination, int frequency) {
		if (source != destination) {
			Instant instant = interactionsCube.get(instantIndex);
			
			if (instant == null) {
				instant = new Instant();
			}
			
			instant.addInteraction(source, destination, frequency);
			
			interactionsCube.put(instantIndex, instant);
		}
	}
	
	public Integer getInteraction(int instantIndex, int source, int destination) {
		Integer frequency = 0;
		
		Instant instant = interactionsCube.get(instantIndex);
		
		if (instant != null) {
			frequency = instant.getInteraction(source, destination);
		}
		
		return frequency;
	}
	
	public String toString() {
		String string = "";
		
		for (Integer instantKey : interactionsCube.keySet()) {
			Instant instant = interactionsCube.get(instantKey);
			
			string += "instant: " + instantKey + "\n" + instant;
		}
		
		return string;
	}
	
	private class Instant {
		private HashMap<Integer, HashMap<Integer, Integer>> instant;
		
		public Instant() {
			instant = new HashMap<Integer, HashMap<Integer,Integer>>();
		}
		
		public void addInteraction(int source, int target, int frequency) {
			HashMap<Integer,Integer> rowValue = instant.get(source);
			
			if (rowValue == null) {
				rowValue = new HashMap<Integer, Integer>();
			}
			
			rowValue.put(target, frequency);
			
			instant.put(source, rowValue);
		}
		
		public Integer getInteraction(int source, int target) {
			Integer frequency = 0;
			
			HashMap<Integer,Integer> rowValue = instant.get(source);
			
			if (rowValue != null) {
				frequency = rowValue.get(target);
				
				if (frequency == null) {
					frequency = 0;
				}
			}
			
			return frequency;
		}

		public String toString() {
			String string = "";

			for (Integer rowKey : instant.keySet()) {
				HashMap<Integer, Integer> row = instant.get(rowKey);
				
				for (Integer columnKey : row.keySet()) {
					Integer frequency = row.get(columnKey);
					
					string += rowKey + " -> " + columnKey + ": " + frequency + "\n";
				}
			}
			
			return string;
		}
	}
}
