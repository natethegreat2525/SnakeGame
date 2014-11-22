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
		Image.setScale(.5,.5);
		SnakeGame.playButton.draw(gl, 0, .5f);
		SnakeGame.quitButton.draw(gl, 0, -.6f);
	}

	@Override
	public State update(double delta) {
		synchronized (TouchPoint.activeList) {
			if (touchPointInBox(-.4, .4, .75, .25)) {
				return new PlayState();
			}
			if(touchPointInBox(-.4, .4, -.35, -.85)){
				System.exit(0);
			}
		}
		return this;
	}
	
	public boolean touchPointInBox(double xleft, double xright, double ytop, double ybottom){
		if (TouchPoint.activeList.size() > 0) {
			TouchPoint first = TouchPoint.activeList.get(0);
			double x = (TouchPoint.transformX(first.getLastX()));
			double y = (TouchPoint.transformY(first.getLastY()));
			if(x>xleft && x<xright && y>ybottom && y<ytop){
				return true;
			}
		}
		
		else {
			touchPoints = TouchPoint.activeList.size();
		}
		return false;
	}

}
