package fnv.gui.pro;

import processing.core.*; 

public class Fnv extends PApplet {


	int nodes = 4;
	int size = 5;
	int color = 0;
	String name = "nodo";
	int id =0;


	PNode[] n = new PNode[nodes];


	//Controls cubie's movement
	float[]x = new float[nodes];
	float[]y = new float[nodes];
	float[]z = new float[nodes];
	float[]xSpeed = new float[nodes];
	float[]ySpeed = new float[nodes];
	float[]zSpeed = new float[nodes];


	//Controls cubie's rotation
	float[]xRot = new float[nodes];
	float[]yRot = new float[nodes];
	float[]zRot = new float[nodes];


	//Size of external cube
	// float bounds = 300; 

	//Per ruotare la scena
	float rotx = PI/4;
	float roty = PI/4;



    @Override
	public void setup(){ 
		size(800,600,P3D); 
		colorMode(RGB, 1);

		//Per usare rotellina
		addMouseWheelListener(new java.awt.event.MouseWheelListener() {
	    @Override
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
				mouseWheel(evt.getWheelRotation());
			}});


		for (int i =0 ; i<nodes; i++ ){


			n[i] = new PNode(size,color, name, id) ;

			// Initialize cubie's position
			x[i] = random(10, 50);
			y[i] = random(10, 50);
			z[i] = random(10, 50);


			xRot[i] = random(40, 100);
			yRot[i] = random(40, 100);
			zRot[i] = random(40, 100);

		}

	} 
	
    @Override
	public void mouseDragged() {
		float rate = 0.01f;
		rotx += (pmouseY-mouseY) * rate;
		roty += (mouseX-pmouseX) * rate;
	}

	int zoom = 500;
	
	public void mouseWheel(int delta) {
		zoom = zoom + (delta * 50);
		//println(zoom);
	}

    @Override
	public void draw(){ 
		background(1);
		lights(); 

		// Center in display window
		translate(width/2, height/2, -130);

		// Outer transparent cube
		// noFill(); 

		// Change height of the camera with mouseY
		camera(1.0f, zoom, 1.0f, // eyeX, eyeY, eyeZ
				0.0f, 0.0f, 0.0f, // centerX, centerY, centerZ
				0.0f, 1.0f, 0.0f); // upX, upY, upZ


		rotateX(rotx);
		rotateY(roty);

		stroke(255);


		for (int i = 0; i < nodes; i++){
			pushMatrix();
			translate(x[i], y[i], z[i]);
			noStroke();

			n[i].create(); 

			popMatrix();
		}
	} 
}