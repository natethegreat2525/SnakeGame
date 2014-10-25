package com.ntg.snake.engine;

public class MainThread extends Thread {
	
	public static final int PAUSE_SLEEP_TIME = 100; //milliseconds
	public static final long SECOND = 1000000000;
	
	private boolean running = false;
	private boolean pause = false;
	
	private Game game;
	
	public MainThread(Game game) {
		super();
		this.game = game;
		
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	private boolean checkPause() {
		boolean paused = false;
		while(pause && running) {
			try {
				Thread.sleep(PAUSE_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
			paused = true;
		}
		
		return paused;
	}
	
	@Override
	public void run() {
		
		long lastTime = 0;
		long curTime = System.nanoTime();
		boolean first = true;
		double delta = 1;
		double targetTime = ((double) SECOND) / (double) game.targetLogicRate;
		int correction = 0;
		int diff = 0;
		
		long lastTime2 = 0;
		long curTime2 = System.nanoTime();
		
		
		while(running) {
			
			lastTime2 = curTime2;
			curTime2 = System.nanoTime();
			
			//calculate delta
			if(first) {
				delta = 1;
			} else {
				delta = (double) (curTime2 - lastTime2) / targetTime;
			}
			
			//simulate and time game update
			lastTime = System.nanoTime();
			{
				game.update(delta*game.deltaFactor);
				
				targetTime = ((double) SECOND) / (double) game.targetLogicRate;
			}
			curTime = System.nanoTime();
			
			
			
			diff = (int) (curTime-lastTime);
			
			correction =  Math.max(0, (int) targetTime - diff)/1000000;
			
			if(!first) {
				try {
					Thread.sleep(correction);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
			
			first = false;
			first = this.checkPause();
			
		}
	}
	
}
