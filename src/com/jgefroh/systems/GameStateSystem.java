package com.jgefroh.systems;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;


/**
 * Controls game state transitions and notifies other systems of state changes.
 * @author Joseph Gefroh
 */
public class GameStateSystem implements ISystem
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
	
	/**Flag that indicates the Game is currently PAUSED.*/
	private boolean isPaused;
	
	/**Flag that indicates the Game is currently in a menu.*/
	private boolean isInMenu;
	
	private enum State{PAUSED, UNPAUSED}
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this System.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GameStateSystem(final Core core)
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
		core.setInterested(this, "REQUEST_STATE_PAUSED");
		core.setInterested(this, "REQUEST_STATE_UNPAUSED");
		core.setInterested(this, "REQUEST_NEW_GAME");
	}

	@Override
	public void start()
	{
		if(isRunning)
		{	
			LOGGER.log(Level.INFO, "System started.");
			this.isRunning = true;
		}
	}

	@Override
	public void work(final long now)
	{
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		this.isRunning = false;
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
		System.out.println("WAT");
		if(id.equals("REQUEST_STATE_PAUSED"))
		{
			if(canPause())
			{
				changeState(State.PAUSED);
				this.isPaused = true;
			}
		}
		else if(id.equals("REQUEST_STATE_UNPAUSED"))
		{
			if(canUnpause())
			{
				changeState(State.UNPAUSED);
				this.isPaused = false;
			}
		}
		else if(id.equals("REQUEST_STATE_START"))
		{
			changeState(State.UNPAUSED);
			this.isPaused = false;
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	private void changeState(final State state)
	{
		if(state==State.PAUSED)
		{
			core.setPaused(true);
			core.send("STATE_PAUSED", core.now() + "");
		}
		else if(state==State.UNPAUSED)
		{
			core.setPaused(false);
			core.send("STATE_UNPAUSED", core.now() + "");
		}
	}
	
	private boolean canPause()
	{
		if(this.isInMenu==false&&this.isPaused==false)
		{
			return true;
		}
		return false;
	}
	
	private boolean canUnpause()
	{
		if(this.isPaused==true)
		{
			return true;
		}
		return false;
	}

}
