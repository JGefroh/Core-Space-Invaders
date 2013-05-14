package systems;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.IEntity;
import events.AlienHitEvent;
import events.FortHitEvent;
import events.IEvent;
import events.PlayerHitEvent;

/**
 * This system executes event specific actions.
 * @author Joseph Gefroh
 */
public class EventSystem implements ISystem
{
	//TODO: Look into broadcasting events to interested systems.
	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(EventSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Stores events to execute when received.*/
	private ArrayList<IEvent> events;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new EventSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public EventSystem(final Core core)
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
		//TODO: Make event initialization automatic.
		initLogger();
		isRunning = true;
		events = new ArrayList<IEvent>();
		trackEvent(new PlayerHitEvent(core));
		trackEvent(new AlienHitEvent(core));
		trackEvent(new FortHitEvent(core));
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
	 * Execute a received event.
	 * @param event		the name of the event to execute
	 * @param source	the source of the event
	 * @param target	the target of the event
	 */
	public void notify(final String event, final IEntity source, 
							final IEntity target)
	{
		//TODO: Poorly named method (or bad execution)? notifyAndExecute()?
		//TODO: Requires predetermined actions - not easy to dynamically extend
		for(IEvent each:events)
		{
			if(each.check(event, source, target))
			{
				each.execute(source, target);
			}
		}
	}
	
	/**
	 * Start tracking an event.
	 * @param event	the event to track
	 */
	public void trackEvent(final IEvent event)
	{
		events.add(event);
	}
}
