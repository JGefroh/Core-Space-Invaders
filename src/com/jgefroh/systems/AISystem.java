package com.jgefroh.systems;


import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.WeaponInfoPack;


/**
 * This system controls the behavior of the AI for the alien enemies.
 * @author Joseph Gefroh
 */
public class AISystem implements ISystem
{	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**A flag for the aliens to see if the squadron is moving left or right.*/
	private boolean isMovingLeft;
	
	/**Time minimum time to wait between movements.*/
	private int movementInterval;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The speed the aliens move.*/
	private int movementSpeed;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	

	private int windowWidth;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this System.
	 * @param core	a reference to the Core controlling this system
	 */
	public AISystem(final Core core)
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
	public void work(final long now)
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

	}
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Makes the aliens move and shoot.
	 */
	private void moveAndShoot()
	{//TODO: Fix this stupid mess of a method.
		Iterator<AIInfoPack> packs
		= core.getInfoPacksOfType(AIInfoPack.class);
		boolean switched = false;
		while(packs.hasNext())
		{
			AIInfoPack each = packs.next();
			if(each.isDirty()==false)
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
					if(each.getXPos()>=1680)
					{
						switched = true;
					}			
					core.getSystem(TransformSystem.class).setXVelocity(each.getOwner(), movementSpeed);
				}
			}

		}
		if(switched==true)
		{
			isMovingLeft = !isMovingLeft;
			movementInterval-=20;
			while(packs.hasNext())
			{
				AIInfoPack each = packs.next();
				if(each.isDirty()==false)
				{
					core.getSystem(TransformSystem.class).shiftPosition(each.getOwner(), 0, 25);
					core.getSystem(TransformSystem.class).setInterval(each.getOwner(), movementInterval);
				}
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
