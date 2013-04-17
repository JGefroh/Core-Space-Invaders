package systems;

import infopacks.MovementInfoPack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import components.VelocityComponent;

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
				each.setXPos(each.getXPos()+each.getXVelocity());
				each.setYPos(each.getYPos()+each.getYVelocity());
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

	@Override
	public void start()
	{
		this.timer = core.getSystem(TimerSystem.class);
		if(this.timer!=null)
		{	
			LOGGER.log(Level.INFO, "Starting System: TransformSystem.");
			this.isRunning = true;
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
