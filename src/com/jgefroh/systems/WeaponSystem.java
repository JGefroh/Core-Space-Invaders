package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.BulletComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.BulletInfoPack;
import com.jgefroh.infopacks.WeaponInfoPack;



/**
 * This system keeps track of weapons and fire requests, along with
 * bullets.
 * @author Joseph Gefroh
 */
public class WeaponSystem implements ISystem
{
	//TODO: This system does too much.
	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The time to wait between executions of this system.*/
	private long waitTime;
	
	/**The time this System was last run, in ms.*/
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
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public WeaponSystem(final Core core)
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
		this.isRunning = true;
		core.setInterested(this, "BULLET_HIT");
		core.setInterested(this, "REQUEST_FIRE");
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
			weaponCheck();
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
	public void recv(final String id, final String... message)
	{
		if(id.equals("BULLET_HIT"))
		{
			String bulletID = message[0];
			setReady(core.getInfoPackFrom(bulletID, BulletInfoPack.class));
			core.removeEntity(bulletID);
		}
		else if(id.equals("REQUEST_FIRE"))
		{
			if(message.length>0)
			{
				fire(message[0]);	//message[0] expected to be entityID
			}
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Go through all of the weapons and bullets.
	 */
	public void weaponCheck()
	{
		//TODO: Make independent (TOO COUPLED).
		Iterator<BulletInfoPack> packs 
			= core.getInfoPacksOfType(BulletInfoPack.class);

		while(packs.hasNext())
		{
			BulletInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(each.getYPos()>=1050||each.getYPos()<=-16)
				{
					destroyBullet(each);
					setReady(each.getBulletOwner());
				}
			}
		}
		
		Iterator<WeaponInfoPack> weaponPacks 
			= core.getInfoPacksOfType(WeaponInfoPack.class);
		while(weaponPacks.hasNext())
		{
			WeaponInfoPack each = weaponPacks.next();
			if(each.isDirty()==false)
			{
				if(each.isFireRequested()&&each.isReady())
				{
					createBullet(each);
					each.setFireRequested(false);
					each.setReady(false);
					core.send("SHOT_FIRED", each.getOwner().getID());
				}
				else
				{
					each.setFireRequested(false);
				}
			}
		}
	}
	
	/**
	 * Create a new bullet.
	 * @param each	the pack with the owner of the bullet
	 */
	public void createBullet(final WeaponInfoPack each)
	{
		//TODO: That's dumb.
		core.getSystem(EntityCreationSystem.class).createBullet(each.getOwner());
	}
	
	/**
	 * Destroy a bullet.
	 * @param each	the pack with the owner of the bullet.
	 */
	private void destroyBullet(final BulletInfoPack each)
	{
		//TODO: That's also dumb.
		core.removeEntity(each.getOwner());
	}
	
	/**
	 * Mark a bullet as having hit a target.
	 * @param entity		the source entity
	 * @param entityTwo		the target entity
	 */
	public void hit(final IEntity entity, final IEntity entityTwo)
	{
		if(entity.getName().equals("BULLET"))
		{
			setReady(entity.getComponent(BulletComponent.class).getBulletOwner());
		}
		else if(entityTwo.getName().equals("BULLET"))
		{
			setReady(entityTwo.getComponent(BulletComponent.class).getBulletOwner());		
		}
	}

	
	/**
	 * Sets a request to fire the weapon.
	 * @param entityID	the ID of the requesting entity
	 */
	public void fire(final String entityID)
	{
		WeaponInfoPack pack = 
			core.getInfoPackFrom(entityID, WeaponInfoPack.class);
			
		if(pack!=null)
		{				
			pack.setFireRequested(true);
		}
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the weapon as ready to be fired.
	 * @param entity	the entity that is ready to fore
	 */
	public void setReady(final IEntity entity)
	{
		WeaponComponent wc = entity.getComponent(WeaponComponent.class);
		if(wc!=null)
		{
			wc.setReady(true);
		}
	}
	
	/**
	 * Set the weapon as ready to be fired.
	 * @param entity	the entity that is ready to fore
	 */
	public void setReady(final BulletInfoPack pack)
	{
		if(pack!=null&&pack.getBulletOwner()!=null)
		{			
			WeaponInfoPack wip = core.getInfoPackFrom(pack.getBulletOwner(), WeaponInfoPack.class);
			if(wip!=null)
			{
				wip.setReady(true);
			}
		}
	}
}
