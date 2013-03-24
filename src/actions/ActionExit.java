package actions;

import entities.IEntity;

public class ActionExit implements IAction
{
	private String command;
	private ActionExit()
	{
		
	}
	public ActionExit(final String command)
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
		System.exit(0);
	}

}
