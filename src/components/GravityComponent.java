package components;

import entities.IEntity;

public class GravityComponent implements IComponent
{
	private IEntity parent;
	
	/**The time the gravity was last updated, as a long*/
	private long lastUpdate = 0;
	
	/**The acceleration per update*/
	private int gravity;
	
	/**Time to wait between updates*/
	private long updateInterval;
	
	/**The maximum acceleration*/
	private int maxGravity;
	
	public GravityComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void init()
	{
	}
	public void setUpdateInterval(final long interval)
	{
		this.updateInterval = interval;
	}
	public void setLastUpdate(final long lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}
	
	public void setGravity(final int gravity)
	{
		this.gravity = gravity;
	}
	
	public void setMaxGravity(final int maxGravity)
	{
		this.maxGravity = maxGravity;
	}
	public int getGravity()
	{
		return this.gravity;
	}
	public int getMaxGravity()
	{
		return this.maxGravity;
	}
	
	public long getLastUpdate()
	{
		return this.lastUpdate;
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

	public long getUpdateInterval()
	{
		return this.updateInterval;
	}

}
