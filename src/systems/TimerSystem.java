package systems;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.util.ResourceLoader;


/**
 * System to help control the timing of events.
 * @author 	Joseph Gefroh
 * @since 	23FEB13
 *
 */
public class TimerSystem implements ISystem
{
	/**The name of the timer*/
	private String name;
	
	/**The amount of time, in milliseconds, to wait before advancing a tick.*/
	private long timeScale;		//1000 = 1 update/second
	
	/**The time of the last tick formatted as a long*/
	private long last;
	
	/**The current time, in long format*/
	private long now;
	
	/**The sequence number of the current tick (starts at 0)*/
	private long currentTick;
	
	/**Is the system running or not?*/
	private boolean isRunning;
	
	private final static Logger LOGGER 
		= Logger.getLogger(TimerSystem.class.getName());
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}

	/**
	 * Create a default timer that ticks once per second.
	 */
	public TimerSystem()
	{
		setName("Default");
		this.timeScale = 1000;
	}
	
	/**
	 * Create a timer that runs at a specified number of ticks per second.
	 * @param tps	the desired ticks per second
	 * @throws IllegalArgumentException	thrown if tps <= 0
	 */
	public TimerSystem(final String name, 
					final double tps) throws IllegalArgumentException
	{
		setName(name);
		setTPS(tps);
	}
	
	/**
	 * Advance to the next tick if the amount of time to wait has been exceeded.
	 * @return true if the timer advanced to the next tick, false otherwise.
	 */
	public boolean nextTick()
	{
		now = getNow();
		if(now-last>=timeScale)
		{//if time for next tick (according to timeScale)
			last = now;
			currentTick++;
			return true;
		}
		return false;
	}
	
	public boolean isUpdateTime(final long interval, final long lastUpdated)
	{
		if(this.now-lastUpdated<interval)
		{
			return false;
		}
		else if(lastUpdated==0)
		{
			return true;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Get the time of the system.
	 * @return the time according to System.currentTimeMillis()
	 */
	public long getNow()
	{
		return this.now;
	}
	
	/**
	 * Get the current time according to the system.
	 * @return	the current time according to the system.
	 */
	public void updateNow()
	{
		this.now = System.currentTimeMillis();
	}
	
	/**
	 * Calculate and return the ticks per second according to the timeScale.
	 * @return	the double ticks per second
	 */
	public double getTPS()
	{
		return (1000.00/timeScale);
	}
	
	/**
	 * Get the time the last tick took place.
	 * @return	the long time of the last tick
	 */
	public long getLast()
	{
		return this.last;
	}
	
	/**
	 * Get the sequence number of the current tick.
	 * @return	the long number of the current tick.
	 */
	public long getCurrentTick()
	{
		return this.currentTick;
	}
	
	/**
	 * Get the timeScale the timer is running on.
	 * @return	the long timeScale
	 */
	public long getTimeScale()
	{
		return this.timeScale;
	}
	
	/**
	 * Get the name of this timer.
	 * @return	the String name of this timer
	 */
	public String getName()
	{
		return this.name;
	}
	
	//
	// Setters
	//
	/**
	 * Set the expected ticks per second. The tps must be greater than 0.
	 * @param tps the ticks per second
	 * @throws IllegalArgumentException	thrown if the TPS is <=0
	 */
	public void setTPS(final double tps) throws IllegalArgumentException
	{
		if(tps>0)
		{
			this.timeScale = (long)(1000/tps);
		}
		else
		{
			throw new IllegalArgumentException("TPS must be > 0 | got " + tps);
		}
	}
	
	/**
	 * Set the name of this timer.
	 * @param name	the String name to give the timer
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
	
	
	/**
	 * Returns a String representation of this object.
	 * @return a String representation of the object
	 */
	public String toString2()
	{
		String returnString = "";
		StringBuilder result = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		result.append(this.getClass().getName());
		result.append("\n{\n");
		for(Field each:fields)
		{
			result.append("\t");
			result.append(each.getName());
			result.append("=");
			try
			{
				result.append(each.get(this));
			}
			catch (IllegalAccessException e)
			{
			}
			result.append("\n");
		}
		result.append("}");
		returnString = result.toString();
		return returnString;
	}

	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "Starting System: TimerSystem.");
		this.isRunning = true;
	}

	@Override
	public void work()
	{
		if(this.isRunning)
		{
			updateNow();			
		}
		else
		{
			stop();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "Stopping System: TimerSystem.");
		this.isRunning = false;
	}
}
