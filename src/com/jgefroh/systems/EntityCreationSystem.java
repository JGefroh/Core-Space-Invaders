package com.jgefroh.systems;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.BulletComponent;
import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.DecayComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.MenuComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.ScoreComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;



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
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
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
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		core.setInterested(this, "REQUEST_NEW_WAVE");
		core.setInterested(this, "EXIT");
		core.setInterested(this, "KILL");
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}
	
	@Override
	public void work(final long now)
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
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String ... message)
	{
		if(id.equals("REQUEST_NEW_WAVE"))
		{
			core.send("START_NEW_WAVE", "");
			newGame();
		}
		else if(id.equals("KILL"))
		{
			IEntity entity = core.getEntityWithID(message[0]);
			entity.removeComponent(AIComponent.class);
			entity.removeComponent(WeaponComponent.class);
			entity.removeComponent(CollisionComponent.class);
			entity.removeComponent(VelocityComponent.class);
			entity.removeComponent(ScoreComponent.class);
			DecayComponent dc = new DecayComponent(entity);
				dc.setLastUpdateTime(core.now());
				dc.setTimeUntilDecay(500);
				entity.addComponent(dc);
			entity.getComponent(AnimationComponent.class).setCurrentAnimation("DEAD");
			entity.getComponent(AnimationComponent.class).setCurrentFrame(0);
			entity.getComponent(RenderComponent.class).setSpriteID(
			entity.getComponent(AnimationComponent.class).getAnimationSprite());
			
		}
		else if(id.equals("EXIT"))
		{
			System.exit(-1);
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Create a player entity.
	 * @param x	the X Position to create the entity
	 * @param y	the Y Position to create the entity
	 */
	public IEntity createPlayer(final int x, final int y)
	{	
		//0 = player
		//1 = alien
		//2 = player bullet
		//3 = alien bullet
		//4 = fort
		IEntity player = new Entity();
		player.setName("PLAYER");
		player.addComponent(new TransformComponent(player));
		player.getComponent(TransformComponent.class).setYPos(y);
		player.getComponent(TransformComponent.class).setXPos(x);
		player.getComponent(TransformComponent.class).setWidth(64);
		player.getComponent(TransformComponent.class).setHeight(64);
		player.addComponent(new RenderComponent(player));
		player.getComponent(RenderComponent.class).setSpriteID(0);
		player.getComponent(RenderComponent.class).setTexturePath("res\\player.png");
		player.addComponent(new VelocityComponent(player));
		player.getComponent(VelocityComponent.class).setInterval(25);
		player.getComponent(VelocityComponent.class).setXVelocity(0);
		player.addComponent(new InputComponent(player));
		player.getComponent(InputComponent.class).setInterested("MOVE_RIGHT");
		player.getComponent(InputComponent.class).setInterested("MOVE_LEFT");
		player.getComponent(InputComponent.class).setInterested("STOPX");
		player.getComponent(InputComponent.class).setInterested("SHOOT");
		player.getComponent(InputComponent.class).setInterested("QUIT");
		player.getComponent(InputComponent.class).setInterested("PAUSE");
		player.addComponent(new CollisionComponent(player));
		player.getComponent(CollisionComponent.class).setCollisionGroup(0);		
		player.addComponent(new WeaponComponent(player));
		HealthComponent hc = new HealthComponent(player);
			hc.setCurHealth(100);
			player.addComponent(hc);
		core.send("HEALTH_UPDATE", 100+ "");
		core.addEntity(player);
		return player;
	}
	
	public void makeAIControlled(final IEntity entity)
	{
		AIComponent ai = new AIComponent(entity);
		entity.addComponent(ai);
	}
	
	public void makePositionable(final IEntity entity, 
										final int xPos, final int yPos, 
										final int width, final int height)
	{
		TransformComponent tc = new TransformComponent(entity);
			tc.setHeight(height);
			tc.setWidth(width);
			tc.setXPos(xPos);
			tc.setYPos(yPos);
		entity.addComponent(tc);
	}
	
	public void makeRenderable(final IEntity entity,
									final String texturePath, 
									final int spriteID)
	{
		RenderComponent rc = new RenderComponent(entity);
			rc.setTexturePath(texturePath);
			rc.setSpriteID(spriteID);
		entity.addComponent(rc);
	}
	
	public void makeAnimated(final IEntity entity,
				final String name, int interval, final int ... sprites)
	{
		AnimationComponent ac = new AnimationComponent(entity);
			ac.addAnimation(name, sprites, interval);
			ac.setCurrentAnimation(name);
		entity.addComponent(ac);
	}
	
	public void makeArmed(final IEntity entity)
	{
		WeaponComponent wc = new WeaponComponent(entity);
		entity.addComponent(wc);
	}
	
	public void makeMoving(final IEntity entity, final int interval)
	{
		VelocityComponent vc = new VelocityComponent(entity);
			vc.setInterval(interval);
		entity.addComponent(vc);
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
		alien.addComponent(new TransformComponent(alien));
		alien.getComponent(TransformComponent.class).setXPos(x);
		alien.getComponent(TransformComponent.class).setYPos(y);
		alien.getComponent(TransformComponent.class).setWidth(32);
		alien.getComponent(TransformComponent.class).setHeight(32);
		alien.addComponent(new RenderComponent(alien));
		alien.getComponent(RenderComponent.class).setSpriteID(1);
		alien.addComponent(new VelocityComponent(alien));
		alien.getComponent(VelocityComponent.class).setInterval(200);
		alien.getComponent(VelocityComponent.class).setXVelocity(0);
		alien.addComponent(new CollisionComponent(alien));
		alien.getComponent(CollisionComponent.class).setCollisionGroup(1);
		alien.addComponent(new AIComponent(alien));
		alien.getComponent(RenderComponent.class).setTexturePath("res\\enemy.png");
		alien.addComponent(new WeaponComponent(alien));
		int[] a = {0, 1};
		alien.addComponent(new AnimationComponent(alien));

		alien.getComponent(AnimationComponent.class).addAnimation("IDLE", a, 200);
		int[] b = {2};
		alien.getComponent(AnimationComponent.class).addAnimation("DEAD", b, 200);
		alien.getComponent(AnimationComponent.class).setCurrentAnimation("IDLE");
		ScoreComponent sc = new ScoreComponent(alien);
			sc.setScore(10);
			alien.addComponent(sc);
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
		fort.addComponent(new TransformComponent(fort));
		fort.getComponent(TransformComponent.class).setXPos(x);
		fort.getComponent(TransformComponent.class).setYPos(y);
		fort.getComponent(TransformComponent.class).setWidth(128);
		fort.getComponent(TransformComponent.class).setHeight(64);
		fort.addComponent(new RenderComponent(fort));
		fort.getComponent(RenderComponent.class).setSpriteID(0);
		fort.getComponent(RenderComponent.class).setTexturePath("res\\fort.png");
		fort.addComponent(new CollisionComponent(fort));
		fort.getComponent(CollisionComponent.class).setCollisionGroup(4);
		fort.addComponent(new HealthComponent(fort));
		fort.getComponent(HealthComponent.class).setCurHealth(4);
		AnimationComponent ac = new AnimationComponent(fort);
			int[] a = {1, 2, 3};
			ac.addAnimation("IDLE", a, Long.MAX_VALUE);
			ac.setCurrentAnimation("IDLE");
			fort.addComponent(ac);
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
		int yPos = owner.getComponent(TransformComponent.class).getYPos();
		bullet.setName("BULLET");
		bullet.addComponent(new BulletComponent(bullet));
		bullet.getComponent(BulletComponent.class).setBulletOwner(owner);
		bullet.addComponent(new TransformComponent(bullet));
		bullet.getComponent(TransformComponent.class).setXPos(xPos);
		bullet.getComponent(TransformComponent.class).setYPos(yPos);
		bullet.getComponent(TransformComponent.class).setWidth(16);
		bullet.getComponent(TransformComponent.class).setHeight(32);
		bullet.addComponent(new VelocityComponent(bullet));
		bullet.getComponent(VelocityComponent.class).setInterval(4);
		bullet.getComponent(VelocityComponent.class).setXVelocity(0);
		bullet.addComponent(new RenderComponent(bullet));
		bullet.getComponent(RenderComponent.class).setSpriteID(0);
		bullet.getComponent(RenderComponent.class).setTexturePath("res\\bullet.png");
		bullet.addComponent(new CollisionComponent(bullet));

		bullet.addComponent(new AnimationComponent(bullet));
		if(owner.getName().equals("ALIEN"))
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(3);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(15);
			bullet.getComponent(RenderComponent.class).setSpriteID(0);
			int[] a = {0, 1};
			bullet.getComponent(AnimationComponent.class).addAnimation("IDLE", a, 100);
		}
		else
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(2);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(-30);
			bullet.getComponent(RenderComponent.class).setSpriteID(3);
			int[] a = {2, 3};
			bullet.getComponent(AnimationComponent.class).addAnimation("IDLE", a, 100);
		}
		bullet.getComponent(AnimationComponent.class).setCurrentAnimation("IDLE");
		core.addEntity(bullet);
	}
	
	public void createAlienBody(final int x, final int y)
	{
		IEntity alien = new Entity();
		alien.setName("ALIEN_BODY");
		alien.addComponent(new TransformComponent(alien));
		alien.getComponent(TransformComponent.class).setXPos(x);
		alien.getComponent(TransformComponent.class).setYPos(y);
		alien.getComponent(TransformComponent.class).setWidth(32);
		alien.getComponent(TransformComponent.class).setHeight(32);
		alien.addComponent(new RenderComponent(alien));
		alien.getComponent(RenderComponent.class).setSpriteID(2);
		alien.getComponent(RenderComponent.class).setTexturePath("res\\enemy.png");
		alien.addComponent(new DecayComponent(alien));
		alien.getComponent(DecayComponent.class).setTimeUntilDecay(500);
		alien.getComponent(DecayComponent.class).setLastUpdateTime(core.now());
		core.addEntity(alien);
	}
	
	public void createMenuOption(final int xPos, final int yPos, final int width, final int height, final int spriteID, final String command)
	{
		IEntity menuOption = new Entity();
		menuOption.setName("MENU");
		TransformComponent tc = new TransformComponent(menuOption);
			tc.setWidth(width);
			tc.setHeight(height);
			tc.setXPos(xPos);
			tc.setYPos(yPos);
		RenderComponent rc = new RenderComponent(menuOption);
			rc.setTexturePath("res\\gui.png");
			rc.setSpriteID(spriteID);
		MenuComponent mc = new MenuComponent(menuOption);
			mc.setCommand(command);
		menuOption.addComponent(tc);
		menuOption.addComponent(rc);
		menuOption.addComponent(mc);
		core.add(menuOption);

	}
	
	public void createInputReceiver(String ... commands)
	{
		IEntity rec = new Entity();
		InputComponent ic = new InputComponent(rec);
		for(String each:commands)
		{
			ic.setInterested(each);
		}
		rec.addComponent(ic);
		core.add(rec);
	}

	public void newGame()
	{
		core.removeAllEntities();
		core.getSystem(GameOverCheckSystem.class).start();
		createPlayer(1680/2, 1000);
	
		createAlien(1680/20*1, 1050/15*3);
		createAlien(1680/20*2, 1050/15*3);
		createAlien(1680/20*3, 1050/15*3);
		createAlien(1680/20*4, 1050/15*3);
		createAlien(1680/20*5, 1050/15*3);
		createAlien(1680/20*6, 1050/15*3);
		createAlien(1680/20*7, 1050/15*3);
		createAlien(1680/20*8, 1050/15*3);
		createAlien(1680/20*9, 1050/15*3);
		createAlien(1680/20*10, 1050/15*3);

		
		createAlien(1680/20*1, 1050/15*4);
		createAlien(1680/20*2, 1050/15*4);
		createAlien(1680/20*3, 1050/15*4);
		createAlien(1680/20*4, 1050/15*4);
		createAlien(1680/20*5, 1050/15*4);
		createAlien(1680/20*6, 1050/15*4);
		createAlien(1680/20*7, 1050/15*4);
		createAlien(1680/20*8, 1050/15*4);
		createAlien(1680/20*9, 1050/15*4);
		createAlien(1680/20*10, 1050/15*4);

		createAlien(1680/20*1, 1050/15*5);
		createAlien(1680/20*2, 1050/15*5);
		createAlien(1680/20*3, 1050/15*5);
		createAlien(1680/20*4, 1050/15*5);
		createAlien(1680/20*5, 1050/15*5);
		createAlien(1680/20*6, 1050/15*5);
		createAlien(1680/20*7, 1050/15*5);
		createAlien(1680/20*8, 1050/15*5);
		createAlien(1680/20*9, 1050/15*5);
		createAlien(1680/20*10, 1050/15*5);
		
		createFort(1680/5, 850);
		createFort(1680/5*2, 850);
		createFort(1680/5*3, 850);
		createFort(1680/5*4, 850);
		
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

	public ArrayList<IEntity> createDrawableText(final String text, final int x, final int y, final int size, final int spacing)
	{
		char[] chars = text.toCharArray();
		ArrayList<IEntity> entities = new ArrayList<IEntity>();
		int xOffset = 0;
		for(int index=0;index<text.length();index++)
		{
			entities.add(createLetter(x+xOffset, y, text.charAt(index), size, spacing));
			xOffset=xOffset+spacing+size;
		}
		return entities;
	}
	public IEntity createLetter(final int x, final int y, final int index, final int size, final int spacing)
	{
		IEntity letter = new Entity();
			letter.setName("text");
			letter.addComponent(new TransformComponent(letter));
			letter.getComponent(TransformComponent.class).setXPos(x);
			letter.getComponent(TransformComponent.class).setYPos(y);
			letter.getComponent(TransformComponent.class).setWidth(size);
			letter.getComponent(TransformComponent.class).setHeight(size);
			letter.addComponent(new RenderComponent(letter));
			letter.getComponent(RenderComponent.class).setSpriteID(index);
			letter.getComponent(RenderComponent.class).setTexturePath("res\\alphabet.png");
		core.addEntity(letter);
		return letter;
	}

}
