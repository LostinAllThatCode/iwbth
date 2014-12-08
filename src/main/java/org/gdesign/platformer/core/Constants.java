package org.gdesign.platformer.core;

public class Constants {

	public static final float WORLD_TO_BOX = 0.02f;
	public static final float BOX_TO_WORLD = 50f;
	
	
	public static final int CATEGORY_PLAYER_HEAD 	= 0x0001;
	public static final int CATEGORY_PLAYER_FEET 	= 0x0002;
	public static final int CATEGORY_PLAYER_LEFT 	= 0x0004;
	public static final int CATEGORY_PLAYER_RIGHT	= 0x0008;
	public static final int CATEGORY_PLAYER 		= 0x000F;
	
	public static final int CATEGORY_ENEMY 			= 0x0010;
	public static final int CATEGORY_ENEMY_NOWORLD 	= 0x0020;
	
	public static final int CATEGORY_UPGRADE		= 0x0080;
	
	public static final int CATEGORY_OBJECT			= 0x0100;
	public static final int CATEGORY_WORLD			= 0x0800;
	
	public static final int CATEGORY_LIGHT			= 0x1000;

	public static final int MASK_WORLD 				= -1;
	public static final int MASK_PLAYER 			= CATEGORY_WORLD | CATEGORY_UPGRADE | CATEGORY_ENEMY | CATEGORY_OBJECT;	
	public static final int MASK_UPGRADE 			= CATEGORY_PLAYER;
	public static final int MASK_ENEMY 				= CATEGORY_WORLD | CATEGORY_PLAYER;
	public static final int MASK_ENEMY_NOWORLD		= MASK_ENEMY - CATEGORY_WORLD;
	public static final int MASK_OBJECT				= CATEGORY_PLAYER | CATEGORY_WORLD | CATEGORY_ENEMY;

	
}
