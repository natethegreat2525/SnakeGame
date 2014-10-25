package com.ntg.snake.engine.viewcore;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
	
	public MyGLSurfaceView(Context context) {
		super(context);
	}
	
	public MyGLSurfaceView(Context context, AttributeSet a) {
		super(context, a);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		int index = MotionEventCompat.getActionIndex(event);
		
		switch (MotionEventCompat.getActionMasked(event)) {
			case (MotionEvent.ACTION_DOWN) : {
				TouchPoint.actionDown((int) MotionEventCompat.getX(event,index), (int) MotionEventCompat.getY(event,index));
				//Log.i("DOWN",MotionEventCompat.getX(event,index) + "    " + MotionEventCompat.getY(event,index));
				
				break;
			}
			case (MotionEvent.ACTION_CANCEL) : {
				TouchPoint.resetPoints();
				break;
			}
			case (MotionEvent.ACTION_UP) : {
				//Log.i("UP",MotionEventCompat.getX(event,index) + "    " + MotionEventCompat.getY(event,index));
				
				TouchPoint.actionUp((int) MotionEventCompat.getX(event,index), (int) MotionEventCompat.getY(event,index));
				break;
			}
			case (MotionEvent.ACTION_MOVE) : {
				int num = event.getPointerCount();
				for(int i = 0; i < num; i++) {
					//Log.i("MOVE",i+" --- "+MotionEventCompat.getX(event,i) + "    " + MotionEventCompat.getY(event,i));
					TouchPoint.actionMove((int) MotionEventCompat.getX(event,i), (int) MotionEventCompat.getY(event,i));
				}
				break;
			}
			case (MotionEventCompat.ACTION_POINTER_DOWN) : {
				TouchPoint.actionDown((int) MotionEventCompat.getX(event,index), (int) MotionEventCompat.getY(event,index));
				
				//Log.i("PDOWN",MotionEventCompat.getX(event,index) + "    " + MotionEventCompat.getY(event,index));
				break;
			}
			case (MotionEventCompat.ACTION_POINTER_UP): {
				TouchPoint.actionUp((int) MotionEventCompat.getX(event,index), (int) MotionEventCompat.getY(event,index));
				
				//Log.i("PUP",MotionEventCompat.getX(event,index) + "    " + MotionEventCompat.getY(event,index));
				break;
			}
		
		}
		
	 	return true;
 	}
	

}
