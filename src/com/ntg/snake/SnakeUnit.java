package com.ntg.snake;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.engine.viewcore.Image;

public class SnakeUnit {
	
	public static final float BODY_SIZE = .06f;
	public static final float LINK_DIST = .12f;
	
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
	
	public int update(ArrayList<SnakePoint> history, int pos) {
		float targetDist = pos*LINK_DIST;
		int cnt = 0;
		for (SnakePoint point : history) {
			if (point.dist < targetDist && point != history.get(history.size() - 1)) {
				targetDist -= point.dist;
			} else {
				x = point.x + point.nx*targetDist;
				y = point.y + point.ny*targetDist;
				angle = (float) Math.toDegrees(Math.atan2(point.ny, point.nx));
				return cnt;
			}
			cnt++;
		}
		return history.size();
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
