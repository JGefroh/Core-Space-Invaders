package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionNewGame implements IAction
{
	private final String command = "REQUEST_NEW_GAME";
	private Core core;
	
	public ActionNewGame(final Core core)
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
		core.send("REQUEST_NEW_GAME", "");
		System.out.println("LOL");
	}

}
