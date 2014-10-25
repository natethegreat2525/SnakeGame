package com.ntg.snake.engine;

import javax.microedition.khronos.opengles.GL10;

import com.ntg.snake.R;
import com.ntg.snake.engine.viewcore.Image;

public class Number {
	
	public static Image[] frames;
	
	public static void init(Game game, GL10 gl) {
		frames = new Image[10];
		frames[0] = new Image(gl, game.context, R.drawable.n0);
		frames[1] = new Image(gl, game.context, R.drawable.n1);
		frames[2] = new Image(gl, game.context, R.drawable.n2);
		frames[3] = new Image(gl, game.context, R.drawable.n3);
		frames[4] = new Image(gl, game.context, R.drawable.n4);
		frames[5] = new Image(gl, game.context, R.drawable.n5);
		frames[6] = new Image(gl, game.context, R.drawable.n6);
		frames[7] = new Image(gl, game.context, R.drawable.n7);
		frames[8] = new Image(gl, game.context, R.drawable.n8);
		frames[9] = new Image(gl, game.context, R.drawable.n9);
	}
	
	public static void drawNumber(GL10 gl, int number, float x, float y, float size) {
		String num = number + "";
		Image.setScale(size, size);
		for(int i = 0;i < num.length(); i++ ) {
			int n = Integer.parseInt(num.charAt(i)+"");
			frames[n].draw(gl,x+i*size,y);
		}
		Image.setScale(1,1);
	}
	
	public static void destroy() {
		frames = null;
	}
	
	
}
