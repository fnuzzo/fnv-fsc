package fnv.gui.pro;

public class Node extends Fnv {

    private float r;
    private int color;
    private String name;
    int id;

    Node() {
    }

    Node(float r, int c, String name, int id) {
	this.r = r;
	this.color = c;
	this.name = name;
	this.id = id;
    }

    public void create() {
	fill(color);
	sphere(r);
    }
}
