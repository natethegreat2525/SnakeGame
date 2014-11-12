package com.ntg.snake.enemy;

import android.util.Log;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.engine.viewcore.Image;
import javax.microedition.khronos.opengles.GL10;

public class BeeEnemy extends Enemy {
	
	public static final float RADIUS = .04f;
	public static final int WING_FLAP = 100;
	
	private float x, y;
	private float angle;
	private float speed;
	
	private long wingFlapMillis;
	
	public BeeEnemy(float x, float y, float angle, float speed) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
	}
	
	public void update(double delta){
		float dx = (float) (Math.cos(Math.toRadians(angle + 180))*speed*delta);
		float dy = (float) (Math.sin(Math.toRadians(angle + 180))*speed*delta);
		x += dx;
		y += dy;
		angle += delta;
	}
	
	public void render(GL10 gl){
		Image.setRotation(angle % 360);
		Image.setScale(RADIUS*2, RADIUS*2);
		SnakeGame.beeImage[(int) ((System.currentTimeMillis() / WING_FLAP) % 2)].draw(gl, x, y);
		Log.i("pos",x + " " + y);
	}
	

	@Override
	void die() {
		// TODO Auto-generated method stub
		
	}
	
}
