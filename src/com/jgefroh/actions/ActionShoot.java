package com.jgefroh.actions;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.systems.StatSystem;
import com.jgefroh.systems.WeaponSystem;


public class ActionShoot implements IAction
{
	private final String command = "SHOOT";
	private Core core;
	
	public ActionShoot(final Core core)
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
		core.send("REQUEST_FIRE", entity.getID());
	}

}
