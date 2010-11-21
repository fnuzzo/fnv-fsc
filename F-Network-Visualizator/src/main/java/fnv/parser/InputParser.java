package fnv.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import fnv.network.InteractionsCube;
import fnv.network.Network;
import fnv.network.Node;
import fnv.network.NodesList;
import fnv.utils.Constants;

public class InputParser {

    private static String name = "";
    private static ArrayList<Node> nodesList = new ArrayList<Node>();
    private static InteractionsCube interactionCube = new InteractionsCube();

    /*
     * Parsing del file di input.
     * Ritorna un oggetto di tipo Network con tutte le informazioni al suo interno
     */
    //TODO validation con XMLSchema prima di fare parsing
    public static Network parse(InputStream inputStream) {
	try {
	    Document document = new SAXBuilder().build(inputStream);
	    Element rootElement = document.getRootElement();

	    for (Iterator<Element> childrenIterator = rootElement.getChildren().iterator(); childrenIterator.hasNext();) {
		Element child = childrenIterator.next();

		if (child.getName().equals(Constants.XML_STATIC)) {
		    /* parsing della parte statica del file di input */
		    parseStaticPart(child);
		} else if (child.getName().equals(Constants.XML_DYNAMIC)) {
		    /* parsing della parte dinamica del file di input */
		    parseDynamicPart(child);
		} else {
		    System.err.println("Elemento non riconosciuto.");
		    System.err.println(child.getName() + ", should be: " + Constants.XML_STATIC + " or " + Constants.XML_DYNAMIC);
		}
	    }
	} catch (JDOMException e) {
	    //e.printStackTrace();
	    return null;
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return new Network(name, new NodesList(nodesList), interactionCube);
    }

    /*
     * Parsing della parte statica del file di input:
     *  - nome della rete
     *  - elenco di tutti i nodi
     */
    private static void parseStaticPart(Element element) throws DataConversionException {
	String networkName;
	for (Iterator<Element> childrenIterator = element.getChildren().iterator(); childrenIterator.hasNext();) {
	    Element child = childrenIterator.next();
	    if (child.getName().equals(Constants.XML_NETWORK_NAME)) {
		name = child.getValue().trim();
	    } else if (child.getName().equals(Constants.XML_NODES_LIST)) {
		/* parsing dei nodi */
		for (Iterator<Element> nodesIterator = child.getChildren().iterator(); nodesIterator.hasNext();) {
		    Element node = nodesIterator.next();

		    if (node.getName().equals(Constants.XML_NODE)) {
			Integer id = node.getAttribute(Constants.XML_NODE_ID_ATTR).getIntValue();
			String label = node.getAttribute(Constants.XML_NODE_LABEL_ATTR).getValue().trim();
			int x = node.getAttribute(Constants.XML_NODE_X_ATTR).getIntValue();
			int y = node.getAttribute(Constants.XML_NODE_Y_ATTR).getIntValue();
			int z = node.getAttribute(Constants.XML_NODE_Z_ATTR).getIntValue();

			nodesList.add(new Node(id, label, x, y, z));
		    } else {
			System.err.println("Elemento non riconosciuto.");
			System.err.println(node.getName() + ", should be: " + Constants.XML_NODE);
		    }
		}
	    } else {
		System.err.println("Elemento non riconosciuto.");
		System.err.println(child.getName() + ", should be: " + Constants.XML_NETWORK_NAME + " or " + Constants.XML_NODES_LIST);
	    }
	}
    }

    /*
     * Parsing della parte dinamica della rete:
     *  - tutte le interazioni tra i nodi, divise per istanti
     */
    private static void parseDynamicPart(Element element) throws DataConversionException {
	for (Iterator<Element> instantIterator = element.getChildren().iterator(); instantIterator.hasNext();) {
	    Element instant = instantIterator.next();

	    if (instant.getName().equals(Constants.XML_INSTANT)) {
		Integer instantValue = instant.getAttribute(Constants.XML_INSTANT_VALUE_ATTR).getIntValue();

		for (Iterator<Element> interactionIterator = instant.getChildren().iterator(); interactionIterator.hasNext();) {
		    Element interaction = interactionIterator.next();

		    if (interaction.getName().equals(Constants.XML_INTERACTION)) {
			/* parsing delle interazioni tra i nodi */
			Integer source = interaction.getAttribute(Constants.XML_INTERACTION_SOURCE_ATTR).getIntValue();
			Integer target = interaction.getAttribute(Constants.XML_INTERACTION_TARGET_ATTR).getIntValue();
			Double frequency = interaction.getAttribute(Constants.XML_INTERACTION_FREQUENCY_ATTR).getDoubleValue();

			interactionCube.addInteraction(instantValue, source, target, frequency);
		    } else {
			System.err.println("Elemento non riconosciuto.");
			System.err.println(interaction.getName() + ", should be: " + Constants.XML_INTERACTION);
		    }
		}
	    } else {
		System.err.println("Elemento non riconosciuto.");
		System.err.println(instant.getName() + ", should be: " + Constants.XML_INSTANT);
	    }
	}
    }
}
