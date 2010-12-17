/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import fnv.network.Instant;
import fnv.network.InteractionElement;
import fnv.network.Network;

/**
 *
 * @author enrico
 */
public class InterfaceFrame extends JFrame {

    private int screenWidth;
    private int screenHeight;

    private AppMenuBar appMenuBar;
    private CentralPanel centralPanel;
    private NetworkCreationPanel networkCreationPanel;
    private FooterPanel footerPanel;

    public InterfaceFrame() {
	/* fullscreen */
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice defaultGraphicsDevice = ge.getDefaultScreenDevice();
	screenWidth = defaultGraphicsDevice.getDisplayMode().getWidth();
	screenHeight = defaultGraphicsDevice.getDisplayMode().getHeight();
	defaultGraphicsDevice.setFullScreenWindow(this);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("F-Network Visualizator");
	setResizable(true);
	setVisible(true);

	appMenuBar = new AppMenuBar(this);
	centralPanel = new CentralPanel(this);
	networkCreationPanel = new NetworkCreationPanel();
	footerPanel = new FooterPanel(this);

	Container contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());


	contentPane.add(appMenuBar, BorderLayout.NORTH);
	contentPane.add(centralPanel, BorderLayout.CENTER);
	contentPane.add(footerPanel, BorderLayout.SOUTH);
	pack();
    }

    public CentralPanel getCentralPanel() {
	return centralPanel;
    }

    public FooterPanel getFooterPanel() {
	return footerPanel;
    }

    public AppMenuBar getAppMenuBar() {
	return appMenuBar;
    }

    public NetworkCreationPanel getNetworkCreationPanel() {
	return networkCreationPanel;
    }

    public int getScreenHeight() {
	return screenHeight;
    }

    public int getScreenWidth() {
	return screenWidth;
    }

    public void switchToNetworkCreationPanel() {
	centralPanel.remove(centralPanel.getSpace());
	networkCreationPanel = new NetworkCreationPanel();
	centralPanel.add(networkCreationPanel);
	networkCreationPanel.setVisible(true);
	pack();
    }

    public void instantChanged() {
	footerPanel.instantChanged();
    }

    public void setJSliderValue(int value) {
	footerPanel.setJSliderValue(value);
    }
}
