package components;

import entities.IEntity;

public class AIComponent implements IComponent
{
	/**The owner of this component.*/
	private IEntity parent;
		
	public AIComponent(final IEntity entity)
	{	
		setParent(entity);
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
}
