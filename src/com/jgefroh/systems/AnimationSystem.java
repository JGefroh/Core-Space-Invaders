package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AnimationInfoPack;


/**
 * This system handles changing the sprite image at the required intervals.
 * The AnimationSystem keeps track of frames and animation sequences for 
 * entities. It provides methods to start and stop animations. 
 * @author Joseph Gefroh
 */
public class AnimationSystem implements ISystem
{
	//TODO: Needs update.
	
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
	 * Create a new AnimationSystem
	 * @param core	a reference to the Core controlling this system
	 */
	public AnimationSystem(final Core core)
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
			animate();
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
	 * Go through all of the entities with AnimationInfoPacks and update
	 * their frames.
	 * @param entities the ArrayList of entities
	 */
	private void animate()
	{
		Iterator<AnimationInfoPack> infoPacks = 
				core.getInfoPacksOfType(AnimationInfoPack.class);
		while(infoPacks.hasNext())
		{
			AnimationInfoPack pack = infoPacks.next();
			if(pack.isDirty()==false)
			{
				if(timer.isUpdateTime(pack.getInterval(), pack.getLastUpdateTime()))
				{	
					nextFrame(pack);
					pack.setLastUpdateTime(timer.getNow());
				}
			}
		}
	}
	
	/**
	 * Advance the frame of the animation.
	 * @param pack	the AnimationInfoPack of the entity
	 */
	public void nextFrame(final AnimationInfoPack pack)
	{
		int currentFrame = pack.getCurrentFrame();
		int numberOfFrames = pack.getNumberOfFrames();
		if(currentFrame<=numberOfFrames-1)
		{
			pack.setAnimationSprite(pack.getAnimationSprite());
			pack.setCurrentFrame(currentFrame+1);
		}
		else
		{
			pack.setCurrentFrame(0);
		}
	}
}
