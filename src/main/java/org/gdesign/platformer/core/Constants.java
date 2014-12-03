package org.gdesign.platformer.core;

public class Constants {

	public static final float WORLD_TO_BOX = 0.02f;
	public static final float BOX_TO_WORLD = 50f;
	
	public static final int CATEGORY_WORLD			= 0x1000;
	public static final int CATEGORY_PLAYER_HEAD 	= 0x0001;
	public static final int CATEGORY_PLAYER_FOOT 	= 0x0002;
	public static final int CATEGORY_PLAYER_LEFT 	= 0x0003;
	public static final int CATEGORY_PLAYER_RIGHT 	= 0x0004;
	 
	public static final int CATEGORY_UPGRADE		= 0x0010;
	public static final int CATEGORY_LIGHT			= 0x0100; 
	
	public static final int CATEGORY_PLAYER 		= CATEGORY_PLAYER_HEAD | CATEGORY_PLAYER_FOOT | CATEGORY_PLAYER_LEFT | CATEGORY_PLAYER_RIGHT;
	
	public static final int MASK_PLAYER 	= CATEGORY_WORLD | CATEGORY_UPGRADE | CATEGORY_LIGHT;
	public static final int MASK_WORLD 		= -1;
	public static final int MASK_UPGRADE 	= CATEGORY_PLAYER | CATEGORY_LIGHT;
	public static final int MASK_LIGTH 		= CATEGORY_WORLD | CATEGORY_PLAYER | CATEGORY_UPGRADE;
}
