package com.ntg.snake.player;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.food.Food;
import com.ntg.snake.food.NormalSnakeFood;

import android.util.Log;

public class Snake {
	
	public static final float EYE_SIZE = .04f;
	public static final float EYE_DIST = .04f;
	public static final float PUPIL_SIZE = .025f;
	
	private ArrayList<SnakeUnit> body;
	private ArrayList<SnakePoint> history;
	
	private float x, y;
	private float ox, oy;
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
		this.ox = x - .01f;
		this.oy = y - .01f;
		this.angle = (float) Math.atan2(vy, vx);
		this.speed = speed;
		this.angleChanged = false;
		this.turningSpeed = .07f;
	}
	
	public void setAngle(float angle) {
		
		if (angle != this.angle)
			angleChanged = true;
		
		this.angle = angle;
		
	}
	
	public float getAngle() {
		return this.angle;
	}
	
	public int getSize() {
		return body.size();
	}
	
	public void addAngle(float angle) {
		
		if (angle != 0)
			angleChanged = true;
		
		this.angle += angle;
	}
	
	public void goToGoal(float goalX, float goalY, float delta) {
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
				addAngle(-turningSpeed*delta);
			} else {
				addAngle(turningSpeed*delta);
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
		renderBody(gl);
		renderEyes(gl);
	}
	
	private void renderBody(GL10 gl) {
		synchronized (body) {
			for (SnakeUnit unit : body) {
				unit.render(gl);
			}
		}
	}
	
	private void renderEyes(GL10 gl) {
		float dx = (float) Math.cos(angle);
		float dy = (float) Math.sin(angle);
		float ex = dx*EYE_DIST;
		float ey = dy*EYE_DIST;
		drawEye(gl, x + ey + ex, y - ex + ey, dx*PUPIL_SIZE, dy*PUPIL_SIZE);
		drawEye(gl, x - ey + ex, y + ex + ey, dx*PUPIL_SIZE, dy*PUPIL_SIZE);
	}
	
	private void drawEye(GL10 gl, float x, float y, float px, float py) {
		Image.setScale(EYE_SIZE, EYE_SIZE);
		SnakeGame.circleImage.draw(gl, x, y);
		gl.glColor4f(0, 0, 0, 1);
		Image.setScale(PUPIL_SIZE, PUPIL_SIZE);
		SnakeGame.circleImage.draw(gl, x + px, y + py);
		gl.glColor4f(1, 1, 1, 1);
	}
	
	public void update(double delta) {
		
		if (angle > 2*Math.PI) {
			angle = (float) (angle - 2*Math.PI);
		} else if (angle < 0) {
			angle = (float) (angle + 2*Math.PI);
		}
		
		ox = x;
		oy = y;
		
		x = x + (float) (Math.cos(angle)*speed*delta);
		y = y + (float) (Math.sin(angle)*speed*delta);
		
		if (x > 1) {
			x -= 2;
			ox -= 2;
			angleChanged = true;
		}
		if (x < -1) {
			x += 2;
			ox += 2;
			angleChanged = true;
		}
		
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
			if (lastHistory + 20 < history.size() - 1) {
				history.remove(history.size() -1);
			}
		}
		
	}
	
	public void foodCheck(Food food){
		if(Math.sqrt((x - food.getX())*(x-food.getX()) + (y - food.getY())*(y - food.getY())) < NormalSnakeFood.RADIUS + SnakeUnit.BODY_SIZE ){
			food.eat();
			this.addUnit(new SnakeUnit(-100,-100,0));
		}
	}
	
	public void newSnakePoint() {
		
		SnakePoint last = history.get(0);

		if (angleChanged) {
			
			angleChanged = false;
			history.add(0, new SnakePoint(x, y, ox - x, oy - y));
			
		} else {
			
			last.setup(x, y, last.vx + (ox - x), last.vy + (oy - y));
			
		}
	}
	
	public void addUnit(SnakeUnit unit) {
		synchronized (body) {
		if (body.size() > 0)
			unit.setAhead(body.get(body.size()-1));
		
		body.add(unit);
		}
	}
	
	public SnakeUnit getHead() {
		return body.get(0);
	}
	
	public boolean tailTouch(){
		synchronized (body) {
		for(int i=2; i < body.size(); i++){
			if(Math.sqrt((x - body.get(i).getX())*(x-body.get(i).getX()) + (y - body.get(i).getY())*(y - body.get(i).getY())) < SnakeUnit.BODY_SIZE * 2){
				return true;
			}
		}
		}
		return false;
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
