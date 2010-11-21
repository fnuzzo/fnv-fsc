package fnv.gui.pro;

public class PNode extends Fnv {

    private float r;
    private int color;
    private String name;
    int id;

    PNode() {
    }

    PNode(float r, int c, String name, int id) {
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
