package components;

import entities.IEntity;

public class GravityComponent implements IComponent
{
	private IEntity parent;
	
	/**The time the gravity was last updated, as a long*/
	private long lastUpdate = 0;
	
	/**The acceleration per update*/
	private int acceleration;
	
	/**Time to wait between updates*/
	private long interval;
	
	/**The maximum acceleration*/
	private int maxAcceleration;
	
	public GravityComponent(final IEntity parent)
	{
		this.parent = parent;
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
	//////////
	public void setUpdateInterval(final long interval)
	{
		this.interval = interval;
	}
	public void setLastUpdate(final long lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}
	public void setAcceleration(final int acceleration)
	{
		this.acceleration = acceleration;
	}
	public void setMaxAcceleration(final int maxAcceleration)
	{
		this.maxAcceleration = maxAcceleration;
	}
	//////////
	public int getAccelerationy()
	{
		return this.acceleration;
	}
	public int getMaxAcceleration()
	{
		return this.maxAcceleration;
	}
	
	public long getLastUpdate()
	{
		return this.lastUpdate;
	}
	public long getInterval()
	{
		return this.interval;
	}

}
