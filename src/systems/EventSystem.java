package systems;

import java.util.ArrayList;

import entities.IEntity;
import events.AlienHitEvent;
import events.FortHitEvent;
import events.IEvent;
import events.PlayerHitEvent;

/**
 * This system queues events that have occurred so that other systems
 * can react based on the events.
 * @author Joseph Gefroh
 */
public class EventSystem implements ISystem
{
	private ArrayList<IEvent> events;
	private Core core;
	
	public EventSystem(final Core core)
	{
		this.core = core;
		events = new ArrayList<IEvent>();
	}
	
	@Override
	public void start() 
	{
		trackEvent(new PlayerHitEvent(core));
		trackEvent(new AlienHitEvent(core));
		trackEvent(new FortHitEvent(core));
	}
	
	
	@Override
	public void work()
	{
		
	}

	@Override
	public void stop()
	{	
	}
	
	public void notify(final String event, final IEntity source, 
							final IEntity target)
	{
		System.out.println(event + " has occurred: " + source.getName() + " to " + target.getName());
		for(IEvent each:events)
		{
			if(each.check(event, source, target))
			{
				each.execute(source, target);
			}
		}
	}
	
	public void trackEvent(final IEvent event)
	{
		events.add(event);
	}

}
