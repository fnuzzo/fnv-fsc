package fnv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import fnv.gui.pro.Space;
import fnv.network.Network;
import fnv.parser.InputParser;

public class Interface extends JFrame implements ActionListener, ChangeListener, WindowStateListener, ComponentListener{

    private JTabbedPane tabpane;
    private JPanel footer;
    private JPanel centralPanel;
    private JPanel networkCreationPanel;
    private JMenuBar menubar;
    private int TICKSPACING = 10;
    //private int TIMEVALUE = 100;
    private Space space;
    private JSlider jsTime;
    private JMenuItem creaN;

	public Interface() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("F-Network Visualizator");
	setResizable(true);
	setVisible(true);

	Container contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());

	
	//networkCreationPanel.setVisible(false);

	contentPane.add(buildMenu(), BorderLayout.NORTH);
	contentPane.add(buildCentralPanel(), BorderLayout.CENTER);
	//contentPane.add(networkCreationPanel, BorderLayout.CENTER);
	contentPane.add(buildFooter(), BorderLayout.SOUTH);
	pack();
	addWindowStateListener(this);
	addComponentListener(this);
    }

    /*public static Interface3 getInstance(){
    if(istance == null)
    istance = new Interface3();

    return istance;
    }*/
	
    private JMenuBar buildMenu() {
	JMenu file;
	JMenu view;
	JMenu help;
	
	JMenuItem importM;
	JMenuItem helpbox;
	JMenuItem about;
	JCheckBoxMenuItem structure;
	JCheckBoxMenuItem details;
	JCheckBoxMenuItem edgeIn;
	JCheckBoxMenuItem edgeOut;

	importM = new JMenuItem("Import");
	importM.setActionCommand("import");
	creaN = new JMenuItem("Crea Network");
	creaN.setActionCommand("creaN");
	helpbox = new JMenuItem("help");
	helpbox.setActionCommand("help");
	about = new JMenuItem("About");
	about.setActionCommand("about");
	structure = new JCheckBoxMenuItem("Nascondi scheletro");
	structure.setActionCommand("struct");
	structure.setSelected(true);
	edgeIn = new JCheckBoxMenuItem("Only Connections in");
	edgeIn.setActionCommand("edgeIn");
	edgeOut = new JCheckBoxMenuItem("Only Connections out");
	edgeOut.setSelected(true);
	edgeOut.setActionCommand("edgeOut");
	details = new JCheckBoxMenuItem("Nascondi log e config");
	details.setActionCommand("vis");

	importM.addActionListener(this);
	creaN.addActionListener(this);
	structure.addActionListener(this);
	details.addActionListener(this);
	helpbox.addActionListener(this);
	about.addActionListener(this);
	edgeIn.addActionListener(this);
	edgeOut.addActionListener(this);
	

	file = new JMenu("File");
	file.add(importM);
	file.add(creaN);
	
	view = new JMenu("View");
	view.add(structure);
	view.addSeparator();
	view.add(details);
	view.add(edgeIn);
	view.add(edgeOut);

	help = new JMenu("Help");
	help.add(helpbox);
	help.add(about);

	menubar = new JMenuBar();
	menubar.add(file);
	menubar.add(view);
	menubar.add(help);

	return menubar;
    }

    private JPanel buildCentralPanel() {
	centralPanel = new JPanel();

	space = new Space(this);
	space.init();

	centralPanel.setPreferredSize(new Dimension(800, 600));
	centralPanel.add(space);

	return centralPanel;
    }

    private JPanel buildFooter() {
	JPanel command;

	JButton play;
	JButton pause;
	JButton stop;

	JTextArea log;
	JPanel logtab;
	JPanel conftab;

	//---- play ----
	play = new JButton();
	ImageIcon p1 = new ImageIcon(getClass().getResource("/img/play.png"));
	play.setIcon(p1);
	play.setBorderPainted(false);
	play.setContentAreaFilled(false);
	play.setOpaque(true);
	play.setToolTipText("Run");
	play.setActionCommand("play");
	play.addActionListener(this);

	//---- pause ----
	pause = new JButton();
	ImageIcon p2 = new ImageIcon(getClass().getResource("/img/pause.png"));
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
	jsTime = new JSlider();
	jsTime.setMajorTickSpacing(TICKSPACING);
	jsTime.setPaintTicks(true);
	jsTime.setPaintLabels(true);
	jsTime.setValue(0);
	jsTime.addChangeListener(this);

	//---- Build command ----
	command = new JPanel();
	command.setLayout(new BoxLayout(command, BoxLayout.LINE_AXIS));
	command.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	command.add(play);
	command.add(pause);
	command.add(stop);
	command.add(jsTime);

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
	centralPanel.remove(networkCreationPanel);
	centralPanel.add(space);
	space.setVisible(true);
	creaN.setEnabled(true);
	pack();

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
	}

	InputStream inputStream = null;
	try {
	    inputStream = new FileInputStream(inputFile);
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NullPointerException npe) {
	}

	if (inputStream != null) {
	    network = InputParser.parse(inputStream);
	    if (network != null) {
		space.setNetwork(network);
		//TIMEVALUE = network.getNumberOfInstants();
	    } else {
		System.out.println("File di input mal formattato.");
	    }
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String c = e.getActionCommand();
	AbstractButton abstractButton = (AbstractButton) e.getSource();

	if (c.equals("import")) {
	    fimportActionPerformed();
	} else if (c.equals("about")) {
	    JOptionPane.showMessageDialog(this, "e' bene che fai una donazione");
	} else if (c.equals("help")) {
	    JOptionPane.showMessageDialog(this, "istruzioni per l'uso");
	} else if (c.equals("struct")) {
	    space.setOptions("spaceVisible");
	} else if (c.equals("vis")) {
	    if (abstractButton.getModel().isSelected()) {
		footer.remove(tabpane);
	    } else {
		footer.add(tabpane);
	    }
	    pack();
	} else if(c.equals("creaN")){
		centralPanel.remove(space);
		networkCreationPanel = new NetworkCreationPanel();
		centralPanel.add(networkCreationPanel);
		networkCreationPanel.setVisible(true);
		creaN.setEnabled(false);
		pack();
	} 
	
	else if (c.equals("edgeIn")) {
	    space.setOptions("edgeIn");
	} else if (c.equals("edgeOut")) {
	    space.setOptions("edgeOut");
	} else if (c.equals("play")) {
	    space.optionsTime("play");
	} else if (c.equals("pause")) {
	    space.optionsTime("pause");
	} else if (c.equals("stop")) {
	    setValuejstime(0);
	    space.optionsTime("stop");
	}
    }

    public void setValuejstime(int value) {
	//footer.remove(jsTime);
	jsTime.setValue(value);
	//footer.add(jsTime);
	System.out.println(value);
	//jsTime.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent event) {
	int t = ((JSlider) event.getSource()).getValue();
	space.setInstant(t);
    }

 
	@Override
	public void windowStateChanged(WindowEvent e) {
//		int width = (int)this.getSize().getWidth();
//		int heigth = (int)this.getSize().getHeight();
//		System.out.println(width+" "+heigth);
	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void componentResized(ComponentEvent e) {
//		System.out.println(this.getSize().getWidth());
//		System.out.println();
//
//		//int h= (int) (this.getSize().getHeight()-50);
//		//int w = (int) (this.getSize().getWidth()-20);
//		//space.resizePanel(w, h);
//
//
//	}
	
    @Override
    public void componentResized(ComponentEvent evt) {
	Dimension newSize = getSize();

	space.resizeSpace(newSize);
	//space.resize(newSize);
    }

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}

/*
 Restituisce in base allo schermo le dimensioni massime 
 Priaticamente state = 6 restituito da windowStateChanged()
 potrebbe servire per il full screen visto che la libreria fullscreen NON funziona 
GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
GraphicsDevice devices = environment.getDefaultScreenDevice()
//  System.out.println(Arrays.toString(devices));


     WIDTH =devices.getDisplayMode().getWidth();
     HEIGHT= devices.getDisplayMode().getHeight();*/




