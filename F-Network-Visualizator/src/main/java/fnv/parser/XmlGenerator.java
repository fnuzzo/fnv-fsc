/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.parser;

import fnv.network.InteractionElement;
import fnv.network.Network;
import fnv.network.Node;
import fnv.utils.Constants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author enrico
 */
public class XmlGenerator {

    public static void generate(Network network, String filename) {
	Element networkRoot = new Element(Constants.XML_ROOT);
	Element networkStatic = new Element(Constants.XML_STATIC);
	Element networkDynamic = new Element(Constants.XML_DYNAMIC);
	networkRoot.addContent(networkStatic);
	networkRoot.addContent(networkDynamic);

	/* nome della rete */
	Element networkName = new Element(Constants.XML_NETWORK_NAME);
	networkName.setText(network.getName());

	networkStatic.addContent(networkName);

	/* elenco dei nodi */
	Element networkNodesList = new Element(Constants.XML_NODES_LIST);
	for (Integer nodeID : network.nodesList.getNodeIDs()) {
	    Node node = network.getNode(nodeID);
	    Element networkNode = new Element(Constants.XML_NODE);
	    networkNode.setAttribute(Constants.XML_NODE_ID_ATTR, String.valueOf(nodeID));
	    networkNode.setAttribute(Constants.XML_NODE_LABEL_ATTR, node.label);
	    networkNode.setAttribute(Constants.XML_NODE_X_ATTR, String.valueOf(node.x));
	    networkNode.setAttribute(Constants.XML_NODE_Y_ATTR, String.valueOf(node.y));
	    networkNode.setAttribute(Constants.XML_NODE_Z_ATTR, String.valueOf(node.z));

	    networkNodesList.addContent(networkNode);
	}
	
	networkStatic.addContent(networkNodesList);

	/* spazio dei nodi */
	Element networkFlat = new Element(Constants.XML_FLAT);
	networkFlat.setText(String.valueOf(network.flat));
        
        networkStatic.addContent(networkFlat);
        
        /* immagine da applicare */
        if (!network.imageFilename.isEmpty()) {
            Element networkImage = new Element(Constants.XML_IMAGE);
            networkImage.setText(String.valueOf(network.imageFilename));
        
            networkStatic.addContent(networkImage);
        }
	
	/* interazioni tra i nodi */
	for (int i = 0; i < network.getNumberOfInstants(); i++) {
	    Element networkInstant = new Element(Constants.XML_INSTANT);
	    networkInstant.setAttribute(Constants.XML_INSTANT_VALUE_ATTR, String.valueOf(i));
	    networkInstant.setAttribute(Constants.XML_INSTANT_LABEL_ATTR, String.valueOf(i));

	    ArrayList<InteractionElement> allInteractions = network.getInteractionCube().getInstant(i).getAllInteractions();
	    for (InteractionElement interactionElement : allInteractions) {
		Element networkInteraction = new Element(Constants.XML_INTERACTION);
		networkInteraction.setAttribute(Constants.XML_INTERACTION_SOURCE_ATTR, String.valueOf(interactionElement.source));
		networkInteraction.setAttribute(Constants.XML_INTERACTION_TARGET_ATTR, String.valueOf(interactionElement.target));
		networkInteraction.setAttribute(Constants.XML_INTERACTION_QUANTITY_ATTR, String.valueOf(interactionElement.quantity));
		networkInteraction.setAttribute(Constants.XML_INTERACTION_FREQUENCY_ATTR, String.valueOf(interactionElement.frequency));

		networkInstant.addContent(networkInteraction);
	    }

	    networkDynamic.addContent(networkInstant);
	}

	/* documento finale */
	Document networkDocument = new Document();
	networkDocument.setRootElement(networkRoot);

	try {
	    XMLOutputter outputter = new XMLOutputter();
	    Format format = outputter.getFormat();
	    format.setIndent("\t");
	    format.setLineSeparator("\n");
	    outputter.setFormat(format);
	    outputter.output(networkDocument, new FileOutputStream (filename));
	} catch (IOException e) {
	    System.err.println(e);
	}
    }
}
