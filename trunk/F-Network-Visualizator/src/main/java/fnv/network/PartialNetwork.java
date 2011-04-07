/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fnv.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author enrico
 */
public class PartialNetwork implements Serializable {

    private String networkName;
    private boolean flat;
    private String imageFilename;
    private int nodeID;
    private HashMap<String, Node> nodes;
    private InteractionsCube interactionsCube;

    public PartialNetwork() {
	networkName = "";
	flat = true;
        imageFilename = "";
	nodeID = 0;
	nodes = new HashMap<String, Node>();
	interactionsCube = new InteractionsCube();
    }

    public InteractionsCube getInteractionsCube() {
	return interactionsCube;
    }

    public void addInteraction(int instantIndex, int source, int target, int quantity, float frequency, String label) {
	interactionsCube.addInteraction(instantIndex, source, target, quantity, frequency, label);
    }

    public String getNetworkName() {
	return networkName;
    }

    public void setNetworkName(String networkName) {
	this.networkName = networkName;
    }

    public boolean isFlat() {
	return flat;
    }

    public void setFlat(boolean flat) {
	this.flat = flat;
    }
    
    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
    
    public String getImageFilename() {
        return imageFilename;
    }

    public int getNodeID() {
	return nodeID++;
    }

    public HashMap<String, Node> getNodes() {
	return nodes;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
	out.defaultWriteObject();
	out.writeUTF(networkName);
	out.writeBoolean(flat);
	out.writeInt(nodeID);
	out.writeObject(nodes.values().toArray(new Node[nodes.size()]));
	out.writeObject(interactionsCube);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	in.defaultReadObject();
	networkName = in.readUTF();
	flat = in.readBoolean();
	nodeID = in.readInt();
	Node[] nodesArray = (Node[]) in.readObject();
	for (Node node : nodesArray) {
	    nodes.put(node.label, node);
	}
	interactionsCube = (InteractionsCube) in.readObject();
    }
}
