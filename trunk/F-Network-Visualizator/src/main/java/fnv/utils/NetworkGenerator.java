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
	if (args.length != 5) {
	    System.out.println("Parametri:"
		    + "\n\tnome della rete (e del file di output)"
		    + "\n\tnumero di nodi, "
		    + "\n\tmassimo valore delle coordinate"
		    + "\n\ttrue|false (true = spazio dei nodi piatto, false = spazio dei nodi sferico)"
		    + "\n\tnumero di istanti");
	    System.exit(1);
	}

	String name = args[0];
	int numberOfNodes = Integer.parseInt(args[1]);
	int maxCoordinate = Integer.parseInt(args[2]);
	boolean flat = Boolean.valueOf(args[3]);
	int instants = Integer.parseInt(args[4]);
	ArrayList<Node> nodes = new ArrayList<Node>();
	Random random = new Random();
	InteractionsCube interactionCube = new InteractionsCube();

	for (int i = 0; i < numberOfNodes; i++) {
	    int x;
	    int y;
	    int z;
	    if (flat) {
		x = random.nextInt(maxCoordinate);
		y = 0;
		z = random.nextInt(maxCoordinate);
	    } else {
		int radius = maxCoordinate;
		int centerX = radius;
		int centerY = radius;
		int centerZ = radius;
		double theta = random.nextDouble() * Math.PI * 2;
		double phi = random.nextDouble() * Math.PI;
		x = (int) (centerX + radius * Math.sin(theta) * Math.cos(phi));
		y = (int) (centerY + radius * Math.sin(theta) * Math.sin(phi));
		z = (int) (centerZ + radius * Math.cos(theta));
	    }

	    Node node = new Node(i, String.valueOf(i), x, y, z);
	    nodes.add(node);
	}

	for (int i = 0; i < instants; i++) {
	    int instant = i;
	    /* ogni istante deve avere almeno un'interazione */
	    int sources = Math.max(1, random.nextInt(nodes.size()));
	    
	    for (int j = 0; j < sources; j++) {
		/* ogni istante deve avere almeno un'interazione */
		int target = Math.max(1, random.nextInt(nodes.size()));
		
		int source = nodes.get(random.nextInt(nodes.size())).id;

		for (int k = 0; k < target; k++) {
		    int destination;
		    do {
			destination = nodes.get(random.nextInt(nodes.size())).id;
//			System.out.println(source + " " + destination);
		    } while (source == destination);

		    interactionCube.addInteraction(instant, source, destination, random.nextFloat(), String.valueOf(instant));
		}
	    }
	}

	Network network = new Network(name, new NodesList(nodes), interactionCube, flat);

	XmlGenerator.generate(network);
    }
}
