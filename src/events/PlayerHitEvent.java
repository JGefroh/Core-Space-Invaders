package events;

import systems.Core;
import entities.IEntity;

/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class PlayerHitEvent implements IEvent
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public PlayerHitEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&(source.getName().equals("PLAYER")
				&&target.getName().equals("BULLET"))
				||(source.getName().equals("PLAYER")
				&&target.getName().equals("ALIEN")))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity entity, final IEntity target)
	{
		System.out.println("~GAME OVER~");
		System.exit(0);
	}
}
