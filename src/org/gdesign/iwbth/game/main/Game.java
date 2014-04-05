package org.gdesign.iwbth.game.main;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.states.GameStateManager;
import org.gdesign.iwbth.game.texture.TextureManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

public class Game {
	
	protected static long lastFPS;
	public static int fps;
	protected static TrueTypeFont font,fontBig,font2;
	protected static long lastFrame;

	public static boolean isRunning = false;
	
	public Game(int width, int height) throws LWJGLException{
		initOpenGL(width,height);

		

		try {
			InputStream in = ClassLoader.getSystemResourceAsStream("font/ka1.ttf");
			Game.font2 = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT,in).deriveFont(Font.PLAIN,40), false);

		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game.font = new TrueTypeFont(new Font("Cordia UPC", Font.PLAIN, 10),true);
		Game.fontBig = new TrueTypeFont(new Font("Impact", Font.PLAIN, 30),true);	
		Game.lastFPS = getTime();	
		
		getDelta();
		
		//ControllerManager.bindKeys();
	
		//setDisplayMode(Game.WIDTH, Game.HEIGHT, true);
	}
	
	public void start(){
		isRunning = true;
		gameLoop();
	}
	
	private void initOpenGL(int width, int height) throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(width,height));
		Display.create();
		Display.setVSyncEnabled(true);
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glClearColor(.7f,.85f,1,1);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glShadeModel(GL_SMOOTH);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);		
	}
	
	private void gameLoop(){
		TextureManager.init();
		GameStateManager.init();
		while (!Display.isCloseRequested() && isRunning){		
			int delta = getDelta();
			updateFPS();
			GameStateManager.handleEvents();
			GameStateManager.update();
			GameStateManager.move(delta);
			GameStateManager.draw();
			//Display.sync(30);
			Display.update();
		}
		GameStateManager.cleanUp();
		AudioManager.close();
	}
	
	
	
	private void updateFPS() {
		if (getTime() - Game.lastFPS > 1000) {
			Display.setTitle("FPS: " + Game.fps);
			Game.fps = 0;
			Game.lastFPS += 1000;
		}
		Game.fps++;
	}
		
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}
	
	public void setDisplayMode(int width, int height, boolean fullscreen) {
        if ((Display.getDisplayMode().getWidth() == width) && 
			(Display.getDisplayMode().getHeight() == height) && 
			(Display.isFullscreen() == fullscreen)) {
			return;
		}
		
		try {
			DisplayMode targetDisplayMode = null;
			
			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];
					
					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
						    (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}
			
			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
			
		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}	
	
	public static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static void drawString(int x, int y, String text, Color color){
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
		TextureImpl.bindNone();
		Color.white.bind();
		Game.font.drawString(x, y, text ,color);
		Color.white.bind();
		glBindTexture(GL_TEXTURE_2D,0);
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
	}
	
	public static void drawf2String(int x, int y, String text, Color color){
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
		TextureImpl.bindNone();
		Color.white.bind();
		Game.font2.drawString(x, y, text ,color);
		Color.white.bind();
		glBindTexture(GL_TEXTURE_2D,0);
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
	}
	
	public static void drawBigString(int x, int y, String text, Color color){
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
		TextureImpl.bindNone();
		Color.white.bind();
		Game.fontBig.drawString(x, y, text ,color);
		Color.white.bind();
		glBindTexture(GL_TEXTURE_2D,0);
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
	}
}
