package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;



/**
 * Contains data to keep track of a menu option.
 * @author Joseph Gefroh
 */
public class MenuComponent implements IComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	private boolean isSelected;
	
	private String command;
	//////////
	// INIT
	//////////
	/**
	 * Create a new menu component.
	 * @param entity	the owner of this component
	 */
	public MenuComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
		isSelected = false;
	}
	
	
	//////////
	// METHODS
	//////////
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public boolean isSelected()
	{
		return this.isSelected;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	public void setSelected(final boolean isSelected)
	{
		this.isSelected = isSelected;
	}
	
	public void setCommand(final String command)
	{
		this.command = command;
	}
}
