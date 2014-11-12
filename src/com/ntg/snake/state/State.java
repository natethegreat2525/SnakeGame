package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

/**
 * Defines a state of the game.
 *
 */
public abstract class State {
	
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
	
	public State updateState(double delta) {
		State next = this.update(delta);
		
		if (childState != null) {
			State nextChild = childState.updateState(delta);
			synchronized (childState) {
				childState = nextChild;
			}
		}
		
		return next;
		
	}
	
	public void renderState(GL10 gl) {
		this.render(gl);
		
		if (childState != null) {
			synchronized (childState) {
				if (childState != null) {
					childState.renderState(gl);
				}
			}
		}
		
	}
	
	
}
