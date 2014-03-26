package org.gdesign.iwbth.game.main;

import org.lwjgl.LWJGLException;

public class Main {

	public static void main(String[] args) {
		Game game;
		try {
			game = new Game(800,600,30);
			game.start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
	}

}
