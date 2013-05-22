package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MovementInfoPack;


/**
 * System that handles movement and repositioning of entities.
 * @author Joseph Gefroh
 */
public class TransformSystem implements ISystem
{
	
	//TODO: Stupid system, make consistent.
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

	/**A reference to the timer*/
	private TimerSystem timer;	//TODO: Remove reference, pass time per loop?
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new TransformSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public TransformSystem(final Core core)
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
		isRunning = true;
	}

	@Override
	public void start()
	{
		this.timer = core.getSystem(TimerSystem.class);
		if(this.timer!=null)
		{	
			LOGGER.log(Level.INFO, "System started.");
			this.isRunning = true;
		}
		else
		{
			LOGGER.log(Level.WARNING, "Failed to start (no timer)");
			this.isRunning = false;
		}
	}

	@Override
	public void work()
	{
		if(this.isRunning&&this.timer!=null)
		{
			move();			
		}
		else
		{
			stop();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		this.isRunning = false;
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Moves all entities that are requesting moves based on their velocities.
	 */
	public void move()
	{
		Iterator<MovementInfoPack> packs 
			= core.getInfoPacksOfType(MovementInfoPack.class);

		while(packs.hasNext())
		{
			MovementInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(timer.isUpdateTime(each.getInterval(), each.getLastUpdated()))
				{
					each.setLastUpdated(timer.getNow());
					int newX = each.getXPos()+each.getXVelocity();
					int newY = each.getYPos()+each.getYVelocity();
					each.setXPos(newX);
					each.setYPos(newY);
				}
			}
		}
	}
	
	/**
	 * Move an entity the desired number of units.
	 * @param entity	the entity to molve
	 * @param xPos		the number of X units to move
	 * @param yPos		the number of Y units to move
	 */
	public void shiftPosition(final IEntity entity, final int xPos, final int yPos)
	{
		MovementInfoPack mip 
			= core.getInfoPackFrom(entity, MovementInfoPack.class);
		mip.setXPos(mip.getXPos()+xPos);
		mip.setYPos(mip.getYPos()+yPos);
	}

	/**
	 * Adjust the X movement speed of an entity by the given amount.
	 * @param entity	the entity
	 * @param xVel		the x Velocity
	 */
	public void adjustXVelocity(final IEntity entity, final int xVel)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setXVelocity(pack.getXVelocity()+xVel);
	}
	
	/**
	 * Adjust the Y movement speed of an entity by the given amount.
	 * @param entity	the entity
	 * @param yVel		the y Velocity
	 */
	public void adjustYVelocity(final IEntity entity, final int yVel)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setYVelocity(pack.getYVelocity()+yVel);
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the X movement speed of an entity.
	 * @param entity	the entity 
	 * @param xVel		the X velocity
	 */
	public void setXVelocity(final IEntity entity, final int xVel)
	{
		MovementInfoPack pack = 
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setXVelocity(xVel);
	}
	
	/**
	 * Set the Y movement speed of an entity.
	 * @param entity	the entity 
	 * @param xVel		the Y velocity
	 */
	public void setYVelocity(final IEntity entity, final int yVel)
	{
		MovementInfoPack pack = 
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setYVelocity(yVel);
	}
	

	public void setInterval(final IEntity entity, final long interval)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setInterval(interval);
	}
}
