package components;

import entities.IEntity;

public class BulletComponent implements IComponent
{
	private IEntity parent;
	
	private IEntity bulletOwner;
	
	public BulletComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	
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
	
	public void setBulletOwner(final IEntity bulletOwner)
	{
		this.bulletOwner = bulletOwner;
	}
	
	public IEntity getBulletOwner()
	{
		return this.bulletOwner;
	}

}
