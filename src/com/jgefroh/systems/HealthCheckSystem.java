package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;

/**
 * This system goes through all entities with health and marks ones with no
 * health as dead.
 * @author Joseph Gefroh
 */
public class HealthCheckSystem implements ISystem
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
	 * Create a new HealthCheckSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public HealthCheckSystem(final Core core)
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
			checkHealth();
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
	 * Check the health of all entities and "deadify" those below 0 health.
	 */
	private void checkHealth()
	{
		ArrayList<HealthInfoPack> packs 
		= core.getInfoPacksOfType(HealthInfoPack.class);
		for(HealthInfoPack each:packs)
		{
			if(each.isDirty()==false)
			{
				if(each.getCurHealth()<=0)
				{
					core.removeEntity(each.getOwner());
					core.getSystem(EventSystem.class)
						.notify("DEAD", each.getOwner(), each.getOwner());
				}
			}
		}
	}
}
