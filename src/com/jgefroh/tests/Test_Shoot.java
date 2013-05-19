package com.jgefroh.tests;





import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.infopacks.AIInfoPackFactory;
import com.jgefroh.infopacks.AnimationInfoPackFactory;
import com.jgefroh.infopacks.BodyInfoPackFactory;
import com.jgefroh.infopacks.BulletInfoPackFactory;
import com.jgefroh.infopacks.CollisionInfoPackFactory;
import com.jgefroh.infopacks.HealthInfoPackFactory;
import com.jgefroh.infopacks.InputInfoPackFactory;
import com.jgefroh.infopacks.MovementInfoPackFactory;
import com.jgefroh.infopacks.RenderInfoPackFactory;
import com.jgefroh.infopacks.WeaponInfoPackFactory;
import com.jgefroh.input.InputSystem;
import com.jgefroh.systems.AISystem;
import com.jgefroh.systems.AnimationSystem;
import com.jgefroh.systems.BodySystem;
import com.jgefroh.systems.CollisionSystem;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.EntityCreationSystem;
import com.jgefroh.systems.EventSystem;
import com.jgefroh.systems.HealthCheckSystem;
import com.jgefroh.systems.RenderSystem;
import com.jgefroh.systems.ResourceLoader;
import com.jgefroh.systems.TimerSystem;
import com.jgefroh.systems.TransformSystem;
import com.jgefroh.systems.WeaponSystem;
import com.jgefroh.systems.WinCheckSystem;
import com.jgefroh.systems.WindowSystem;


/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Test_Shoot
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_Shoot ts = new Test_Shoot();
		ts.loop();
	}
	public Test_Shoot()
	{
		init();
	}
	

	private void init()
	{
		initSystems();
		initFactories();
		loadTexture();
	}
	
	/**
	 * Initialize all of the various systems of the game.
	 */
	private void initSystems()
	{
		core = new Core();
		core.addSystem(new TimerSystem(core), 0);
		core.addSystem(new WindowSystem(1680, 1050, "Test_SI"), -1);
		core.addSystem(new RenderSystem(core), 1);
		core.addSystem(new TransformSystem(core), -1);
		core.addSystem(new InputSystem(core), 0);
		core.addSystem(new EntityCreationSystem(core), 1);
		core.addSystem(new AnimationSystem(core), 1);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new AnimationInfoPackFactory());
	}
	
	/**
	 * Loop through game functions (main game loop).
	 */
	public void loop()
	{
		newGame();
		while(!Display.isCloseRequested())
		{
			Display.update();
			core.work();
			core.getSystem(WindowSystem.class).updateFPS();
			Display.sync(240);
		}
	}
	////////////
	public void newGame()
	{
		core.getSystem(EntityCreationSystem.class).createPlayer(800, 950);
	}
	
	public void loadTexture()
	{
		rl.loadTexture("res/player.png");
		rl.loadTexture("res/bullet.png");
	}
	

}
