package components;

import entities.IEntity;

public class VelocityComponent implements IComponent
{
	private IEntity parent;
	private int xVelocity;
	private int yVelocity;
	private boolean isMoveRequested;
	public VelocityComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	public int getXVelocity()
	{
		return this.xVelocity;
	}
	public int getYVelocity()
	{
		return this.yVelocity;
	}
	public void setXVelocity(final int xVelocity)
	{
		this.xVelocity = xVelocity;
		if(xVelocity!=0)
		{			
			setMoveRequested(true);
		}
	}
	
	public void setYVelocity(final int yVelocity)
	{
		this.yVelocity = yVelocity;
		if(yVelocity!=0)
		{			
			setMoveRequested(true);
		}
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
	public boolean isMoveRequested()
	{
		return this.isMoveRequested;
	}
	public void setMoveRequested(final boolean isMoveRequested)
	{
		this.isMoveRequested = isMoveRequested;
	}

}
