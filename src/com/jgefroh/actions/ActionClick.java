package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionClick implements IAction
{
	private final String command = "LEFTCLICK";
	private Core core;
	
	public ActionClick(final Core core)
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
		core.send("INPUT_LEFT_CLICK", "");
	}

}
