package org.gdesign.iwbth.game.texture;

public class Sprite {

	private int id,x,y,w,h,texture;
	
	public Sprite(int texture, int x, int y, int w, int h,int id){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.id = id;
		this.texture = texture;
	}
		
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getWidth(){
		return this.w;
	}
	
	public int getHeight(){
		return this.h;
	}
	
	public int getId(){
		return id;
	}
	
	public int getTexture(){
		return this.texture;
	}
	
	@Override
	public String toString() {
		String s = "";
		s+= "TEXTURE:" + texture + " ID: "+id+" X: "+x+" Y: "+y+ " W: "+w +" H:"+h+"\n";
		return s;
	}
}
