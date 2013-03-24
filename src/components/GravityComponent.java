package components;

import entities.IEntity;

public class GravityComponent implements IComponent
{
	private IEntity parent;
	private boolean isFalling = false;
	public GravityComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(IEntity parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}

}
