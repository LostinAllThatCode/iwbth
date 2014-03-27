package org.gdesign.iwbth.game.main;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.entity.Player;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.states.GameStateManager;
import org.gdesign.iwbth.game.texture.TextureManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.TextureImpl;

public class Game {

	public static int WIDTH,HEIGHT,TSIZE;
	public static GameStateManager GSM;
	
	protected static long lastFPS;
	public static int fps;
	protected static TrueTypeFont font;
	protected static long lastFrame;

	protected static boolean isRunning = false;
	
	public static boolean showTileGrid = false;
	
	public Audio oggStream;
	
	public Game(int width, int height, int tilesize) throws LWJGLException{
		initOpenGL(width,height);
		
		
		
		TextureManager.init();
		EntityManager.addEntity(new Player(200,400,TextureManager.getSpriteSheet(TextureManager.PLAYER)));
		
		Game.WIDTH = width;
		Game.HEIGHT = height;
		Game.TSIZE = width/20;
		Game.GSM = new GameStateManager();
		Game.font = new TrueTypeFont(new Font("Cordia UPC", Font.PLAIN, 10),false);
		
		Game.lastFPS = getTime();
		
		getDelta();
		
		ControllerManager.bindKeys();
	
		AudioManager.init();	
	}
	
	public void start(){
		isRunning = true;
		gameLoop();
	}
	
	private void initOpenGL(int width, int height) throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(width,height));
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
	
	private void gameLoop(){
		while (!Display.isCloseRequested() && isRunning){
			int delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			updateFPS();
			
			GSM.handleEvents();
			GSM.update(delta);
			GSM.draw();
			
			Display.sync(60);
			Display.update();
		}
		GSM.cleanUp();
		AL.destroy();
	}
	
	
	
	private void updateFPS() {
		if (getTime() - Game.lastFPS > 1000) {
			Display.setTitle("FPS: " + Game.fps);
			Game.fps = 0;
			Game.lastFPS += 1000;
		}
		Game.fps++;
	}
	
	public static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    
	    return delta;
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
}
