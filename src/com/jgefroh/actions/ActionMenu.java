package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionMenu implements IAction
{
	private final String command = "QUIT";
	private Core core;
	
	public ActionMenu(final Core core)
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
		core.send("REQUEST_SHOW_MENU", "");
	}

}
