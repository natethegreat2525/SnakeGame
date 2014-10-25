package com.ntg.snake.engine.viewcore;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Image {
	
	private static float angle = 0;
	private static float scaleX = 1;
	private static float scaleY = 1;
	
	private static FloatBuffer vertexBuffer;	// buffer holding the vertices

	private static float vertices[] = {
			-1.0f, -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f, -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f			// V4 - top right
	};
	
	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	private float texture[] = {
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	/** The texture pointer */
	private int[] textures = new int[1];
	
	private float handleX, handleY;
	
	public static void init() {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		Image.vertexBuffer = byteBuffer.asFloatBuffer();
		Image.vertexBuffer.put(vertices);
		Image.vertexBuffer.position(0);
	}

	public Image() {
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		this.setHandle(0,0);
	}
	
	public Image(GL10 gl, Context context, int resource) {
		this();
		
		this.loadGLTexture(gl, context, resource);
	}
	
	/** The draw method for the square with the GL context */
	public void draw(GL10 gl, float x, float y) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Image.vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		gl.glPushMatrix();
			
			gl.glTranslatef(x, y, 0f);
			gl.glRotatef(Image.angle, 0, 0, 1);
			gl.glScalef(Image.scaleX, Image.scaleY, 1f);
			
			// Draw the vertices as triangle strip
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
			
		gl.glPopMatrix();
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	
	public void loadGLTexture(GL10 gl, Context context, int resource) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resource);

		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}

	public float getHandleY() {
		return handleY;
	}
	
	public float getHandleX() {
		return handleX;
	}

	public void setHandle(float handleX, float handleY) {
		this.handleY = handleY;
		this.handleX = handleX;
	}
	
	
	
	
	//static methods start here
	
	public static double getRotation() {
		return angle;
	}

	public static void setRotation(double angle) {
		Image.angle = (float) angle;
	}
	public static void setRotation(int angle) {
		Image.angle = angle;
	}
	public static void setRotation(float angle) {
		Image.angle = angle;
	}
	
	public static void setScale(float scaleX, float scaleY) {
		Image.scaleX = scaleX;
		Image.scaleY = scaleY;
	}
	
	public static void setScale(double scaleX, double scaleY) {
		Image.scaleX = (float) scaleX;
		Image.scaleY = (float) scaleY;
	}
	
	public static void setAlphaBlend(GL10 gl) {
		gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	public static void setMultiplyBlend(GL10 gl) {
		gl.glBlendFunc(GL10.GL_DST_COLOR,GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	public static void setAddBlend(GL10 gl) {
		gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE);
	}
	
	public static void setMaskedBlend(GL10 gl) {
		gl.glBlendFunc(GL10.GL_DST_COLOR,GL10.GL_ZERO);
	}
}