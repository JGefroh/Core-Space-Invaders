package com.jgefroh.infopacks;

import com.jgefroh.components.InputComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


public class InputInfoPack implements IInfoPack
{
	private IEntity parent;
	
	private InputComponent ic;
	
	public InputInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	
	@Override
	public boolean updateReferences()
	{
		ic = parent.getComponent(InputComponent.class);
		if(ic!=null)
		{
			return true;
		}
		return false;
	}
	
	public boolean isInterested(final String command)
	{
		return ic.checkInterested(command);
	}

	public IEntity getOwner()
	{
		return this.parent;
	}
	


}
