package fnv.gui.pro;

import fnv.network.InteractionElement;
import fnv.network.Node;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import fnv.network.Network;

import fnv.parser.InputParser;
import processing.core.*;
import peasy.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet {

    //Gestione nodi iniziale
    int i = 0;
    Timer timer;
    //Frame al secondo
    int framerate = 20;
    int instant = 0;
    //Lato dello spazio in box
    int boxN = 10;
    //Lato di un box in px
    int box = 30;
    int mbox = box / 2;//utile in più parti
    //Lato dello spazio in px
    int spaceBox = box * boxN;
    //Lato dei nodi in px
    int nodesize = 10;
    //Visualizza i punti e le linee di controllo
    boolean showControlPoint = false;
    PGraphics3D g3d;
    boolean rotate = true;
    // http://mrfeinberg.com/peasycam/reference/index.html
    private PeasyCam cam;
    //Nodi
    ANode[] node = new ANode[0];
    Network network = new Network();

    public void setNetwork(Network network) {
	this.network = network;
	rotate = true;
	timer.start();

	ArrayList<ANode> alnode = new ArrayList<ANode>();
	for (Node n : network.nodesList.toArray()) {
	    alnode.add(new ANode(n));
	}
	node = alnode.toArray(new ANode[alnode.size()]);
    }

    @Override
    public void setup() {
	size(800, 600, P3D);

	g3d = (PGraphics3D) g;

	//smooth();

	frameRate(framerate);

	//Inizializzazione camera
	cam = new PeasyCam(this, spaceBox / 2, -spaceBox / 2, spaceBox / 2, spaceBox);
	//cam = new PeasyCam(this, spaceBox);
	cam.setMinimumDistance(10);
	cam.setMaximumDistance(700);

	StartTimer();

	try {
	    this.setNetwork(InputParser.parse(new FileInputStream("/home/giacomo/network-test-01.xml")));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	}


    }

    @Override
    public void keyPressed() {
	if (key == CODED) {

	    switch (keyCode) {
		case UP:

		    break;
		case DOWN:

		    break;
		case RIGHT:

		    break;
		case LEFT:

		    break;
		case KeyEvent.VK_PAGE_UP:
		    instant = instant + 1;
		    print(instant);
		    break;
		case KeyEvent.VK_PAGE_DOWN:
		    instant = instant - 1;
		    print(instant);
		    break;

	    }

	} else {
	    int intk = -1;
	    try {
		intk = Integer.parseInt(key + "");
	    } catch (NumberFormatException e) {
	    }

	    if (intk == -1) {
		switch (key) {
		    case 'p':
			showControlPoint = !showControlPoint;
			break;
		}

	    }
	}

    }

    public void StartTimer() {

	timer = new Timer(600, new ActionListener() {

	    int i = 0;

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (i < node.length) {
		    node[i].visible = true;
		    i++;
		} else {
		    timer.stop();
		    rotate = false;
		}
	    }
	});


    }

    //Disegna il Cubo dello spazio
    public void draw3DSpace() {
	pushMatrix();
	//Spazio 3D
	stroke(255);
	noFill();
	translate(spaceBox / 2, -spaceBox / 2, spaceBox / 2);
	box(spaceBox);
	popMatrix();

	for (int i = 0; i <= spaceBox; i += spaceBox / boxN) {
	    stroke(255, 0, 255);
	    line(i, 0, 0, i, 0, spaceBox);//Linee verticali
	    line(0, 0, i, spaceBox, 0, i);//Linee orizzontali
	    stroke(80);
	    line(i, 0, spaceBox, spaceBox, 0, i);//Linee trasversali
	    line(i, 0, 0, 0, 0, i);

	    stroke(0, 0, 255);
	    line(0, i * -1, 0, spaceBox, i * -1, 0);//Parete dietro
	}

    }

    @Override
    public void draw() {
	// Per non mostrare la scena esattamente avanti al punto 0
	// rotateX(PI / 4);
	// rotateY(PI / 4);

	if (rotate) {
	    cam.rotateY(0.02);
	}

	background(0);

	lights();

	draw3DSpace();


	for (int i = 0; i < node.length; i++) {
	    if (node[i].visible) {
		//Nodi
		pushMatrix();

		fill(
			i * 100,
			255,
			255 / (i + 1),
			127//Trasparenza
			);

		//Nodo Quadrato
		stroke(0);
		translate(
			node[i].cx,
			node[i].cy,
			node[i].cz);
		box(nodesize);

		popMatrix();

	    }

	}


	// Collegamenti
	noFill();

	//Disegno i nodi per l'istante giusto
	InteractionElement[] edge = network.getInteractionCube().getAllInteractions(instant);

	for (InteractionElement anEdge : edge) {
	    if (node[anEdge.source].visible && node[anEdge.target].visible) {

		//Scostamento punti controllo
		int pcX = ((node[anEdge.source].ax > node[anEdge.target].ax) ? 20 : -20);
		int pcY = ((node[anEdge.source].ay < node[anEdge.target].ay) ? 20 : -20);
		int pcZ = ((node[anEdge.source].az > node[anEdge.target].az) ? 20 : -20);


		stroke(255);
		/*line(
		node[anEdge.source].cx,
		node[anEdge.source].cy,
		node[anEdge.source].cz,
		node[anEdge.destination].cx,
		node[anEdge.destination].cy,
		node[anEdge.destination].cz
		);*/

		strokeWeight(3);
		bezier(
			//Nodo A
			node[anEdge.source].cx,
			node[anEdge.source].cy,
			node[anEdge.source].cz,
			//Punto Controllo A
			node[anEdge.source].cx,
			node[anEdge.source].cy - (float) anEdge.frequency * mbox,
			node[anEdge.source].cz,
			//Punto Controllo B
			node[anEdge.target].cx - pcX,
			node[anEdge.target].cy - (float) anEdge.frequency * mbox,
			node[anEdge.target].cz - pcZ,
			//Nodo B
			node[anEdge.target].cx,
			node[anEdge.target].cy,
			node[anEdge.target].cz);
		strokeWeight(1);

		if (showControlPoint) {


		    //Linea di controllo
		    stroke(255, 0, 0);
		    line(
			    node[anEdge.source].cx,
			    node[anEdge.source].cy,
			    node[anEdge.source].cz,
			    node[anEdge.source].cx,
			    node[anEdge.source].cy - (float) anEdge.frequency * mbox,
			    node[anEdge.source].cz);
		    //Linea di controllo
		    stroke(0, 0, 255);
		    line(
			    node[anEdge.target].cx,
			    node[anEdge.target].cy,
			    node[anEdge.target].cz,
			    node[anEdge.target].cx - pcX,
			    node[anEdge.target].cy - (float) anEdge.frequency * mbox,
			    node[anEdge.target].cz - pcZ);

		}
	    }

	}

    }

    class ANode {

	boolean visible = false;
	//Posizioni relative
	public int x;
	public int y;
	public int z;
	//Posizioni assolute
	public float ax;
	public float ay;
	public float az;
	//Posizioni assolute centrate rispetto al nodo
	public float cx;
	public float cy;
	public float cz;

	ANode(int x, int y, int z) {
	    this.x = x;
	    this.z = z;
	    this.y = y;
	    this.ax = map(x, 0, boxN, 0, spaceBox);
	    this.ay = map(y, 0, boxN, 0, spaceBox) * -1;
	    this.az = map(z, 0, boxN, 0, spaceBox);
	    this.cx = ax + mbox;
	    this.cy = (map(y, 0, boxN, 0, spaceBox) + mbox) * -1;
	    this.cz = az + mbox;
	}

	ANode(fnv.network.Node n) {
	    this(n.x, n.y, n.z);
	}
    }

    //Mappa tutti i valori relativi nello spazio dei pixel
    public float[] mapAll(int[] toMap) {
	float[] mapped = new float[toMap.length];

	for (int i = 0; i < toMap.length; i++) {
	    mapped[i] = map(toMap[i], 0, boxN, 0, spaceBox);
	}

	return mapped;
    }
}
