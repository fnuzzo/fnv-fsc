package fnv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import fnv.gui.pro.MyCubesWithinCube;
import fnv.gui.pro.Space;
import fnv.network.Network;
import fnv.parser.InputParser;

public class Interface3 extends JFrame implements ActionListener{

	private JPanel footer;
	private JPanel centralPanel;
	private JMenuBar menubar;
	private int TICKSPACING = 10;
	private int TIMEVALUE = 100;
	private Space space;
	
	
	public Interface3(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("F-Network Visualizator");
		setResizable(true);
		setVisible(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(buildMenu(),BorderLayout.NORTH);
		contentPane.add(buildCentralPanel(), BorderLayout.CENTER);
		contentPane.add(buildFooter(), BorderLayout.SOUTH);
		pack();
	}

	
	
	
	
	
	
	
		private JMenuBar buildMenu(){
			
			JMenu file;
			JMenu view;
			JMenu help;
			JMenuItem importM;
			JMenuItem helpbox;
			JMenuItem about;
			JCheckBoxMenuItem structure;
			JCheckBoxMenuItem details;
			
			
			importM = new JMenuItem("Import");
			importM.setActionCommand("import");
			helpbox = new JMenuItem("help");
			helpbox.setActionCommand("help");
			about = new JMenuItem("About");
			about.setActionCommand("about");
			structure = new JCheckBoxMenuItem("Nascondi scheletro");
			structure.setActionCommand("struct");
			details = new JCheckBoxMenuItem("Nascondi log e config");
			details.setActionCommand("vis");

				
			importM.addActionListener(this);
			structure.addActionListener(this);
			details.addActionListener(this);
			helpbox.addActionListener(this);
			about.addActionListener(this);
				
			file = new JMenu("File");
				file.add(importM);
				
			view = new JMenu("View");
				view.add(structure);
				view.addSeparator();
				view.add(details);
				
			help = new JMenu("Help");
				help.add(helpbox);
				help.add(about);

				
				
			menubar = new JMenuBar();
				menubar.add(file);
				menubar.add(view);
				menubar.add(help);
				
				return menubar;
			
		}
		
		
		private JPanel buildCentralPanel(){
			centralPanel = new JPanel();
			
			space = new Space();
			space.init();
			
			centralPanel.setPreferredSize(new Dimension(800,600));
			centralPanel.add(space);
			
			return centralPanel;
		}
		JTabbedPane tabpane;
		
		private JPanel buildFooter(){
			
			JPanel command;
			
			JButton play;
			JButton pause;
			JButton stop;
			JSlider time;	
			JTextArea log;
			JPanel logtab;
			JPanel conftab;
    			
			//---- play ----
			play = new JButton();
                ImageIcon p1= new ImageIcon(getClass().getResource("/img/play.png"));
            	play.setIcon(p1);
	            play.setBorderPainted(false);
	            play.setContentAreaFilled(false);
	            play.setOpaque(true);
	            play.setToolTipText("Run");
	            play.setActionCommand("play");
	            play.addActionListener(this);
				
            //---- pause ----
            pause = new JButton();
	            ImageIcon p2= new ImageIcon(getClass().getResource("/img/pause.png"));
	            pause.setIcon(p2);
	            pause.setBorderPainted(false);
	            pause.setContentAreaFilled(false);
	            pause.setOpaque(true);
	            pause.setActionCommand("pause");
	            pause.addActionListener(this);
			
            //---- stop ----
            stop = new JButton();
	            ImageIcon p3 = new ImageIcon(getClass().getResource("/img/stop.png"));
	            stop.setIcon(p3);
	            stop.setBorderPainted(false);
	            stop.setContentAreaFilled(false);
	            stop.setOpaque(true);
	            stop.setActionCommand("stop");
	            stop.addActionListener(this);
          
            //---- Time ---
            time = new JSlider();
	    		time.setMajorTickSpacing(TICKSPACING);
	    		time.setPaintTicks(true);
	    		time.setPaintLabels(true);
	    		time.setValue(TIMEVALUE);
	    		time.addChangeListener(new ChangeListener() {

	    			public void stateChanged(ChangeEvent event) {
	    				int t = ((JSlider) event.getSource()).getValue();
	    				//space.
	    			}
	    		});
                  
    		//---- Build command ----
        	command = new JPanel();
				command.setLayout(new BoxLayout(command, BoxLayout.LINE_AXIS));
				command.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	            command.add(play);
	            command.add(pause);
	            command.add(stop);
	            command.add(time);

            // --- tab log e config ---
            tabpane = new JTabbedPane();
            
            log = new JTextArea("asdadadadadss");
            	log.setEditable(false);
            
			logtab = new JPanel();
				logtab.add(log);
			
			conftab = new JPanel();
			
			tabpane.addTab("Log", logtab);
			tabpane.addTab("Configurations", conftab);
			
			//build footer
			footer = new JPanel();
	    		footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
	    		footer.add(command);
	    		footer.add(tabpane);
			
			return footer;
			
		}
		
		
	
		
        private void fimportActionPerformed() {
    	    JFileChooser fc = new JFileChooser("./");
    	    File inputFile = null;
    	    Network network = null;

    	    fc.addChoosableFileFilter(new FileFilter() {

    		@Override
    		public boolean accept(File file) {
    		    if (file.isDirectory()) {
    			return true;
    		    }

    		    String filename = file.getName();
                return (filename.endsWith("xml") || filename.endsWith("XML"));
    		    }

    		@Override
    		public String getDescription() {
    		    return "xml files";
    		}
    	    });
    	    int returnVal = fc.showOpenDialog(this);

    	    if (returnVal == JFileChooser.APPROVE_OPTION) {
    		inputFile = fc.getSelectedFile();
    	    } else {
    	    }

    	    InputStream inputStream = null;
    	    try {
    		inputStream = new FileInputStream(inputFile);
    	    } catch (FileNotFoundException ex) {
    		Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
    	    }
    	    catch(NullPointerException npe){
    	    	
    	    }

    	    if (inputStream != null) {
    		network = InputParser.parse(inputStream);
    		if (network != null) {
    		    space.setNetwork(network);
    		} else {
    		    System.out.println("File di input mal formattato.");
    		}
    	    }
    	}


		@Override
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			if(c.equals("import"))
				fimportActionPerformed();
			else if(c.equals("about"))
				JOptionPane.showMessageDialog( this, "è bene che fai una donazione");
			else if(c.equals("help"))
			JOptionPane.showMessageDialog( this, "istruzioni per l'uso");
			else if(c.equals("struct")){
			AbstractButton abstractButton = (AbstractButton)e.getSource();
	        if(abstractButton.getModel().isSelected())
	        System.out.println("true");
	        else
	        	System.out.println("true");	
	        
			}
			else if(c.equals("vis")){
				AbstractButton abstractButton = (AbstractButton)e.getSource();
		        if(abstractButton.getModel().isSelected())
		        	 footer.remove(tabpane);
		        else
		        	footer.add(tabpane);
		       
		        pack();
				}
			
			
		}


		
		
	
}
