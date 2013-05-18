package com.jgefroh.systems;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.BulletComponent;
import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;



/**
 * This is a temporary system used to create specific entities.
 * The eventual goal is to have this information read from an external file
 * and the entities created from that (like loading a level).
 * @author Joseph Gefroh
 */
public class EntityCreationSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(EntityCreationSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new EntityCreationSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public EntityCreationSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	/**
	 * Initialize the Logger with default settings.
	 */
	private void initLogger()
	{
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(debugLevel);
		LOGGER.addHandler(ch);
		LOGGER.setLevel(debugLevel);
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		initLogger();
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}
	
	@Override
	public void work()
	{
		if(isRunning)
		{
		}
	}

	@Override
	public void stop()
	{	
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	

	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Create a player entity.
	 * @param x	the X Position to create the entity
	 * @param y	the Y Position to create the entity
	 */
	public void createPlayer(final int x, final int y)
	{	
		//0 = player
		//1 = alien
		//2 = player bullet
		//3 = alien bullet
		//4 = fort
		IEntity player = new Entity();
		player.setName("PLAYER");
		player.addComponent(TransformComponent.class, new TransformComponent(player));
		player.getComponent(TransformComponent.class).setYPos(y);
		player.getComponent(TransformComponent.class).setXPos(x);
		player.getComponent(TransformComponent.class).setWidth(64);
		player.getComponent(TransformComponent.class).setHeight(64);
		player.addComponent(RenderComponent.class, new RenderComponent(player));
		player.getComponent(RenderComponent.class).setSpriteID(0);
		player.getComponent(RenderComponent.class).setTextureID(3);
		player.addComponent(VelocityComponent.class, new VelocityComponent(player));
		player.getComponent(VelocityComponent.class).setInterval(4);
		player.getComponent(VelocityComponent.class).setXVelocity(0);
		player.addComponent(InputComponent.class, new InputComponent(player));
		player.getComponent(InputComponent.class).setInterested("MOVE_RIGHT");
		player.getComponent(InputComponent.class).setInterested("MOVE_LEFT");
		player.getComponent(InputComponent.class).setInterested("STOPX");
		player.getComponent(InputComponent.class).setInterested("STOPY");
		player.getComponent(InputComponent.class).setInterested("SHOOT");
		player.getComponent(InputComponent.class).setInterested("QUIT");
		player.addComponent(CollisionComponent.class, new CollisionComponent(player));
		player.getComponent(CollisionComponent.class).setCollisionGroup(0);		
		player.addComponent(WeaponComponent.class, new WeaponComponent(player));
		core.addEntity(player);
	}
	
	/**
	 * Create an alien entity.
	 * @param x	the X Position of the entity
	 * @param y	the Y position of the entity
	 */
	public void createAlien(final int x, final int y)
	{
		IEntity alien = new Entity();
		alien.setName("ALIEN");
		alien.addComponent(TransformComponent.class, new TransformComponent(alien));
		alien.getComponent(TransformComponent.class).setXPos(x);
		alien.getComponent(TransformComponent.class).setYPos(y);
		alien.getComponent(TransformComponent.class).setWidth(32);
		alien.getComponent(TransformComponent.class).setHeight(32);
		alien.addComponent(RenderComponent.class, new RenderComponent(alien));
		alien.getComponent(RenderComponent.class).setSpriteID(1);
		alien.getComponent(RenderComponent.class).setTextureID(2);
		alien.addComponent(VelocityComponent.class, new VelocityComponent(alien));
		alien.getComponent(VelocityComponent.class).setInterval(200);
		alien.getComponent(VelocityComponent.class).setXVelocity(0);
		alien.addComponent(CollisionComponent.class, new CollisionComponent(alien));
		alien.getComponent(CollisionComponent.class).setCollisionGroup(1);
		alien.addComponent(AIComponent.class, new AIComponent(alien));
		alien.getComponent(RenderComponent.class).setTextureID(1);
		alien.getComponent(RenderComponent.class).setTexturePath("res/enemy.png");
		alien.addComponent(WeaponComponent.class, new WeaponComponent(alien));
		int[] a = {0, 1};
		alien.addComponent(AnimationComponent.class, new AnimationComponent(alien));

		alien.getComponent(AnimationComponent.class).addAnimation("IDLE", a, 200);
		int[] b = {2};
		alien.getComponent(AnimationComponent.class).addAnimation("DEAD", b, 200);
		alien.getComponent(AnimationComponent.class).setCurrentAnimation("IDLE");
		core.addEntity(alien);
	}
	
	/**
	 * Create a fort entity.
	 * @param x the X Position of the entity
	 * @param y	the Y positon of the entity
	 */
	public void createFort(final int x, final int y)
	{
		IEntity fort = new Entity();
		fort.setName("FORT");
		fort.addComponent(TransformComponent.class, new TransformComponent(fort));
		fort.getComponent(TransformComponent.class).setXPos(x);
		fort.getComponent(TransformComponent.class).setYPos(y);
		fort.getComponent(TransformComponent.class).setWidth(128);
		fort.getComponent(TransformComponent.class).setHeight(64);
		fort.addComponent(RenderComponent.class, new RenderComponent(fort));
		fort.getComponent(RenderComponent.class).setSpriteID(0);
		fort.getComponent(RenderComponent.class).setTextureID(2);
		fort.addComponent(CollisionComponent.class, new CollisionComponent(fort));
		fort.getComponent(CollisionComponent.class).setCollisionGroup(4);
		fort.addComponent(HealthComponent.class, new HealthComponent(fort));
		fort.getComponent(HealthComponent.class).setCurHealth(4);
		core.addEntity(fort);
	}
	
	/**
	 * Create a bullet entity at the owner's position.
	 * @param owner	the owner of the bullet.
	 */
	public void createBullet(final IEntity owner)
	{
		IEntity bullet = new Entity();
		
		int xPos = owner.getComponent(TransformComponent.class).getXPos();
		xPos+= owner.getComponent(TransformComponent.class).getWidth()/2;
		xPos-=8;
		int yPos = owner.getComponent(TransformComponent.class).getYPos();
		bullet.setName("BULLET");
		bullet.addComponent(BulletComponent.class, new BulletComponent(bullet));
		bullet.getComponent(BulletComponent.class).setBulletOwner(owner);
		bullet.addComponent(TransformComponent.class, new TransformComponent(bullet));
		bullet.getComponent(TransformComponent.class).setXPos(xPos);
		bullet.getComponent(TransformComponent.class).setYPos(yPos);
		bullet.getComponent(TransformComponent.class).setWidth(16);
		bullet.getComponent(TransformComponent.class).setHeight(32);
		bullet.addComponent(VelocityComponent.class, new VelocityComponent(bullet));
		bullet.getComponent(VelocityComponent.class).setInterval(4);
		bullet.getComponent(VelocityComponent.class).setXVelocity(0);
		bullet.addComponent(RenderComponent.class, new RenderComponent(bullet));
		bullet.getComponent(RenderComponent.class).setSpriteID(0);
		bullet.getComponent(RenderComponent.class).setTextureID(4);
		bullet.addComponent(CollisionComponent.class, new CollisionComponent(bullet));
		int[] a = {0, 1};
		bullet.addComponent(AnimationComponent.class, new AnimationComponent(bullet));

		bullet.getComponent(AnimationComponent.class).addAnimation("IDLE", a, 100);
		bullet.getComponent(AnimationComponent.class).setCurrentAnimation("IDLE");
		if(owner.getName().equals("ALIEN"))
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(3);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(10);
		}
		else
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(2);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(-10);
		}
		core.addEntity(bullet);
	}
}
