package org.gdesign.iwbth.game.tilemap;

import java.util.ArrayList;

import org.gdesign.iwbth.game.main.Constants;

public class TileMap {
	
	private int offsetx=0,offsety=0;
	
	private ArrayList<ArrayList<Tile>> currentMap = new ArrayList<ArrayList<Tile>>();
	
	public TileMap(){
	}
	
	public void addTileSet(ArrayList<Tile> tileset){
		currentMap.add(tileset);
	}

	public void update() {
		for (int j=offsety; j < offsety+15;j++) {
			for (int i=offsetx;i< offsetx+20;i++){
				currentMap.get(j).get(i).update();
			}
		}
	}

	public void move(long delta) {
		for (int j=offsety; j < offsety+15;j++) {
			for (int i=offsetx;i< offsetx+20;i++){
				currentMap.get(j).get(i).move(delta);
			}
		}
	}
	
	public void draw(){
        for (int j=offsety; j < offsety+15;j++) {
			for (int i=offsetx;i< offsetx+20;i++){
				currentMap.get(j).get(i).draw();
			}
		}	
	}
	
	public Tile getTileAtLocation(int x, int y){
		int j = (y / Constants.GAME_TILESIZE) + offsety;
		int i = (x / Constants.GAME_TILESIZE) + offsetx;
		return currentMap.get(j).get(i);
	}

	public int getOffsetx() {
		return offsetx;
	}
  
	public void setOffsetx(int offsetx) {
		this.offsetx += offsetx;
	}

	public int getOffsety() {
		return offsety;
	}

	public void setOffsety(int offsety) {
		this.offsety += offsety;
	}
	
	@Override
	public String toString() {
		String toString = "";
		toString += "MapSize: "+currentMap.size()+"x"+currentMap.get(0).size();
		return toString;
	}


}
