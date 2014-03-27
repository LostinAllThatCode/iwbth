package org.gdesign.iwbth.game.entity;

import java.util.ArrayList;

import org.gdesign.iwbth.game.texture.TextureManager;

public class EntityManager {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static ArrayList<Entity> trash = new ArrayList<Entity>();
	
	private static ArrayList<PlayerShot> shotsAvailable	= new ArrayList<PlayerShot>();
	private static ArrayList<PlayerShot> shotsInUse		= new ArrayList<PlayerShot>();
	
	private static int MAX_PLAYER_SHOTS = 10;
	
	private static int shotsFired = 0;
	
	private static Player player;
	
	public static void init(){
		if (shotsAvailable.size() == 0){
			for (int i=1; i<=MAX_PLAYER_SHOTS;i++) shotsAvailable.add(new PlayerShot(0, 0,TextureManager.getSpriteSheet(TextureManager.PLAYER)));
		}
	}
	
	public static void addEntity(Entity e){
		if (e instanceof Player) player = (Player) e;
		entities.add(e);
	}
	
	public static void update(){
	}
	
	public static void move(long delta){
		for (Entity e : entities){
			e.move(delta);
		}
		
		for (PlayerShot p : shotsInUse){
			p.move(delta);
		}
		
		cleanUpShots();
		update();
	}
	
	public static void draw(){
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

	private static void cleanUpShots(){
		for (PlayerShot s : shotsAvailable) {
			if (shotsInUse.remove(s)) shotsFired--;			
		}
	}
	
	public static void addShot(int x, int y, int direction){		
		if (shotsFired < MAX_PLAYER_SHOTS){
			if (!shotsAvailable.isEmpty()) {
				PlayerShot shot = shotsAvailable.get(shotsAvailable.size()-1);
				shot.setDirection(x, y, direction);
				shotsInUse.add(shot);
				shotsAvailable.remove(shot);
				shotsFired++;
			}			
		}
	}
	
	public static void removeShot(PlayerShot p){
		shotsAvailable.add(p);
	}
}
