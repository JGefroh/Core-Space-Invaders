package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Wait;


/**
 * Allows {@code Systems} to receive a callback after a certain amount of time.
 * 
 * Date: 31MAY13
 * @author Joseph Gefroh
 */
public class WaitSystem implements ISystem
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
	
	private ArrayList<Wait> waits = new ArrayList<Wait>();

	//////////
	// INIT
	//////////
	/**
	 * Create a new TransformSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public WaitSystem(final Core core)
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
		this.waits = new ArrayList<Wait>();
		core.setInterested(this, "TIMER_REQUEST");
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

	/**
	 * Sends a reply message for every Wait request that has expired.
	 * @param now	the current time
	 */
	@Override
	public void work(final long now)
	{
		updateTimes(now);
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
	
	/**
	 * Acts on the following messages:
	 * 
	 * 
	 * <p>TIMER_REQUEST[String]
	 * - Creates a timer and sends a callback with the accompanying message
	 * as its ID.</p>
	 */
	@Override
	public void recv(final String id, final String... message)
	{
		if(id.equals("TIMER_REQUEST"))
		{
			if(message.length>=2)
			{
				String responseID = message[0];
				try
				{					
					long timeToWait = Long.parseLong(message[1]);
					createTimer(responseID, timeToWait);
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Error creating timer: "
								+ responseID);
					e.printStackTrace();
				}
			}
			else
			{
				LOGGER.log(Level.WARNING, "Error creating timer: expected more arguments.");
			}
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Sends a reply message for every Wait request that has expired.
	 * @param now	the current time
	 */
	private void updateTimes(final long now)
	{
		Iterator<Wait> iter = waits.iterator();
		while(iter.hasNext())
		{
			Wait current = iter.next();
			if(now-current.getStartTime()>=current.getTimeToWait())
			{
				core.send(current.getResponse(), now + "");
				iter.remove();
			}
		}
	}
	
	/**
	 * Creates and adds a new timer to this System.
	 * @param responseID	the ID to respond with
	 * @param timeToWait	the amount of time to wait before responding
	 */
	private void createTimer(final String responseID, final long timeToWait)
	{
		Wait wait = new Wait(responseID, core.now(), timeToWait);
		waits.add(wait);
	}
}
