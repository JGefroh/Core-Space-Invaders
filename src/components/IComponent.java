package components;

import entities.IEntity;

public interface IComponent
{
	public void init();
	public String toString();
	public void setParent(final IEntity parent);
	public IEntity getParent();
}
