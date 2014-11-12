package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;

public class MainState extends State {

	public MainState() {
		super();
		this.childState = new MenuState();
	}
	
	@Override
	public void render(GL10 gl) {
		return;
	}

	@Override
	public State update(double delta) {
		
		return this;
	}

}
