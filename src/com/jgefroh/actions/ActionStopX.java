package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionStopX implements IAction
{
	private final String command = "STOPX";
	private Core core;
	
	public ActionStopX(final Core core)
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
		core.getSystem(TransformSystem.class).setXVelocity(entity, 0);
	}

}
