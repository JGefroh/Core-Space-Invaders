package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.BodyInfoPack;


/**
 * Handles corpses.
 * @author Joseph Gefroh
 */
public class BodySystem implements ISystem
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
	
	private TimerSystem timer;
	//////////
	// INIT
	//////////
	/**
	 * Create a new BodySystem
	 * @param core	a reference to the Core controlling this system
	 */
	public BodySystem(final Core core)
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
		timer = core.getSystem(TimerSystem.class);
		if(timer!=null)
		{
			isRunning = true;			
		}
		else
		{
			isRunning = false;
			LOGGER.log(Level.SEVERE, "Unable to start system - missing timer.");
		}
	}

	@Override
	public void work()
	{		
		if(timer!=null&&isRunning)
		{			
			decay();
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
	 * Go through all of the bodies and remove the ones that have decayed.
	 * @param entities the ArrayList of entities
	 */
	private void decay()
	{
		ArrayList<BodyInfoPack> infoPacks = 
				core.getInfoPacksOfType(BodyInfoPack.class);
		for(BodyInfoPack pack:infoPacks)
		{
			if(timer.isUpdateTime(pack.getTimeUntilDecay(), pack.getLastUpdateTime()))
			{	
				core.removeEntity(pack.getOwner());
			}
		}
	}

}
