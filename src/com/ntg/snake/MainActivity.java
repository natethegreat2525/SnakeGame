package com.ntg.snake;

import com.ntg.snake.engine.sensor.SensorMgr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
	
	
	private static SnakeGame game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        game = new SnakeGame(this);
        game.init();
        setContentView(game.glSurface);
        SensorMgr.getInstance(this);
	}
	
	protected void onStart() {
		super.onStart();
		
		game.start();
		
	}
	
	protected void onPause() {
		super.onPause();
		SensorMgr.getInstance().onPause();
		game.pause();
		
	}
	
	protected void onResume() {
		super.onResume();
		SensorMgr.getInstance().onResume();
		game.resume();
	}
	
	protected void onStop() {
		super.onStop();
		
		game.stop();
		
	}
	
	@Override
	public void onBackPressed() {
		game.destroy();
	    super.onBackPressed(); 
	}
}
