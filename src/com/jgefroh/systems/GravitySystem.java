package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.GravityInfoPack;

/**
 * This system accelerates objects downward up (harhar) to a maximum velocity.
 * @author Joseph Gefroh
 */
public class GravitySystem implements ISystem
{
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
	 * Create a new GravitySystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GravitySystem(final Core core)
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
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}

	@Override
	public void work()
	{
		if(isRunning)
		{
			gravitate();
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
	 * Go through everything affected by gravity and accelerate them.
	 */
	private void gravitate()
	{
		//TODO: Ugly.
		Iterator<GravityInfoPack> packs 
				= core.getInfoPacksOfType(GravityInfoPack.class);
		while(packs.hasNext())
		{
			GravityInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(core.getSystem(TimerSystem.class).getNow()-each.getLastUpdate()>=each.getUpdateInterval())
				{//If it is time to make them fall again...
					accelerate(each);
					each.setLastUpdate(core.getSystem(TimerSystem.class).getNow());

				}
			}
		}
	}
	
	/**
	 * Accelerate an entity if it has not yet reached maximum acceleration.
	 * @param each	the GravityInfoPack belonging to the entity
	 */
	private void accelerate(final GravityInfoPack each)
	{
		if(each.getGravity()+each.getYVelocity()<each.getMaxGravity())
		{//If max gravity not yet reached...
			each.setYVelocity(each.getYVelocity()+each.getGravity());
		}
	}
}
