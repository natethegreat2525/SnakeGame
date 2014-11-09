package com.ntg.snake;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.Rnd;
import com.ntg.snake.engine.viewcore.Image;

public class SuperSnakeFood extends Food {
public static final float RADIUS = .07f;
	
	private float x, y, angle, speed;
	
	public SuperSnakeFood(float x, float y) {
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
