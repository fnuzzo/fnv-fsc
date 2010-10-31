package fnv.gui;

import javax.swing.SwingUtilities;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Interface();
			}
		});

		//new Interface();
	}
}
