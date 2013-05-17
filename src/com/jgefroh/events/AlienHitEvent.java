package com.jgefroh.events;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.WeaponSystem;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class AlienHitEvent implements IEvent
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public AlienHitEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&source.getName().equals("ALIEN")
				&&target.getName().equals("BULLET"))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity entity, final IEntity target)
	{		
		core.getSystem(WeaponSystem.class).hit(entity, target);
		core.removeEntity(entity);
		core.removeEntity(target);
	}
}
