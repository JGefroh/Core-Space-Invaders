package actions;

import components.AnimationComponent;
import components.VelocityComponent;

import systems.Core;
import systems.MovementSystem;
import entities.IEntity;

public class ActionStopX implements IAction
{
	private String command;
	private Core core;
	private ActionStopX()
	{
		
	}
	public ActionStopX(final Core core, final String command)
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
		ac.setCurrentAnimation("IDLE");
		if(vc!=null)
		{			
			vc.setXVelocity(0);
		}
	}

}
