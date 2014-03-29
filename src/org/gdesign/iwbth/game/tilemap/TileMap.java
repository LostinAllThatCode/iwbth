package org.gdesign.iwbth.game.tilemap;

import java.util.ArrayList;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.TextureManager;

import static org.gdesign.iwbth.game.texture.TextureManager.*;

public class TileMap {
	
	private int tilesize;
	
	private int offsetx=0,offsety=0;
	
	private int[][] level1 = {	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{2,2,2,2,2,2,2,2,2,2,2,3,3,2,2,2,2,2,2,2,4,2,3,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,6},
								{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
								};
	
	private ArrayList<ArrayList<Tile>> currentMap = new ArrayList<ArrayList<Tile>>();
	
	public TileMap(String level){
		this.tilesize = Game.TSIZE;
		int x = 0 ,y = 0;
		for (int j=0;j<level1.length;j++){
			currentMap.add(new ArrayList<Tile>());
			for (int i=0;i<level1[0].length;i++){
				currentMap.get(j).add(new Tile(x,y,tilesize,TextureManager.getSpriteFromSpriteSheet(WORLD, level1[j][i])));
				x += tilesize;
			}
			x = 0;
			y += tilesize;
		}
	}
	
	public void update(long delta){
		for (int j=offsety; j < offsety+15;j++) {
			for (int i=offsetx;i< offsetx+20;i++){
				currentMap.get(j).get(i).update(delta);
			}
		}
	}
	
	
	public void translate(int dx, int dy){
		for (int j=offsety; j < offsety+15;j++) {
			for (int i=offsetx;i< offsetx+20;i++){
				currentMap.get(j).get(i).translate(dx, dy);
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
	
	public int getTileAtLocation(int x, int y){
		int j = x / tilesize,i = y / tilesize;
		return i*j;
	}

	public int getOffsetx() {
		return offsetx;
	}
  
	public void setOffsetx(int offsetx) {
		this.offsetx = offsetx;
	}

	public int getOffsety() {
		return offsety;
	}

	public void setOffsety(int offsety) {
		this.offsety = offsety;
	}
}
