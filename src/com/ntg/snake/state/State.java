package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

/**
 * Defines a state of the game.
 *
 */
public abstract class State implements StateManager {
	
	public State childState;
	
	public State() {
		super();
		childState = null;
	}
	
	public abstract void render(GL10 gl);
	
	/**
	 * Whenever child state is changed, it must by synchronized
	 * @param delta
	 * @return
	 */
	public abstract State update(double delta);
	
	public void updateState(double delta) {
		State next = this.update(delta);
		
		synchronized (childState) {
			childState = next;
		}
		
		if (childState != null) {
			childState.updateState(delta);
		}
		
	}
	
	public void renderState(GL10 gl) {
		this.render(gl);
		
		synchronized (childState) {
			if (childState != null) {
				childState.renderState(gl);
			}
		}
		
	}
	
	
}
