package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionMoveDown implements IAction
{
	private final String command = "MOVE_DOWN";
	private Core core;
	
	public ActionMoveDown(final Core core)
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
		core.getSystem(TransformSystem.class).setYVelocity(entity, 5);
	}

}
