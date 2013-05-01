package actions;

import systems.Core;
import systems.TransformSystem;
import entities.IEntity;

public class ActionMoveUp implements IAction
{
	private final String command = "MOVE_UP";
	private Core core;
	
	public ActionMoveUp(final Core core)
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
		core.getSystem(TransformSystem.class).setYVelocity(entity, -5);
	}

}
