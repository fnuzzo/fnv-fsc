package fnv.network;

import java.io.Serializable;

/*
 * Struttura dati che rappresenta un nodo della rete
 */
public class Node implements Serializable {
    /* identificativo del nodo usato dal sistema */

    public Integer id;
    /* nome visualizzato del nodo */
    public String label;
    /* coordinate 3D del nodo */
    public int x;
    public int y;
    public int z;

    public Node(Integer id, String label, int x, int y, int z) {
	this.id = id;
	this.label = label;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    @Override
    public String toString() {
	String string = "";

	string += "id: " + id + ", ";
	string += "label " + label + ", ";
	string += "x: " + x + ", ";
	string += "y: " + y + ", ";
	string += "z: " + z;

	return string;
    }
}
