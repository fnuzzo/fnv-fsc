package fnv.gui.pro;

import fnv.gui.Interface;
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
import java.util.Random;
import processing.core.*;
import peasy.*;

import processing.opengl.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet{
    

	public Space(Interface inter){
		this.inter = inter;
	}
	
	
	Interface inter;
	
	Timer rotationTimer, animationTimer;
   
    //Frame al secondo
    int framerate = 20;

    //Font etichette
    PFont f;
    int fontSize = 32;
    String text = "";

    private int instant = 0;

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

    private int WIDTH;
    private int HEIGHT;

    private int animationTimeSec = 1;
      
    //Nodi
    ANode[] nodes = new ANode[0];
    Network network = new Network();
    /* indica se e' stata inizializzata una rete */
    boolean networkInitialized = false;

    //Archi
    AEdge[][] edges = new AEdge[0][0];

    public int getAnimationTime() {
	return animationTimeSec;
    }

    public void setAnimationTime(int animationTimeSec) {
	this.animationTimeSec = animationTimeSec;
	updateAnimationTimer(animationTimeSec);
    }



    public void incrementInstant() {
	if (network != null) {
	    if (instant < (network.getNumberOfInstants()-1)) {
		instant++;
	    }
	}
    }

    public void decrementInstant() {
	if (instant > 0) {
	    instant--;
	}
    }

    public void setInstant(int sliderValue) {
	if (network != null) {
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
            InteractionElement[] eds = network.getInteractionCube().getAllInteractions(i);
            AEdge[] aEdges = new AEdge[eds.length];
            for (int j = 0; j < eds.length; j++) {
                aEdges[j] = new AEdge(eds[j]);
            }
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
        cam.setResetOnDoubleClick(false);

        initializeTimer();

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
    
    @Override
    public void mouseClicked() {
        println(frameRate+" "+selected);

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
                	resize(new Dimension(800, 600));
                    println(instant);
                    break;
                case LEFT:
                	resize(new Dimension(200, 200));
                    println(instant);
                    break;
                case KeyEvent.VK_PAGE_UP:
		    incrementInstant();
                    print(instant);
                    break;
                case KeyEvent.VK_PAGE_DOWN:
		    decrementInstant();
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
                        showControlPoint = !showControlPoint;  //Da lasciare per Debug
                        break;
                    case 'f':
                        println(frameRate);
                        break;
                    case 's':
                        spaceVisible = !spaceVisible;  //ok
                        break;
                    case 'e':
                        edgeIn = !edgeIn;  //ok
                        break;
                    case 'r':
                    	edgeVisible = !edgeVisible;  //ok
                        break;
                }
            }
        }

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
    

    public void setOptions(String options){
    	if(options.equals("edgeIn"))
    		edgeIn = !edgeIn;
    	else if(options.equals("edgeOut"))
    		edgeVisible = !edgeVisible;
    	else if(options.equals("spaceVisible"))
    		spaceVisible = !spaceVisible;
    }

    private void setAnimationTime() {
	//if (networkInitialized) {

	 animationTimer = new Timer(animationTimeSec * 1000, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (networkInitialized) {
		if (network.getNumberOfInstants() != instant) {
		    incrementInstant();
		    int value = (instant * 100) / network.getNumberOfInstants();
		    inter.setValuejstime(value);
		} else {
		    animationTimer.stop();
		}
		}
	    }
	});
	//}
    }

    public void updateAnimationTimer(int newAnimationTimeSec) {
	animationTimer.setDelay(newAnimationTimeSec);
    }
    
    
    public void optionsTime(String options){
    	if(options.equals("play"))
    		animationTimer.start();
    	else if (options.equals("pause"))
    		animationTimer.stop();
    	else if (options.equals("stop")){
    			//instant = network.getNumberOfInstants();
	    instant = 0;
    			animationTimer.stop();
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
		    //setAnimationTime();
		}
	    }
	});
    }

    //Disegna il Cubo dello spazio

    public void draw3DSpaceFlat() {
//        pushMatrix();
        //Spazio 3D
//        stroke(0,0,100);
//        noFill();
//        translate(space / 2, -space / 2, space / 2);
//        box(spaceBox);
//        popMatrix();

        for (int i = 0; i <= space; i += space / boxN) {
            //stroke(255, 0, 255, 127);
            stroke(0,0,50);
            line(i, 0, 0, i, 0, space);//Linee verticali
            line(0, 0, i, space, 0, i);//Linee orizzontali
            stroke(0,0,50);
//            line(i, 0, spaceBox, spaceBox, 0, i);//Linee trasversali
//            line(i, 0, 0, 0, 0, i);
//
//            stroke(nodes.length/2,100,100);
//            line(0, i * -1, 0, spaceBox, i * -1, 0);//Parete dietro
        }

    }

    public void draw3DSpaceSphere() {
        pushMatrix();
        translate(space / 2, -space / 2, space / 2);
        sphere(space / 2);

//	ellipse(network.maxCoordinate, network.maxCoordinate, space, space);
        popMatrix();

	//drawSphere(space / 2, 10);
//	draw3dCircle();

//	for (int i = network.maxCoordinate * 2; i > 0; i -= box) {
//	    ellipse(network.maxCoordinate, i, space, space);
//
//	}
    }

 



    void drawSphere(int heightSteps, int points) {
	//int radius = space / 2;
	int sphereRow = 1; //(int)random(8);
	int sphereRowInc = 2;
	int sphereColumn = 1; //(int)random(8);
	int sphereColumnInc = 2;
//	boolean isSelected = true;


	float cx[][] = new float[heightSteps][points], cy[][] = new float[heightSteps][points], cz[][] = new float[heightSteps][points];

//	for (int i = 0; i < heightSteps; i++) {
//	    float czTmp = radius * cos(i * TWO_PI / (heightSteps - 1) / 2);
//	    float radiusTmp = sqrt(sq(radius) - sq(czTmp));
//	    for (int j = 0; j < points; j++) {
//		float cxTmp = radiusTmp * sin(j * TWO_PI / points + rotZ);
//		float cyTmp = radiusTmp * cos(j * TWO_PI / points + rotZ);
//		cx[i][j] = cxTmp + x;
//		cy[i][j] = cyTmp + y;
//		cz[i][j] = czTmp + 0;
//		//stroke(255); strokeWeight(2);
//		//point(cxTmp, cyTmp, czTmp);
//	    } // end for j
	    //cz += heightStepsDis;
//	} // end for i

	// draw sphere
	if (frameCount % 4 == 0) {
	    sphereRow += sphereRowInc;
	    if (sphereRow >= cx.length - 1 || sphereRow <= 0) {
		sphereRowInc *= -1;
	    }

	    sphereColumn += sphereColumnInc;
	    if (sphereColumn >= points) {
		sphereColumn = 1;
	    }
	}
	for (int i = 0; i < cx.length - 1; i++) {
	    for (int j = 0; j < cx[i].length; j++) {
//		fill((int)(255 - (i + 1) * 255 / heightSteps * 1.0));
//		if (i == sphereRow || j == sphereColumn) {
//		    fill((float)(255 - (i + 1) * 255 / heightSteps * 1.0), (float)0, (float)0);
//		}
//		if (isSelected) {
//		    fill((float)(255 - (i + 1) * 255 / heightSteps * 1.0), (float)(150 - (i + 1) * 150 / heightSteps * 1.0), (float)0);
//		}
//		if (isSelected && (i == sphereRow || j == sphereColumn)) {
//		    fill((float)(255 - (i + 1) * 255 / heightSteps * 1.0));
//		}
		//else continue;
//		if ((j) % 2 == 0 && i % 2 == 0) {
//		    continue;
//		}
		float scaleSphere = 1;
		int indexNext = j + 1;
		if (j == cx[i].length - 1) {
		    indexNext = 0;
		}
		float x1 = cx[i + 0][j + 0] * scaleSphere, y1 = cy[i + 0][j + 0] * scaleSphere, z1 = cz[i + 0][j + 0] * scaleSphere;
		float x2 = cx[i + 0][indexNext] * scaleSphere, y2 = cy[i + 0][indexNext] * scaleSphere, z2 = cz[i + 0][indexNext] * scaleSphere;
		float x3 = cx[i + 1][indexNext] * scaleSphere, y3 = cy[i + 1][indexNext] * scaleSphere, z3 = cz[i + 1][indexNext] * scaleSphere;
		float x4 = cx[i + 1][j + 0] * scaleSphere, y4 = cy[i + 1][j + 0] * scaleSphere, z4 = cz[i + 1][j + 0] * scaleSphere;

		noStroke();
		beginShape(TRIANGLE_STRIP);
		vertex(x1, y1, z1);
		vertex(x2, y2, z2);
		vertex(x4, y4, z4);
		vertex(x3, y3, z3);
		endShape();
		//stroke(0);
		//strokeWeight((float)0.2);
		//line(x1, y1, z1, x2, y2, z2); line(x2, y2, z2, x3, y3, z3); line(x3, y3, z3, x4, y4, z4); line(x4, y4, z4, x1, y1, z1);
	    } // end for j
	} // end for i
    } // end void drawSphere(int heightSteps, int points)

    /* disegna i nodi nello spazio 3d */
    public void drawNodes() {
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
	    }
	}
	noFill();
    }

    public void drawEdges() {

	//Disegno i nodi per l'istante giusto
	//InteractionElement[] edges = network.getInteractionCube().getAllInteractions(instant);

	for (AEdge edge : edges[instant]) {
	    if (nodes[edge.s].visible && nodes[edge.t].visible) {

            if (selected == -1) {
                stroke(edge.c,100,100);//Archi colorati
            } else {
                stroke(0,0,100);//Archi bianchi
            }
            //I collegamenti del nodo selezionato sono più grossi
            boolean ev = false;
            if (//Archi visualizzati colorati se
                    (edgeIn && selected == edge.t)//Nodo selezionato è una destinazione
                    ^
                    (!edgeIn && selected == edge.s)//Nodo selezionato è una sorgente
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

                //Aereoplano
                int t = (frameCount % framerate)  ;
                pushMatrix();
                translate(
                        edge.beizPx[t],
                        edge.beizPy[t],
                        edge.beizPz[t]
                        );
                fill(0, 0, 100);
                //sphere(2);
                box(2);
                popMatrix();
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

    @Override
    public void draw() {
    	
	if (rotate) {
	    cam.rotateY(0.045);
	} else {
	    rotationTimer.stop();
	}

	//background(0);
	background(62, 62, 62);

	lights();

        if (mousePressed) {
            selected = selectNode();
        }

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
         pushMatrix();

        noSmooth();
        stroke(0,0,100);
        line(width / 2 - 9, height / 2 , width / 2 + 9, height / 2);
        line(width / 2 , height / 2 - 9, width / 2 , height / 2 + 9);

        if (selected != -1) {
             //Label
             fill(0,0,100);
             text(text, scrX+mbox, scrY-mbox);
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

            //punti controllo sorgente
            if (network.flat) {
                this.cpsx = ns.cx;
                this.cpsy = ns.cy - this.af;
                this.cpsz = ns.cz;
            } else {
                /*this.cpsx = (ns.ax > nt.ax) ? ns.cx + 50 : ns.cx -50;
                this.cpsy = (ns.ay < nt.ay) ? ns.cy + 50 : ns.cy -50;
                this.cpsz = (ns.az > nt.az) ? ns.cz + 50 : ns.cz -50;*/
                this.cpsx = ns.cx + 50 ;
                this.cpsy = ((ns.y + nt.y) < boxN) ? ns.cy + space : ns.cy -space;
                this.cpsz = ns.cz ;
            }
            //punti di controllo target
            if (network.flat) {
                this.cptx = nt.cx - pcX;
                this.cpty = nt.cy - this.af;
                this.cptz = nt.cz - pcZ;
            } else {
                /*this.cptx = (ns.ax < nt.ax) ? nt.cx + 50 : nt.cx -50;
                this.cpty = (ns.ay > nt.ay) ? nt.cy + 50 : nt.cy -50;
                this.cptz = (ns.az < nt.az) ? nt.cz + 50 : nt.cz -50;*/
                this.cptx = nt.cx + 50;
                this.cpty = ((ns.y + nt.y) < boxN) ? nt.cy + space : nt.cy - space;
                this.cptz = nt.cz;

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
            this.ax = map(x, 0, boxN, 0, space);
            this.ay = map(y, 0, boxN, 0, space) * -1;
            this.az = map(z, 0, boxN, 0, space);
            this.cx = ax + mbox;
            this.cy = (map(y, 0, boxN, 0, space) + mbox) * -1;
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
            mapped[i] = map(toMap[i], 0, boxN, 0, space);
        }

        return mapped;
    }




}
