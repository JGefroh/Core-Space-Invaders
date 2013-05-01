package components;

import entities.IEntity;

/**
 * This class contains data related to the object's positioning, rotation,
 * and scale.
 * @author Joseph Gefroh
 */
public class WeaponComponent implements IComponent
{
	private IEntity parent;
	
	private boolean isReady;
	private boolean isFireRequested;
	
	public WeaponComponent(final IEntity parent)
	{
		this.parent = parent;
		this.isReady = true;
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
	////////////////////
	public void setReady(final boolean isReady)
	{
		this.isReady = isReady;
	}
	public void setFireRequested(final boolean isFireRequested)
	{
		this.isFireRequested = isFireRequested;
	}
	////////////////////
	public boolean isReady()
	{
		return isReady;
	}
	public boolean isFireRequested()
	{
		return isFireRequested;
	}
}
