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
		Interface anInterface = new Interface();
		anInterface.setVisible(true);
	    }
	});

	//new fnv.gui.fnv.gui.fnv.gui.fnv.gui.fnv.gui.Interface.fnv.gui.fnv.gui.fnv.gui.fnv.gui.Interface();
    }
}
