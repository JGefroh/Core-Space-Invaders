package components;

import java.util.HashMap;

import entities.IEntity;

public class InputComponent_dep implements IComponent
{
	private IEntity parent;
	private boolean isEnabled = false;
	
	private HashMap<String, Boolean> watchList; 
	
	private InputComponent_dep()
	{
	}
	
	public InputComponent_dep(final IEntity parent)
	{
		this.parent = parent;
		setEnabled(true);
		watchList = new HashMap<String, Boolean>();
	}
	
	public boolean isEnabled()
	{
		return isEnabled;
	}
	
	public void setEnabled(final boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	////////////////////////////////////
	public boolean isInterested(final String command)
	{
		Boolean isInterested = watchList.get(command);
		if(isInterested!=null)
		{
			return isInterested.booleanValue();
		}
		return false;
	}
	
	public void setInterested(final String command, final boolean isInterested)
	{
		watchList.put(command, isInterested);
	}
	
	////////////////////////////////////
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



}
