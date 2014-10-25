package com.ntg.snake.engine.viewcore;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.Game;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class GLRenderer implements Renderer {
	
	//private Context		 context;
	private Game		game;
	
	private int width, height;

	/** Constructor to set the handed over context */
	public GLRenderer(Context context, Game game) {
		//this.context = context;
		this.game = game;
		Log.i("FPS", "Frames Initiated");
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();
		
		game.render(this, gl);
		
		//Log.i("FPS", "NEW FRAME");

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix
		
		this.width = width;
		this.height = height;
		
		//Calculate The Aspect Ratio Of The Window
		//GLU.gluPerspective(gl, 90.0f, (float)width / (float)height, 0.1f, 100.0f);
		//GLU.gluOrtho2D(gl, -1.0f, 1.0f, -height/((float)width), height/((float)width));
		//GLU.gluOrtho2D(gl, -width/((float)height), width/((float)height),-1.0f, 1.0f);
		GLU.gluOrtho2D(gl,-1.0f, 1.0f, -height/((float)width), height/((float)width));
		
		TouchPoint.setWidth(width);
		TouchPoint.setHeight(height);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		Image.init();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_ALWAYS); 			//The Type Of Depth Testing To Do
		gl.glEnable(GL10.GL_BLEND);					//Enable blending
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		//Load our game assets here
		game.loadGLAssets(gl);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float transformX(int x) {
		//return 2*((float)x)/(float)width - 1.0f;
		return 2*((float)x)/(float)height - width/(float)height;
				
	}
	
	public float transformY(int y) {
		//return -2*((float)y)/(float)width + height/(float)width;
		return -2*((float)y)/(float)height + 1.0f;
	}
}