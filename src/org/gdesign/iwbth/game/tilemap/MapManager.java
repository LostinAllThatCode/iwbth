package org.gdesign.iwbth.game.tilemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.texture.TextureManager;

public class MapManager {
	
	public static enum Shift { UP, DOWN, LEFT, RIGHT };
	
	private static boolean initialized = false;
	
	private static int tilesize;
	
	private static TileMap map;
	
	public static void init(){
		if (!initialized) {	
			new Thread(new Runnable() {
				@Override
				public void run() {				
					try {
						System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Initializing map...");
						tilesize = Constants.GAME_TILESIZE;
						System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Tilesize is set to "+tilesize);
						map = loadMapFromFile("data/maptest");
						System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Map loaded with size "+map+" tiles");
						initialized = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	//TODO:
	public static TileMap loadMapFromBinary(){return map;}
	
	public static TileMap loadMapFromFile(String path) throws IOException{
		System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Parsing data from "+path+"...");
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
				System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Unknown/invalid line. Can't parse this line:"+line);
			}
		}
		inputreader.close();
		in.close();
		return tilemap;
	}
	
	public static void shiftMap(Shift shift){
		System.out.println(Constants.getCurrentTimeStamp()+" [MAPMANAGER] : Shift map operation triggerd > "+shift);
		switch (shift) {
		case UP:
			map.setOffsety(-15);
			break;
		case DOWN:
			map.setOffsety(15);
			break;
		case LEFT:
			map.setOffsetx(-20);
			break;
		case RIGHT:
			map.setOffsetx(20);
			break;
		default:
			return;
		}
		EntityManager.cleanUp();
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
	
	public static Tile getTileAtLocation(int x, int y) {
		if (map != null) return map.getTileAtLocation(x, y); return null;
	}
	
	public static boolean initialized() { return initialized; }

}
