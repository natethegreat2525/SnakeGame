package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.engine.viewcore.TouchPoint;

public class PlayState extends State {
	
	@Override
	public void render(GL10 gl) {
		Image.setRotation((System.currentTimeMillis()/10) % 360);
		Image.setScale(.5,.5);
		SnakeGame.bodyImage.draw(gl, 0, -.5f);
	}

	@Override
	public com.ntg.snake.state.State update(double delta) {
		synchronized (TouchPoint.activeList) {
			if (TouchPoint.activeList.size() > 0) {
				TouchPoint.activeList.remove(0);
				return new MenuState();
			}
		}
		return this;
	}
}
