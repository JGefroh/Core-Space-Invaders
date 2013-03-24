package components;

import entities.IEntity;

public class CursorComponent implements IComponent
{
	private IEntity parent;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
