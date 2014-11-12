package com.ntg.snake.state;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.SnakeGame;
import com.ntg.snake.enemy.BeeEnemy;
import com.ntg.snake.engine.viewcore.Image;
import com.ntg.snake.engine.viewcore.TouchPoint;
import com.ntg.snake.food.Food;
import com.ntg.snake.food.NormalSnakeFood;
import com.ntg.snake.player.Snake;
import com.ntg.snake.player.SnakeUnit;
import com.ntg.snake.engine.Number;

public class PlayState extends State {
	
	public Snake snake;
	
	public Food food;
	
	public BeeEnemy bee;
	
	public int score;
	
	public PlayState() {
		snake = new Snake(0, 0, 1, 0, .01f);
		snake.addUnit(new SnakeUnit(0,0,0));
		for (int i = 0; i < 1; i++)
			snake.addUnit(new SnakeUnit(i+1,0,45));
		food = new NormalSnakeFood(.5f, .5f);
		bee = new BeeEnemy(0, 0, 90, .006f);
	}
	
	@Override
	public void render(GL10 gl) {
		Image.setAlphaBlend(gl);
		bee.render(gl);
		snake.render(gl);
		food.render(gl);
		Image.setRotation(0);
		Number.drawNumber(gl, score, TouchPoint.transformX(0) + .07f, TouchPoint.transformY(0) - .07f, .07f);
	}

	@Override
	public State update(double delta) {
		synchronized (TouchPoint.activeList) {
			if (TouchPoint.activeList.size() > 0) {
				TouchPoint first = TouchPoint.activeList.get(0);
				double x = (TouchPoint.transformX(first.getLastX()));
				double y = (TouchPoint.transformY(first.getLastY()));
				snake.goToGoal((float) x, (float) y, (float) delta);
			}
		}
		bee.update(delta);
		snake.update(delta);
		score = snake.getSize() - 2;
		snake.foodCheck(food);
		if(snake.tailTouch()){
			return new MenuState();
		}
		return this;
	}
}
