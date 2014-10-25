package com.ntg.snake.engine.viewcore;

import java.util.ArrayList;


public class TouchPoint {
	
	public static final double MAXIMUM_SWIPE_DIST = 1000.0;
	
	public static ArrayList<TouchPoint> activeList = new ArrayList<TouchPoint>();
	public static ArrayList<TouchPoint> unusedList = new ArrayList<TouchPoint>(); 
	
	private static int width;
	private static int height;
	
	public ArrayList<Point> path;
	private boolean active = true;
	private boolean used = false;
	private int lastX;
	private int lastY;
	
	public TouchPoint(int x, int y) {
		path = new ArrayList<Point>();
		
		path.add(new Point(x, y, System.currentTimeMillis()));
		lastX = x;
		lastY = y;
		
		synchronized (activeList) {
			activeList.add(this);
		}
		synchronized (unusedList) {
			unusedList.add(this);
		}
		
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public int getLastX() {
		return lastX;
	}
	
	public int getLastY() {
		return lastY;
	}
	
	public Point getLastPoint() {
		return new Point(lastX, lastY,0);
	}
	
	public void addPoint(int x, int y) {
		synchronized (path) {
			path.add(new Point(x, y, System.currentTimeMillis()));
		}
		lastX = x;
		lastY = y;
	}
	
	public Point getIndexPoint(int index) {
		synchronized (path) {
			if(index > path.size()-1)
				index = path.size()-1;
			return path.get((path.size()-index)-1);
		}
	}
	
	public static void actionDown(int x, int y) {
		new TouchPoint(x, y);
	}
	
	public static void actionMove(int x, int y) {
		double minDist = MAXIMUM_SWIPE_DIST*MAXIMUM_SWIPE_DIST;
		TouchPoint closest = null;
		synchronized (activeList) {
			for(TouchPoint p : activeList) {
				Point pTmp = p.getIndexPoint(0);
				int dx = pTmp.x - x;
				int dy = pTmp.y - y;
				int dist = dx*dx + dy*dy;
				if(dist < minDist) {
					minDist = dist;
					closest = p;
				}
			}
		}
		if(closest != null) {
			if(x != closest.getLastX() || y != closest.getLastY())
				closest.addPoint(x, y);
		}
	}
	
	public static void actionUp(int x, int y) {
		double minDist = MAXIMUM_SWIPE_DIST*MAXIMUM_SWIPE_DIST;
		TouchPoint closest = null;
		synchronized (activeList) {
			for(TouchPoint p : activeList) {
				Point pTmp = p.getIndexPoint(0);
				int dx = pTmp.x - x;
				int dy = pTmp.y - y;
				int dist = dx*dx + dy*dy;
				if(dist < minDist) {
					minDist = dist;
					closest = p;
				}
			}
			if(closest != null) {
				activeList.remove(closest);
				closest.active = false;
			}
		}
	}
	
	public void markUsed() {
		synchronized (unusedList) {
			unusedList.remove(this);
		}
		this.used = true;
	}
	
	public static void resetPoints() {
		synchronized (activeList) {
			
			for(TouchPoint p : activeList) {
				p.active = false;
			}
			
			activeList.clear();
		}
	}
	
	public static void setWidth(int width) {
		TouchPoint.width = width;
	}
	
	public static void setHeight(int height) {
		TouchPoint.height = height;
	}
	
	public static int getWidth() {
		return TouchPoint.width;
	}
	
	public static int getHeight() {
		return TouchPoint.height;
	}
	
	public static float transformX(int x) {
		return 2*((float)x)/(float)width - 1.0f;
		//return 2*((float)x)/(float)height - width/(float)height;
				
	}
	
	public static float transformY(int y) {
		return -2*((float)y)/(float)width + height/(float)width;
		//return -2*((float)y)/(float)height + 1.0f;
	}
	
}
