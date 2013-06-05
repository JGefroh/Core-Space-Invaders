package com.jgefroh.systems;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.ScoreInfoPack;


/**
 * Tracks basic gameplay statistics such as time taken and shots fired.
 * 
 * Date: 31MAY13
 * @author Joseph Gefroh
 */
public class StatSystem implements ISystem
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
	
	/**The number of shots that the player has fired.*/
	private int numShotsFired;
	
	/**The time the counting was started.*/
	private long before;
	
	/**The total time the round has taken.*/
	private long totalTimeTaken;
	
	/**The time that has passed since last calculation.*/
	private long timeDifference;
	
	private int currentScore = 0;

	//////////
	// INIT
	//////////
	/**
	 * Create a new TransformSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public StatSystem(final Core core)
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
		this.totalTimeTaken = 0;
		this.numShotsFired = 0;
		this.before = core.now();
		this.currentScore = 0;
		this.timeDifference = 0;
		core.setInterested(this, "IN_MENU");
		core.setInterested(this, "SHOT_FIRED");
		core.setInterested(this, "STATE_UNPAUSED");
		core.setInterested(this, "REQUEST_TIME_UPDATE");
		core.setInterested(this, "REQUEST_SHOT_UPDATE");
		core.setInterested(this, "REQUEST_SCORE_UPDATE");
		core.setInterested(this, "START_NEW_GAME");
		core.setInterested(this, "START_NEW_WAVE");
		core.setInterested(this, "ADD_SCORE");
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
		updateTime(now);
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
	 * Acts on messages sent by Core.
	 * 
	 * 
	 * The following messages are processed by this System.
	 * <p>ADD_SCORE [entityID]
	 * 		- Increments score based on the ScoreInfoPack of owner</p>
	 * <p>IN_MENU
	 * 		- Resets the stats to default values and stops the system</p>
	 * <p>SHOT_FIRED [entityID]
	 * 		-Increments the shots fired counter if the entity named PLAYER</p>
	 * <p>START_NEW_GAME
	 * 		- Resets the stats, starts the System, sets the current time</p>
	 * <p>START_NEW_WAVE
	 * 		- Resets the stats except for current score, and restarts.</p>
	 * <p>STATE_UNPAUSED
	 * 		- Begins counting the time again</p>
	 * <p>REQUEST_SCORE_UPDATE[]
	 * 		- Sends a SCORE_UPDATE[int] message with the current score</p>
	 * <p>REQUEST_SHOT_UPDATE
	 * 		- Sends a SHOT_UPDATE[int] message with the number of shots</p>
	 * <p>REQUEST_TIME_UPDATE
	 * 			- Sends a TIME_UPDATE[long] message with the current time, in s</p>
	 */
	@Override
	public void recv(final String id, final String... message)
	{
		if(id.equals("SHOT_FIRED"))
		{
			if(message.length>0)
			{
				IEntity entity = core.getEntityWithID(message[0]);
				if(entity!=null&&entity.getName().equals("PLAYER"))
				{
					incNumShotsFired();
				}
			}
		}
		else if(id.equals("ADD_SCORE"))
		{
			if(message.length>0)
			{				
				addScore(message[0]);
			}
			else
			{
				LOGGER.log(Level.WARNING, 
							id + " error: expected values but received none. "
							+"Skipping...");
			}
		}
		else if(id.equals("START_NEW_GAME"))
		{
			reset();
			start();
		}
		else if(id.equals("START_NEW_WAVE"))
		{
			int temp = this.currentScore;	//Save the current score
			reset();
			start();
			this.currentScore = temp;		//Restore the current score
			core.send("SCORE_UPDATE", this.currentScore + "");
		}
		else if(id.equals("STATE_UNPAUSED"))
		{
			if(message.length>0)
			{	
				try
				{					
					this.before = Long.parseLong(message[0]);
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Error handling " + id 
								+ " Timer may become inaccurate...");
				}
			}
			else
			{
				LOGGER.log(Level.WARNING, 
						id + " error: expected values but received none. "
						+"Timer may become inaccurate...");
			}
		}
		else if(id.equals("REQUEST_TIME_UPDATE"))
		{
			core.send("TIME_UPDATE", this.totalTimeTaken/100 + "");
		}	
		else if(id.equals("REQUEST_SHOT_UPDATE"))
		{
			core.send("SHOT_UPDATE", this.numShotsFired + "");
		}
		else if(id.equals("REQUEST_SCORE_UPDATE"))
		{
			core.send("SCORE_UPDATE", this.currentScore + "");
		}
		else if(id.equals("IN_MENU"))
		{
			stop();
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Update the timer.
	 * @param now	the current time
	 */
	private void updateTime(final long now)
	{
		//Calculate the time difference between now and before
		this.timeDifference = now-this.before;
		//Add the difference to the time taken.
		this.totalTimeTaken+=this.timeDifference;
		//Set before to now
		this.before = now;
	}
	
	/**
	 * Adds the value of the entity with the passed ID to the current score.
	 * @param id	the unique ID assigned to the Entity
	 */
	private void addScore(final String id)
	{
		ScoreInfoPack sip = core.getInfoPackFrom(id, ScoreInfoPack.class);
		if(sip!=null)
		{			
			this.currentScore+=sip.getScore();
		}
	}
	
	/**
	 * Increments the counter for the number of shots fired.
	 */
	private void incNumShotsFired()
	{
		this.numShotsFired++;
	}

	/**
	 * Resets all stats for a new round.
	 */
	private void reset()
	{
		this.numShotsFired = 0;
		this.before = core.now();
		this.totalTimeTaken = 0;
		this.currentScore = 0;
		this.timeDifference = 0;
	}
}
