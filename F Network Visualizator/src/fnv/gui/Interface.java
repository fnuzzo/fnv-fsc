/*
 * Created by JFormDesigner on Sun Oct 17 18:23:34 CEST 2010
 */

package fnv.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


/**
 * @author free fab
 */
public class Interface extends JFrame {
	public Interface() {
		initComponents();
		show();
	}

	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - free fab
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
	// Generated using JFormDesigner Evaluation license - free fab
	private Net PrincipalPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	private class Net extends JPanel {
		private Net() {
			initComponents();
		}

		private void fimportActionPerformed(ActionEvent e) {
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

		private void asseXButtonLeftActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void asseXButtonRightActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void asseYButtonLeftActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void asseYButtonRightActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void asseZButtonLeftActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void asseZButtonRightActionPerformed(ActionEvent e) {
			// TODO add your code here
		}

		private void circleSpinnerStateChanged(ChangeEvent e) {
			// TODO add your code here
		}

		private void archSpinnerStateChanged(ChangeEvent e) {
			// TODO add your code here
		}

		private void progressBarStateChanged(ChangeEvent e) {
			// TODO add your code here
		}

		private void durataIstanteSpinnerStateChanged(ChangeEvent e) {
			// TODO add your code here
		}

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			// Generated using JFormDesigner Evaluation license - free fab
			nordPanel = new JPanel();
			importButton = new JButton();
			titleLabel = new JLabel();
			panelComand = new JPanel();
			asseXLabel = new JLabel();
			asseXButtonLeft = new JButton();
			asseXButtonRight = new JButton();
			asseYLabel = new JLabel();
			asseYButtonLeft = new JButton();
			asseYButtonRight = new JButton();
			asseZLabel = new JLabel();
			asseZButtonLeft = new JButton();
			asseZButtonRight = new JButton();
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

				//---- asseXLabel ----
				asseXLabel.setText(" Asse X");
				panelComand.add(asseXLabel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

				//---- asseXButtonLeft ----
				asseXButtonLeft.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseXButtonLeftActionPerformed(e);
					}
				});
				panelComand.add(asseXButtonLeft, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 2), 0, 0));

				//---- asseXButtonRight ----
				asseXButtonRight.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseXButtonRightActionPerformed(e);
					}
				});
				panelComand.add(asseXButtonRight, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

				//---- asseYLabel ----
				asseYLabel.setText("Asse Y");
				panelComand.add(asseYLabel, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

				//---- asseYButtonLeft ----
				asseYButtonLeft.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseYButtonLeftActionPerformed(e);
					}
				});
				panelComand.add(asseYButtonLeft, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 2), 0, 0));

				//---- asseYButtonRight ----
				asseYButtonRight.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseYButtonRightActionPerformed(e);
					}
				});
				panelComand.add(asseYButtonRight, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

				//---- asseZLabel ----
				asseZLabel.setText("Asse Z");
				panelComand.add(asseZLabel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

				//---- asseZButtonLeft ----
				asseZButtonLeft.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseZButtonLeftActionPerformed(e);
					}
				});
				panelComand.add(asseZButtonLeft, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 2), 0, 0));

				//---- asseZButtonRight ----
				asseZButtonRight.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						asseZButtonRightActionPerformed(e);
					}
				});
				panelComand.add(asseZButtonRight, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 2, 0), 0, 0));

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
				play.setSelectedIcon(new ImageIcon(getClass().getResource("/com/grap/img/play.png")));
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
				pause.setSelectedIcon(new ImageIcon(getClass().getResource("/com/grap/img/pause.png")));
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
				stop.setSelectedIcon(new ImageIcon(getClass().getResource("/com/grap/img/stop.png")));
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
		// Generated using JFormDesigner Evaluation license - free fab
		private JPanel nordPanel;
		private JButton importButton;
		private JLabel titleLabel;
		private JPanel panelComand;
		private JLabel asseXLabel;
		private JButton asseXButtonLeft;
		private JButton asseXButtonRight;
		private JLabel asseYLabel;
		private JButton asseYButtonLeft;
		private JButton asseYButtonRight;
		private JLabel asseZLabel;
		private JButton asseZButtonLeft;
		private JButton asseZButtonRight;
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
