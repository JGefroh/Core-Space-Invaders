package com.jgefroh.actions;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.TransformSystem;


public class ActionMoveLeft implements IAction
{
	private final String command = "MOVE_LEFT";
	private Core core;
	
	public ActionMoveLeft(final Core core)
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
		int xPos = entity.getComponent(TransformComponent.class).getXPos();
		int width = entity.getComponent(TransformComponent.class).getWidth();
		if(xPos>=10)
		{			
			core.getSystem(TransformSystem.class).setXVelocity(entity, -10);
		}
		else
		{
			core.getSystem(TransformSystem.class).setXVelocity(entity, 0);
		}
	}

}
