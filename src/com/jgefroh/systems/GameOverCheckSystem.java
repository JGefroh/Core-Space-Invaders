package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.InputInfoPack;

/**
 * This system goes through all entities with health and marks ones with no
 * health as dead.
 * @author Joseph Gefroh
 */
public class GameOverCheckSystem implements ISystem
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
	
	private boolean expectingResponse = false;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GameOverCheckSystem(final Core core)
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
		core.setInterested(this, "START_NEW_GAME");
		core.setInterested(this, "START_NEW_WAVE");
		core.setInterested(this, "GAMEOVER_WAKE_UP_CALL");
		core.setInterested(this, "LOSE_WAKE_UP_CALL");
		core.setInterested(this, "IN_MENU");
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
			aWinnerIsYou();
			checkLose();
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
		if(id.equals("START_NEW_GAME") || id.equals("START_NEW_WAVE"))
		{
			start();
			this.expectingResponse = false;
		}
		else if(id.equals("IN_MENU"))
		{
			this.expectingResponse = false;
		}
		else if(id.equals("GAMEOVER_WAKE_UP_CALL"))
		{
			if(this.expectingResponse)
			{				
				core.send("REQUEST_NEW_WAVE", "");
				this.expectingResponse = false;
			}
		}
		else if(id.equals("LOSE_WAKE_UP_CALL"))
		{
			core.send("REQUEST_SHOW_MENU");
		}
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
			core.send("EVENT_WIN", "WIN");
			stop();
			core.send("TIMER_REQUEST", "GAMEOVER_WAKE_UP_CALL", 3000 + "");
			this.expectingResponse = true;
		}
	}
	
	private void checkLose()
	{
		Iterator<InputInfoPack> packs 
		= core.getInfoPacksOfType(InputInfoPack.class);
		if(packs.hasNext()==false)
		{
			core.send("EVENT_LOSE", "LOSE");
			stop();
			core.send("TIMER_REQUEST",  "LOSE_WAKE_UP_CALL", 3000 + "");
		}
	}
}
