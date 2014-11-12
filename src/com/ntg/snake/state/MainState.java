package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;

public class MainState extends State {

	public MainState() {
		super();
		
	}
	
	@Override
	public void render(GL10 gl) {
		Image.setRotation((System.currentTimeMillis()/10) % 360);
		Image.setScale(.5,.5);
		SnakeGame.foodImage.draw(gl, 0, 0);
	}

	@Override
	public State update(double delta) {
		return this;
	}

}
