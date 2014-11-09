package com.ntg.snake;

import javax.microedition.khronos.opengles.GL10;


public abstract class Food {
	
	abstract float getX();
	
	abstract float getY();
	
	abstract void eat();

	abstract void render(GL10 gl);
	
}
