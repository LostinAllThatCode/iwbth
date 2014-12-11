package org.gdesign.platformer.factories;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.entities.Enemy;
import org.gdesign.platformer.entities.Player;
import org.gdesign.platformer.entities.Slider;
import org.gdesign.platformer.managers.PlayerManager;
import org.gdesign.platformer.managers.TextureManager;

public class EntityFactory {

	private static World world;
	private static TextureManager texMan;
	private static PlayerManager playerMan;
	
	public static void initialize(World w){
		world = w;
		texMan = w.setManager(new TextureManager("textures"));
		playerMan = w.setManager(new PlayerManager());
	}
	
	public static Entity createPlayer(int x, int y){
		Entity player = new Player(world,x,y,texMan.getTexture("textures/entity/player/player.png"),texMan.getAnimationData("textures/entity/player/player.png"));
		playerMan.setPlayer(player);
		return player;
	}
	
	public static Entity createEnemy(int x, int y, String texture, String behaviourLua, boolean isAnim){
		if (isAnim) return new Enemy(world, x, y, behaviourLua, texMan.getTexture(texture), texMan.getAnimationData(texture));
		else return new Enemy(world, x, y, behaviourLua, texMan.getTexture(texture));
	}
	
	public static Entity createSlider(int x, int y, String texture, String behaviourLua, float startTime){
		return new Slider(world, x, y, behaviourLua, startTime);
	}
}
