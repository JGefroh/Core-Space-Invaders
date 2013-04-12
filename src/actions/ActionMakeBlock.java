package actions;

import systems.Core;
import systems.EntityForgeSystem;

import components.PositionComponent;

import entities.IEntity;

public class ActionMakeBlock implements IAction
{
	private String command;
	private Core core;
	private ActionMakeBlock()
	{
		
	}
	public ActionMakeBlock(final Core core, final String command)
	{
		this.core = core;
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
		int x = entity.getComponent(PositionComponent.class).getGlobalX();
		int y = entity.getComponent(PositionComponent.class).getGlobalY();
		core.getSystem(EntityForgeSystem.class).makeBlock(x, y);
		System.out.println("Making block");
	}

}
