package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class AlienCollisionEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public AlienCollisionEffect(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&source.getName().equals("BULLET")
				&&target.getName().equals("ALIEN"))
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
			core.send("ADD_SCORE", target.getID());
			core.send("ALIEN_HIT", target.getID());
			core.send("KILL", target.getID());
		}
		else
		{
			core.send("BULLET_HIT", target.getID());
			core.send("ADD_SCORE", source.getID());
			core.send("ALIEN_HIT", source.getID());
			core.send("KILL", source.getID());
		}
	}
}
