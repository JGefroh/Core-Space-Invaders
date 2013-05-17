package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
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
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(AnimationSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	
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
	
	/**
	 * Initialize the Logger with default settings.
	 */
	private void initLogger()
	{
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(debugLevel);
		LOGGER.addHandler(ch);
		LOGGER.setLevel(debugLevel);
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		initLogger();
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
			updateFrames();
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
	private void updateFrames()
	{
		ArrayList<AnimationInfoPack> infoPacks = 
				core.getInfoPacksOfType(AnimationInfoPack.class);
		for(AnimationInfoPack pack:infoPacks)
		{
			if(pack.updateReferences());
			{
				if(pack.isAnimating()
						&&isUpdateTime(pack.getLastUpdateTime(), pack.getFrameDelay()))
					{//If animations are active and it is time to update...
						pack.setLastUpdateTime(core.getSystem(TimerSystem.class).getNow());
						if((pack.isReversing()==false
								&&pack.getMaxFrame()>pack.getCurrentFrame())
								|| (pack.isReversing()==true&&0<pack.getCurrentFrame()))
						{//If it is not at the last frame in either direction
							pack.nextFrame();
						}
						else
						{//else if at the last frame in either direction
							if(pack.getLoopType()==AnimationComponent.LoopType.LOOPFROMSTART)
							{//if looping from beginning of the animation
								pack.setCurrentFrame(0);
							}
							else if(pack.getLoopType()==AnimationComponent.LoopType.LOOPSCAN)
							{//if playing the animation in the opposite direction
								pack.setReversing(!pack.isReversing());
							}
							else
							{//stop playing the animation
								pack.setAnimating(false);
								pack.setCurrentFrame(0);
							}
						}
						pack.setSpriteIndex(pack.getFrameSprite());
					}
			}
		}
	}
	
	/**
	 * Check to see if it is time to update the animation.
	 * @param lastUpdateTime	the time the animation was last updated
	 * @param frameDelay		the time to wait until updating the animation
	 * @return					true if time to update, false otherwise.
	 */
	public boolean isUpdateTime(final long lastUpdateTime, final long frameDelay)
	{
		//TODO: Remove direct references, go through Core or pass time?
		if(core.getSystem(TimerSystem.class).getNow()-lastUpdateTime>=frameDelay)
		{
			return true;
		}
		return false;
	}
}
