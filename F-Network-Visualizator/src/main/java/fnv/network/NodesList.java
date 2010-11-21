package fnv.network;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* struttura dati che rappresenta la lista di nodi presenti nel sistema */
public class NodesList {

    private HashMap<Integer, Node> nodes;

//    public NodesList(Node[] nodes) {
//	this.nodes = new HashMap<Integer, Node>();
//	for (Node node : nodes) {
//	    this.nodes.put(node.getId(), node);
//	}
//    }

    public NodesList(List<Node> nodes) {
	this.nodes = new HashMap<Integer, Node>();
	for (Node node : nodes) {
	    this.nodes.put(node.id, node);
	}
    }

    public Set<Integer> getNodeIDs() {
	return nodes.keySet();
    }

    public Node getNode(Integer nodeID) {
	return nodes.get(nodeID);
    }

    public Node[] toArray() {
	return nodes.values().toArray(new Node[0]);
    }

    @Override
    public String toString() {
	String string = "";

	for (Integer nodeID : nodes.keySet()) {
	    Node node = nodes.get(nodeID);

	    string += node + "\n";
	}

	return string;
    }
}
