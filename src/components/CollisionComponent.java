package components;

import java.util.HashMap;

import entities.IEntity;

public class CollisionComponent implements IComponent
{
	private IEntity parent;
	private int collisionGroup;
	
	private boolean left;
	private boolean right;
	private boolean top;
	private boolean bottom;
	
	public CollisionComponent(final IEntity parent)
	{
		setParent(parent);
	}

	@Override
	public void init()
	{	
	}
	///////////
	@Override
	public void setParent(final IEntity parent)
	{
		this.parent = parent;
	}	
	public void setCollisionGroup(final int collisionGroup)
	{
		this.collisionGroup = collisionGroup;
	}
	public void setCollidingLeft(final boolean left)
	{
		this.left = left;
	}
	public void setCollidingRight(final boolean right)
	{
		this.right = right;
	}
	public void setCollidingTop(final boolean top)
	{
		this.top = top;
	}
	public void setCollidingBottom(final boolean bottom)
	{
		this.bottom = bottom;
	}
	
	//////////
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}
	public int getCollisionGroup()
	{
		return this.collisionGroup;
	}
	
	public boolean isCollidingLeft()
	{
		return left;
	}
	public boolean isCollidingRight()
	{
		return right;
	}
	public boolean isCollidingTop()
	{
		return top;
	}
	public boolean isCollidingBottom()
	{
		return bottom;
	}
	
	
}
