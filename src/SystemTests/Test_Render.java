package SystemTests;




import infopacks.CameraInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import main.WindowSystem;

import org.lwjgl.opengl.Display;

import systems.Core;
import systems.EntityForgeSystem;
import systems.RenderSystem;
import systems.ResourceLoader;
import systems.TimerSystem;
import systems.TransformSystem;

import components.RenderComponent;
import components.TransformComponent;
import components.VelocityComponent;

import entities.IEntity;

/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Test_Render
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_Render ts = new Test_Render();
		ts.loop();
	}
	public Test_Render()
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
		core.addSystem(new WindowSystem(1680, 1050, "Test_Render"), -1);
		core.addSystem(new RenderSystem(core, core.getSystem(WindowSystem.class).getWidth(), core.getSystem(WindowSystem.class).getHeight()), 1);
		core.addSystem(new TransformSystem(core), -1);
		core.addSystem(new EntityForgeSystem(core), -1);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
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
		IEntity e = core.getSystem(EntityForgeSystem.class).createEntity();
		e.addComponent(TransformComponent.class, new TransformComponent(e));
		e.addComponent(RenderComponent.class, new RenderComponent(e, 64, 64, 0, true));
		e.addComponent(VelocityComponent.class, new VelocityComponent(e));
		e.getComponent(VelocityComponent.class).setInterval(4);
		e.getComponent(VelocityComponent.class).setXVelocity(1);
		e.getComponent(VelocityComponent.class).setYVelocity(1);
		core.addEntity(e);
	}
	

}
