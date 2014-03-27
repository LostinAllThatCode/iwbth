package org.gdesign.iwbth.game.entity;

import java.util.ArrayList;

public class EntityManager {
	
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static ArrayList<Entity> trash = new ArrayList<Entity>();
	
	private static Player player;
	
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
		update();
	}
	
	public static void draw(){
		for (Entity e : entities){
			e.draw();
		}
	}

	public static Player getPlayer() {
		if (player != null)	return player; else return null;
	}

}
