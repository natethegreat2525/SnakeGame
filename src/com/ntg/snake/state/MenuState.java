package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.engine.viewcore.TouchPoint;
import com.ntg.snake.state.State;

public class MenuState extends State {

	public int touchPoints;
	
	public MenuState() {
		super();
		synchronized (TouchPoint.activeList) {
			touchPoints = TouchPoint.activeList.size();
		}
	}
	
	@Override
	public void render(GL10 gl) {
		Image.setRotation((System.currentTimeMillis()/10) % 360);
		Image.setScale(.5,.5);
		SnakeGame.beeImage[0].draw(gl, 0, .5f);
	}

	@Override
	public State update(double delta) {
		synchronized (TouchPoint.activeList) {
			if (TouchPoint.activeList.size() > touchPoints) {
				return new PlayState();
			} else {
				touchPoints = TouchPoint.activeList.size();
			}
		}
		return this;
	}

}
