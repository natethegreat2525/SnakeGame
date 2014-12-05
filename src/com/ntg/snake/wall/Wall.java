package com.ntg.snake.wall;

import java.awt.geom.Line2D;

import com.ntg.snake.player.SnakeUnit;

public class Wall implements Collidable{
	
	private double x1, y1, x2, y2;
	Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
	
	public Wall(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	
	public boolean hasCollided(double x, double y) {
		double dist = line.ptSegDist(x,y);
		if(dist<SnakeUnit.BODY_SIZE){
			return true;
		}else{
			return false;
		}
	}
}
