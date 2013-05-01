package main;




import infopacks.AnimationInfoPackFactory;
import infopacks.CameraInfoPackFactory;
import infopacks.CollisionInfoPackFactory;
import infopacks.InputInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import input.InputSystem;

import org.lwjgl.opengl.Display;

import systems.Core;
import systems.RenderSystem;
import systems.ResourceLoader;
import systems.TimerSystem;
import systems.TransformSystem;

/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Game
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	
	public Game()
	{
		init();
	}
	

	private void init()
	{
		initSystems();
		initFactories();
	}
	
	/**
	 * Initialize all of the various systems of the game.
	 */
	private void initSystems()
	{
		core = new Core();
		core.addSystem(new TimerSystem(), 0);
		core.addSystem(new WindowSystem(1680, 1050, "THE GAME"), -1);
		core.addSystem(new RenderSystem(core, core.getSystem(WindowSystem.class).getWidth(), core.getSystem(WindowSystem.class).getHeight()), 1);
		//core.addSystem(new AnimationSystem(core), -1);
		core.addSystem(new InputSystem(core), -1);
		core.addSystem(new TransformSystem(core), -1);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new AnimationInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new CollisionInfoPackFactory());
		core.addFactory(new CameraInfoPackFactory());
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
	private void newGame()
	{
	}
	

}
