package actions;

import systems.Core;
import systems.TransformSystem;
import entities.IEntity;

public class ActionStopY implements IAction
{
	private final String command = "STOPY";
	private Core core;
	
	public ActionStopY(final Core core)
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
		core.getSystem(TransformSystem.class).setYVelocity(entity, 0);
	}

}
