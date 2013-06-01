package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.GUISystem;
import com.jgefroh.systems.TransformSystem;


public class ActionPause implements IAction
{
	private final String command = "PAUSE";
	private Core core;
	
	public ActionPause(final Core core)
	{
		this.core = core;
	}

	@Override
	public String getCommand()
	{
		return this.command;
	}

	@Override
	public void execute(final IEntity entity)
	{
		if(core.isPaused())
		{
			core.send("REQUEST_STATE_UNPAUSED", core.now() + "");
		}
		else
		{			
			core.send("REQUEST_STATE_PAUSED", core.now() + "");
		}
	}

}
