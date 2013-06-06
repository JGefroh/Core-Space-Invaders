package com.jgefroh.systems;

import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;


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
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);

	/**Flag that indicates the aliens should switch direction.*/
	private boolean switchDirection;
	
	/**Flag that indicates the aliens should shift down.*/
	private boolean adjust;
	
	/**The velocity the aliens move.*/
	private int movementVel;
	
	/**The timing of the aliens movements.*/
	private long movementInterval;

	/**The random number generator to determine if the aliens can shoot.*/
	private Random randNum;
	
	/**The odds of firing (1 in X)*/
	private int dice;
	
	/**Flag that indicates the AI should switch to menu mode.*/
	private boolean isInMenu;
	
	
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
		LOGGER.log(Level.FINE, "Setting system values to default.");
		this.isRunning = true;
		this.movementVel = 16;
		this.movementInterval = 400;
		this.randNum = new Random();
		this.dice = 1000;
		this.adjust = true;
		this.switchDirection = false;
		this.isInMenu = false;
		
		core.setInterested(this, "START_NEW_WAVE");
		core.setInterested(this, "START_NEW_GAME");
		core.setInterested(this, "IN_MENU");
	}
	
	@Override
	public void start()
	{
		isRunning = true;
		LOGGER.log(Level.INFO, "System started.");
	}

	@Override
	public void work(final long now)
	{
		if(isRunning)
		{
			if(this.isInMenu)
			{
				executeMenuAI();
			}
			else
			{				
				moveAsGroup();
			}
		}
	}
	
	@Override
	public void stop()
	{
		isRunning = false;
		LOGGER.log(Level.INFO, "System stopped.");
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
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);

		if(id.equals("START_NEW_WAVE") || id.equals("START_NEW_GAME"))
		{//Switch to normal AI (moves downward and increases speed)
			init();
			this.isInMenu = false;
			LOGGER.log(Level.INFO, "Switched to normal AI.");
		}
		else if(id.equals("IN_MENU"))
		{//Switch to menu AI (moves back and forth, fires)
			init();
			this.isInMenu = true;
			LOGGER.log(Level.INFO, "Switched to menu AI.");
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Executes the AI for use in the menu (does not go down or speed up).
	 */
	private void executeMenuAI()
	{
		Iterator<AIInfoPack> packs = core.getInfoPacksOfType(AIInfoPack.class);
		
		while(packs.hasNext())
		{
			AIInfoPack each = packs.next();
			if(this.adjust)
			{
				core.send("SET_XVELOCITY",
						each.getOwner().getID(), 
						this.movementVel + "");
			}
		
			//Check to see if this unit is moving past the edge...
			if(isMovingPastEdge(each.getXPos()))
			{
				this.switchDirection = true;	//
			}
			
			//Check to see if the unit should ATTAAAAAAAACK!
			rollForAttack(each);
		}
		
		//Reset downward flag.
		this.adjust = false;
		
		//If it is time to switch direction...
		if(this.switchDirection)
		{
			LOGGER.log(Level.FINEST, 
					"Switching movement direction.");
			this.movementVel *= -1;
			this.switchDirection = false;
			this.adjust = true;
		}
	}
	
	/**
	 * Executes the normal AI, which speeds up every time it reaches the edge.
	 */
	private void moveAsGroup()
	{
		Iterator<AIInfoPack> packs = core.getInfoPacksOfType(AIInfoPack.class);
		
		while(packs.hasNext())
		{
			AIInfoPack each = packs.next();
			
			//If time to shift down...
			if(this.adjust)
			{
				//Change the X velocity
				core.send("SET_XVELOCITY",
						each.getOwner().getID(), 
						this.movementVel + "");
				//Change the update time (use with above to smooth out movement)
				core.send("SET_MOVEMENTINTERVAL",
						each.getOwner().getID(), 
						this.movementInterval + "");
				
				//Shift downward
				core.send("SHIFT_Y", each.getOwner().getID(), 32 + "");
			}
		
			//Check to see if this unit is moving past the edge...
			if(isMovingPastEdge(each.getXPos()))
			{
				this.switchDirection = true;	//Set the flag to switch
			}
			
			//Check to see if the unit should ATTAAAAAAAACK!
			rollForAttack(each);
		}
		
		//Reset downward flag.
		this.adjust = false;
		
		//If it is time to switch direction...
		if(this.switchDirection)
		{
			LOGGER.log(Level.FINEST, 
							"Adjusting AI | Velocity: " + this.movementVel
							+ ", Interval: " + this.movementInterval);
			this.movementVel *= -1;
			if(this.movementVel<0)
			{				
				this.movementVel-=1;
			}
			else
			{
				this.movementVel+=1;
			}
			this.movementInterval-=50;
			this.switchDirection = false;
			this.adjust = true;
		}
	}
	
	/**
	 * Checks to see if the unit gets to attack this turn.
	 * @param each	the AIInfoPack of the entity that is rolling
	 */
	private void rollForAttack(final AIInfoPack each)
	{
		if(randNum.nextInt(this.dice)==5)
		{
			//If a roll was successful...
			core.send("REQUEST_FIRE", each.getOwner().getID());
		}
	}
	
	/**
	 * Checks if a unit is moving past the edge.
	 * @param xPos	the X position of the unit.
	 * @return	true if the unit is attempting to move past game boundaries;
	 * 			false otherwise
	 */
	private boolean isMovingPastEdge(final int xPos)
	{
		if((xPos<0&&this.movementVel<0) 
				|| (xPos>1680 && this.movementVel>0))
		{
			//if the unit is moving left past the edge OR
			//if the using is moving right past the edge...

			LOGGER.log(Level.FINEST, 
					"Entity attempting to move past edge at X-coord: " + xPos);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
