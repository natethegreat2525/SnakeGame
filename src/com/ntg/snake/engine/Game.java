package com.ntg.snake.engine;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.viewcore.GLRenderer;
import com.ntg.snake.engine.viewcore.MyGLSurfaceView;

import android.content.Context;

public abstract class Game {
		
	public int targetLogicRate = 120; //logic ticks per second
	public double deltaFactor = .5;
	
	public Context context;
	
	public MyGLSurfaceView glSurface;
	public GLRenderer renderer;
	
	public MainThread main;
	
	public Game(Context context) {
		glSurface = new MyGLSurfaceView(context);
		this.context = context;
		
		GLRenderer renderer = new GLRenderer(context, this);
        glSurface.setRenderer(renderer);
		Rnd.init();
	}
	
	public void initAll() {
		this.init();
	}
	
	public abstract void init();
	
	public void loadAssets(GL10 gl) {
		Number.init(this, gl);
		this.loadGLAssets(gl);
	}
	
	public abstract void loadGLAssets(GL10 gl);
		
//		this.title = new Image(gl, this.context, R.drawable.title);
//		this.touchStart = new Image(gl, this.context, R.drawable.touchstart);
//		this.ship = new Image(gl, this.context, R.drawable.ship);
//		this.blank = new Image(gl, this.context, R.drawable.blank);
//		this.floaterenemy = new Image(gl, this.context, R.drawable.floaterenemy);
//		this.circle = new Image(gl, this.context, R.drawable.circle);
//		this.blueenemy = new Image(gl, this.context, R.drawable.blueenemy);
//		
//		this.greenenemy = new Image(gl, this.context, R.drawable.greenenemy);
//		this.greenenemygun = new Image(gl, this.context, R.drawable.greenenemygun);
//		
//		this.rocket[0] = new Image(gl, this.context, R.drawable.rocket1);
//		this.rocket[1] = new Image(gl, this.context, R.drawable.rocket2);
//		
//		this.hud = new Image(gl, this.context, R.drawable.hud);
	
	public abstract void render(GLRenderer renderer, GL10 gl);
	
	public abstract void update(double delta);
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void destroy() {
		//executed when back button is pressed
		
	}
	
	public void start() {
		main = new MainThread(this);
	}
	
	public void resume() {
		main.setRunning(true);
		main.start();
		
	}
	
	public void pause() {
		main.setRunning(false);
	}
	
	public void stop() {
	}
	
	
}
