package com.ntg.snake;

import com.ntg.snake.engine.viewcore.Image;
import javax.microedition.khronos.opengles.GL10;

public class BeeEnemy extends Enemies {
	
	public static final float RADIUS = .04f;
	
	private float x, y;
	
	public BeeEnemy(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		
	}
	
	public void render(GL10 gl){
		Image.setRotation((System.currentTimeMillis() / 6) % 360);
		Image.setScale(RADIUS, RADIUS);
		//SnakeGame.BeeImage.draw(gl, x, y);
	}
	

	@Override
	void die() {
		// TODO Auto-generated method stub
		
	}
	
}
