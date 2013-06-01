package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class PlayerCollisionEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public PlayerCollisionEffect(final Core core)
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
		if(entity.getName().equals("ALIEN") || target.getName().equals("ALIEN"))
		{
			core.send("GAME_OVER");
		}
		else
		{	
			if(entity.getName().equals("PLAYER"))
			{				
				core.send("PLAYER_HIT", entity.getID());
				core.send("BULLET_HIT", target.getID());
			}
			else
			{
				core.send("PLAYER_HIT", target.getID());
				core.send("BULLET_HIT", entity.getID());
			}
		}
	}
}
