package com.ntg.snake;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Snake {
	
	private ArrayList<SnakeUnit> body;
	
	public Snake() {
		super();
		body = new ArrayList<SnakeUnit>();
	}
	
	public void render(GL10 gl) {
		synchronized (body) {
			for (SnakeUnit unit : body) {
				unit.render(gl);
			}
		}
	}
	
	public void update() {
		synchronized (body) {
			for (SnakeUnit unit : body) {
				unit.update();
			}
		}
	}
	
	public void addUnit(SnakeUnit unit) {
		if (body.size() > 0)
			unit.setAhead(body.get(body.size()-1));
		body.add(unit);
	}
	
	public SnakeUnit getHead() {
		return body.get(0);
	}
}
