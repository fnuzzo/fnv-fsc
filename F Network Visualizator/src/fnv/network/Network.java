package fnv.network;

/*
 * Struttura dati che rappresenta una rete
 */
public class Network {

	/* il nome della rete */
	private String name;
	/* l'elenco di nodi presenti nella rete */
	private NodesList nodesList;
	/* le interazioni presenti nella rete */
	private InteractionsCube interactionCube;
	
	public Network(String name, NodesList nodesList, InteractionsCube interactionCube) {
		this.name = name;
		this.nodesList = nodesList;
		this.interactionCube = interactionCube;
	}

	public String getName() {
		return name;
	}

	public Node getNode(Integer nodeID) {
		return nodesList.getNode(nodeID);
	}
	
	public Integer getFrequency(Integer instant, Integer source, Integer target) {
		return interactionCube.getInteraction(instant, source, target);
	}

	public String toString() {
		String string = "";
		
		string += "name: " + name + "\n";
		
		string += "nodesList:\n";
		string += nodesList;
		
		string += "interactions:\n";
		string += interactionCube;
		
		return string;
	}
}
