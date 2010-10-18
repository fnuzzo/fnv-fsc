package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import network.InteractionsCube;
import network.Network;
import network.Node;
import network.NodesList;

public class InputParserTest {

	private String inputFilename = "networks/network-test-01.xml";
	private Network network;
	
	@Before
	public void setup() {
		String name = "Rete di prova";
		ArrayList<Node> nodesList = new ArrayList<Node>();
		Node nodeA = new Node(0, "A", 0, 0, 0);
		Node nodeB = new Node(1, "B", 1, 1, 0);
		Node nodeC = new Node(2, "C", 2, 2, 0);
		Node nodeD = new Node(3, "D", 3, 3, 0);
		nodesList.add(nodeA);
		nodesList.add(nodeB);
		nodesList.add(nodeC);
		nodesList.add(nodeD);
		
		InteractionsCube interactionCube = new InteractionsCube();
		interactionCube.addInteraction(1, 0, 1, 1);
		interactionCube.addInteraction(1, 1, 0, 2);
		interactionCube.addInteraction(2, 2, 3, 3);
		interactionCube.addInteraction(2, 3, 0, 4);
		interactionCube.addInteraction(2, 0, 1, 5);
		interactionCube.addInteraction(2, 1, 2, 6);
		interactionCube.addInteraction(3, 2, 3, 7);
		interactionCube.addInteraction(3, 3, 2, 8);
		interactionCube.addInteraction(3, 1, 2, 9);
		interactionCube.addInteraction(3, 2, 1, 10);
		interactionCube.addInteraction(3, 1, 3, 11);
		interactionCube.addInteraction(3, 3, 1, 12);
		
		network = new Network(name, new NodesList(nodesList), interactionCube);
	}
	
	@Test
	public void testParse() {
		network.Network parsedNetwork = InputParser.parse(inputFilename);
		
		assertEquals(network, parsedNetwork);
	}

}
