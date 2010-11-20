package fnv.gui.pro;

import fnv.network.InteractionElement;
import fnv.network.Network;
import fnv.network.Node;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import fnv.network.Network;

import processing.core.*;
import peasy.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet {

    //Frame al secondo
    int framerate = 30;

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

    PGraphics3D g3d;

    // http://mrfeinberg.com/peasycam/reference/index.html
    private PeasyCam cam;

    //Nodi
    ANode[] node = new ANode[0];

    Network network = new Network();

    public void setNetwork(Network network) {
	    this.network = network;

        ArrayList<ANode> alnode = new ArrayList<ANode>();
        for (Node n : network.nodesList.toArray()) {
            alnode.add(new ANode(n));
        }
        node = alnode.toArray(new ANode[0]);
    }

    @Override
    public void setup() {
        size(1024, 768, P3D);

        g3d = (PGraphics3D) g;

        //smooth();

        frameRate(framerate);

        //Inizializzazione camera
        cam = new PeasyCam(this, spaceBox / 2, -spaceBox / 2, spaceBox / 2, spaceBox);
        //cam = new PeasyCam(this, spaceBox);
        cam.setMinimumDistance(10);
        cam.setMaximumDistance(700);

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
                    instant = instant+1;
                    print(instant);
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    instant = instant-1;
                    print(instant);
                    break;

            }

        } else {

            int intk = Integer.parseInt(key+"");
                    node[1] = new ANode(node[1].x, node[1].y, 9);

        }

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
            line(0 + i, 0, 0, 0 + i, 0, spaceBox);//Linee verticali
            line(0, 0, 0 + i, spaceBox, 0, 0 + i);//Linee orizzontali
            stroke(80);
            line(0 + i, 0, spaceBox, spaceBox, 0, 0 + i);//Linee trasversali
            line(0 + i, 0, 0, 0, 0, 0 + i);

            stroke(0, 0, 255);
            line(0, i * -1, 0, spaceBox, i * -1, 0);//Parete dietro
        }

    }


    @Override
    public void draw() {
        // Per non mostrare la scena esattamente avanti al punto 0
        // rotateX(PI / 4);
        // rotateY(PI / 4);

        background(0);

        lights();

        draw3DSpace();


        for (int i = 0; i < node.length; i++) {

            //Nodi
            pushMatrix();

            fill(i * 100, 255, 255 / (i + 1));

            //Nodo Quadrato
            stroke(0);
            translate(
                    node[i].cx,
                    node[i].cy,
                    node[i].cz
            );
            box(nodesize);

            popMatrix();


            // Collegamenti
            noFill();

            /*
            if (i < nodeN && edge[i] > 0) {

                stroke(255);

                //Scostamento punti controllo
                int pcX = ((node[i].ax > node[edge[i]].ax) ? 20 : -20);
                int pcY = ((node[i].ay < node[edge[i]].ay) ? 20 : -20);
                int pcZ = ((node[i].az > node[edge[i]].az) ? 20 : -20);


                strokeWeight(3);
                bezier(
                        //Nodo A
                        node[i].cx,
                        node[i].cy,
                        node[i].cz,
                        //Punto Controllo A
                        node[i].cx,
                        node[i].cy + pcY,
                        node[i].cz,
                        //Punto Controllo B
                        node[edge[i]].cx,
                        node[edge[i]].cy - pcY,
                        node[edge[i]].cz,
                        //Nodo B
                        node[edge[i]].cx,
                        node[edge[i]].cy,
                        node[edge[i]].cz
                );
                strokeWeight(1);

                //Linea di controllo
                stroke(255, 0, 0);
                line(
                        node[i].cx,
                        node[i].cy,
                        node[i].cz,
                        node[i].cx,
                        node[i].cy + pcY,
                        node[i].cz

                );
                //Linea di controllo
                stroke(0, 0, 255);
                line(
                        node[edge[i]].cx,
                        node[edge[i]].cy,
                        node[edge[i]].cz,
                        node[edge[i]].cx,
                        node[edge[i]].cy - pcY,
                        node[edge[i]].cz
                );

            }*/

        }

        //Disegno i nodi per l'istante giusto
        InteractionElement[] interactions = network.getInteractionCube().getAllInteractions(instant);



        for (int i = 0; i < interactions.length; i++) {
            stroke(255);
            line(
                node[interactions[i].source].cx,
                node[interactions[i].source].cy,
                node[interactions[i].source].cz,
                node[interactions[i].destination].cx,
                node[interactions[i].destination].cy,
                node[interactions[i].destination].cz
            );

        }

    }

    class ANode {
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
            this.cy = (map(y, 0, boxN, 0, spaceBox) + mbox)*-1;
            this.cz = az + mbox;
        }

        ANode(fnv.network.Node n) {
            this(n.getX(),n.getY(),n.getZ());
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
