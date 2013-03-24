package actions;

import systems.Core;

import components.AnimationComponent;
import components.VelocityComponent;

import entities.IEntity;

public class ActionMoveLeft implements IAction
{
	private String command;
	private Core core;
	private ActionMoveLeft()
	{
		
	}
	public ActionMoveLeft(final Core core, final String command)
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
		VelocityComponent vc = entity.getComponent(VelocityComponent.class);
		AnimationComponent ac = entity.getComponent(AnimationComponent.class);
		ac.setCurrentAnimation("MOVELEFT");
		if(vc!=null)
		{			
			vc.setXVelocity(-5);
		}
	}

}
