package com.jgefroh.events;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.WeaponSystem;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class FortHitEvent implements IEvent
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public FortHitEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&(source.getName().equals("BULLET")
				&&target.getName().equals("FORT")))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity source, final IEntity target)
	{
		core.getSystem(WeaponSystem.class).hit(source, target);
		core.getSystem(DamageSystem.class).damage(1, source, target);
		int spriteID = target.getComponent(RenderComponent.class).getSpriteID();
		target.getComponent(RenderComponent.class).setSpriteID(spriteID+1);
		core.removeEntity(source);
	}
}
