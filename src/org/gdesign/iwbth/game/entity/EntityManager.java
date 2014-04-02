package org.gdesign.iwbth.game.entity;

import java.util.ArrayList;
import java.util.Vector;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.states.GameStateManager;
import org.gdesign.iwbth.game.texture.TextureManager;

public class EntityManager {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	private static Vector<Entity> spawn = new Vector<Entity>();
	private static Vector<Entity> free = new Vector<Entity>();

	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private static ArrayList<PlayerShot> shotsAvailable	= new ArrayList<PlayerShot>();
	private static ArrayList<PlayerShot> shotsInUse		= new ArrayList<PlayerShot>();
	private static int shotsMaximum;
	
	private static Player player;
	private static Monitor monitor;
	
	
	public static void init(){
		System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : Initializing entites...");	
		shotsMaximum = Constants.PLAYER_MAXSHOTS;
		System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : PlayerShots maximum is set to "+shotsMaximum);	
		while (shotsAvailable.size() < 10)
			shotsAvailable.add(new PlayerShot(0, 0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));
		System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : PlayerShots entites loaded.");
		
		add(new Monitor(0,0,0,0));
		add(new Player(150,0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));	
	}
	
	public static void add(Entity e){
		if (e instanceof Player) {
			player = (Player) e;				
		} else if (e instanceof Monitor) {
			monitor = (Monitor) e;
		} else {
			spawn.add(e);
		}
		System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : Entity -> "+e+" added.");
	}
	
	public static void addShot(Entity parent){		
		if (parent instanceof Player){
			if (shotsInUse.size() < shotsMaximum){
				if (!shotsAvailable.isEmpty()) {
					PlayerShot shot = shotsAvailable.get(0);
					shot.setSpawn(parent.getX()+parent.rect.getWidth()/2, parent.getY()-8, parent.getFacing());
					//System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : Shot added by "+parent.getClass().getSimpleName()+"("+shot+")");
					shotsInUse.add(shot);
					shotsAvailable.remove(shot);
				}			
			}	
		}
	}
	
	public static void update(){		
		/*
		for (int i=1; i<= 1-entities.size(); i++) {
			EnemyMe enemy = new EnemyMe(myRandom(0,Constants.GAME_WIDTH),myRandom(0,Constants.GAME_HEIGHT-200),TextureManager.getSpriteSheet(TextureManager.PLAYER));
			enemy.setRandomFacing(myRandom(1, 4));
			add(enemy);
		 }		 
		*/
		for (PlayerShot s : shotsAvailable) shotsInUse.remove(s);
		
		while (!spawn.isEmpty()) {
			entities.add(spawn.get(0));
			spawn.remove(0);
		}
		
		while (!free.isEmpty()) {
			entities.remove(free.get(0));
			System.out.println(Constants.getCurrentTimeStamp()+" [ENTITYMANAGER] : Entity -> "+free.get(0)+" removed.");
			free.remove(0);
		}
	}
	
	public static void move(long delta){	
		player.move(delta);
		
		for (PlayerShot p : shotsInUse){
			p.move(delta);
		}
		
		for (Entity e : entities){
			e.move(delta);
			if (e.rect.intersects(player.rect)) player.kill();
			for (PlayerShot p : shotsInUse){
				p.checkCollsion(e);
			}
		}
	}
	
	public static void draw(){		
		for (PlayerShot p : shotsInUse)	p.draw();
		player.draw();
		monitor.draw();
		for (Entity e : entities) e.draw();
	}

	public static Player getPlayer() 	{ if (player != null)	return player; else return null; }	
	public static Monitor getMonitor() 	{ if (monitor != null)	return monitor; else return null; }
	public static int getShotCount() 	{ return shotsInUse.size(); }
	
	public static void remove(Entity e){
		if (e instanceof Player) {
			GameStateManager.setState(GameStateManager.GAMESTATE_PAUSED);
			AudioManager.setBackgroundSound(AudioManager.SOUND_MUSIC_DEATH, true);
		} else if (e instanceof PlayerShot) {
			shotsAvailable.add((PlayerShot) e);
		} else {
			free.add(e);
		}
	}
	
	public static void cleanUp(){
		spawn.clear();
		free.clear();
		entities.clear();				
		for (PlayerShot s : shotsInUse) shotsAvailable.add(s);
		shotsInUse.clear();		
	}
	
	public static int getEntityCount(){
		return entities.size() + shotsInUse.size() + 1;
	}
	
	//TODO: REMOVE THIS TEMPORARY FUNCTION
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
