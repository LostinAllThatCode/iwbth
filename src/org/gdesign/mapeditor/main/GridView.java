package org.gdesign.mapeditor.main;

import java.util.ArrayList;

public class GridView {
		
	private ArrayList<ArrayList<GridItem>> gridview = new ArrayList<ArrayList<GridItem>>();
	
	public GridView(int w, int h){
		int tilesize = w/20;
		int x = 0 ,y = 0;
		for (int j=0;j<15;j++){
			gridview.add(new ArrayList<GridItem>());
			for (int i=0;i<20;i++){
				gridview.get(j).add(new GridItem(x,y,tilesize,null));
				x += tilesize;
			}
			x = 0;
			y += tilesize;
		}
	}
	
	public void draw(){
		for (int j=0; j < 15;j++) {
			for (int i=0;i < 20;i++){
				gridview.get(j).get(i).draw();
			}
		}
	}
}
