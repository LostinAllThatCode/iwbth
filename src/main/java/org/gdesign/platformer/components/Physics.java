package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.Box2dBodyFactory;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.Body;

public class Physics extends BaseComponent{
	
	public static enum PhysicType { PLAYER, ENEMY, UPGRADE, WORLD}
	public boolean _feet,_head,_left,_right;
	
	public Body body;
	public int w,h;
	public boolean isGrounded;
	public PhysicType ptype;
	
	public Physics(World world, int x, int y, int width, int height, PhysicType type){
		switch (type) {
		case PLAYER:
			setBody(Box2dBodyFactory.createPlayerBody(world, x, y, width, height));
			break;
		case ENEMY:
			break;
		case UPGRADE:
			setBody(Box2dBodyFactory.createKynematicBox(world, x, y, width, height,0));
			break;
		case WORLD:
			setBody(Box2dBodyFactory.createStaticBox(world, x, y, width, height,0));
			break;
		default:
			break;
		}
		body.setUserData(this);
		isGrounded = false;
		w = width;
		h = height;
		ptype = type;
	}
		
	public Physics setBody(Body body){
		this.body = body;
		return this;
	}
	
	public Physics setUserdata(Object o){
		this.body.setUserData(o);
		return this;
	}
	
	public void setSensorCollision(int category, boolean collision){
		if (ptype == PhysicType.PLAYER){
			switch (category) {
				case Constants.CAT_PlayerFeet:
					_feet = collision;
					break;
				case Constants.CAT_PlayerHead:
					_head = collision;
					break;
				case Constants.CAT_PlayerLeft:
					_left = collision;
					break;
				case Constants.CAT_PlayerRight:
					_right = collision;
					break;
				default:
					break;
			}
		}
	}
	
	public int getBodyWidth(){
		return w;
	}
	
	public int getBodyHeight(){
		return h;
	}

}
