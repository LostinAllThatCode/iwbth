package org.gdesign.iwbth.game.tilemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.gdesign.iwbth.game.entity.Entity;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Functions;
import org.gdesign.iwbth.game.texture.TextureManager;

public class MapManager {
	public static final int UP 		= 0x01;
	public static final int DOWN 	= 0x02;
	public static final int LEFT 	= 0x03;
	public static final int RIGHT 	= 0x04;
	
	private static boolean initialized = false;
	
	private static int tilesize;
	
	private static TileMap map;
	
	public static void init(){
		if (!initialized) {	
			new Thread(new Runnable() {
				@Override
				public void run() {				
					try {
						System.out.println(Functions.getTimeStamp()+" [MAPMANAGER] : Initializing map...");
						tilesize = Constants.GAME_TILESIZE;
						System.out.println(Functions.getTimeStamp()+" [MAPMANAGER] : Tilesize is set to "+tilesize);
						map = loadMapFromFile("data/maptest");
						System.out.println(Functions.getTimeStamp()+" [MAPMANAGER] : "+map);
						initialized = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	public static void update() {
		if (map != null) map.update();	
	}
	
	public static void move(long delta) {
		if (map != null) map.move(delta);	
	}
	
	public static void draw(){
		if (map != null) map.draw();
	}
	
	//TODO: Loading map data from binary file
	public static TileMap loadMapFromBinary(){return map;}
	
	public static TileMap loadMapFromFile(String path) throws IOException{
		System.out.println(Functions.getTimeStamp()+" [MAPMANAGER] : Parsing data from "+path+"...");
		InputStreamReader in = new InputStreamReader(ClassLoader.getSystemResourceAsStream(path));
		BufferedReader inputreader = new BufferedReader(in);
		String line = null;
		boolean found = false;
		TileMap tilemap = null;
		int x=0,y=0;
		while ( (line = inputreader.readLine()) != null) {
			if (line.compareTo("<spritelayer>") == 0){
				tilemap = new TileMap();
				found = true;
			} else if ( line.compareTo("</spritelayer>") == 0) {				
				found = false;
			} else if (found) {
				if (line.startsWith("[") && line.endsWith("]")){
					ArrayList<Tile> tileset = new ArrayList<Tile>();					
					String data = line.substring(1, line.length()-1);
					String[] dataarray = data.split(",");
					for (int i=0; i<dataarray.length;i++){
						tileset.add(new Tile(x, y, tilesize, 
								TextureManager.getSpriteFromSpriteSheet(TextureManager.WORLD, Integer.valueOf(dataarray[i]))));
						x+=tilesize;
						if ((x / tilesize) > 19) x = 0;
					}
					tilemap.addTileSet(tileset);					
					y+=tilesize;
					if ((y / tilesize) > 14) y = 0;
				}			
			} else {
				System.out.println(Functions.getTimeStamp()+" [MAPMANAGER] : Unknown/invalid line. Can't parse this line:"+line);
			}
		}
		inputreader.close();
		in.close();
		return tilemap;
	}
	
	public static void shiftMap(int position){
		switch (position) {
			case UP: 	map.setOffsetY(-15); 	break;
			case DOWN:	map.setOffsetY(15);		break;
			case LEFT:	map.setOffsetX(-20);	break;
			case RIGHT:	map.setOffsetX(20);		break;
		}		
		EntityManager.cleanUp();
	}
	
	public static Tile getTileConnectedToEntity(Entity e, int location){
		if (map != null) {		
			switch (location) {
			case UP:
				return map.getTile(e.getX(),e.getY()-e.getHeight());
			case DOWN:
				return map.getTile(e.getX(),e.getY());
			case LEFT:
				return map.getTile(e.getX()-e.getWidth(),e.getY()-1);
			case RIGHT:
				return map.getTile(e.getX()+e.getWidth(),e.getY()-1);
			default:
				return null;
			}
		}
		return null;
	}
	
	public static Tile getTileOfEntity(Entity e){
		if (map != null) return map.getTile(e.getX(),e.getY()-1); return null;
	}
	
	public static Tile getTile(int x, int y) {
		if (map != null) return map.getTile(x, y); return null;
	}
	
	public static boolean initialized() { return initialized; }

}
