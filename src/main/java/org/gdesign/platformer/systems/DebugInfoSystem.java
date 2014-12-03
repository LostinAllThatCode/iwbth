package org.gdesign.platformer.systems;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.gdesign.games.ecs.BaseSystem;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.games.ecs.systems.IntervalEntitySystem;
import org.gdesign.games.ecs.systems.SingleProcessSystem;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Controller;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.managers.PlayerManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class DebugInfoSystem extends SingleProcessSystem {

	private SpriteBatch batch;
	private FreeTypeFontGenerator gen;
	private FreeTypeFontParameter settings;
	private BitmapFont font;
	private int lineY = 0;
	private int lineheight = 12;
	private DecimalFormat df = new DecimalFormat("0.0");
		
	public void setFontSize(int size){
		settings.size = size;
		font = this.gen.generateFont(settings);
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VCR_OSD_MONO.ttf"));
		settings = new FreeTypeFontParameter();
		settings.size = 10;
		font = gen.generateFont(settings);
		df.setDecimalSeparatorAlwaysShown(true);
		gen.dispose();
	}
	
	@Override
	protected void begin() {
	}
	
	
	public void processSystem() {
		batch.begin();
		lineY = 8;
		//RIGHT - ALIGNMENT
		font.setColor(1, 1, 1, 1f);
		
		//LEFT - ALIGNMENT
		font.draw(batch, "[OPENGL]     DESKTOP: "+ Gdx.graphics.getDesktopDisplayMode().toString().toUpperCase() + " | GAME: " +Gdx.graphics.getWidth() +"x"+ Gdx.graphics.getHeight() + " FPS: "+ Gdx.graphics.getFramesPerSecond(), 10, lineY+=lineheight);		
		font.draw(batch,getPlayerInfo(),10,lineY+=lineheight);
		font.drawMultiLine(batch, getCameraInfo(),  10,lineY+=lineheight,10,HAlignment.LEFT);
		font.draw(batch, getLightInfo(),10,lineY+=lineheight);
		lineY+=lineheight;
		font.drawMultiLine(batch, getSystemInfo(),  10,lineY+=lineheight,10,HAlignment.LEFT);

		lineY+=lineheight;
		
		batch.end();
	}
	
	private String getPlayerInfo(){
		String text="[PLAYER]    ";
		Position position = world.getManager(PlayerManager.class).getPlayer().getComponent(Position.class);
		Physics physics = world.getManager(PlayerManager.class).getPlayer().getComponent(Physics.class);
		Animatable anim = world.getManager(PlayerManager.class).getPlayer().getComponent(Animatable.class);
		text += " X: " + (int) (position.x * Constants.BOX_TO_WORLD) + "("+df.format(position.x)+"m) "
				+"Y: " + (int)  (position.y * Constants.BOX_TO_WORLD) + "("+ df.format(position.y) +"m) "
				+"vX: "+ df.format(physics.body.getLinearVelocity().x);
		text += " ANIM: " + anim.getCurrentAnimation() + "("+ anim.getRegion() .isFlipX()+ "|"+ anim.getRegion() .isFlipY() + ") T: "+df.format(anim.stateTime);
		text += " CTRL: " + world.getManager(PlayerManager.class).getPlayer().getComponent(Controller.class);
		return text.toUpperCase();
	}
	
	private String getSystemInfo(){
		String text="[SYSTEMS]    ";
		try {
			ArrayList<BaseSystem> systems = world.getSystems();
			text += "SYSTEMS: "+systems.size()+ " ";
			int EPS=0,SPS=0,IES=0;
			for (BaseSystem sys : systems){
				if (sys instanceof EntityProcessingSystem) EPS++;
				if (sys instanceof SingleProcessSystem) SPS++;
				if (sys instanceof IntervalEntitySystem) IES++;
			}
			text += "EPS: " + EPS + " SPS: " + SPS + " IES: " + IES +" " ;
			text+=" ENTITIES : "+world.getEntityManager().getCreatedCount() +","+ world.getEntityManager().getRemovedCount() +","+ world.getEntityManager().getActiveCount();
		} catch (Exception e) {
			text += "null " + e.getMessage(); 
		}
		return text;
	}
	
	private String getCameraInfo(){
		String text="[CAMERA]     ";
		try {
			CameraSystem camera = world.getSystem(CameraSystem.class);
			text += "X: " + (int) camera.getCamera().position.x + " Y: " + (int) camera.getCamera().position.y ;
			text += " ZOOM: " + df.format(camera.getCamera().zoom);
		} catch (Exception e) {
			text += "null : " + e.toString();
		}
		return text.toUpperCase();
	}
	
	private String getLightInfo(){
		String text="[LIGHT]      ";
		try {
			LightSimulationSystem light = world.getSystem(LightSimulationSystem.class);
			text += "C: " + light.getLightCount();
		} catch (Exception e) {
			text += e.toString();
		}
		return text.toUpperCase();
	}
	
}
