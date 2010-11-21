/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.utils;

import fnv.network.InteractionsCube;
import fnv.network.Network;
import fnv.network.Node;
import fnv.network.NodesList;
import fnv.parser.XmlGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author enrico
 *
 * il primo parametro e' il nome della rete, e del file di output (senza .xml)
 * il secondo parametro e' il numero di nodi nella rete
 * il terzo parametro e' il massimo valore delle coordinate
 * il quarto valore e' il numero di istanti nella rete
 */
public class NetworkGenerator {
    public static void main(String[] args) {
	if (args.length != 4) {
	    System.out.println("Parametri:"
		    + "\n\tnome della rete (e del file di output)"
		    + "\n\tnumero di nodi, "
		    + "\n\tmassimo valore delle coordinate"
		    + "\n\tnumero di istanti");
	    System.exit(1);
	}

	String name = args[0];
	int numberOfNodes = Integer.parseInt(args[1]);
	int maxCoordinate = Integer.parseInt(args[2]);
	int instants = Integer.parseInt(args[3]);
	ArrayList<Node> nodes = new ArrayList<Node>();
	Random random = new Random();
	InteractionsCube interactionCube = new InteractionsCube();

	for (int i = 0; i < numberOfNodes; i++) {
	    int x = random.nextInt(maxCoordinate);
	    int y = random.nextInt(4);
	    int z = random.nextInt(maxCoordinate);
	    Node node = new Node(i, String.valueOf(i), x, y, z);
	    nodes.add(node);
	}

	for (int i = 0; i < instants; i++) {
	    int sources = random.nextInt(nodes.size());

	    for (int j = 0; j < sources; j++) {
		int target = random.nextInt(nodes.size());

		int source = nodes.get(random.nextInt(nodes.size())).id;

		for (int k = 0; k < target; k++) {
		    int destination = nodes.get(random.nextInt(nodes.size())).id;

		    interactionCube.addInteraction(i + 1, source, destination, random.nextDouble());
		}
	    }
	}

	Network network = new Network(name, new NodesList(nodes), interactionCube);

	XmlGenerator.generate(network);
//	System.out.println(network);
    }
}
