/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.gui;

import fnv.network.Instant;
import fnv.network.InteractionElement;
import fnv.network.Network;
import fnv.utils.Constants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author enrico
 */
public class FooterPanel extends JPanel implements ActionListener, ChangeListener {

    private InterfaceFrame interfaceFrame;
    private JPanel command;
    private JButton play;
    private JButton pause;
    private JButton stop;
    private JSlider jSlider;
    private JSpinner jSpinner;
    private JScrollPane jScrollPane;
    private JTextArea logTextArea;
    private JPanel logPanel;

    public FooterPanel(InterfaceFrame interfaceFrame) {
	this.interfaceFrame = interfaceFrame;

	//---- play ----
	play = new JButton();
	ImageIcon playIcon = new ImageIcon(getClass().getResource(Constants.ICON_PLAY));
	play.setIcon(playIcon);
	play.setBorderPainted(false);
	play.setContentAreaFilled(false);
	play.setOpaque(true);
	play.setToolTipText(Constants.ICON_PLAY_TOOLTIP);
	play.setActionCommand(Constants.ICON_PLAY_ACTIONCOMMAND);
	play.addActionListener(this);

	//---- pause ----
	pause = new JButton();
	ImageIcon pauseIcon = new ImageIcon(getClass().getResource(Constants.ICON_PAUSE));
	pause.setIcon(pauseIcon);
	pause.setBorderPainted(false);
	pause.setContentAreaFilled(false);
	pause.setOpaque(true);
	pause.setToolTipText(Constants.ICON_PAUSE_TOOLTIP);
	pause.setActionCommand(Constants.ICON_PAUSE_ACTIONCOMMAND);
	pause.addActionListener(this);

	//---- stop ----
	stop = new JButton();
	ImageIcon stopIcon = new ImageIcon(getClass().getResource(Constants.ICON_STOP));
	stop.setIcon(stopIcon);
	stop.setBorderPainted(false);
	stop.setContentAreaFilled(false);
	stop.setOpaque(true);
	stop.setToolTipText(Constants.ICON_STOP_TOOLTIP);
	stop.setActionCommand(Constants.ICON_STOP_ACTIONCOMMAND);
	stop.addActionListener(this);

	//---- Time ---
	jSlider = new JSlider();
	jSlider.setMajorTickSpacing(Constants.JSLIDER_TICK_SPACING);
	jSlider.setPaintTicks(true);
	jSlider.setPaintLabels(true);
	jSlider.setValue(0);
	jSlider.addChangeListener(this);

	//TODO da mettere in constants?
	SpinnerModel spinnerModel = new SpinnerNumberModel(interfaceFrame.getCentralPanel().getSpace().getAnimationTime(), 0, Double.MAX_VALUE, 0.5);
	jSpinner = new JSpinner(spinnerModel);
	Dimension spinnerDimension = new Dimension(50, 50);
	jSpinner.setSize(spinnerDimension);
	jSpinner.setPreferredSize(spinnerDimension);
	jSpinner.setMinimumSize(spinnerDimension);
	jSpinner.setMaximumSize(spinnerDimension);
	jSpinner.addChangeListener(this);

	//---- Build command ----
	command = new JPanel();
	command.setLayout(new BoxLayout(command, BoxLayout.LINE_AXIS));
	command.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	command.add(play);
	command.add(pause);
	command.add(stop);
	command.add(jSlider);
	command.add(jSpinner);

	// --- tab log ---
	//logTextArea = new JTextArea("\n\n\n\n\n\n");
	logTextArea = new JTextArea();
	logTextArea.setEditable(false);
	Font font = logTextArea.getFont();
	logTextArea.setFont(new Font(font.getFontName(), font.getStyle(), 16));
	jScrollPane = new JScrollPane(logTextArea);
	jScrollPane.setPreferredSize(new Dimension(interfaceFrame.getScreenWidth(), Constants.LOG_HEIGHT));

	logPanel = new JPanel();
	//logPanel.add(logTextArea);
	logPanel.add(jScrollPane);

	//build footer
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(command);
	add(logPanel);
	//setPreferredSize(new Dimension(interfaceFrame.getScreenWidth(), Constants.FOOTER_HEIGHT));
    }

     public void setLogVisible(boolean visible) {
	 if (visible) {
	     add(logPanel);
	 } else {
	     remove(logPanel);
	 }
     }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
	String actionCommand = actionEvent.getActionCommand();

	if (actionCommand.equals(Constants.ICON_STOP_ACTIONCOMMAND)) {
	    setJSliderValue(0);
	}

	interfaceFrame.getCentralPanel().getSpace().optionsTime(actionCommand);
    }

    public void setJSliderValue(int value) {
	jSlider.setValue(value);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
	if (changeEvent.getSource() instanceof JSlider) {
	    int t = ((JSlider) changeEvent.getSource()).getValue();
	    interfaceFrame.getCentralPanel().getSpace().setInstant(t);
	} else if (changeEvent.getSource() instanceof JSpinner) {
	    SpinnerModel dateModel = jSpinner.getModel();
	    if (dateModel instanceof SpinnerNumberModel) {
		double newAnimationTimeSec = ((SpinnerNumberModel) dateModel).getNumber().doubleValue();
		interfaceFrame.getCentralPanel().getSpace().setAnimationTime(newAnimationTimeSec);
	    }
	}
    }

    public void instantChanged() {
	String logText = "";
	int currentInstant = interfaceFrame.getCentralPanel().getSpace().getInstant();

	Network network = interfaceFrame.getCentralPanel().getSpace().getNetwork();
	Instant instant = network.getInteractionCube().getInstant(currentInstant);

	logText += "Instant: " + instant.getLabel() + "\n";
	for (InteractionElement interactionElement : instant.getAllInteractions()) {
	    logText +=
		    network.getNode(interactionElement.source).label +
		    " -> " +
		    network.getNode(interactionElement.target).label +
		    ": " +
		    interactionElement.frequency +
		    "\n";
	}

	logTextArea.setText(logText);
    }
}
