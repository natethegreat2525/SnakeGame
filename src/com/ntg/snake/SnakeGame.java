package com.ntg.snake;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.hardware.SensorManager;

import com.ntg.snake.R;
import com.ntg.snake.engine.Game;
import com.ntg.snake.engine.Rnd;
import com.ntg.snake.engine.sensor.SensorMgr;
import com.ntg.snake.engine.viewcore.GLRenderer;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.engine.viewcore.TouchPoint;

public class SnakeGame extends Game {
	
	public static Image bodyImage;
	
	public static Image foodImage;
	
	public Snake snake;
	
	public SnakeFood food;
	
	public SnakeGame(Context context) {
		super(context);
	}
	
	@Override
	public void render(GLRenderer renderer, GL10 gl) {
		Image.setAlphaBlend(gl);
		snake.render(gl);
		food.render(gl);
	}

	@Override
	public void update(double delta) {
		synchronized (TouchPoint.activeList) {
			for (TouchPoint p : TouchPoint.activeList) {
				double x = (TouchPoint.transformX(p.getLastX()));
				double y = (TouchPoint.transformY(p.getLastY()));
				snake.addAngle((float) ((x/20.0)*delta));
			}
		}
		snake.update(delta);
	}
	

	@Override
	public void loadGLAssets(GL10 gl) {
		bodyImage = new Image(gl, this.context, R.drawable.body);
		foodImage = new Image(gl, this.context, R.drawable.food);
	}

	@Override
	public void init() {
		snake = new Snake(0, 0, 1, 0, .005f);
		snake.addUnit(new SnakeUnit(0,0,0));
		for (int i = 0; i < 10; i++)
			snake.addUnit(new SnakeUnit(i+1,0,45));
		food = new SnakeFood(.5f, .5f);
	}
}
