package events;

import entities.IEntity;

/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public interface IEvent
{
	public boolean check(final String string, final IEntity source, final IEntity target);
	public void execute(final IEntity entity, final IEntity target);
}
