package fnv.network;

/*
 * Struttura dati che rappresenta un nodo della rete
 */
public class Node {
    /* identificativo del nodo usato dal sistema */

    public final Integer id;
    /* nome visualizzato del nodo */
    public final String label;
    /* coordinate 3D del nodo */
    public final int x;
    public final int y;
    public final int z;

    public Node(Integer id, String label, int x, int y, int z) {
        this.id = id;
        this.label = label;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //	public Integer getId() {
//		return id;
//	}
//
//	public String getLabel() {
//		return label;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public int getZ() {
//		return z;
//	}

    @Override
    public String toString() {
    String string="";

    string+="id: "+id+", ";
    string+="label "+label+", ";
    string+="x: "+x+", ";
    string+="y: "+y+", ";
    string+="z: "+z;

    return string;
    }
}
