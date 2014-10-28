package com.ntg.snake;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Snake {
	
	private ArrayList<SnakeUnit> body;
	private ArrayList<SnakePoint> history;
	
	private float x, y;
	private float angle;
	private float speed;
	private float turningSpeed;
	
	private boolean angleChanged;
	
	public Snake(float x, float y, float vx, float vy, float speed) {
		super();
		body = new ArrayList<SnakeUnit>();
		history = new ArrayList<SnakePoint>();
		
		history.add(new SnakePoint(x, y, -vx * 100, -vy * 100));
		this.x = x;
		this.y = y;
		this.angle = (float) Math.atan2(vy, vx);
		this.speed = speed;
		this.angleChanged = false;
		this.turningSpeed = .02f;
	}
	
	public void setAngle(float angle) {
		
		if (angle != this.angle)
			angleChanged = true;
		
		this.angle = angle;
		
	}
	
	public float getAngle() {
		return this.angle;
	}
	
	public void addAngle(float angle) {
		
		if (angle != 0)
			angleChanged = true;
		
		this.angle += angle;
	}
	
	public void goToGoal(float goalX, float goalY) {
		float sinAng = (float) Math.sin(angle);
		float cosAng = (float) Math.cos(angle);
		float diffX = goalX - x;
		float diffY = goalY - y;
		
		if (Math.abs(cosAng) <= .7) {
			float slope = cosAng/sinAng;
			float xint = x - slope * y;
			float lx = slope*goalY + xint;
			if (sinAng < 0) {
				lx = -lx;
				goalX = -goalX;
			}
			if (lx < goalX) {
				addAngle(-turningSpeed);
			} else {
				addAngle(turningSpeed);
			}
		} else {
			float slope = sinAng/cosAng;
			float yint = y - slope * x;
			float ly = slope*goalX + yint;
			if (cosAng < 0) {
				ly = -ly;
				goalY = -goalY;
			}
			if (ly < goalY) {
				addAngle(turningSpeed);
			} else {
				addAngle(-turningSpeed);
			}
		}
	}
	
	public void render(GL10 gl) {
		synchronized (body) {
			for (SnakeUnit unit : body) {
				unit.render(gl);
			}
		}
	}
	
	public void update(double delta) {
		
		if (angle > 2*Math.PI) {
			angle = (float) (angle - 2*Math.PI);
		} else if (angle < 0) {
			angle = (float) (angle + 2*Math.PI);
		}
		
		x = x + (float) (Math.cos(angle)*speed*delta);
		y = y + (float) (Math.sin(angle)*speed*delta);
		
		newSnakePoint();
		
		synchronized (body) {
			int pos = 0;
			int lastHistory = 0;
			for (SnakeUnit unit : body) {
				int tmp = unit.update(history, pos);
				if (tmp > lastHistory)
					lastHistory = tmp;
				pos++;
			}
			if (lastHistory < history.size() - 1) {
				history.remove(history.size() -1);
			}
		}
	}
	
	public void foodCheck(SnakeFood food){
		if(Math.sqrt((x - food.getX())*(x-food.getX()) + (y - food.getX())*(y - food.getX())) < 0.05 + SnakeUnit.BODY_SIZE ){
			food.eat();
		}
	}
	
	public void newSnakePoint() {
		
		SnakePoint last = history.get(0);

		if (angleChanged) {
			
			angleChanged = false;
			history.add(0, new SnakePoint(x, y, last.x - x, last.y - y));
			
		} else {
			
			last.setup(x, y, last.vx + (last.x - x), last.vy + (last.y - y));
			
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


class SnakePoint {
	public float x;
	public float y;
	
	/**
	 * Vector to the previous snakepoint
	 */
	public float vx;
	public float vy;
	
	/**
	 * Distance between this snakePoint and the previous SnakePoint
	 */
	public float dist;
	
	/**
	 * Normalized vector to previous snake point
	 */
	public float nx;
	public float ny;
	
	public SnakePoint(float x, float y, float vx, float vy) {
		super();
		setup(x, y, vx, vy);
	}
	
	public void setup(float x, float y, float vx, float vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.dist = (float) Math.sqrt(vx*vx + vy*vy);
		this.nx = this.vx/this.dist;
		this.ny = this.vy/this.dist;
	}
}
