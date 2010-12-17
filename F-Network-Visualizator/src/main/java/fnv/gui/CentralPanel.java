/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.gui;

import fnv.gui.pro.Space;
import fnv.utils.Constants;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author enrico
 */
public class CentralPanel extends JPanel {
    private Space space;

    public CentralPanel(InterfaceFrame interfaceFrame) {
	setPreferredSize(new Dimension(
		interfaceFrame.getScreenWidth(),
		interfaceFrame.getScreenHeight() - Constants.FOOTER_HEIGHT));

	space = new Space(interfaceFrame,getPreferredSize().width, getPreferredSize().height);
	space.init();

	add(space);
    }

    public Space getSpace() {
	return space;
    }
}
