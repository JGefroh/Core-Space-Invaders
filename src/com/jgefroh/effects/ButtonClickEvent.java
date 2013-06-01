package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class ButtonClickEvent implements IEffect
{
	private final String EVENT = "CLICK";
	private Core core;
	
	public ButtonClickEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&(source.getName().equals("BUTTON")))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity entity, final IEntity target)
	{
		if(entity.getName().equals("QUIT"))
		{
			System.out.println("Thanks for playing!");
			System.exit(0);
		}
	}
}
