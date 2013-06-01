package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class FortCollisionEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public FortCollisionEffect(final Core core)
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
		if(source.getName().equals("BULLET"))
		{
			core.send("BULLET_HIT", source.getID());
			core.send("FORT_HIT", target.getID());
			core.send("ADVANCE_FRAME", target.getID());
		}
		else
		{
			core.send("BULLET_HIT", target.getID());
			core.send("FORT_HIT", source.getID());
			core.send("ADVANCE_FRAME", source.getID());
		}
	}
}
