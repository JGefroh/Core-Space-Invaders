package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;



/**
 * Displays statistics from various sources on the screen during gameplay.
 * @author Joseph Gefroh
 */
public class GUISystem implements ISystem
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
		
	private ArrayList<IEntity> text;

	/**The score to display.*/
	private int score;
	
	/**The health to display.*/
	private int health;
	
	/**The time to display.*/
	private long time;
	
	/**The number of shots to display.*/
	private int shots;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GUISystem(final Core core)
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
		this.isRunning = true;
		text = new ArrayList<IEntity>();	
		core.setInterested(this, "EVENT_WIN");
		core.setInterested(this, "EVENT_LOSE");
		core.setInterested(this, "SCORE_UPDATE");
		core.setInterested(this, "HEALTH_UPDATE");
		core.setInterested(this, "SHOT_UPDATE");
		core.setInterested(this, "STATE_PAUSED");
		core.setInterested(this, "TIME_UPDATE");
		core.setInterested(this, "IN_MENU");
		core.setInterested(this, "START_NEW_GAME");
		core.setInterested(this, "START_NEW_WAVE");
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
			clear();
			updateScore();
			updateLives();
			updateTime();
			showPaused();
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
		if(message.length>0)
		{
			String payload = message[0];
			if(id.equals("EVENT_WIN"))
			{
				work(core.now());
				showVictory();
			}
			else if(id.equals("EVENT_LOSE"))
			{
				work(core.now());
				showDefeat();
			}
			else if(id.equals("SCORE_UPDATE"))
			{
				this.score = Integer.parseInt(payload); 
			}
			else if(id.equals("HEALTH_UPDATE"))
			{
				this.health = Integer.parseInt(payload);
			}
			else if(id.equals("SHOT_UPDATE"))
			{
				this.shots = Integer.parseInt(payload);
			}
			else if(id.equals("TIME_UPDATE"))
			{
				this.time = Long.parseLong(payload);
			}
			else if(id.equals("IN_MENU"))
			{
				//The GUI system should stop.
				resetValues();
				stop();
			}
			else if(id.equals("STATE_PAUSED"))
			{
				showPaused();
			}
			else if(id.equals("START_NEW_GAME"))
			{
				resetValues();
				start();
			}
			else if(id.equals("START_NEW_WAVE"))
			{
				resetValues();
				start();
			}
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	public void updateScore()
	{
		core.send("REQUEST_SCORE_UPDATE", "");
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		text.addAll(ecs.createDrawableText("SCORE " + this.score, 100, 100, 16, 3));
	}
	
	public void updateLives()
	{
		core.send("REQUEST_HEALTH_UPDATE", "");
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		text.addAll(ecs.createDrawableText("HEALTH " + this.health +"%", 100, 120, 16, 3));
	}
	
	public void updateTime()
	{
		core.send("REQUEST_TIME_UPDATE", "");
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		text.addAll(ecs.createDrawableText("TIME " + time/10, 100, 140, 16, 3));
	}
	public void showPaused()
	{
		if(core.isPaused())
		{
			EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);			
			text.addAll(ecs.createDrawableText("PAUSED", 100, 50, 32, 3));
		}
	}
	
	public void showVictory()
	{
		isRunning = false;
		core.send("REQUEST_TIME_UPDATE", "");
		core.send("REQUEST_SHOT_UPDATE", "");
		core.getSystem(EntityCreationSystem.class)
		.createDrawableText("STAGE CLEARED", 300, 1050/2, 64, -5);
		core.getSystem(EntityCreationSystem.class)
		.createDrawableText("TIME TAKEN: " + time/10 + " SECONDS", 300, 1050/2+64, 32, -5);
		core.getSystem(EntityCreationSystem.class)
		.createDrawableText("SHOTS FIRED: " + shots, 300, 1050/2+128, 32, -5);
	}
	
	public void showDefeat()
	{
		isRunning = false;
		core.getSystem(EntityCreationSystem.class)
		.createDrawableText("YOU LOSE! GAME OVER!", 300, 1050/2, 64, -5);
	}
	
	public void resetValues()
	{
		this.score = 0;
		this.health = 100;	//TODO: THIS IS DIRTY
		this.time = 0;
	}

	public void clear()
	{
		for(IEntity each:text)
		{
			core.removeEntity(each);
		}
		text = new ArrayList<IEntity>();
	}
	

}
