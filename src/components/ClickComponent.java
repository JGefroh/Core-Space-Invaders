package components;

import entities.IEntity;

public class ClickComponent implements IComponent
{
	private IEntity parent;
	private boolean clicked;
	private String onClickAction;
	
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
	//////////
}
