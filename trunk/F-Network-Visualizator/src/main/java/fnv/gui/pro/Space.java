package fnv.gui.pro;

import processing.core.*;
import peasy.*;


/**
 * Created by IntelliJ IDEA.
 * User: giacomo
 * Date: Nov 1, 2010
 * Time: 3:12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Space extends PApplet {

    int nodeN = 2;
    int nodesize = 10;

    float[] nodeX = new float[nodeN];
    float[] nodeY = new float[nodeN];
    float[] nodeZ = new float[nodeN];

    //Punti di controllo
    float[][] edgeX = new float[nodeN][nodeN];
    float[][] edgeY = new float[nodeN][nodeN];
    float[][] edgeZ = new float[nodeN][nodeN];

    //http://mrfeinberg.com/peasycam/reference/index.html
    private PeasyCam cam;

    public void setup() {
        size(800, 800, P3D);

        cam = new PeasyCam(this, 100);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(500);

        nodeX[0] = nodeY[0] = nodeZ[0] = 0;
        nodeX[1] = 0;
        nodeY[1] = 0;
        nodeZ[1] = 30;

        edgeX[0][1] = nodeX[1];
        edgeY[0][1] = 15;
        edgeZ[0][1] = nodeZ[1];

        edgeX[1][0] = nodeX[0];
        edgeY[1][0] = 15;
        edgeZ[1][0] = nodeZ[0];
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                edgeX[1][0] += 5;
            } else if (keyCode == DOWN) {
                edgeX[1][0] -= 5;
            }
        } else {

        }

    }


    public void draw() {
        //Per non mostrare la scena esattamente da sopra
        //rotateX(PI / 4);
        //rotateY(PI / 4);

        background(0);

        for (int i = 0; i < nodeN; i++) {
            pushMatrix();
            fill(i * 255, 255, 0);
            stroke(0);
            translate(nodeX[i], nodeY[i], nodeZ[i]);
            box(nodesize);
            popMatrix();

            //Collegamenti
            noFill();
            if (i < nodeN - 1) {
                stroke(255);
                
                line(nodeX[i], nodeY[i], nodeZ[i], nodeX[i + 1], nodeY[i + 1], nodeZ[i + 1]);
                bezier(nodeX[i], nodeY[i], nodeZ[i],
                        edgeX[i][i + 1], edgeY[i][i + 1], edgeZ[i][i + 1],
                        edgeX[i + 1][i], edgeY[i + 1][i], edgeZ[i + 1][i],
                        nodeX[i + 1], nodeY[i + 1], nodeZ[i + 1]
                );

                stroke(255, 0, 0);
                line(nodeX[i], nodeY[i], nodeZ[i],
                        edgeX[i][i + 1], edgeY[i][i + 1], edgeZ[i][i + 1]);
                line(nodeX[i + 1], nodeY[i + 1], nodeZ[i + 1],
                        edgeX[i + 1][i], edgeY[i + 1][i], edgeZ[i + 1][i]);
            }


        }

        /*
        fill(255,0,0);
        box(30);
        
        pushMatrix();

        translate(0,0,20);

        fill(0,0,255);
        box(5);

        translate(100,0,0);

        fill(0,255,0);
        box(5);

        popMatrix();
        */
    }


}
