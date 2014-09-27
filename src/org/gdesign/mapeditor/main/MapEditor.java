package org.gdesign.mapeditor.main;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.texture.TextureManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class MapEditor {
	

	public final int WIDTH = 1200;
	public final int HEIGHT = 600;
	public final int TSIZE = WIDTH/20;
	
	private GridView grid;
	
	public MapEditor(){
		try {
			initOpenGL(WIDTH,HEIGHT);
			TextureManager.init();
			grid = new GridView(WIDTH-400, HEIGHT);
			loop();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	private void loop(){
		Display.setVSyncEnabled(true);
		while (!Display.isCloseRequested()){		
			grid.draw();
			Display.sync(60);
			Display.update();
		}
		TextureManager.cleanUp();
		Display.destroy();
	}
	
	
	private void initOpenGL(int width, int height) throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(width,height));
		Display.setVSyncEnabled(true);
		Display.create();
	
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glShadeModel(GL_SMOOTH);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
	}
}
