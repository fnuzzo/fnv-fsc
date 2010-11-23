package parser;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


import fnv.network.InteractionsCube;
import fnv.network.Network;
import fnv.network.Node;
import fnv.network.NodesList;
import fnv.parser.InputParser;

public class InputParserTest {

    private String inputFilename = "/network-test-01.xml";
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
	interactionCube.addInteraction(1, 0, 1, 1, "1");
	interactionCube.addInteraction(1, 1, 0, 2, "1");
	interactionCube.addInteraction(2, 2, 3, 3, "2");
	interactionCube.addInteraction(2, 3, 0, 4, "2");
	interactionCube.addInteraction(2, 0, 1, 5, "2");
	interactionCube.addInteraction(2, 1, 2, 6, "2");
	interactionCube.addInteraction(3, 2, 3, 7, "3");
	interactionCube.addInteraction(3, 3, 2, 8, "3");
	interactionCube.addInteraction(3, 1, 2, 9, "3");
	interactionCube.addInteraction(3, 2, 1, 10, "3");
	interactionCube.addInteraction(3, 1, 3, 11, "3");
	interactionCube.addInteraction(3, 3, 1, 12, "3");

	network = new Network(name, new NodesList(nodesList), interactionCube);
    }

    @Test
    public void testParse() {
	InputStream inputStream = getClass().getResourceAsStream(inputFilename);

	fnv.network.Network parsedNetwork = InputParser.parse(inputStream);

	assertEquals(network, parsedNetwork);
    }
}
