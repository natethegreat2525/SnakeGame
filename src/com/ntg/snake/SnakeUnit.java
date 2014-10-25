package com.ntg.snake;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.viewcore.Image;

public class SnakeUnit {
	
	public static final float BODY_SIZE = .05f;
	public static final float LINK_DIST = .1f;
	
	private float x, y;
	private float angle;
	private SnakeUnit ahead;
	
	public SnakeUnit(float x, float y, float angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public void render(GL10 gl) {
		Image.setScale(BODY_SIZE, BODY_SIZE);
		Image.setRotation(angle);
		SnakeGame.bodyImage.draw(gl, x, y);
	}
	
	public void update() {
		if (ahead != null) {
			double dx = ahead.getX() - x;
			double dy = ahead.getY() - y;
			double dist = Math.sqrt(dx*dx + dy*dy);
			double dist_diff = dist - LINK_DIST;
			double nx = dx/dist;
			double ny = dy/dist;
			x += dist_diff*nx;
			y += dist_diff*ny;
			angle = (float) Math.toDegrees(Math.atan2(dy, dx));
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public SnakeUnit getAhead() {
		return ahead;
	}

	public void setAhead(SnakeUnit ahead) {
		this.ahead = ahead;
	}
}
