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
//    /* valore massimo della coordinata x */
//    public final int maxX;
//    /* valore massimo della coordinata y */
//    public final int maxY;
//    /* valore massimo della coordinata z */
//    public final int maxZ;
    /* valore massimo della coordinata in ogni direzione */
    public final int maxCoordinate;
    /* le interazioni presenti nella rete */
    private InteractionsCube interactionCube;

    public Network() {
	name = "";
	nodesList = new NodesList(new ArrayList<Node>());
	interactionCube = new InteractionsCube();
//	maxX = maxY = maxZ = 0;
	maxCoordinate = 0;
    }

    public Network(String name, NodesList nodesList, InteractionsCube interactionCube) {
	this.name = name;
	this.nodesList = nodesList;
	this.interactionCube = interactionCube;

//	int localMaxX = 0;
//	int localMaxY = 0;
//	int localMaxZ = 0;
//	for (Node node : nodesList.toArray()) {
//	    if (node.x > localMaxX) {
//		localMaxX = node.x;
//	    }
//	    if (node.y > localMaxY) {
//		localMaxY = node.y;
//	    }
//	    if (node.z > localMaxZ) {
//		localMaxZ = node.z;
//	    }
//	}
//	this.maxX = localMaxX;
//	this.maxY = localMaxY;
//	this.maxZ = localMaxZ;

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

//    public double getFrequency(Integer instant, Integer source, Integer target) {
//	return interactionCube.getInteraction(instant, source, target);
//    }

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
