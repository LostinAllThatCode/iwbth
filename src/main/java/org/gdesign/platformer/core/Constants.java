package org.gdesign.platformer.core;

public class Constants {

	public static final float WORLD_TO_BOX = 0.02f;
	public static final float BOX_TO_WORLD = 50f;
	
	
	public static final int CAT_PlayerHead 	= 0x0001;
	public static final int CAT_PlayerFeet 	= 0x0002;
	public static final int CAT_PlayerLeft 	= 0x0004;
	public static final int CAT_PlayerRight	= 0x0008;
	public static final int CAT_Player 		= 0x000F;
	
	public static final int CAT_Upgrade		= 0x0010;
	public static final int CAT_Light		= 0x0020; 
	public static final int CAT_World		= 0x0100;

	public static final int MASK_World 		= -1;
	public static final int MASK_Player 	= CAT_World | CAT_Upgrade;	
	public static final int MASK_Upgrade 	= CAT_Player;

	
}
