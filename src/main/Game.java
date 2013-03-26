package main;




import infopacks.AnimationInfoPackFactory;
import infopacks.CameraInfoPackFactory;
import infopacks.CollisionInfoPackFactory;
import infopacks.CursorInfoPackFactory;
import infopacks.InputInfoPackFactory;
import infopacks.MovementInfoPackFactory;
import infopacks.RenderInfoPackFactory;
import input.InputSystem;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import systems.AnimationSystem;
import systems.CollisionSystem;
import systems.Core;
import systems.EntityForgeSystem;
import systems.GUISystem;
import systems.MovementSystem;
import systems.RenderSystem;
import systems.TimerSystem;

import components.AnimationComponent.LoopType;
import components.CollisionComponent;
import components.PositionComponent;

import entities.IEntity;

/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Game
{
	private Core core;
	private boolean continueGame = true;		//Continue
	
	
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
		core.addSystem(new AnimationSystem(core), -1);
		core.addSystem(new InputSystem(core), -1);
		core.addSystem(new MovementSystem(core), -1);
		core.addSystem(new EntityForgeSystem(core), -1);
		core.addSystem(new CollisionSystem(core), -1);
		core.addSystem(new GUISystem(core), -1);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new AnimationInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new CursorInfoPackFactory());
		core.addFactory(new CollisionInfoPackFactory());
		core.addFactory(new CameraInfoPackFactory());
	}
	
	/**
	 * Loop through game functions (main game loop).
	 */
	public void loop()
	{
		newGame();
		createEntities();
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
		//createPlayer();
		int baseX = 0;
		int baseY = core.getSystem(WindowSystem.class).getHeight()/15;
		int xSize = 64;
		int ySize = 64;
		for(int row=0;row<3;row++)
		{
			baseX=core.getSystem(WindowSystem.class).getWidth()/3;
			baseY+=ySize/2;
			for(int col=0;col<8;col++)
			{				
				createAlien(col*xSize+baseX, row*ySize+baseY, 
							xSize, ySize, row);
				baseX+=xSize;
			}
		}

	}
	
	
	
	
	
	
	private void createEntities()
	{
		core.getSystem(EntityForgeSystem.class).createPlayer();
		core.getSystem(EntityForgeSystem.class).createCursor();
		core.getSystem(EntityForgeSystem.class).createCamera();
	}
	
	
	private void createAlien(final int xPos, final int yPos,
							final int sizeX, final int sizeY, final int skin)
	{
		EntityForgeSystem efs = core.getSystem(EntityForgeSystem.class);
		IEntity alien = efs.createEntity();
		efs.makeRenderable(alien, sizeX, sizeY, "res/enemy.png");
		efs.makePositionable(alien, xPos, yPos);
		efs.makeAnimated(alien);
		ArrayList<Integer> position = new ArrayList<Integer>();
		position.add(0);
		efs.addAnimation(alien, "IDLE", 500, LoopType.LOOPFROMSTART, position);
		alien.addComponent(CollisionComponent.class, new CollisionComponent(alien));
		alien.getComponent(CollisionComponent.class).setCollision(0, 1, true);
		alien.getComponent(CollisionComponent.class).setCollision(1, 0, true);
		alien.getComponent(CollisionComponent.class).setGroup(0);
		alien.getComponent(PositionComponent.class).setLocalZ(-1);

		core.addEntity(alien);
	}
	

}
