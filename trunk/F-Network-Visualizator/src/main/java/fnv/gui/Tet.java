package fnv.gui;

import javax.swing.JPanel;

import fnv.gui.pro.Space;
import fnv.network.Network;

public class Tet extends JPanel {
    private Space space;

    public Tet() {
	setSize(800, 600);

	//Fnv fnv = new Fnv();
	//fnv.init();

	//add(fnv);

	/*MyCubesWithinCube mcwc = new MyCubesWithinCube();
	mcwc.init();
	add(mcwc);*/

	space = new Space();
	space.init();
	add(space);
    }

    public void setNetwork(Network network) {
	space.setNetwork(network);
    }
}
