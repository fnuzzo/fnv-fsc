package fnv.gui;

import javax.swing.JPanel;

import fnv.gui.pro.MyCubesWithinCube;
import fnv.gui.pro.Space;

public class Tet extends JPanel{

	public Tet(){
		setSize(800, 600);

		//Fnv fnv = new Fnv();
		//fnv.init();

		//add(fnv);

		/*MyCubesWithinCube mcwc = new MyCubesWithinCube();
		mcwc.init();
		add(mcwc);*/

        Space space = new Space();
        space.init();
        add(space);

	}
}