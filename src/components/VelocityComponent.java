package components;

import entities.IEntity;

/**
 * This component contains data used to move objects smoothly.
 * @author Joseph Gefroh
 */
public class VelocityComponent implements IComponent
{
	private IEntity parent;
	
	/**The number of units to move on the X axis every interval.*/
	private int xVelocity;
	
	/**The number of units to move on the Y axis every interval.*/
	private int yVelocity;
	
	/**The amount of time to wait in-between movements, in milliseconds.*/
	private long interval;
	
	/**The time the velocity was last updated.*/
	private long lastUpdated;
	
	public VelocityComponent(final IEntity parent)
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
	public int getXVelocity()
	{
		return this.xVelocity;
	}
	public int getYVelocity()
	{
		return this.yVelocity;
	}
	public long getInterval()
	{
		return this.interval;
	}
	public long getLastUpdated()
	{
		return this.lastUpdated;
	}
	//////////
	public void setXVelocity(final int xVelocity)
	{
		this.xVelocity = xVelocity;
	}
	public void setYVelocity(final int yVelocity)
	{
		this.yVelocity = yVelocity;
	}
	public void setInterval(final long interval)
	{
		this.interval = interval;
	}
	public void setLastUpdated(final long lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	


}
