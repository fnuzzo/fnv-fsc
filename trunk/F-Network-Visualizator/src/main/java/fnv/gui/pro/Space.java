package fnv.gui.pro;

import fnv.network.InteractionElement;
import fnv.network.Network;
import fnv.network.Node;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import fnv.parser.InputParser;
import processing.core.*;
import peasy.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet {
    Timer timer;

    //Frame al secondo
    int framerate = 20;

    //Font etichette
    PFont f;
    int fontSize = 32;
    String text = "";

    int instant = 0;

    //Lato dello spazio in box
    int boxN;
    /* Lato di un box in px */
    int box = 30;
    /* Meta' del valore di "box". Variabile di comodita' */
    int mbox;
    /* Lato dello spazio in px */
    int spaceBox;

    boolean spaceVisible = true;
    //Evidenzia gli archi entranti al posto degli uscenti
    boolean edgeIn = false;

    //Il nodo correntemente selezionato
    int selected = -1;

    //Nasconde gli archi non selezionati
    boolean edgeVisible = true;

    //Lato dei nodi in px
    int nodesize = 10;

    //Visualizza i punti e le linee di controllo
    boolean showControlPoint = false;

    //Per disegnare in primo piano, ad esempio il mirino
    PMatrix3D currCameraMatrix;
    PGraphics3D g3d;
    /* indica il numero di nodi gia' disegnati quando si importa una rete */
    int nodesDrawn;// = 0;
    /* indica se lo spazio 3d deve ruotare su se stesso (quando si importa una rete) */
    boolean rotate = false;
    // http://mrfeinberg.com/peasycam/reference/index.html
    private PeasyCam cam;

    //Nodi
    ANode[] nodes = new ANode[0];
    Network network = new Network();
    /* indica se e' stata inizializzata una rete */
    boolean networkInitialized = false;

    public void setNetwork(Network network) {
	this.network = network;

	rotate = true;

	initializeBox();

	initializeNodes();

	initializeTimer();
	timer.start();

	networkInitialized = true;
    }

    public void initializeNodes() {
        /* crea gli oggetti che rappresentano i nodi nello spazio 3d */
        ArrayList<ANode> allnodes = new ArrayList<ANode>();
        for (Node n : network.nodesList.toArray()) {
            allnodes.add(new ANode(n));
        }
        nodes = allnodes.toArray(new ANode[allnodes.size()]);
        nodesDrawn = 0;
        colorMode(HSB, nodes.length, 100, 100);
    }

    public void initializeBox() {
        /* setta il lato del cubo che contiene tutto lo spazio 3d */
        boxN = network.maxCoordinate + 1;
        mbox = box / 2;
        spaceBox = box * boxN;

        cam.lookAt(spaceBox / 2, -spaceBox / 2, spaceBox / 2, spaceBox * 2, 2000);
    }

    @Override
    public void setup() {
        size(800, 600, P3D);

        g3d = (PGraphics3D) g;

        f = loadFont("ArialMT-48.vlw");
        textFont(f,fontSize);

        smooth();

        frameRate(framerate);

        //Inizializzazione camera
        cam = new PeasyCam(this, 10,10,10,100);
        //cam = new PeasyCam(this, 300);
        cam.setMinimumDistance(10);
        //cam.setMaximumDistance(700);

        initializeTimer();

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
                    case 'f':
                        println(frameRate);
                        break;
                    case 's':
                        spaceVisible = !spaceVisible;
                        break;
                    case 'e':
                        edgeIn = !edgeIn;
                        break;
                    case 'r':
                        edgeVisible = !edgeVisible;
                        break;
                }
            }
        }

    }

    public void initializeTimer() {

	timer = new Timer(1000, new ActionListener() {
	    int nodesFraction;

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (nodes.length >= 10) {
		    nodesFraction = (int) Math.ceil((double) nodes.length / (double) 10);

		    if ((nodesDrawn + nodesFraction) > nodes.length) {
			nodesFraction = nodes.length - nodesDrawn;
		    }
		} else {
		    nodesFraction = 1;
		}

		if (nodesDrawn != nodes.length) {
		    for (int i = 0; i < nodesFraction; i++) {
			nodes[nodesDrawn + i].visible = true;
		    }
		    nodesDrawn += nodesFraction;
		} else {
		    rotate = false;
		}
	    }
	});
    }

    //Disegna il Cubo dello spazio

    public void draw3DSpace() {
        pushMatrix();
        //Spazio 3D
        stroke(0,0,100);
        noFill();
        translate(spaceBox / 2, -spaceBox / 2, spaceBox / 2);
        box(spaceBox);
        popMatrix();

        for (int i = 0; i <= spaceBox; i += spaceBox / boxN) {
            //stroke(255, 0, 255, 127);
            stroke(0,0,50);
            line(i, 0, 0, i, 0, spaceBox);//Linee verticali
            line(0, 0, i, spaceBox, 0, i);//Linee orizzontali
            stroke(0,0,50);
            line(i, 0, spaceBox, spaceBox, 0, i);//Linee trasversali
            line(i, 0, 0, 0, 0, i);

            stroke(nodes.length/2,100,100);
            line(0, i * -1, 0, spaceBox, i * -1, 0);//Parete dietro
        }

    }

    /* disegna i nodi nello spazio 3d */
    public void drawNodes() {
        for (int i = 0; i < nodes.length; i++) {
	    if (nodes[i].visible) {
		//Nodi
		pushMatrix();

        fill(i,100,100);
		/*fill(
			i * 100,
			255,
			255 / (i + 1),
			127//Trasparenza
			);
        */
		//Nodo Quadrato
		stroke(0);
		translate(
			nodes[i].cx,
			nodes[i].cy,
			nodes[i].cz);
		box(nodesize);

		popMatrix();
	    }
	}
	noFill();
    }

    public void drawEdges() {

	//Disegno i nodi per l'istante giusto
	InteractionElement[] edges = network.getInteractionCube().getAllInteractions(instant);

	for (InteractionElement anEdge : edges) {
        AEdge edge = new AEdge(anEdge);
	    if (nodes[edge.s].visible && nodes[edge.t].visible) {

		//Scostamento punti controllo
		int pcX = ((nodes[edge.s].ax > nodes[edge.t].ax) ? 20 : -20);
		int pcY = ((nodes[edge.s].ay < nodes[edge.t].ay) ? 20 : -20);
		int pcZ = ((nodes[edge.s].az > nodes[edge.t].az) ? 20 : -20);

            if (selected == -1) {
                stroke(edge.c,100,100);
            } else {
                stroke(0,0,100);
            }
            //I collegamenti del nodo selezionato sono più grossi
            boolean ev = false;
            if (edgeIn) {
                if(selected == edge.t) {
                    strokeWeight(3);
                    stroke(edge.c, 100, 100);
                    ev = true;
                }
            } else if(selected == edge.s) {
                strokeWeight(3);
                stroke(edge.c, 100, 100);
                ev = true;
            }

            if (edgeVisible || ev) {
                bezier(
                        //Nodo A
                        nodes[edge.s].cx,
                        nodes[edge.s].cy,
                        nodes[edge.s].cz,
                        //Punto Controllo A
                        nodes[edge.s].cx,
                        nodes[edge.s].cy - edge.af,
                        nodes[edge.s].cz,
                        //Punto Controllo B
                        nodes[edge.t].cx - pcX,
                        nodes[edge.t].cy - edge.af,
                        nodes[edge.t].cz - pcZ,
                        //Nodo B
                        nodes[edge.t].cx,
                        nodes[edge.t].cy,
                        nodes[edge.t].cz);
                strokeWeight(1);
            }

		if (showControlPoint) {

		    //Linea di controllo
		    stroke(edge.t, 100, 100);
		    line(
			    nodes[edge.s].cx,
			    nodes[edge.s].cy,
			    nodes[edge.s].cz,
			    nodes[edge.s].cx,
			    nodes[edge.s].cy - edge.af,
			    nodes[edge.s].cz);
		    //Linea di controllo
		    stroke(edge.s, 100, 100);
		    line(
			    nodes[edge.t].cx,
			    nodes[edge.t].cy,
			    nodes[edge.t].cz,
			    nodes[edge.t].cx - pcX,
			    nodes[edge.t].cy - edge.af,
			    nodes[edge.t].cz - pcZ);
		}
	    }
	}
	noFill();
    }


    @Override
    public void draw() {
	if (rotate) {
	    cam.rotateY(0.02);
	} else {
	    timer.stop();
	}

	background(0);

	lights();

    selected = selectNode();

	/* disegna il cubo che contiene la rete e i nodi solo se e' stata inizializzata una rete */
	if (networkInitialized) {
	    if (spaceVisible) {
		    draw3DSpace();
	    }
	    drawNodes();

	    drawEdges();
	}

	foreground();
    }
    
     private void foreground() {
         float scrX = 0;
         float scrY = 0;

         if (selected != -1) {

             text = network.nodesList.getNode(selected).label;

             stroke(0,0,100);
             scrX = screenX(nodes[selected].cx,nodes[selected].cy,nodes[selected].cz);
             scrY = screenY(nodes[selected].cx,nodes[selected].cy,nodes[selected].cz);

         }

        //Mirino
        // reset camera and disable depth test and blending
        currCameraMatrix = new PMatrix3D(g3d.camera);
        camera();
         double distance = cam.getDistance();
         pushMatrix();

         //translate(0,0,(float) distance);

        noSmooth();
        stroke(0,0,100);
        line(width / 2 - 9, height / 2 , width / 2 + 9, height / 2);
        line(width / 2 , height / 2 - 9, width / 2 , height / 2 + 9);

        if (selected != -1) {
             //Label
             fill(0,0,100);
             text(text, scrX+mbox, scrY-mbox);

         /*line(width-(textWidth(text)/2),
                     fontSize + 2,
                     scrX,
                     scrY
             );*/
        }

        smooth();
        // restore camera
        popMatrix();
        g3d.camera = currCameraMatrix;
    }

    private int selectNode() {
        int selected = -1;
        float mouseDisShortest = -1;
        for (int i = 0; i < nodes.length; i++) {

            float scrX, scrY;
            scrX = screenX( nodes[i].cx, nodes[i].cy, nodes[i].cz );
            scrY = screenY( nodes[i].cx, nodes[i].cy, nodes[i].cz );

            float mouseDis = sqrt( sq(mouseX - scrX) + sq(mouseY - scrY) );

            if((
                    mouseDisShortest == -1 ||//Non ancora inizializzato
                            mouseDis <= mouseDisShortest
            ) && mouseDis < box//Quasi sopra il nodo
                    ){
                selected = i; mouseDisShortest = mouseDis;
            }
        }

        return selected;
    }

    class AEdge {

        public int s;
        public int t;
        public float f;
        public float af;
        public float c;//color

        AEdge(InteractionElement ie) {
            this.s = ie.source;
            this.t = ie.target;
            this.f = (float) ie.frequency;
            this.af = map((float) ie.frequency, (float) network.getInteractionCube().getMinFrequency(), (float) network.getInteractionCube().getMaxFrequency(),0,spaceBox);
            this.c = map((float) ie.frequency,(float) network.getInteractionCube().getMinFrequency(), (float) network.getInteractionCube().getMaxFrequency(),0,nodes.length);
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

        ANode(Node n) {
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

    @Override
    public void size(int i, int i1) {
        super.size(i, i1);
    }
}
