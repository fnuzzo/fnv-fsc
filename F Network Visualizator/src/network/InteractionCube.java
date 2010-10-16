package network;

import java.util.HashMap;

public class InteractionCube {
	private HashMap<Integer, Instant> cube;

	public InteractionCube() {
		cube = new HashMap<Integer, Instant>();
	}
	
	public void addInteraction(int instantIndex, int source, int destination, int frequency) {
		if (source != destination) {
			Instant instant = cube.get(instantIndex);
			
			if (instant == null) {
				instant = new Instant();
			}
			
			instant.addInteraction(source, destination, frequency);
			
			cube.put(instantIndex, instant);
		}
	}
	
	public Integer getInteraction(int instantIndex, int source, int destination) {
		Integer frequency = 0;
		
		Instant instant = cube.get(instantIndex);
		
		if (instant != null) {
			frequency = instant.getInteraction(source, destination);
		}
		
		return frequency;
	}
	
	private class Instant {
		private HashMap<Integer, HashMap<Integer, Integer>> instant;
		
		public Instant() {
			instant = new HashMap<Integer, HashMap<Integer,Integer>>();
		}
		
		public void addInteraction(int source, int destination, int frequency) {
			HashMap<Integer,Integer> rowValue = instant.get(source);
			
			if (rowValue == null) {
				rowValue = new HashMap<Integer, Integer>();
			}
			
			rowValue.put(destination, frequency);
			
			instant.put(source, rowValue);
		}
		
		public Integer getInteraction(int source, int destination) {
			Integer frequency = 0;
			
			HashMap<Integer,Integer> rowValue = instant.get(source);
			
			if (rowValue != null) {
				frequency = rowValue.get(destination);
				
				if (frequency == null) {
					frequency = 0;
				}
			}
			
			return frequency;
		}
	}
}
