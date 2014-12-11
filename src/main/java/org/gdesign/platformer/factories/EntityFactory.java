package org.gdesign.platformer.factories;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.json.JSONParser;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.components.Renderable;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.entities.Enemy;
import org.gdesign.platformer.entities.Player;
import org.gdesign.platformer.entities.Slider;
import org.gdesign.platformer.managers.PlayerManager;
import org.gdesign.platformer.managers.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;


public class EntityFactory {

	private static World world;
	private static TextureManager texMan;
	private static PlayerManager playerMan;
	
	public static void initialize(World w){
		world = w;
		texMan = w.setManager(new TextureManager("textures"));
		playerMan = w.setManager(new PlayerManager());
	}
	
	public static Entity createEntityById(int x, int y, int id){
		JsonValue val = JSONParser.jsonReader.parse(Gdx.files.internal("entities.json"));
		JsonValue prototype = val.get("prototypes").get(id);
		if (prototype != null){
			Entity entity = world.createEntity();
			if (prototype.has("texture")) {
				JsonValue texture = prototype.get("texture");
				if (texture.has("json")) {
					Animatable component = new Animatable(texMan.getTexture(texture.getString("path")), texture.getString("json"));
					if (texture.has("x") && texture.has("y") && texture.has("width") && texture.has("height") ){
						component.setRegion( texture.getInt("x"), texture.getInt("y"), texture.getInt("width"), texture.getInt("height"));
					}
					entity.addComponent(component);
				} else {
					Renderable component = (new Renderable(texMan.getTexture(texture.getString("path"))));
					if (texture.has("x") && texture.has("y") && texture.has("width") && texture.has("height") ){
						component.setRegion( texture.getInt("x"), texture.getInt("y"), texture.getInt("width"), texture.getInt("height"));
					}
					entity.addComponent(component);
				}		
			}
			if (prototype.has("behaviour")) {
				JsonValue behaviour = prototype.get("behaviour");
				if (behaviour.has("lua")) {
					if (behaviour.has("init")){
						JsonValue init = behaviour.get("init");
						if (init.isArray())
						{
							Object[] objects = new Object[init.size];
							for (int i=0;i<init.size;i++){
								switch (init.get(i).type()) {
								case object:
									objects[i] = (Object) init.get(i);
									break;
								case stringValue:
									if (init.get(i).asString().equals("_self")) objects[i] = entity;
									else objects[i] = init.get(i).asString();
									break;									
								case booleanValue:
									objects[i] = init.get(i).asBoolean();	
									break;
								case doubleValue:
								case longValue:
									objects[i] = init.get(i).asFloat();	
									break;
								default:
									objects[i] = null;
									break;
								};
							}
							entity.addComponent(new Behaviour(entity, behaviour.getString("lua"),objects));	
						}
					} else entity.addComponent(new Behaviour(entity, behaviour.getString("lua")));	
				}	
			}
			if (prototype.has("physics")) {
				JsonValue physics = prototype.get("physics");
				if (prototype.getString("type").equals("player")){
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.DynamicBody,
							Constants.CATEGORY_PLAYER, Constants.MASK_PLAYER, entity));
					playerMan.setPlayer(entity);
				} else if (prototype.getString("type").equals("enemy")) {
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.DynamicBody,
							Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, entity));					
				} else if (prototype.getString("type").equals("slider")) {
					entity.addComponent(new Physics(world, x, y, physics.getInt("width"), physics.getInt("height"), BodyType.KinematicBody,
							Constants.CATEGORY_WORLD, Constants.MASK_WORLD, entity));					
				}
			}
			
			entity.addComponent(new Position());
			entity.addToWorld();
			System.out.println(entity);
			return entity;
		}
		return null;
	}
	
	public static Entity createPlayer(int x, int y){
		Entity player = new Player(world,x,y,texMan.getTexture("textures/entity/player/player.png"),texMan.getAnimationData("textures/entity/player/player.png"));
		playerMan.setPlayer(player);
		return player;
	}
	
	public static Entity createEnemy(int x, int y, String texture, String behaviourLua, boolean isAnim){
		if (isAnim) return new Enemy(world, x, y, behaviourLua, texMan.getTexture(texture), texMan.getAnimationData(texture));
		else return new Enemy(world, x, y, behaviourLua, texMan.getTexture(texture));
	}
	
	public static Entity createSlider(int x, int y, String texture, String behaviourLua, float startTime){
		return new Slider(world, x, y, behaviourLua, startTime);
	}
}
