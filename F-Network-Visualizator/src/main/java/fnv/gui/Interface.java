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
/*
 * Created by JFormDesigner on Sat Nov 20 16:59:30 CET 2010
 */



/**
 * @author Giacomo Benvenuti
 */
public class Interface extends JFrame {
    public Interface() {
        initComponents();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Giacomo Benvenuti
        PrincipalPanel = new Net();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(PrincipalPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Giacomo Benvenuti
    private Net PrincipalPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private class Net extends JPanel {
        private Net() {
            initComponents();
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
		}
	    }
	}

        private void circleSpinnerStateChanged(ChangeEvent e) {
            // TODO add your code here
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
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            // Generated using JFormDesigner Evaluation license - Giacomo Benvenuti
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

            //======== this ========
            setBorder(null);

            // JFormDesigner evaluation mark
            setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            setLayout(new BorderLayout());

            //======== nordPanel ========
            {
                nordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- importButton ----
                importButton.setText("Import");
                importButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fimportActionPerformed(e);
                    }
                });
                nordPanel.add(importButton);

                //---- titleLabel ----
                titleLabel.setText("                  Fucking Network Visulizator                 ");
                titleLabel.setFont(new Font("Copperplate", Font.BOLD, 18));
                nordPanel.add(titleLabel);
            }
            add(nordPanel, BorderLayout.NORTH);

            //======== panelComand ========
            {
                panelComand.setBorder(new EmptyBorder(5, 2, 5, 5));
                panelComand.setLayout(new GridBagLayout());
                ((GridBagLayout)panelComand.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)panelComand.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)panelComand.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
                ((GridBagLayout)panelComand.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- configurationLabel ----
                configurationLabel.setText("Configurations");
                panelComand.add(configurationLabel, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- circleLabel ----
                circleLabel.setText("Circle");
                panelComand.add(circleLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 2, 2), 0, 0));

                //---- circleSpinner ----
                circleSpinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        circleSpinnerStateChanged(e);
                    }
                });
                panelComand.add(circleSpinner, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- archLabel ----
                archLabel.setText("Arch");
                panelComand.add(archLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 2, 2), 0, 0));

                //---- archSpinner ----
                archSpinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        archSpinnerStateChanged(e);
                    }
                });
                panelComand.add(archSpinner, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- durataIstanteLabel ----
                durataIstanteLabel.setText("durata Istante");
                panelComand.add(durataIstanteLabel, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- durataIstanteSpinner ----
                durataIstanteSpinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        durataIstanteSpinnerStateChanged(e);
                    }
                });
                panelComand.add(durataIstanteSpinner, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            add(panelComand, BorderLayout.EAST);

            //======== footer ========
            {
                footer.setBorder(new EtchedBorder());
                footer.setLayout(new GridBagLayout());
                ((GridBagLayout)footer.getLayout()).columnWidths = new int[] {0, 0, 0, 357, 0, 0};
                ((GridBagLayout)footer.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)footer.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
                ((GridBagLayout)footer.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- play ----
                play.setSelectedIcon(new ImageIcon(getClass().getResource("/img/play.png")));
                play.setToolTipText("Run");
                play.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playActionPerformed(e);
                    }
                });
                footer.add(play, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- pause ----
                pause.setSelectedIcon(new ImageIcon(getClass().getResource("/img/pause.png")));
                pause.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseActionPerformed(e);
                    }
                });
                footer.add(pause, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- stop ----
                stop.setSelectedIcon(new ImageIcon(getClass().getResource("/img/stop.png")));
                stop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        stopActionPerformed(e);
                    }
                });
                footer.add(stop, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- progressBar ----
                progressBar.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        progressBarStateChanged(e);
                    }
                });
                footer.add(progressBar, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 5), 0, 0));
            }
            add(footer, BorderLayout.SOUTH);

            //---- centralPanel ----
            centralPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            add(centralPanel, BorderLayout.CENTER);
            // JFormDesigner - End of component initialization  //GEN-END:initComponents
        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        // Generated using JFormDesigner Evaluation license - Giacomo Benvenuti
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
        private Tet centralPanel;
        // JFormDesigner - End of variables declaration  //GEN-END:variables
    }
}
