package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;

/**
 * This system goes through all entities with health and marks ones with no
 * health as dead.
 * @author Joseph Gefroh
 */
public class WinCheckSystem implements ISystem
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
	 * Create a new WinCheckSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public WinCheckSystem(final Core core)
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
			aWinnerIsYou();
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
	 * Check to see if the player has killed all the aliens yet.
	 */
	private void aWinnerIsYou()
	{
		//TODO: make this restart the game and increase difficulty.
		Iterator<AIInfoPack> packs 
		= core.getInfoPacksOfType(AIInfoPack.class);
		if(packs.hasNext()==false)
		{
			System.out.println("GAME OVER - You Win!");
			System.exit(0);
		}
	}
}
