package com.jgefroh.systems;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;


/**
 * This system is in charge of dealing damage. It is not designed to be
 * run as part of the game loop (although for consistency it probably should).
 * @author Joseph Gefroh
 */
public class DamageSystem implements ISystem
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
	 * Create a new DamageSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public DamageSystem(final Core core)
	{
		this.core = core;
		init();
	}

	/////////
	// ISYSTEM INTERFACE
	/////////
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
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	
	/////////
	// SYSTEM METHODS
	/////////
	/**
	 * Deal damage to an entity.
	 * @param amount	the amount of damage to deal
	 * @param source	the source of the damage
	 * @param target	the receiver of the damage
	 */
	public void damage(final int amount, final IEntity source, final IEntity target)
	{
		//TODO: Remove amount, deal based on weapon damage? Too coupled?
		HealthInfoPack hip = core.getInfoPackFrom(target, HealthInfoPack.class);
		if(hip!=null)
		{
			hip.setCurHealth(hip.getCurHealth()-amount);
		}
	}
}
