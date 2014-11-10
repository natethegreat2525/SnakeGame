package com.ntg.snake.food;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.Rnd;
import com.ntg.snake.engine.viewcore.Image;

public class NormalSnakeFood extends Food {
	
	public static final float RADIUS = .04f;
	
	private float x, y;
	
	public NormalSnakeFood(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public void render(GL10 gl){
		Image.setRotation((System.currentTimeMillis() / 6) % 360);
		Image.setScale(RADIUS, RADIUS);
		SnakeGame.foodImage.draw(gl, x, y);
	}
	
	public void update(){
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getY(){
		return y;
	}


	public void eat() {
		x = (float)Rnd.getRange(-1, 1);
		y = (float)Rnd.getRange(-1, 1);
	}
	
}
