/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import fnv.gui.pro.Space;
import fnv.network.Network;
import fnv.utils.Constants;

/**
 *
 * @author enrico
 */
public class InterfaceFrame extends JFrame {

    private int screenWidth;
    private int screenHeight;
    private AppMenuBar appMenuBar;
    private NetworkCreationPanel networkCreationPanel;
    private FooterPanel footerPanel;
    private Space space;
    private Container contentPane;

    public InterfaceFrame() {

	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice defaultGraphicsDevice = ge.getDefaultScreenDevice();
	screenWidth = defaultGraphicsDevice.getDisplayMode().getWidth();
	screenHeight = defaultGraphicsDevice.getDisplayMode().getHeight();
	//defaultGraphicsDevice.setFullScreenWindow(this);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("3D Network Visualizator");
	setResizable(true);
	setVisible(true);
	setPreferredSize(new Dimension(800, 600));

	appMenuBar = new AppMenuBar(this);
	networkCreationPanel = new NetworkCreationPanel();
	contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());
	contentPane.add(appMenuBar, BorderLayout.NORTH);
	contentPane.add(networkCreationPanel, BorderLayout.CENTER);
	pack();
    }

    public int getScreenHeight() {
	return screenHeight;
    }

    public int getScreenWidth() {
	return screenWidth;
    }

    public FooterPanel getFooterPanel() {
	return footerPanel;
    }

    public AppMenuBar getAppMenuBar() {
	return appMenuBar;
    }

    public Space getSpace() {
	return space;
    }
    public void switchToNetworkCreationPanel(){
    
    	setPreferredSize(new Dimension(800, 600));
   	 	contentPane.removeAll();
   	 	networkCreationPanel = new NetworkCreationPanel();
   	 	
   	 	footerPanel = new FooterPanel(this);
   	 	contentPane.add(appMenuBar, BorderLayout.NORTH);
   	 	contentPane.add(footerPanel, BorderLayout.SOUTH);
   	 	contentPane.add(networkCreationPanel, BorderLayout.CENTER);
    	
   	 pack();
    }

    public void switchFromNetworkCreationPanel(Network network) {
       
    	setPreferredSize(new Dimension(screenWidth, screenHeight));
    	contentPane.removeAll();

        if (space == null) {
            space = new Space(this, screenWidth, screenHeight - Constants.FOOTER_HEIGHT);
            space.init();
        }

    	footerPanel = new FooterPanel(this);

    	contentPane.add(appMenuBar, BorderLayout.NORTH);
    	contentPane.add(footerPanel, BorderLayout.SOUTH);
    	contentPane.add(space, BorderLayout.CENTER);

    	space.setNetwork(network);

     pack();
    }

//    public void instantChanged() {
//	footerPanel.instantChanged();
//    }

    public void setJSliderValue(int value) {
	footerPanel.setJSliderValue(value);
    }
}
