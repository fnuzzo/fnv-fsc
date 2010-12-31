package fnv.gui.pro;

import fnv.gui.Interface;
import fnv.gui.InterfaceFrame;
import fnv.network.InteractionElement;
import fnv.network.Network;
import fnv.network.Node;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.Timer;

import fnv.parser.InputParser;
import fnv.utils.Constants;
import java.util.Random;
import processing.core.*;
import peasy.*;
import fullscreen.*;

import processing.opengl.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet{
    
    //Interface swingInterface;
    InterfaceFrame swingInterface;
    Timer rotationTimer, animationTimer;
    //Frame al secondo
    final int framerate = 20;
    //Font etichette
    PFont f;
    int fontSize = 32;
    private int instant = -1;
    //Lato dello spazio in box
    int boxN;
    /* Lato di un box in px */
    int box = 30;
    /* Meta' del valore di "box". Variabile di comodita' */
    int mbox;
    /* Lato dello spazio in px */
    int space;
    boolean spaceVisible = true;
    //Evidenzia gli archi entranti al posto degli uscenti
    boolean edgeIn = false;
    //Almeno un nodo correntemente selezionato
    boolean selected = false;
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
//    private int WIDTH;
//    private int HEIGHT;
    private int spaceWidth;
    private int spaceHeight;
    private double animationTimeSec = 1;
    //Nodi
    ANode[] nodes = new ANode[0];
    Network network = new Network();
    /* indica se e' stata inizializzata una rete */
    boolean networkInitialized = false;
    //Archi
    AEdge[][] edges = new AEdge[0][0];

//    public Space(Interface inter) {
//	this.swingInterface = inter;
//    }
    public Space(InterfaceFrame inter, int width, int height) {
    super();
	this.swingInterface = inter;
	spaceWidth = width;
	spaceHeight = height;
    }

    public double getAnimationTime() {
	return animationTimeSec;
    }

    public void setAnimationTime(double animationTimeSec) {
	this.animationTimeSec = animationTimeSec;
	updateAnimationTimer(animationTimeSec);
    }

    public int getInstant() {
	return instant;
    }

    public void incrementInstant() {
	if (networkInitialized) {
	    //if (instant < (network.getNumberOfInstants()-1)) {
	    if (instant < network.getNumberOfInstants()) {
		instant++;
		swingInterface.instantChanged();
	    }
	}
    }

    public void decrementInstant() {
	if (instant > 0) {
	    instant--;
	    swingInterface.instantChanged();
	}
    }

    public void setInstant(int sliderValue) {
	if (networkInitialized) {
	    instant = (network.getNumberOfInstants() * sliderValue) / 100;
	}
    }

    public void setNetwork(Network network) {
	this.network = network;
	
	rotate = true;

	initializeBox();
	initializeNodes();
	initializeTimer();
	setAnimationTime();
	
	rotationTimer.start();

	networkInitialized = true;
    }

    public Network getNetwork() {
	return network;
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

        //Inizializza archi
        ArrayList<AEdge[]> aedgeList = new ArrayList<AEdge[]>();
        for (int i = 0; i < network.getNumberOfInstants(); i++) {
	    ArrayList<InteractionElement> eds2 = network.getInteractionCube().getInstant(i).getAllInteractions();
	    AEdge[] aEdges = new AEdge[eds2.size()];
            for (int j = 0; j < eds2.size(); j++) {
                aEdges[j] = new AEdge(eds2.get(j));
            }
	    //InteractionElement[] eds = network.getInteractionCube().getInstant(i).getAllInteractions().toArray(new InteractionElement[0]);
            //AEdge[] aEdges = new AEdge[eds.length];
            //for (int j = 0; j < eds.length; j++) {
            //    aEdges[j] = new AEdge(eds[j]);
            //}
            aedgeList.add(i,aEdges);
        }
        edges = aedgeList.toArray(new AEdge[0][aedgeList.size()]);
    }

    public void initializeBox() {
        /* setta il lato del cubo che contiene tutto lo spazio 3d */
        boxN = network.maxCoordinate + 1;
        mbox = box / 2;
        space = box * boxN;

        cam.lookAt(space / 2, -space / 2, space / 2, space * 2, 2000);

    }

    @Override
    public void setup() {

	    size(spaceWidth, spaceHeight, P3D);

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
        cam.setResetOnDoubleClick(false);

        initializeTimer();

        cursor(MOVE);

      /*  try {
            this.setNetwork(InputParser.parse(new FileInputStream("./network-test-01.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/

    }

//    public void resizePanel(int w, int h){
//    	setSize(w, h);
//    	draw();
//
//    }
    
    private void toggleEdgesIn() {
	    edgeIn = !edgeIn;
    }

    private void toggleEdges() {
	    edgeVisible = !edgeVisible;
    }

    private void toggleSpaceVisible() {
	    spaceVisible = !spaceVisible;
    }

    @Override
    public void keyPressed() {
        if (key == CODED) {
            switch (keyCode) {
                case UP:
                    cam.rotateX(0.045);
                    break;
                case DOWN:
                    cam.rotateX(-0.045);
                    break;
                case RIGHT:
                    cam.rotateY(0.045);
                    break;
                case LEFT:
                    cam.rotateY(-0.045);
                    break;
                case KeyEvent.VK_PAGE_UP:
		            incrementInstant();
                    break;
                case KeyEvent.VK_PAGE_DOWN:
		            decrementInstant();
                    break;
                case KeyEvent.VK_CONTROL:
                    cursor(HAND);
                    break;
                case KeyEvent.VK_SHIFT:
                    cursor(CROSS);
                    break;
                default:
                    println(keyCode);
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
                        showControlPoint = !showControlPoint;  //Da lasciare per Debug
                        break;
                    case 'f':
                        println(frameRate);
                        break;
                    case 's':
                        toggleSpaceVisible();  //ok
                        break;
                    case 'e':
                        toggleEdgesIn();  //ok
                        break;
                    case 'r':
                    	toggleEdges();  //ok
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased() {
        if (key == CODED) {
            switch (keyCode) {
                case KeyEvent.VK_CONTROL:
                    cursor(MOVE);
                break;
                case KeyEvent.VK_SHIFT:
                    cursor(MOVE);
                break;
            }

        }
    }

    public void setOptions(String options){
    	if(options.equals(Constants.BUTTON_EDGEIN_ACTIONCOMMAND))
    		toggleEdgesIn();
    	else if(options.equals(Constants.BUTTON_ALLEDGES_ACTIONCOMMAND))
    		toggleEdges();
    	else if(options.equals(Constants.BUTTON_STRUCTURE_ACTIONCOMMAND))
    		toggleSpaceVisible();
    }
    
    public void resizeSpace(Dimension d){    	
    	//resize(new Dimension(200, 200));
	//size(800, 600, OPENGL);
	//size(d.height, d.width, P3D);
	//resize(d);

//	setSize(d);
//	setMinimumSize(d);
//	setMaximumSize(d);
//	setPreferredSize(d);
	resizeRenderer(d.width, d.height);
    }

    private void setAnimationTime() {
	animationTimer = new Timer((int) (animationTimeSec * 1000), new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (networkInitialized) {
		    //TODO debug
		    System.out.println("animationTimer.getDelay(): " + animationTimer.getDelay() + " instant = " + instant);

		    incrementInstant();
		    int value = (instant * 100) / network.getNumberOfInstants();
		    swingInterface.setJSliderValue(value);

		    if (instant == network.getNumberOfInstants()) {
			animationTimer.stop();
		    }
		}
	    }
	});
    }

    public void updateAnimationTimer(double newAnimationTimeSec) {
	animationTimer.setDelay((int) (newAnimationTimeSec * 1000));
    }
    
    public void optionsTime(String options) {
	if (!rotate) {
	    if (options.equals(Constants.ICON_PLAY_ACTIONCOMMAND)) {
		animationTimer.start();
	    } else if (options.equals(Constants.ICON_PAUSE_ACTIONCOMMAND)) {
		animationTimer.stop();
	    } else if (options.equals(Constants.ICON_STOP_ACTIONCOMMAND)) {
		instant = 0;
		animationTimer.stop();
	    }
	}
    }

    public void initializeTimer() {

	rotationTimer = new Timer(1000, new ActionListener() {
	    int nodesFraction;

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int seconds = 8;
		if (nodes.length >= seconds) {
		    nodesFraction = (int) Math.ceil((double) nodes.length / (double) seconds);

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

    public void draw3DSpaceFlat() {
	for (int i = 0; i <= space; i += space / boxN) {
	    //stroke(255, 0, 255, 127);
	    stroke(0, 0, 50);
	    line(i, 0, 0, i, 0, space);//Linee verticali
	    line(0, 0, i, space, 0, i);//Linee orizzontali
	    stroke(0, 0, 50);
	}

    }

    public void draw3DSpaceSphere() {
	pushMatrix();
	translate(space / 2, -space / 2, space / 2);
    //sphereDetail(3);
    //stroke(1, 100, 100);
    //fill(1, 100, 100);
	sphere(space / 2);

	popMatrix();
    }

    /* disegna i nodi nello spazio 3d */
    public void drawNodes() {
        boolean recalcSelection = false; //Così so se sono stati cambiate le selezioni
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].visible) {
                //Nodi
                pushMatrix();

                fill(i, 100, 100);
                //Nodo Quadrato
                stroke(0);
                translate(
                        nodes[i].cx,
                        nodes[i].cy,
                        nodes[i].cz);
                box(nodesize);

                popMatrix();
                if (mousePressed && keyPressed && selectNode(nodes[i]) && key == CODED) {
                    switch (keyCode) {
                        case KeyEvent.VK_CONTROL:
                            nodes[i].selected = true;
                            recalcSelection = true;
                            break;
                        case KeyEvent.VK_SHIFT:
                            nodes[i].selected = false;
                            recalcSelection = true;
                            break;
                        //default:
                        //  println(keyCode);
                        //break;
                    }

                }


            }
        }
        if (recalcSelection) {
            //Almeno un nodo selezionato
            for (int i = 0; i < nodes.length; i++) {
                selected = selected || nodes[i].selected;
            }
        }
        noFill();
    }

    public boolean isNodeSelect() {
        boolean selected = false;

        return selected;
    }

    public void drawEdges() {
        if (instant != -1) {
            int displayedInstant;
            if (instant < edges.length) {
                displayedInstant = instant;
            } else {
                displayedInstant = edges.length - 1;
            }

	    //Disegno i nodi per l'istante giusto
	    //InteractionElement[] edges = network.getInteractionCube().getAllInteractions(instant);

	    for (AEdge edge : edges[displayedInstant]) {
		if (nodes[edge.s].visible && nodes[edge.t].visible) {

		    if (selected) {
			   stroke(0, 0, 100, 127);//Archi bianchi
		    } else {
               stroke(edge.c, 100, 100);//Archi colorati
		    }
		    //I collegamenti del nodo selezionato sono più grossi
		    boolean ev = false;
		    if (//Archi visualizzati colorati se
			    (edgeIn && nodes[edge.t].selected)//Nodo selezionato è una destinazione
			    ^ (!edgeIn && nodes[edge.s].selected)//Nodo selezionato è una sorgente
			    ) {
			    strokeWeight(3);
			    stroke(edge.c, 100, 100);
			    ev = true;
		    }

		    if (edgeVisible || ev) {
			    noFill();
			    bezierDetail(40);//Aumenta dettaglio grafico dei collegamenti
			    bezier(
				//Nodo A
				    edge.ns.cx,
				    edge.ns.cy,
				    edge.ns.cz,
				//Punto Controllo A
				    edge.cpsx,
				    edge.cpsy,
				    edge.cpsz,
				//Punto Controllo B
				    edge.cptx,
				    edge.cpty,
				    edge.cptz,
				//Nodo B
				    edge.nt.cx,
				    edge.nt.cy,
				    edge.nt.cz);
			    strokeWeight(1);

                if (ev || !selected) {
			        //Aereoplano
			        int t = (frameCount % framerate);
			        pushMatrix();
			        translate(
				        edge.beizPx[t],
				        edge.beizPy[t],
				        edge.beizPz[t]);
			        fill(0, 0, 100);
                    sphereDetail(3);
			        sphere(2);
			        popMatrix();
                }
		    }

		    if (showControlPoint) {

			//Linea di controllo
			stroke(edge.t, 100, 100);
			line(
				edge.ns.cx,
				edge.ns.cy,
				edge.ns.cz,
				edge.cpsx,
				edge.cpsy,
				edge.cpsz);
			//Linea di controllo
			stroke(edge.s, 100, 100);
			line(
				edge.nt.cx,
				edge.nt.cy,
				edge.nt.cz,
				edge.cptx,
				edge.cpty,
				edge.cptz);
			noStroke();
			//Palline sul punto di controllo
			pushMatrix();
			translate(
				edge.cpsx,
				edge.cpsy,
				edge.cpsz);
			fill(edge.s, 100, 100);
			sphere(2);
			popMatrix();

			pushMatrix();
			translate(
				edge.cptx,
				edge.cpty,
				edge.cptz);
			fill(edge.t, 100, 100);
			sphere(2);
			popMatrix();
		    }
		}
	    }
	    noFill();
	}
    }



    @Override
    public void draw() {
    	
	if (rotate) {
	    cam.rotateY(0.045);
	} else {
	    rotationTimer.stop();
	}

	background(0);
	//background(62, 62, 62);

	lights();

        /*if (mousePressed  && keyPressed) {
            //print(selected);
            int newSelected = selectNode();

            if (key == CODED) {
                switch (keyCode) {
                    case KeyEvent.VK_CONTROL:
                        if (newSelected != -1) selected = newSelected;
                        break;
                    case KeyEvent.VK_SHIFT:
                        selected = -1;//Deseleziono
                        break;
                    //default:
                      //  println(keyCode);
                        //break;
                }
            }
            //println(" -> " + selected);
        }*/

	/* disegna il cubo che contiene la rete e i nodi solo se e' stata inizializzata una rete */
	if (networkInitialized) {
	    if (spaceVisible) {
		if (network.flat) {
		    draw3DSpaceFlat();
		} else {
		    draw3DSpaceSphere();
		}
		    
	    }
	    drawNodes();

	    drawEdges();
	}

	foreground();
    }
    
     private void foreground() {

        //Mirino
        // reset camera and disable depth test and blending
        currCameraMatrix = new PMatrix3D(g3d.camera);
        camera();
         pushMatrix();

        noSmooth();
        stroke(0,0,100);
        line(width / 2 - 9, height / 2 , width / 2 + 9, height / 2);
        line(width / 2 , height / 2 - 9, width / 2 , height / 2 + 9);

         //Label
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].selected) {
                fill(0,0,100);
                float labelX = screenX(nodes[i].cx,nodes[i].cy,nodes[i].cz)+mbox;
                float labelY = screenY(nodes[i].cx,nodes[i].cy,nodes[i].cz)-mbox;
                text(nodes[i].label, labelX, labelY);
            }
        }

        smooth();
        // restore camera
        popMatrix();
        g3d.camera = currCameraMatrix;
    }

    private boolean selectNode(ANode node) {
        float scrX, scrY;
        scrX = screenX( node.cx, node.cy, node.cz );
        scrY = screenY( node.cx, node.cy, node.cz );
        float mouseDis = sqrt( sq(mouseX - scrX) + sq(mouseY - scrY) );
        if (mouseDis < box) {
            return true;
        } else {
            return false;
        }
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
            ) &&
                    (mouseDis < box)//Quasi sopra il nodo
                    ){
                selected = i;
		mouseDisShortest = mouseDis;
            }
        }

        return selected;
    }

    class AEdge {

        public int s;//indice nodo sorgente
        public int t;//indice nodo target
        public ANode ns;
        public ANode nt;
        //Coordinate punti di controllo
        public float cpsx;
        public float cpsy;
        public float cpsz;
        public float cptx;
        public float cpty;
        public float cptz;

        //bezierPoint per disegnare aereoplani
        public float[] beizPx;
        public float[] beizPy;
        public float[] beizPz;

        public float f;
        public float af;
        public float c;//color

        AEdge(InteractionElement ie) {
            this.s = ie.source;
            this.ns = nodes[s];
            this.t = ie.target;
            this.nt = nodes[t];
            this.f = ie.frequency;
            this.af = map(ie.frequency, network.getInteractionCube().getMinFrequency(), network.getInteractionCube().getMaxFrequency(),0,space);
            this.c = map(ie.frequency, network.getInteractionCube().getMinFrequency(), network.getInteractionCube().getMaxFrequency(),0,nodes.length);

            //Scostamento punti controllo
		    int pcX = ((ns.ax > nt.ax) ? 20 : -20);
		    int pcY = ((ns.ay < nt.ay) ? 20 : -20);
		    int pcZ = ((ns.az > nt.az) ? 20 : -20);

            if (network.flat) {
                //punti controllo sorgente
                this.cpsx = ns.cx;
                this.cpsy = ns.cy - this.af;
                this.cpsz = ns.cz;
                //punti di controllo target
                this.cptx = nt.cx - pcX;
                this.cpty = nt.cy - this.af;
                this.cptz = nt.cz - pcZ;

            } else {

                this.cpsx = ((ns.x + nt.x) > boxN) ? ns.cx + this.af+10 : ns.cx - this.af+10;
                this.cpsy = ((ns.y + nt.y) > boxN) ? ns.cy - this.af+10 : ns.cy + this.af+10;
                this.cpsz = ((ns.z + nt.z) > boxN) ? ns.cz + this.af+10 : ns.cz - this.af+10;

                this.cptx = ((ns.x + nt.x) > boxN) ? nt.cx + this.af+10 : nt.cx - this.af+10;
                this.cpty = ((ns.y + nt.y) > boxN) ? nt.cy - this.af+10 : nt.cy + this.af+10;
                this.cptz = ((ns.z + nt.z) > boxN) ? nt.cz + this.af+10 : nt.cz - this.af+10;

            }

            //beizerPoint
            this.beizPx  = new float[framerate];
            this.beizPy  = new float[framerate];
            this.beizPz  = new float[framerate];

            for (int i = 0; i < framerate; i++) {
                float t = i /  (float) framerate ;
                this.beizPx[i] = bezierPoint(
                        ns.cx,
                        cpsx,
                        cptx,
                        nt.cx,
                        t);
                this.beizPy[i] = bezierPoint(
                        ns.cy,
                        cpsy,
                        cpty,
                        nt.cy,
                        t);
                this.beizPz[i] = bezierPoint(
                        ns.cz,
                        cpsz,
                        cptz,
                        nt.cz,
                        t);
            }


        }
    }

    class ANode {

        boolean visible = false;

        boolean selected = false;

        String label = "";

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

        ANode(int x, int y, int z, String label) {
            this.x = x;
            this.z = z;
            this.y = y;
            this.ax = map(x, 0, boxN, 0, space);
            this.ay = map(y, 0, boxN, 0, space) * -1;
            this.az = map(z, 0, boxN, 0, space);
            this.cx = ax + mbox;
            this.cy = (map(y, 0, boxN, 0, space) + mbox) * -1;
            this.cz = az + mbox;
            this.label = label;

        }

        ANode(Node n) {
            this(n.x, n.y, n.z, n.label);
        }


    }

}
