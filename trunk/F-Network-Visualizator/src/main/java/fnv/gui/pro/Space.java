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
	//Lato dello spazio in px
	int spaceBox = box*boxN;
	
	//Numero nodi
	int nodeN = 4;
	//Lato dei nodi in px
	int nodesize = 10;
	//Distanza fra nodi
	int nodeDist = 15;
	
	
	int[] nodeX = new int[nodeN];
	int[] nodeY = new int[nodeN];
	int[] nodeZ = new int[nodeN];

	// Punti di controllo
	float[][] edgeX = new float[nodeN][nodeN];
	float[][] edgeY = new float[nodeN][nodeN];
	float[][] edgeZ = new float[nodeN][nodeN];

	// http://mrfeinberg.com/peasycam/reference/index.html
	private PeasyCam cam;

	public void setup() {
		size(1024, 768, P3D);
		
		frameRate(30);

		//Inizializzazione camera
		cam = new PeasyCam(this, spaceBox/2, -spaceBox/2, spaceBox/2, spaceBox);
		cam.setMinimumDistance(10);
		cam.setMaximumDistance(700);
		

		nodeX[0] = nodeY[0] = nodeZ[0] = 0;
		nodeX[1] = nodeY[1] = 2;
		nodeZ[1] = 1;
		nodeX[2] = nodeY[2] = nodeZ[2] = 2;
		nodeX[3] = nodeY[3] = nodeZ[3] = 3;
		
		// Arco 0 -> 1
		edgeX[0][1] = nodeX[0];
		edgeY[0][1] = -nodeDist;
		edgeZ[0][1] = nodeZ[0];

		// Arco 1 -> 0
		edgeX[1][0] = nodeX[1];
		edgeY[1][0] = -nodeDist;
		edgeZ[1][0] = nodeZ[1];
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
		translate(spaceBox/2,-spaceBox/2,spaceBox/2);
		box(spaceBox);
		popMatrix();
		
		stroke(255, 0, 255);
		for (int i = 0; i <= spaceBox; i += spaceBox/boxN ) {
			line(0 + i, 0, 0, 0 + i, 0, spaceBox);//Linee verticali
			line(0, 0, 0 + i, spaceBox, 0, 0 + i);//Linee orizzontali
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
			fill(i * 100, 255, 255 / (i+1));
			stroke(0);
			translate(map(nodeX[i],0,boxN,0,spaceBox)+box/2, 0-(nodesize/2), map(nodeZ[i],0,boxN,0,spaceBox)+box/2);
			box(nodesize);
			
			popMatrix();

			// Collegamenti
			noFill();
			if (i < nodeN - 1) {
				stroke(255);
				
				
				
				line(nodeX[i], nodeY[i], nodeZ[i], nodeX[i + 1], nodeY[i + 1], nodeZ[i + 1]);
				/*bezier(nodeX[i], nodeY[i], nodeZ[i], edgeX[i][i + 1],
						edgeY[i][i + 1], edgeZ[i][i + 1], edgeX[i + 1][i],
						edgeY[i + 1][i], edgeZ[i + 1][i], nodeX[i + 1],
						nodeY[i + 1], nodeZ[i + 1]);

				stroke(255, 0, 0);
				line(nodeX[i], nodeY[i], nodeZ[i], edgeX[i][i + 1],
						edgeY[i][i + 1], edgeZ[i][i + 1]);
				line(nodeX[i + 1], nodeY[i + 1], nodeZ[i + 1], edgeX[i + 1][i],
						edgeY[i + 1][i], edgeZ[i + 1][i]);
				*/
			}

		}

		
	}

}
