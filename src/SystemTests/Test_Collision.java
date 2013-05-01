package SystemTests;




import infopacks.CameraInfoPackFactory;
import infopacks.CollisionInfoPackFactory;
import infopacks.GravityInfoPackFactory;
import infopacks.InputInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import input.InputSystem;
import main.WindowSystem;

import org.lwjgl.opengl.Display;

import systems.CollisionSystem;
import systems.Core;
import systems.GravitySystem;
import systems.RenderSystem;
import systems.ResourceLoader;
import systems.TimerSystem;
import systems.TransformSystem;

import components.CollisionComponent;
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
public class Test_Collision
{
	private Core core;
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_Collision ts = new Test_Collision();
		ts.loop();
	}
	public Test_Collision()
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
		core.addSystem(new CollisionSystem(core), 0);
		
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new CameraInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new CollisionInfoPackFactory());
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
		IEntity cube = new Entity();
		cube.setName("Cube");
		cube.addComponent(TransformComponent.class, new TransformComponent(cube));
		cube.getComponent(TransformComponent.class).setYPos(256);
		cube.getComponent(TransformComponent.class).setXPos(256);
		cube.getComponent(TransformComponent.class).setWidth(64);
		cube.getComponent(TransformComponent.class).setHeight(64);
		cube.addComponent(RenderComponent.class, new RenderComponent(cube, 0, true));
		cube.addComponent(VelocityComponent.class, new VelocityComponent(cube));
		cube.getComponent(VelocityComponent.class).setInterval(4);
		cube.getComponent(VelocityComponent.class).setXVelocity(0);
		cube.addComponent(InputComponent.class, new InputComponent(cube));
		cube.getComponent(InputComponent.class).setInterested("MOVE_DOWN");
		cube.getComponent(InputComponent.class).setInterested("MOVE_UP");
		cube.getComponent(InputComponent.class).setInterested("MOVE_RIGHT");
		cube.getComponent(InputComponent.class).setInterested("MOVE_LEFT");
		cube.getComponent(InputComponent.class).setInterested("STOPX");
		cube.getComponent(InputComponent.class).setInterested("STOPY");
		cube.addComponent(GravityComponent.class, new GravityComponent(cube));
		cube.getComponent(GravityComponent.class).setAcceleration(1);
		cube.getComponent(GravityComponent.class).setMaxAcceleration(20);
		cube.getComponent(GravityComponent.class).setUpdateInterval(100);
		cube.addComponent(CollisionComponent.class, new CollisionComponent(cube));
		cube.getComponent(CollisionComponent.class).setCollisionGroup(0);
		core.addEntity(cube);
		
		IEntity ground = new Entity();
		ground.setName("Ground");
		ground.addComponent(TransformComponent.class, new TransformComponent(ground));
		ground.getComponent(TransformComponent.class).setYPos(768);
		ground.getComponent(TransformComponent.class).setXPos(0);
		ground.getComponent(TransformComponent.class).setWidth(1280);
		ground.getComponent(TransformComponent.class).setHeight(32);
		ground.addComponent(RenderComponent.class, new RenderComponent(ground, 0, true));
		ground.addComponent(CollisionComponent.class, new CollisionComponent(ground));
		ground.getComponent(CollisionComponent.class).setCollisionGroup(2);
		core.addEntity(ground);

		IEntity cloud = new Entity();
		cloud.setName("Cloud");
		cloud.addComponent(TransformComponent.class, new TransformComponent(cloud));
		cloud.getComponent(TransformComponent.class).setYPos(512);
		cloud.getComponent(TransformComponent.class).setXPos(0);
		cloud.getComponent(TransformComponent.class).setWidth(1280);
		cloud.getComponent(TransformComponent.class).setHeight(32);
		cloud.addComponent(RenderComponent.class, new RenderComponent(cloud, 0, true));
		cloud.addComponent(CollisionComponent.class, new CollisionComponent(cloud));
		cloud.getComponent(CollisionComponent.class).setCollisionGroup(1);
		core.addEntity(cloud);
		
		core.getSystem(CollisionSystem.class).setCollision(0, 1, false);
		core.getSystem(CollisionSystem.class).setCollision(0, 2, true);
	}
	

}
