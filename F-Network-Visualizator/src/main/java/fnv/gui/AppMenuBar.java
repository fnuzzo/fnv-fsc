/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.gui;

import fnv.gui.pro.Space;
import fnv.network.Network;
import fnv.parser.InputParser;
import fnv.utils.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author enrico
 */
public class AppMenuBar extends JMenuBar implements ActionListener {
    private InterfaceFrame interfaceFrame;
    private JMenu file;
    private JMenu view;
    private JMenu about;
    private JMenuItem importNetwork;
    private JMenuItem createNetwork;
    private JMenuItem map;
    private JMenuItem exit;
    private JMenuItem authors;
    private JMenuItem help;
    private JCheckBoxMenuItem structure;
    private JCheckBoxMenuItem label;
    private JCheckBoxMenuItem edgeIn;
    private JCheckBoxMenuItem allEdges;

    public AppMenuBar(InterfaceFrame interfaceFrame) {
	this.interfaceFrame = interfaceFrame;

	importNetwork = new JMenuItem(Constants.BUTTON_IMPORT_LABEL);
	importNetwork.setActionCommand(Constants.BUTTON_IMPORT_ACTIONCOMMAND);
	createNetwork = new JMenuItem(Constants.BUTTON_CREATE_LABEL);
	createNetwork.setActionCommand(Constants.BUTTON_CREATE_ACTIONCOMMAND);
        map = new JMenuItem(Constants.BUTTON_MAP_LABEL);
        map.setActionCommand(Constants.BUTTON_MAP_ACTIONCOMMAND);
	exit = new JMenuItem(Constants.BUTTON_EXIT_LABEL);
	exit.setActionCommand(Constants.BUTTON_EXIT_ACTIONCOMMAND);

	structure = new JCheckBoxMenuItem(Constants.BUTTON_STRUCTURE_LABEL);
	structure.setActionCommand(Constants.BUTTON_STRUCTURE_ACTIONCOMMAND);
	structure.setSelected(true);
	edgeIn = new JCheckBoxMenuItem(Constants.BUTTON_EDGEIN_LABEL);
	edgeIn.setActionCommand(Constants.BUTTON_EDGEIN_ACTIONCOMMAND);
	allEdges = new JCheckBoxMenuItem(Constants.BUTTON_ALLEDGES_LABEL);
	allEdges.setActionCommand(Constants.BUTTON_ALLEDGES_ACTIONCOMMAND);
	allEdges.setSelected(true);
	label = new JCheckBoxMenuItem(Constants.BUTTON_VISUALIZE_LABEL);
	label.setActionCommand(Constants.BUTTON_LABEL_ACTIONCOMMAND);

	authors = new JMenuItem(Constants.BUTTON_AUTHORS_LABEL);
	authors.setActionCommand(Constants.BUTTON_AUTHORS_ACTIONCOMMAND);
	
	help = new JMenuItem(Constants.BUTTON_HELP_LABEL);
	help.setActionCommand(Constants.BUTTON_HELP_ACTIONCOMMAND);

	importNetwork.addActionListener(this);
	createNetwork.addActionListener(this);
        map.addActionListener(this);
	structure.addActionListener(this);
	edgeIn.addActionListener(this);
	allEdges.addActionListener(this);
	label.addActionListener(this);
	authors.addActionListener(this);
	help.addActionListener(this);
	
	file = new JMenu(Constants.BUTTON_FILE_LABEL);
	file.add(importNetwork);
	file.add(createNetwork);
        file.add(map);
	file.add(exit);

	view = new JMenu(Constants.BUTTON_VIEW_LABEL);
	view.add(structure);
	view.add(edgeIn);
	view.add(allEdges);
	view.add(label);

	about = new JMenu(Constants.BUTTON_ABOUT_LABEL);
	about.add(authors);
	about.add(help);

	add(file);
	add(view);
	add(about);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
	String actionCommand = actionEvent.getActionCommand();
	AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	
	if (actionCommand.equals(Constants.BUTTON_IMPORT_ACTIONCOMMAND)) {
	    importNetwork();
	} else if (actionCommand.equals(Constants.BUTTON_CREATE_ACTIONCOMMAND)) {
	    createNetwork((JMenuItem) actionEvent.getSource());
	} else if (actionCommand.equals(Constants.BUTTON_MAP_ACTIONCOMMAND)) {
            importMap();
        } else if (actionCommand.equals(Constants.BUTTON_EXIT_ACTIONCOMMAND)) {
	    System.exit(0);
	} else if (actionCommand.equals(Constants.BUTTON_STRUCTURE_ACTIONCOMMAND)) {
	    interfaceFrame.getSpace().setOptions(Constants.BUTTON_STRUCTURE_ACTIONCOMMAND);
	} else if (actionCommand.equals(Constants.BUTTON_EDGEIN_ACTIONCOMMAND)) {
	    interfaceFrame.getSpace().setOptions(Constants.BUTTON_EDGEIN_ACTIONCOMMAND);
	} else if (actionCommand.equals(Constants.BUTTON_ALLEDGES_ACTIONCOMMAND)) {
	    interfaceFrame.getSpace().setOptions(Constants.BUTTON_ALLEDGES_ACTIONCOMMAND);
	} else if (actionCommand.equals(Constants.BUTTON_HELP_ACTIONCOMMAND)) {
		 JOptionPane.showMessageDialog(this, Constants.GUI_HELP_MESSAGE);
	} else if (actionCommand.equals(Constants.BUTTON_AUTHORS_ACTIONCOMMAND)) {
	    JOptionPane.showMessageDialog(this, Constants.GUI_ABOUT_MESSAGE);
	}else if (actionCommand.equals(Constants.BUTTON_LABEL_ACTIONCOMMAND)){
		 interfaceFrame.getSpace().setOptions(Constants.BUTTON_LABEL_ACTIONCOMMAND);	
		}
   	}

