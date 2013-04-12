package SystemTests;




import infopacks.CameraInfoPackFactory;
import infopacks.CursorInfoPackFactory;
import infopacks.GravityInfoPackFactory;
import infopacks.InputInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import input.BindMap;
import input.InputSystem;
import main.WindowSystem;

import org.lwjgl.opengl.Display;

import systems.Core;
import systems.EntityForgeSystem;
import systems.GUISystem;
import systems.GravitySystem;
import systems.MovementSystem;
import systems.RenderSystem;
import systems.ResourceLoader;
import systems.TimerSystem;
import actions.ActionMakeBlock;
import entities.IEntity;

/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Test_GravitySystem
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_GravitySystem tgs = new Test_GravitySystem();
		tgs.loop();
	}
	public Test_GravitySystem()
	{
		init();
		System.out.println("Launching Gravity System Test");
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
		core.addSystem(new WindowSystem(1680, 050, "THE GAME"), -1);
		core.addSystem(new RenderSystem(core, core.getSystem(WindowSystem.class).getWidth(), core.getSystem(WindowSystem.class).getHeight()), 1);
		core.addSystem(new InputSystem(core), -1);
		core.addSystem(new MovementSystem(core), -1);
		core.addSystem(new EntityForgeSystem(core), -1);
		core.addSystem(new GUISystem(core), -1);
		core.addSystem(new GravitySystem(core), -1);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new CursorInfoPackFactory());
		core.addFactory(new CameraInfoPackFactory());
		core.addFactory(new GravityInfoPackFactory());
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
		EntityForgeSystem efs = core.getSystem(EntityForgeSystem.class);
		rl.loadTexture("res/gui.png");
		IEntity cursor = core.getSystem(EntityForgeSystem.class).createCursor();
		efs.makeControllable(cursor, "MAKEBLOCK");
		
		BindMap mbs = new BindMap();
		mbs.bind(0, new ActionMakeBlock(core, "MAKEBLOCK"), mbs.PRESS);
		core.getSystem(InputSystem.class).setBindSystem(core.getSystem(InputSystem.class).MOUSE, mbs);
	}
	

}
