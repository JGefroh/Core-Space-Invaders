package com.jgefroh.systems;


import java.util.ArrayList;
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
import com.jgefroh.infopacks.CollisionInfoPack;
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
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new WeaponSystem.
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
			weaponCheck();
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
		//TODO: How little sleep did I get when I wrote this?
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
	 * Set a request to fire the weapon.
	 * @param entity	the requesting entity
	 */
	public void fire(final IEntity entity)
	{
		entity.getComponent(WeaponComponent.class).setFireRequested(true);
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
		//TODO: Remove direct component interaction.
		entity.getComponent(WeaponComponent.class).setReady(true);
	}

}