     private void importNetwork() {
	JFileChooser fc = new JFileChooser(Constants.FILE_CHOOSER_ROOT);
	File inputFile = null;
	Network network = null;

	fc.addChoosableFileFilter(new FileFilter() {

	    @Override
	    public boolean accept(File file) {
		if (file.isDirectory()) {
		    return true;
		}

		String filename = file.getName();
		return (
			filename.endsWith(Constants.FILE_CHOOSER_FILTER_XML_LOWER) ||
			filename.endsWith(Constants.FILE_CHOOSER_FILTER_XML_UPPER));
	    }

	    @Override
	    public String getDescription() {
		return Constants.FILE_CHOOSER_XML_FILTER_DESCRIPTION;
	    }
	});
	int returnVal = fc.showOpenDialog(interfaceFrame);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    inputFile = fc.getSelectedFile();
	}

	InputStream inputStream = null;
	try {
	    inputStream = new FileInputStream(inputFile);
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(AppMenuBar.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NullPointerException npe) {
	}

	if (inputStream != null) {
	    network = InputParser.parse(inputStream);
	    if (network != null) {
	    	interfaceFrame.switchFromNetworkCreationPanel(network);
	    } else {
		System.out.println(Constants.FILE_CHOOSER_ERR_MSG);
	    }
	}
    }

     private void importMap() {
	JFileChooser fc = new JFileChooser(Constants.FILE_CHOOSER_ROOT);
	File inputFile = null;

	fc.addChoosableFileFilter(new FileFilter() {

	    @Override
	    public boolean accept(File file) {
		if (file.isDirectory()) {
		    return true;
		}

		String filename = file.getName();
		return (
			filename.endsWith(Constants.FILE_CHOOSER_FILTER_JPG_LOWER) ||
			filename.endsWith(Constants.FILE_CHOOSER_FILTER_JPG_UPPER) ||
                        filename.endsWith(Constants.FILE_CHOOSER_FILTER_JPEG_LOWER) ||
                        filename.endsWith(Constants.FILE_CHOOSER_FILTER_JPEG_UPPER));
	    }

	    @Override
	    public String getDescription() {
		return Constants.FILE_CHOOSER_JPG_FILTER_DESCRIPTION;
	    }
	});
	int returnVal = fc.showOpenDialog(interfaceFrame);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    inputFile = fc.getSelectedFile();
	}
        try {
            interfaceFrame.setImageTexture(inputFile.getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(AppMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }	
    }
     
    private void createNetwork(JMenuItem source) {
	interfaceFrame.switchToNetworkCreationPanel();
    }
}
