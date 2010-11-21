package fnv.gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.peer.PanelPeer;

import javax.swing.JPanel;

import fnv.gui.pro.Space;
import fnv.network.Network;

public class Tet extends JPanel {
    private Space space;
   
    public Tet() {
	setPreferredSize(new Dimension(820, 620));
	
	space = new Space();
	space.init();
	//space.setPreferredSize(new Dimension(this.getPreferredSize()));
	add(space);
    }

    public void setNetwork(Network network) {
	space.setNetwork(network);
    }
    
    public void changeStateWindows(int s){
    	//if (s==0) //full screen
    	//space. ridimensiona state		
    //else if(s==6) // normal 
    	//dimensione normale metˆ schermo
    	//else
    	//da gestire
    	
    	}


    
}
