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

//    private void writeObject(ObjectOutputStream out) throws IOException {
//	out.writeInt(id);
//	out.writeUTF(label);
//	out.writeInt(x);
//	out.writeInt(y);
//	out.writeInt(z);
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//	id = in.readInt();
//	label = in.readUTF();
//	x = in.readInt();
//	y = in.readInt();
//	z = in.readInt();
//    }

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
