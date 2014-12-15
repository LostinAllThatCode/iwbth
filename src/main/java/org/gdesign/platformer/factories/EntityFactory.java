package org.gdesign.platformer.factories;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.json.JSONParser;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.components.Renderable;
import org.gdesign.platformer.components.Type;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.managers.PlayerManager;
import org.gdesign.platformer.managers.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.JsonValue;


public class EntityFactory {

	private static World world;
	private static TextureManager texMan;
	private static PlayerManager playerMan;
	
	public static void initialize(World w){
		world = w;
		texMan = w.setManager(new TextureManager("textures"));
		playerMan = w.setManager(new PlayerManager());
		Gdx.app.debug(EntityFactory.class.toString(), "*");
	}
	
	public static Entity createEntityById(int x, int y, int id){
		JsonValue val = JSONParser.jsonReader.parse(Gdx.files.internal("entities.json"));
		JsonValue prototype = val.get("prototypes").get(id);
		if (prototype != null){
			Entity entity = world.createEntity();
			entity.addComponent(new Type(prototype.getString("type")));
			entity.addComponent(new Position());
			if (prototype.has("renderable")) entity.addComponent(createRenderable(prototype.get("renderable")));
			if (prototype.has("animatable")) entity.addComponent(createAnimatable(prototype.get("animatable")));
			if (prototype.has("behaviour")) entity.addComponent(createBehaviour(prototype.get("behaviour"), entity));
			if (prototype.has("physics")) {
				JsonValue physics = prototype.get("physics");
				String type = prototype.getString("type").toUpperCase();
				if (type.equals("PLAYER")){
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.DynamicBody,
							Constants.CATEGORY_PLAYER, Constants.MASK_PLAYER, entity));
					playerMan.setPlayer(entity);
				} else if (type.equals("ENEMY")) {
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.DynamicBody,
							Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, entity));					
				} else if (type.equals("SLIDER")) {
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.KinematicBody,
							Constants.CATEGORY_WORLD, Constants.MASK_WORLD, entity));					
				}
			}
			entity.addToWorld();
			Gdx.app.debug(EntityFactory.class.toString(), "+ " + entity);
			return entity;
		}
		return null;
	}
	
	private static Renderable createRenderable(JsonValue renderable){
		Texture texture = texMan.getTexture((renderable.has("path") ? renderable.getString("path") : ""));
		int tx = (renderable.has("x") ? renderable.getInt("x") : 0);
		int ty = (renderable.has("y") ? renderable.getInt("y") : 0);
		int width = (renderable.has("width") ? renderable.getInt("width") : texture.getWidth());
		int height = (renderable.has("height") ? renderable.getInt("height") : texture.getHeight());
		int offset_x = (renderable.has("offsetX") ? renderable.getInt("offsetX") : 0);
		int offset_y = (renderable.has("offsetY") ? renderable.getInt("offsetY") : 0);
		boolean flipX = (renderable.has("flipX") ? renderable.getBoolean("flipX") : false);
		boolean flipY = (renderable.has("flipY") ? renderable.getBoolean("flipY") : false);
		
		Renderable c = new Renderable(texture,tx,ty,width,height);
		c.setOffset(offset_x, offset_y);
		c.flip(flipX, flipY);
		return c;
	}

	private static Animatable createAnimatable(JsonValue animatable){
		Texture texture = texMan.getTexture((animatable.has("path") ? animatable.getString("path") : ""));
		String data = (animatable.has("data") ? animatable.getString("data") : "");
		
		int offset_x = (animatable.has("offsetX") ? animatable.getInt("offsetX") : 0);
		int offset_y = (animatable.has("offsetY") ? animatable.getInt("offsetY") : 0);
		boolean flipX = (animatable.has("flipX") ? animatable.getBoolean("flipX") : false);
		boolean flipY = (animatable.has("flipY") ? animatable.getBoolean("flipY") : false);
		
		Animatable c = new Animatable(texture,data);
		c.setOffset(offset_x, offset_y);
		c.flip(flipX, flipY);
		return c;
	}
	
	private static Behaviour createBehaviour(JsonValue behaviour, Entity entity){
		String lua = (behaviour.has("lua") ? behaviour.getString("lua") : "");
		if (behaviour.has("args")){
			JsonValue args = behaviour.get("args");
			if (args.isArray())
			{
				Object[] objects = new Object[args.size];
				for (int i=0;i<args.size;i++){
					switch (args.get(i).type()) {
					case object:
						objects[i] = (Object) args.get(i);
						break;
					case stringValue:
						if (args.get(i).asString().equals("_self")) objects[i] = entity;
						else objects[i] = args.get(i).asString();
						break;									
					case booleanValue:
						objects[i] = args.get(i).asBoolean();	
						break;
					case doubleValue:
					case longValue:
						objects[i] = args.get(i).asFloat();	
						break;
					default:
						objects[i] = null;
						break;
					};
				}
				return new Behaviour(entity, behaviour.getString("lua"),objects);	
			} else {
				Object obj =  behaviour.get("args");
				switch (args.type()) {
				case object:
					break;
				case stringValue:
					if (args.asString().equals("_self")) obj = entity;
					else obj = args.asString();
					break;									
				case booleanValue:
					obj = args.asBoolean();	
					break;
				case doubleValue:
				case longValue:
					obj = args.asFloat();	
					break;
				default:
					obj = null;
					break;
				};
				return new Behaviour(entity, behaviour.getString("lua"),obj);	
			}
		} else return new Behaviour(entity, lua);
	}
	
	private static Physics createPhysics(JsonValue behaviour, Entity entity){
		return null;
	}
}
