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
	
	public static Image playButton;
	
	public static Image quitButton;

	
	public State mainState;
	
	public SnakeGame(Context context) {
		super(context);
	}
	
	@Override
	public void render(GLRenderer renderer, GL10 gl) {
		mainState.renderState(gl);
		
	}

	@Override
	public void update(double delta) {
		mainState.updateState(delta);
	}
	

	@Override
	public void loadGLAssets(GL10 gl) {
		bodyImage = new Image(gl, this.context, R.drawable.body);
		foodImage = new Image(gl, this.context, R.drawable.food);
		
		beeImage = new Image[2];
		beeImage[0] = new Image(gl, this.context, R.drawable.bee1);
		beeImage[1] = new Image(gl, this.context, R.drawable.bee2);
		
		playButton = new Image(gl, this.context, R.drawable.play_button);
		quitButton = new Image(gl, this.context, R.drawable.quit_button);
		
		Number.init(this, gl);
	}

	@Override
	public void init() {
		Rnd.init();
		mainState = new MainState();
	}
}
