package SystemTests;




import infopacks.CameraInfoPackFactory;
import infopacks.GravityInfoPackFactory;
import infopacks.InputInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import input.InputSystem;
import main.WindowSystem;

import org.lwjgl.opengl.Display;

import systems.Core;
import systems.GravitySystem;
import systems.RenderSystem;
import systems.ResourceLoader;
import systems.TimerSystem;
import systems.TransformSystem;

import components.GravityComponent;
import components.InputComponent;
import components.RenderComponent;
import components.TransformComponent;
import components.VelocityComponent;

import entities.Entity;
import entities.IEntity;

/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Test_Gravity
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_Gravity ts = new Test_Gravity();
		ts.loop();
	}
	public Test_Gravity()
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
		core.addSystem(new InputSystem(core), 0);
		core.addSystem(new GravitySystem(core), 0);
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new CameraInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());

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
		IEntity e = new Entity();
		e.addComponent(TransformComponent.class, new TransformComponent(e));
		e.getComponent(TransformComponent.class).setYPos(256);
		e.getComponent(TransformComponent.class).setWidth(64);
		e.getComponent(TransformComponent.class).setHeight(64);
		e.addComponent(RenderComponent.class, new RenderComponent(e, 0, true));
		e.addComponent(VelocityComponent.class, new VelocityComponent(e));
		e.getComponent(VelocityComponent.class).setInterval(4);
		e.getComponent(VelocityComponent.class).setXVelocity(0);
		e.addComponent(InputComponent.class, new InputComponent(e));
		e.getComponent(InputComponent.class).setInterested("MOVE_DOWN");
		e.getComponent(InputComponent.class).setInterested("MOVE_UP");
		e.getComponent(InputComponent.class).setInterested("MOVE_RIGHT");
		e.getComponent(InputComponent.class).setInterested("MOVE_LEFT");
		e.addComponent(GravityComponent.class, new GravityComponent(e));
		e.getComponent(GravityComponent.class).setAcceleration(1);
		e.getComponent(GravityComponent.class).setMaxAcceleration(20);
		e.getComponent(GravityComponent.class).setUpdateInterval(100);
		
		core.addEntity(e);
	}
	

}
