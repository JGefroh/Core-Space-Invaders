package systems;

import infopacks.MovementInfoPack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import components.VelocityComponent;
import entities.IEntity;

/**
 * System that handles movement and repositioning of entities.
 * @author Joseph Gefroh
 */
public class TransformSystem implements ISystem
{
	private Core core;
	
	/**Determines whether the system will run or not.*/
	private boolean isRunning;

	private TimerSystem timer;
	
	//////////
	private final static Logger LOGGER 
		= Logger.getLogger(TransformSystem.class.getName());
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
	
	//////////
	public TransformSystem(final Core core)
	{
		this.core = core;
		initLogger();
	}
	
	public void move()
	{
		ArrayList<MovementInfoPack> packs 
			= core.getInfoPacksOfType(MovementInfoPack.class);

		for(MovementInfoPack each:packs)
		{
			if(timer.isUpdateTime(each.getInterval(), each.getLastUpdated()))
			{
				each.setLastUpdated(timer.getNow());
				int newX = each.getXPos()+each.getXVelocity();
				int newY = each.getYPos()+each.getYVelocity();
				each.setXPos(newX);
				each.setYPos(newY);
			}
		}
	}
	
	/**
	 * Performs rotation.
	 */
	public void rotate()
	{
		
	}
	
	/**
	 * Performs scaling.
	 */
	public void scale()
	{
		
	}
	
	public void shiftPosition(final IEntity entity, final int xPos, final int yPos)
	{
		MovementInfoPack mip 
			= core.getInfoPackFrom(entity, MovementInfoPack.class);
		mip.setXPos(mip.getXPos()+xPos);
		mip.setYPos(mip.getYPos()+yPos);
	}
	//////////
	/**
	 * Set the X movement speed of an entity.
	 * @param entity	the entity 
	 * @param xVel		the X velocity
	 */
	public void setXVelocity(final IEntity entity, final int xVel)
	{
		MovementInfoPack pack = 
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setXVelocity(xVel);
	}
	
	/**
	 * Set the Y movement speed of an entity.
	 * @param entity	the entity 
	 * @param xVel		the Y velocity
	 */
	public void setYVelocity(final IEntity entity, final int yVel)
	{
		MovementInfoPack pack = 
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setYVelocity(yVel);
	}
	
	/**
	 * Adjust the X movement speed of an entity by the given amount.
	 * @param entity	the entity
	 * @param xVel		the x Velocity
	 */
	public void adjustXVelocity(final IEntity entity, final int xVel)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setXVelocity(pack.getXVelocity()+xVel);
	}
	
	/**
	 * Adjust the Y movement speed of an entity by the given amount.
	 * @param entity	the entity
	 * @param yVel		the y Velocity
	 */
	public void adjustYVelocity(final IEntity entity, final int yVel)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setYVelocity(pack.getYVelocity()+yVel);
	}
	public void setInterval(final IEntity entity, final long interval)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setInterval(interval);
	}
	//////////
	@Override
	public void start()
	{
		this.timer = core.getSystem(TimerSystem.class);
		if(this.timer!=null)
		{	
			LOGGER.log(Level.INFO, "Starting System: TransformSystem.");
			this.isRunning = true;
		}
		else
		{
			LOGGER.log(Level.WARNING, "Failed to start (no timer)");
		}
	}

	@Override
	public void work()
	{
		if(this.isRunning&&this.timer!=null)
		{
			move();
			rotate();
			scale();			
		}
		else
		{
			stop();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "Stopping System: TransformSystem.");
		this.isRunning = false;
	}

}
