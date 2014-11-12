package com.ntg.snake;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.hardware.SensorManager;

import com.ntg.snake.R;
import com.ntg.snake.enemy.BeeEnemy;
import com.ntg.snake.engine.Game;
import com.ntg.snake.engine.Rnd;
import com.ntg.snake.engine.sensor.SensorMgr;
import com.ntg.snake.engine.viewcore.GLRenderer;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.engine.viewcore.TouchPoint;
import com.ntg.snake.engine.Number;
import com.ntg.snake.food.Food;
import com.ntg.snake.food.NormalSnakeFood;
import com.ntg.snake.player.Snake;
import com.ntg.snake.player.SnakeUnit;
import com.ntg.snake.state.MainState;
import com.ntg.snake.state.State;

public class SnakeGame extends Game {
	
	public static Image bodyImage;
	
	public static Image foodImage;
	
	public static Image[] beeImage;
	
	public Snake snake;
	
	public Food food;
	
	public BeeEnemy bee;
	
	public State mainState;
	
	public int score;
	
	public SnakeGame(Context context) {
		super(context);
	}
	
	@Override
	public void render(GLRenderer renderer, GL10 gl) {
		Image.setAlphaBlend(gl);
//		bee.render(gl);
//		snake.render(gl);
//		food.render(gl);
//		Image.setRotation(0);
//		Number.drawNumber(gl, score, TouchPoint.transformX(0) + .07f, TouchPoint.transformY(0) - .07f, .07f);
		mainState.renderState(gl);
		
	}

	@Override
	public void update(double delta) {
//		synchronized (TouchPoint.activeList) {
//			if (TouchPoint.activeList.size() > 0) {
//				TouchPoint first = TouchPoint.activeList.get(0);
//				double x = (TouchPoint.transformX(first.getLastX()));
//				double y = (TouchPoint.transformY(first.getLastY()));
//				snake.goToGoal((float) x, (float) y, (float) delta);
//			}
//		}
//		bee.update(delta);
//		snake.update(delta);
//		score = snake.getSize() - 2;
//		snake.foodCheck(food);
		mainState.updateState(delta);
	}
	

	@Override
	public void loadGLAssets(GL10 gl) {
		bodyImage = new Image(gl, this.context, R.drawable.body);
		foodImage = new Image(gl, this.context, R.drawable.food);
		
		beeImage = new Image[2];
		beeImage[0] = new Image(gl, this.context, R.drawable.bee1);
		beeImage[1] = new Image(gl, this.context, R.drawable.bee2);
		
		Number.init(this, gl);
	}

	@Override
	public void init() {
//		snake = new Snake(0, 0, 1, 0, .01f);
//		snake.addUnit(new SnakeUnit(0,0,0));
//		for (int i = 0; i < 1; i++)
//			snake.addUnit(new SnakeUnit(i+1,0,45));
//		food = new NormalSnakeFood(.5f, .5f);
//		bee = new BeeEnemy(0, 0, 90, .006f);
		Rnd.init();
		mainState = new MainState();
	}
}
