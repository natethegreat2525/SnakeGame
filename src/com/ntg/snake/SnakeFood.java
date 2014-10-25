package com.ntg.snake;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.viewcore.Image;

public class SnakeFood {
	
	private float x, y;
	
	public SnakeFood(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void render(GL10 gl){
		Image.setScale(.05f, .05f);
		SnakeGame.foodImage.draw(gl, x, y);
	}
	
	public void update(){
		
	}
	
}
