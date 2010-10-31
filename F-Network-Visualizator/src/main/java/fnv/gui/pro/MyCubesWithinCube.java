package fnv.gui.pro;

import processing.core.*; 

public class MyCubesWithinCube extends PApplet {

	/**
	 * Cubes Contained Within a Cube 
	 * by Ira Greenberg.  
	 * 
	 * Collision detection against all
	 * outer cube's surfaces. 
	 * Uses the Point3D and Cube classes. 
	 */

	Cube stage; // external large cube
	int cubies = 30;
	Cube[]c = new Cube[cubies]; // internal little cubes

	Sphere[]s = new Sphere[cubies];

	int[][]quadBG = new int[cubies][6];

	// Controls cubie's movement
	float[]x = new float[cubies];
	float[]y = new float[cubies];
	float[]z = new float[cubies];
	float[]xSpeed = new float[cubies];
	float[]ySpeed = new float[cubies];
	float[]zSpeed = new float[cubies];

	// Controls cubie's rotation
	float[]xRot = new float[cubies];
	float[]yRot = new float[cubies];
	float[]zRot = new float[cubies];

	// Size of external cube
	float bounds = 300;

	//Per ruotare la scena
	float rotx = PI/4;
	float roty = PI/4;


	public void setup() {
		size(800, 600, P3D);
		colorMode(RGB, 1);
		//noStroke();

		//Per usare rotellina
		addMouseWheelListener(new java.awt.event.MouseWheelListener() {
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
				mouseWheel(evt.getWheelRotation());
			}});


		for (int i = 0; i < cubies; i++){

			// Cubies are randomly sized
			float cubieSize = random(5, 15);
			c[i] =  new Cube(cubieSize, cubieSize, cubieSize);
			s[i] = new Sphere(cubieSize);

			// Initialize cubie's position, speed and rotation
			x[i] = 0;
			y[i] = 0; 
			z[i] = 0;

			xSpeed[i] = random(-1, 1);
			ySpeed[i] = random(-1, 1); 
			zSpeed[i] = random(-1, 1); 

			xRot[i] = random(40, 100);
			yRot[i] = random(40, 100);
			zRot[i] = random(40, 100);
		}

		// Instantiate external large cube
		//stage =  new Cube(bounds, bounds, bounds);
	}

	public void mouseDragged() {
		float rate = 0.01f;
		rotx += (pmouseY-mouseY) * rate;
		roty += (mouseX-pmouseX) * rate;
	}

	int zoom = 500;
	public void mouseWheel(int delta) {
		zoom = zoom + (delta * 50);
		println(zoom);
	}


	public void draw(){
		background(1);
		lights();

		// Center in display window
		translate(width/2, height/2, -130);

		// Outer transparent cube
		noFill(); 

		// Rotate everything, including external large cube
		/*rotateX(frameCount * 0.001);
  rotateY(frameCount * 0.002);
  rotateZ(frameCount * 0.001);
		 */

		// Change height of the camera with mouseY
		camera(1.0f, zoom, 1.0f, // eyeX, eyeY, eyeZ
				0.0f, 0.0f, 0.0f, // centerX, centerY, centerZ
				0.0f, 1.0f, 0.0f); // upX, upY, upZ


		rotateX(rotx);
		rotateY(roty);


		stroke(255);

		// Draw external large cube
		// stage.createVoid();

		// Move and rotate cubies
		for (int i = 0; i < cubies; i++){
			pushMatrix();
			translate(x[i], y[i], z[i]);
			rotateX(frameCount*PI/xRot[i]);
			rotateY(frameCount*PI/yRot[i]);
			rotateX(frameCount*PI/zRot[i]);
			noStroke();

			if(i%2 == 0) {
				c[i].create();
			} else {
				s[i].create();
			}   

			x[i] += xSpeed[i];
			y[i] += ySpeed[i];
			z[i] += zSpeed[i];
			popMatrix();

			// Draw lines connecting cubbies
			stroke(0);
			if (i < cubies-1){
				line(x[i], y[i], z[i], x[i+1], y[i+1], z[i+1]);
			}

			// Check wall collisions
			if (x[i] > bounds/2 || x[i] < -bounds/2){
				xSpeed[i]*=-1;
			}
			if (y[i] > bounds/2 || y[i] < -bounds/2){
				ySpeed[i]*=-1;
			}
			if (z[i] > bounds/2 || z[i] < -bounds/2){
				zSpeed[i]*=-1;
			}
		}
	}


	// Custom Cube Class

	class Cube{
		PVector[] vertices = new PVector[24];
		float w, h, d;
		int c;

		// Default constructor
		Cube(){ }

		// Constructor 2
		Cube(float w, float h, float d) {
			this.w = w;
			this.h = h;
			this.d = d;

			//c= color(int(random(0,1)),int(random(0,1)),int(random(0,1)));
			println(random(0,1));
			c = color(random(0,1));

			// cube composed of 6 quads
			//front
			vertices[0] = new PVector(-w/2,-h/2,d/2);
			vertices[1] = new PVector(w/2,-h/2,d/2);
			vertices[2] = new PVector(w/2,h/2,d/2);
			vertices[3] = new PVector(-w/2,h/2,d/2);
			//left
			vertices[4] = new PVector(-w/2,-h/2,d/2);
			vertices[5] = new PVector(-w/2,-h/2,-d/2);
			vertices[6] = new PVector(-w/2,h/2,-d/2);
			vertices[7] = new PVector(-w/2,h/2,d/2);
			//right
			vertices[8] = new PVector(w/2,-h/2,d/2);
			vertices[9] = new PVector(w/2,-h/2,-d/2);
			vertices[10] = new PVector(w/2,h/2,-d/2);
			vertices[11] = new PVector(w/2,h/2,d/2);
			//back
			vertices[12] = new PVector(-w/2,-h/2,-d/2); 
			vertices[13] = new PVector(w/2,-h/2,-d/2);
			vertices[14] = new PVector(w/2,h/2,-d/2);
			vertices[15] = new PVector(-w/2,h/2,-d/2);
			//top
			vertices[16] = new PVector(-w/2,-h/2,d/2);
			vertices[17] = new PVector(-w/2,-h/2,-d/2);
			vertices[18] = new PVector(w/2,-h/2,-d/2);
			vertices[19] = new PVector(w/2,-h/2,d/2);
			//bottom
			vertices[20] = new PVector(-w/2,h/2,d/2);
			vertices[21] = new PVector(-w/2,h/2,-d/2);
			vertices[22] = new PVector(w/2,h/2,-d/2);
			vertices[23] = new PVector(w/2,h/2,d/2);



		}
		public void create(){
			// Draw cube
			for (int i=0; i<6; i++){
				beginShape(QUADS);
				for (int j=0; j<4; j++){
					fill(c);
					vertex(vertices[j+4*i].x, vertices[j+4*i].y, vertices[j+4*i].z);
				}
				endShape();
			}
		}

		public void createVoid(){
			// Draw cube
			for (int i=0; i<6; i++){
				beginShape(QUADS);
				stroke(0);
				for (int j=0; j<4; j++){
					vertex(vertices[j+4*i].x, vertices[j+4*i].y, vertices[j+4*i].z);
				}
				endShape();
			}
		}

	}

	class Sphere{
		int c;
		float d;

		// Default constructor
		Sphere(){ }

		// Constructor 2
		Sphere(float d) {
			this.d = d;

			//c= color(int(random(0,1)),int(random(0,1)),int(random(0,1)));
			println(random(0,1));
			c = color(random(0,1));
		}

		public void create(){
			fill(c);
			sphere(d);
		}
	}
}