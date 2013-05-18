package com.jgefroh.tests;





import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.infopacks.AIInfoPackFactory;
import com.jgefroh.infopacks.AnimationInfoPackFactory;
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
public class Test_SI
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_SI ts = new Test_SI();
		ts.loop();
	}
	public Test_SI()
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
		core.addSystem(new CollisionSystem(core), 0);
		core.addSystem(new WeaponSystem(core), 0);
		core.addSystem(new AISystem(core),0);
		core.addSystem(new EntityCreationSystem(core), 1);
		core.addSystem(new EventSystem(core), 1);
		core.addSystem(new HealthCheckSystem(core), 1);
		core.addSystem(new DamageSystem(core), 1);
		core.addSystem(new WinCheckSystem(core), 1);
		core.addSystem(new AnimationSystem(core), 1);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new CollisionInfoPackFactory());
		core.addFactory(new WeaponInfoPackFactory());
		core.addFactory(new BulletInfoPackFactory());
		core.addFactory(new AIInfoPackFactory());
		core.addFactory(new HealthInfoPackFactory());
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
		
		core.getSystem(EntityCreationSystem.class).createAlien(100, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(200, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(300, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(400, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(500, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(600, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(700, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(800, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(900, 150);
		core.getSystem(EntityCreationSystem.class).createAlien(1000, 150);

		
		core.getSystem(EntityCreationSystem.class).createAlien(100, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(200, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(300, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(400, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(500, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(600, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(700, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(800, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(900, 200);
		core.getSystem(EntityCreationSystem.class).createAlien(1000, 200);	

		core.getSystem(EntityCreationSystem.class).createAlien(100, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(200, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(300, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(400, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(500, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(600, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(700, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(800, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(900, 250);
		core.getSystem(EntityCreationSystem.class).createAlien(1000, 250);
		
		core.getSystem(EntityCreationSystem.class).createFort(500, 850);
		core.getSystem(EntityCreationSystem.class).createFort(1000, 850);
		core.getSystem(EntityCreationSystem.class).createFort(1500, 850);
		
		//Set Collision Groups
		//0 = player
		//1 = alien
		//2 = player bullet
		//3 = alien bullet
		//4 = fort
		
		core.getSystem(CollisionSystem.class).setCollision(0, 1, true);	//player, alien
		core.getSystem(CollisionSystem.class).setCollision(1, 2, true);	//alien, pBullet
		core.getSystem(CollisionSystem.class).setCollision(0, 3, true);	//player, aBullet
		core.getSystem(CollisionSystem.class).setCollision(2, 4, true);	//pBullet, fort
		core.getSystem(CollisionSystem.class).setCollision(3, 4, true);	//aBullet, fort
		core.getSystem(CollisionSystem.class).setCollision(3, 1, false);	//aBullet, alien
	}
	
	public void loadTexture()
	{
		rl.loadTexture("res/enemy.png");
		rl.loadTexture("res/fort.png");
		rl.loadTexture("res/player.png");
		rl.loadTexture("res/bullet.png");
	}
	

}
