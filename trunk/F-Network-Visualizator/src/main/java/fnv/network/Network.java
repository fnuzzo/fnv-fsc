package fnv.network;

import java.util.ArrayList;

/*
 * Struttura dati che rappresenta una rete
 */
public class Network {

    /* il nome della rete */
    private String name;
    /* l'elenco di nodi presenti nella rete */
    public NodesList nodesList;
    /* valore massimo della coordinata in ogni direzione */
    public final int maxCoordinate;
    /* indica se lo spazio dei nodi e' piatto (true) o sferico (false) */
    public final boolean flat;
    /* le interazioni presenti nella rete */
    private InteractionsCube interactionCube;

    public Network() {
	name = "";
	nodesList = new NodesList(new ArrayList<Node>());
	interactionCube = new InteractionsCube();
	maxCoordinate = 0;
	flat = true;
    }

    public Network(String name, NodesList nodesList, InteractionsCube interactionCube, boolean flat) {
	this.name = name;
	this.nodesList = nodesList;
	this.interactionCube = interactionCube;
	this.flat = flat;

	int localMaxCoordinate = 0;
	for (Node node : nodesList.toArray()) {
	    if (node.x > localMaxCoordinate) {
		localMaxCoordinate = node.x;
	    }
	    if (node.y > localMaxCoordinate) {
		localMaxCoordinate = node.y;
	    }
	    if (node.z > localMaxCoordinate) {
		localMaxCoordinate = node.z;
	    }
	}
	maxCoordinate = localMaxCoordinate;
    }

    public String getName() {
	return name;
    }

    public Node getNode(Integer nodeID) {
	return nodesList.getNode(nodeID);
    }

    public InteractionsCube getInteractionCube() {
	return interactionCube;
    }

    public int getNumberOfInstants() {
	return interactionCube.getNumberOfInstants();
    }

    @Override
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
