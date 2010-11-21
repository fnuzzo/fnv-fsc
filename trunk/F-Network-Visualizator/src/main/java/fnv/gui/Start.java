package fnv.gui;

import javax.swing.SwingUtilities;

public class Start {

    /**
     * @param args
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		Interface2 anInterface = new Interface2();
		anInterface.setVisible(true);
	    }
	});

	//new fnv.gui.fnv.gui.fnv.gui.fnv.gui.fnv.gui.Interface.fnv.gui.fnv.gui.fnv.gui.fnv.gui.Interface();
    }
}
