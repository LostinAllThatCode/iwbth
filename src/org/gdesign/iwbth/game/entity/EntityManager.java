package org.gdesign.iwbth.game.entity;

import java.util.ArrayList;
import java.util.Vector;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.TextureManager;

public class EntityManager {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	private static Vector<Entity> addentities = new Vector<Entity>();
	private static Vector<Entity> trashbin = new Vector<Entity>();

	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private static ArrayList<PlayerShot> shotsAvailable	= new ArrayList<PlayerShot>();
	private static ArrayList<PlayerShot> shotsInUse		= new ArrayList<PlayerShot>();
	
	private static int MAX_PLAYER_SHOTS = 10;
	
	private static Player player;
	private static DebugMonitor monitor;
	
	private static int KILLCOUNTER = 0;
	
	public static void init(){		
		addEntity(new Player(160,0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));
		addEntity(new DebugMonitor(0,0,Game.WIDTH,200));
		
		while (shotsAvailable.size() < 10)
			shotsAvailable.add(new PlayerShot(0, 0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));

	}
	
	public static void addEntity(Entity e){
		if (e instanceof Player) {
			player = (Player) e;
		} else if (e instanceof DebugMonitor) {
			monitor = (DebugMonitor) e; 
		} else addentities.add(e);
	}
	
	
	public static void update(long delta){		
		for (int i=1; i<= 5-entities.size(); i++) {
			EnemyMe enemy = new EnemyMe(myRandom(0,Game.WIDTH),myRandom(0,Game.HEIGHT-200),TextureManager.getSpriteSheet(TextureManager.PLAYER));
			enemy.setRandomFacing(myRandom(1, 4));
			addEntity(enemy);
		 }		 
		
		for (PlayerShot s : shotsAvailable) {
			shotsInUse.remove(s);	
		}
		
		while (!addentities.isEmpty()) {
			entities.add(addentities.get(0));
			addentities.remove(0);
		}
		
		while (!trashbin.isEmpty()) {
			entities.remove(trashbin.get(0));
			trashbin.remove(0);
		}
		
		move(delta);
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
		player.draw();
		monitor.draw();
		
		for (PlayerShot p : shotsInUse){
			p.draw();
		}
		for (Entity e : entities){
			e.draw();
		}
	}

	public static Player getPlayer() {
		if (player != null)	return player; else return null;
	}
	
	public static DebugMonitor getMonitor() {
		if (monitor != null)	return monitor; else return null;
	}
	
	public static int getShotCount(){
		return shotsInUse.size();
	}
	
	public static boolean addShot(int x, int y, int direction){		
		if (shotsInUse.size() < MAX_PLAYER_SHOTS){
			if (!shotsAvailable.isEmpty()) {
				PlayerShot shot = shotsAvailable.get(0);
				shot.setDirection(x, y, direction);
				shotsInUse.add(shot);
				shotsAvailable.remove(shot);
				return true;
			}			
		}
		return false;
	}
	
	public static void removeEntity(Entity e){	
		if (e instanceof Player) {
			Game.GSM.setState(1);
		} else {
			trashbin.add(e);		
			KILLCOUNTER++;		
		}
	}
	
	public static void removeShot(PlayerShot p){
		shotsAvailable.add(p);
	}
	
	public static int getKillCounter(){
		return KILLCOUNTER;
	}
	
	public static void cleanUp(){
		addentities.clear();
		trashbin.clear();
		entities.clear();
		
		shotsAvailable.clear();
		shotsInUse.clear();
		
		KILLCOUNTER=0;
	}
	
	//TODO: REMOVE THIS TEMPORARY FUNCTION
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
