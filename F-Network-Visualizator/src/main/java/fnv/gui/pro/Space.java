package fnv.gui.pro;

import java.awt.event.KeyEvent;

import processing.core.*;
import peasy.*;

/**
 * Created by IntelliJ IDEA. User: giacomo Date: Nov 1, 2010 Time: 3:12:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Space extends PApplet {

    //Lato dello spazio in box
    int boxN = 10;
    //Lato di un box in px
    int box = 30;
    int mbox = box / 2;//utile in più parti
    //Lato dello spazio in px
    int spaceBox = box * boxN;

    //Numero nodi
    int nodeN = 4;
    //Lato dei nodi in px
    int nodesize = 10;
    //Distanza fra nodi
    int nodeDist = 15;

    int[] nodeX = new int[nodeN];
    int[] nodeY = new int[nodeN];
    int[] nodeZ = new int[nodeN];

    float[] mappedX = new float[nodeN];
    float[] mappedY = new float[nodeN];
    float[] mappedZ = new float[nodeN];

    int[] edge = new int[nodeN];

    // Punti di controllo
    float[][] edgeX = new float[nodeN][nodeN];
    float[][] edgeY = new float[nodeN][nodeN];
    float[][] edgeZ = new float[nodeN][nodeN];

    // http://mrfeinberg.com/peasycam/reference/index.html
    private PeasyCam cam;

    public void setup() {
        size(1024, 768, P3D);

        smooth();

        frameRate(30);

        //Inizializzazione camera
        cam = new PeasyCam(this, spaceBox / 2, -spaceBox / 2, spaceBox / 2, spaceBox);
        cam.setMinimumDistance(10);
        cam.setMaximumDistance(700);


        nodeY[0] = nodeX[0] = 6;
        nodeZ[0] = 1;
        nodeX[1] = nodeY[1] = 2;
        nodeZ[1] = 1;
        nodeX[2] = nodeY[2] = nodeZ[2] = 2;
        nodeZ[3] = 4;
        nodeX[3] = nodeY[3] = 3;

        edge[0] = 2;
        edge[1] = 0;
        edge[2] = -1;
        edge[3] = 2;


        mappedX = mapAll(nodeX);
        mappedY = mapAll(nodeY);
        mappedZ = mapAll(nodeZ);
    }

    public void keyPressed() {
        if (key == CODED) {

            switch (keyCode) {
                case UP:
                    edgeY[1][0] -= 5;
                    edgeY[0][1] -= 5;
                    break;
                case DOWN:
                    edgeY[1][0] += 5;
                    edgeY[0][1] += 5;
                    break;
                case RIGHT:
                    edgeX[1][0] += 5;
                    edgeX[0][1] -= 5;
                    break;
                case LEFT:
                    edgeX[1][0] -= 5;
                    edgeX[0][1] += 5;
                    break;
                case KeyEvent.VK_PAGE_UP:
                    edgeZ[1][0] += 5;
                    edgeZ[0][1] += 5;
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    edgeZ[1][0] -= 5;
                    edgeZ[0][1] -= 5;
                    break;
            }

        } else {

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
            line(0, i * -1, 0, spaceBox, i * -1, 0);
        }


    }


    public void draw() {
        // Per non mostrare la scena esattamente da sopra
        // rotateX(PI / 4);
        // rotateY(PI / 4);

        background(0);

        draw3DSpace();


        for (int i = 0; i < nodeN; i++) {
            pushMatrix();
            fill(i * 100, 255, 255 / (i + 1));
            stroke(0);
            translate(
                    mappedX[i] + mbox,
                    (mappedY[i] + mbox) * -1,
                    mappedZ[i] + mbox
            );
            box(nodesize);

            popMatrix();

            // Collegamenti
            noFill();
            //if (i < nodeN - 1) {
            if (i < nodeN && edge[i] != -1) {

                stroke(255);

                /*line(
                        mappedX[i] + mbox,
                        (mappedY[i] + mbox) * -1,
                        mappedZ[i] + mbox,
                        mappedX[edge[i]] + mbox,
                        (mappedY[edge[i]] + mbox) * -1,
                        mappedZ[edge[i]] + mbox
                );*/

                strokeWeight(3);
                bezier(
                        //Nodo A
                       mappedX[i] + mbox,
                        (mappedY[i] + mbox) * -1,
                        mappedZ[i] + mbox,
                        //Punto Controllo A
                        mappedX[i] + mbox + ((mappedX[i] > mappedX[edge[i]]) ? 30 : -30),
                        (mappedY[i] + mbox) * -1 + ((mappedY[i] > mappedY[edge[i]]) ? 30 : -30),
                        mappedZ[i] + mbox + ((mappedZ[i] > mappedZ[edge[i]]) ? 30 : -30),
                        //Punto Controllo B
                        mappedX[edge[i]] + mbox + ((mappedX[i] < mappedX[edge[i]]) ? 30 : -30),
                        (mappedY[edge[i]] + mbox) * -1 + ((mappedY[i] < mappedY[edge[i]]) ? 30 : -30),
                        mappedZ[edge[i]] + mbox + ((mappedZ[i] < mappedZ[edge[i]]) ? 30 : -30),
                        //Nodo B
                        mappedX[edge[i]] + mbox,
                        (mappedY[edge[i]] + mbox) * -1,
                        mappedZ[edge[i]] + mbox
                );
                strokeWeight(1);

                stroke(255, 0, 0);
                line(
                        mappedX[i] + mbox,
                        (mappedY[i] + mbox) * -1,
                        mappedZ[i] + mbox,
                        mappedX[i] + mbox + ((mappedX[i] > mappedX[edge[i]]) ? 30 : -30),
                        (mappedY[i] + mbox) * -1 + ((mappedY[i] > mappedY[edge[i]]) ? 30 : -30),
                        mappedZ[i] + mbox + ((mappedZ[i] > mappedZ[edge[i]]) ? 30 : -30)

                );
                stroke(0, 0, 255);
                line(
                        mappedX[edge[i]] + mbox,
                        (mappedY[edge[i]] + mbox) * -1,
                        mappedZ[edge[i]] + mbox,
                        mappedX[edge[i]] + mbox + ((mappedX[i] < mappedX[edge[i]]) ? 30 : -30),
                        (mappedY[edge[i]] + mbox) * -1 + ((mappedY[i] < mappedY[edge[i]]) ? 30 : -30),
                        mappedZ[edge[i]] + mbox + ((mappedZ[i] < mappedZ[edge[i]]) ? 30 : -30)

                );

            }

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
