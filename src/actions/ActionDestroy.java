package actions;

import systems.Core;
import systems.EntityForgeSystem;

import components.PositionComponent;

import entities.IEntity;

public class ActionDestroy implements IAction
{
	private String command;
	private Core core;
	public ActionDestroy(final Core core, final String command)
	{
		this.core = core;
		this.command = command;
	}
	public ActionDestroy(final String command)
	{
		setCommand(command);
	}
	@Override
	public void setCommand(final String command)
	{
		this.command = command;
	}

	@Override
	public String getCommand()
	{
		return this.command;
	}
	
	public void execute(final IEntity entity)
	{
	}
}
