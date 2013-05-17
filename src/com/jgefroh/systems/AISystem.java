package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.WeaponInfoPack;


/**
 * This system controls the behavior of the AI for the Space Invaders
 * aliens.
 * @author Joseph Gefroh
 */
public class AISystem implements ISystem
{
	//TODO: Interval and speed should be held by the entity.
	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**A flag for the aliens to see if the squadron is moving left or right.*/
	private boolean isMovingLeft;
	
	/**Time minimum time to wait between movements.*/
	private int movementInterval;
	
	/**The speed the aliens move.*/
	private int movementSpeed;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(AISystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new AISystem.
	 * @param core	a reference to the Core controlling this system
	 */
	public AISystem(final Core core)
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
		isMovingLeft = false;
		movementInterval = 200;
		movementSpeed = 10;
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
			moveAndShoot();
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
	 * Makes the aliens move and shoot.
	 */
	private void moveAndShoot()
	{//TODO: Fix this stupid mess of a method.
		ArrayList<AIInfoPack> packs
		= core.getInfoPacksOfType(AIInfoPack.class);
		boolean switched = false;
		for(AIInfoPack each:packs)
		{
			//SHOOT
			shoot(each.getOwner());
			if(isMovingLeft==true)
			{				
				if(each.getXPos()<=0)
				{
					switched = true;
				}
				core.getSystem(TransformSystem.class).setXVelocity(each.getOwner(), -movementSpeed);
			}
			else if(isMovingLeft==false)
			{
				if(each.getXPos()>=1648)
				{
					switched = true;
				}			
				core.getSystem(TransformSystem.class).setXVelocity(each.getOwner(), movementSpeed);
			}
		}
		if(switched==true)
		{
			isMovingLeft = !isMovingLeft;
			movementInterval-=20;
			for(AIInfoPack each:packs)
			{
				core.getSystem(TransformSystem.class).shiftPosition(each.getOwner(), 0, 25);
				core.getSystem(TransformSystem.class).setInterval(each.getOwner(), movementInterval);
			}
		}
	}
	
	/**
	 * Randomly determines if an entity should shoot or not.
	 * @param parent	the Entity that it is making the determination for
	 */
	private void shoot(final IEntity parent)
	{//TODO: Ugh.
		Random r = new Random();
		if(r.nextInt(1000)==5)
		{
			WeaponInfoPack wip = core.getInfoPackFrom(parent, WeaponInfoPack.class);
			wip.setFireRequested(true);
		}
	}
}
