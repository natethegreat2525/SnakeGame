package com.ntg.snake.state;

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
	
	public abstract State update(double delta);
	
	public State updateState(double delta) {
		State next = this.update(delta);
		
		if (childState != null) {
			childState.updateState(delta);
		}
		
		return next;
	}
	
	
}
