package org.gdesign.iwbth.game.entity;

import java.util.ArrayList;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.TextureManager;

public class EntityManager {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	private static ArrayList<Entity> addentities = new ArrayList<Entity>();
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static ArrayList<Entity> trashbin = new ArrayList<Entity>();
	
	private static ArrayList<PlayerShot> shotsAvailable	= new ArrayList<PlayerShot>();
	private static ArrayList<PlayerShot> shotsInUse		= new ArrayList<PlayerShot>();
	
	private static int MAX_PLAYER_SHOTS = 10;
	
	private static Player player;
	private static DebugMonitor mon;
	
	private static int KILLCOUNTER = 0;
	
	public static void init(){
		if (shotsAvailable.size() == 0){
			for (int i=1; i<=MAX_PLAYER_SHOTS;i++) shotsAvailable.add(new PlayerShot(0, 0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));
		}
	}
	
	public static void addEntity(Entity e){
		if (e instanceof Player) {
			player = (Player) e;
		} else if (e instanceof DebugMonitor) {
			mon = (DebugMonitor) e;
		} else addentities.add(e);
	}
	
	public static void update(){
		for (PlayerShot s : shotsAvailable) {
			shotsInUse.remove(s);	
		}
		
		if (addentities.size() != 0){
			for (Entity e : addentities){
				entities.add(e);
			}
			for (Entity e : entities){
				addentities.remove(e);
			}
		}
		
		if (trashbin.size() > 0){
			for (Entity e : trashbin) {
				entities.remove(e);		
			}		
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
		
		update();
	}
	
	public static void draw(){		
		player.draw();
		mon.draw();
		
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
		trashbin.add(e);
		KILLCOUNTER++;
		if (e instanceof Player) {
			//Game.isRunning = false;
			Game.GSM.setState(1);
		} else {
			for (int i=1; i<= 5-entities.size(); i++) {
				EnemyMe enemy = new EnemyMe(myRandom(0,Game.WIDTH),myRandom(0,Game.HEIGHT-200),TextureManager.getSpriteSheet(TextureManager.PLAYER));
				enemy.setRandomFacing(myRandom(1, 4));
				addEntity(enemy);
			 }
	 	}		
	}
	
	public static void removeShot(PlayerShot p){
		shotsAvailable.add(p);
	}
	
	public static int getKillCounter(){
		return KILLCOUNTER;
	}
	
	//TODO: REMOVE THIS TEMPORARY FUNCTION
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
