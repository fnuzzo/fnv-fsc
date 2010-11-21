package fnv.gui;

import fnv.network.Network;
import fnv.parser.InputParser;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

public class Interface2 extends JFrame implements  WindowStateListener {
    
	   private Net PrincipalPanel;
	   private JPanel nordPanel;
       private JButton importButton;
       private JLabel titleLabel;
       private JPanel panelComand;
       private JLabel configurationLabel;
       private JLabel circleLabel;
       private JSpinner circleSpinner;
       private JLabel archLabel;
       private JSpinner archSpinner;
       private JLabel durataIstanteLabel;
       private JSpinner durataIstanteSpinner;
       private JPanel footer;
       private JButton play;
       private JButton pause;
       private JButton stop;
       private JProgressBar progressBar;
       public Tet centralPanel;
       private JPanel panel1footer;
       private  JScrollPane scrollingArea;
       private TextArea textarea;
	
	
       
      public Interface2() {
			initComponents();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			addWindowStateListener(this);
		}	
	
	  @Override
		public void windowStateChanged(WindowEvent e) {
			
			PrincipalPanel.changeStateWindows(e.getNewState());
			
		}
		
    private void initComponents() {
    	
    	PrincipalPanel = new Net();
       
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(PrincipalPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
 
    }

 
    private class Net extends JPanel {
        
    	private Net() {
        
    		initComponents();
          
        }

        private void changeStateWindows(int s) {
        	
        	centralPanel.changeStateWindows(s);
        }
        
        
        private void fimportActionPerformed(ActionEvent e) {
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

	    if (inputStream != null) {
		network = InputParser.parse(inputStream);
		if (network != null) {
		    centralPanel.setNetwork(network);
		} else {
		    System.out.println("File di input mal formattato.");
		}
	    }
	}

      

        private void archSpinnerStateChanged(ChangeEvent e) {
            // TODO add your code here
        }

        private void durataIstanteSpinnerStateChanged(ChangeEvent e) {
            // TODO add your code here
        }

        private void playActionPerformed(ActionEvent e) {
            // TODO add your code here
        }

        private void pauseActionPerformed(ActionEvent e) {
            // TODO add your code here
        }

        private void stopActionPerformed(ActionEvent e) {
            // TODO add your code here
        }

        private void progressBarStateChanged(ChangeEvent e) {
            // TODO add your code here
        }

        private void initComponents() {
          
            nordPanel = new JPanel();
            importButton = new JButton();
            titleLabel = new JLabel();
            panelComand = new JPanel();
            configurationLabel = new JLabel();
            circleLabel = new JLabel();
            circleSpinner = new JSpinner();
            archLabel = new JLabel();
            archSpinner = new JSpinner();
            durataIstanteLabel = new JLabel();
            durataIstanteSpinner = new JSpinner();
            footer = new JPanel();
            play = new JButton();
            pause = new JButton();
            stop = new JButton();
            progressBar = new JProgressBar();
            centralPanel = new Tet();
            panel1footer = new JPanel();
            textarea     = new TextArea(6,20);
            scrollingArea = new JScrollPane(textarea);

            
            //======== this ========
            setBorder(null);
            setLayout(new BorderLayout());

         
            //======== nordPanel ========
            {
                nordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            }
            add(nordPanel, BorderLayout.NORTH);

            //======== panelComand ========
            {
                //---- importButton ----
                importButton.setText("Import");
                importButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fimportActionPerformed(e);
                    }
                });
                
                
                
                
                
                panelComand.setBorder(new EmptyBorder(5, 2, 5, 5));
                panelComand.setLayout(new GridLayout(20, 2));
   
                panelComand.add(importButton);
                
                //---- configurationLabel ----
                configurationLabel.setText("Configurations");
                panelComand.add(configurationLabel);

                //---- archLabel ----
                archLabel.setText("Arch");
                panelComand.add(archLabel);

                //---- archSpinner ----
                archSpinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        archSpinnerStateChanged(e);
                    }
                });
                panelComand.add(archSpinner);

                //---- durataIstanteLabel ----
                durataIstanteLabel.setText("durata Istante");
                panelComand.add(durataIstanteLabel);

                //---- durataIstanteSpinner ----
                durataIstanteSpinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        durataIstanteSpinnerStateChanged(e);
                    }
                });
                panelComand.add(durataIstanteSpinner);
            }
            add(panelComand, BorderLayout.EAST);

           
            
            
            //======== footer ========
            {
                footer.setBorder(new EtchedBorder());
                footer.setLayout(new BorderLayout());
   
   
                
                panel1footer.setLayout(new FlowLayout(FlowLayout.LEFT));
   
                //---- play ----
                ImageIcon p1= new ImageIcon(getClass().getResource("/img/play.png"));
                play.setIcon(p1);
                play.setBorderPainted(false);
                play.setContentAreaFilled(false);
                play.setOpaque(true);
                play.setToolTipText("Run");
                
                play.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playActionPerformed(e);
                    }
                });
                panel1footer.add(play);

                
                //---- pause ----
                ImageIcon p2= new ImageIcon(getClass().getResource("/img/pause.png"));
                pause.setIcon(p2);
                pause.setBorderPainted(false);
                pause.setContentAreaFilled(false);
                pause.setOpaque(true);
                pause.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseActionPerformed(e);
                    }
                });
                panel1footer.add(pause);

                //---- stop ----
                ImageIcon p3 = new ImageIcon(getClass().getResource("/img/stop.png"));
                stop.setIcon(p3);
                stop.setBorderPainted(false);
                stop.setContentAreaFilled(false);
                stop.setOpaque(true);
                stop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        stopActionPerformed(e);
                    }
                });
                panel1footer.add(stop);

                
                //---- progressBar ----
                progressBar.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        progressBarStateChanged(e);
                    }
                });
              //  progressBar.
                
                panel1footer.add(progressBar);
                footer.add(panel1footer);
                footer.add(scrollingArea, BorderLayout.SOUTH);
                
            }
            
            add(footer, BorderLayout.SOUTH);

            
            //---- centralPanel ----
          // centralPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            add(centralPanel, BorderLayout.CENTER);
          
        }
     
        
        
        
    }
  
	
}
