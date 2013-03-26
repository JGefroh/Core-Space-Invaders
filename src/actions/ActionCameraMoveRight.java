package actions;

import components.AnimationComponent;
import components.VelocityComponent;

import systems.Core;
import systems.MovementSystem;
import entities.IEntity;

public class ActionCameraMoveRight implements IAction
{
	private String command;
	private Core core;
	private ActionCameraMoveRight()
	{
		
	}
	public ActionCameraMoveRight(final Core core, final String command)
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
		if(ac!=null)
		{
			ac.setCurrentAnimation("MOVERIGHT");
		}
			
		if(vc!=null)
		{			
			vc.setXVelocity(5);
		}
	}

}
