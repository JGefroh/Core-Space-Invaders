package systems;

import java.lang.reflect.Field;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * System to help control the timing of events.
 * @author 	Joseph Gefroh
 * @since 	13MAY13
 */
public class TimerSystem implements ISystem
{
	//TODO: Clean up and fix, make consistent.
	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(TimerSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
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


	//////////
	// INIT
	//////////
	/**
	 * Create a default timer that ticks once per second.
	 * @param core	a reference to the Core controlling this system
	 */
	public TimerSystem(final Core core)
	{
		this.core = core;
		setName("Default");
		this.timeScale = 1000;
	}
	
	/**
	 * Create a timer that runs at a specified number of ticks per second.
	 * @param tps	the desired ticks per second
	 * @throws IllegalArgumentException	thrown if tps <= 0
	 */
	public TimerSystem(final Core core, final String name, 
					final double tps) throws IllegalArgumentException
	{
		this.core = core;
		setName(name);
		setTPS(tps);
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
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
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
	
	/**
	 * Check to see the desired amount of time has passed.
	 * @param interval		the amount of time to check
	 * @param lastUpdated	the time the check was last conducted
	 * @return		true if the interval has passed, false otherwise.
	 */
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
	 * Get the current time according to the system.
	 * @return	the current time according to the system.
	 */
	public void updateNow()
	{
		this.now = System.currentTimeMillis();
	}
	
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the time of the system.
	 * @return the time according to System.currentTimeMillis()
	 */
	public long getNow()
	{
		return this.now;
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
	
	
	//////////
	// SETTERS
	//////////
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
	
	
	//////////
	// UTIL
	//////////
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
}