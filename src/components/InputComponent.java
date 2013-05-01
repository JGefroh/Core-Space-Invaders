package components;

import java.util.ArrayList;
import java.util.HashMap;

import entities.IEntity;

public class InputComponent implements IComponent
{
	private IEntity parent;
	
	/**Contains all of the commands this entity responds to.*/
	private ArrayList<String> commandList;

	public InputComponent(final IEntity parent)
	{
		this.parent = parent;
		commandList = new ArrayList<String>();
	}
	
	@Override
	public void init()
	{
	}

	@Override
	public void setParent(final IEntity parent)
	{
		this.parent = parent;
	}
	
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}
	
	////////////////////////////////////
	public boolean isInterested(final String command)
	{
		if(commandList.contains(command))
		{
			return true;
		}
		return false;
	}
	
	public void setInterested(final String command)
	{
		if(isInterested(command)==false)
		{
			commandList.add(command);
		}
	}
	
	////////////////////////////////////




}
