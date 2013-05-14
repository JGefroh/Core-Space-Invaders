package systems;

import infopacks.HealthInfoPack;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This system goes through all entities with health and marks ones with no
 * health as dead.
 * @author Joseph Gefroh
 */
public class HealthCheckSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(HealthCheckSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new HealthCheckSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public HealthCheckSystem(final Core core)
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
			checkHealth();
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
	 * Check the health of all entities and "deadify" those below 0 health.
	 */
	private void checkHealth()
	{
		ArrayList<HealthInfoPack> packs 
		= core.getInfoPacksOfType(HealthInfoPack.class);
		for(HealthInfoPack each:packs)
		{
			if(each.getCurHealth()<=0)
			{
				core.removeEntity(each.getOwner());
				core.getSystem(EventSystem.class)
					.notify("DEAD", each.getOwner(), each.getOwner());
			}
		}
	}
}
