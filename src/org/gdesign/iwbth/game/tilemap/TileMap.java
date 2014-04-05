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
	
	public Tile getTile(int x, int y){
		int j = (y / Constants.GAME_TILESIZE) + offsety;
		int i = (x / Constants.GAME_TILESIZE) + offsetx;
		if (i < currentMap.get(0).size() && j < currentMap.size()) return currentMap.get(j).get(i); else return null;
	}

	public int getOffsetX() {
		return offsetx;
	}
 	
	public void setOffsetX(int offsetX) {
		this.offsetx += offsetX;
	}

	public int getOffsetY() {
		return offsety;
	}

	public void setOffsetY(int offsetY) {
		this.offsety += offsetY;
	}
	
	@Override
	public String toString() {
		String toString = "";
		toString += "MapSize: "+currentMap.size()+"x"+currentMap.get(0).size()+" Current offset: dX="+offsetx+",dY="+offsety;
		return toString;
	}


}
