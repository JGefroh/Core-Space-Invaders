package com.jgefroh.infopacks;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * Intended to be used by the CollisionSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * CollisionComponent
 * 
 * @author Joseph Gefroh
 */
public class CollisionInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private CollisionComponent cc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public CollisionInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}

	
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			cc = owner.getComponent(CollisionComponent.class);
			tc = owner.getComponent(TransformComponent.class);			
			if(tc==null||cc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	/**
	 * @see CollisionComponent#getCollisionGroup()
	 */
	public int getGroup()
	{
		return cc.getCollisionGroup();
	}
	
	/**
	 * @see TransformComponent#getXPos()
	 */
	public int getXPos()
	{
		return tc.getXPos();
	}
	
	/**
	 * @see TransformComponent#getYPos()
	 */
	public int getYPos()
	{
		return tc.getYPos();
	}
	
	/**
	 * @see TransformComponent#getLastXPos()
	 */
	public int getLastXPos()
	{
		return tc.getLastXPos();
	}
	
	/**
	 * @see TransformComponent#getLastYPos()
	 */
	public int getLastYPos()
	{
		return tc.getLastYPos();
	}
	
	/**
	 * @see TransformComponent#getWidth()
	 */
	public int getWidth()
	{
		return tc.getWidth();
	}
	
	/**
	 * @see TransformComponent#getHeight()
	 */
	public int getHeight()
	{
		return tc.getHeight();
	}
	
	/**
	 * @see CollisionComponent#isCollidingTop()
	 */
	public boolean isCollidingTop()
	{
		return cc.isCollidingTop();
	}
	
	/**
	 * @see CollisionComponent#isCollidingBottom()
	 */
	public boolean isCollidingBottom()
	{
		return cc.isCollidingBottom();
	}
	
	/**
	 * @see CollisionComponent#isCollidingLeft()
	 */
	public boolean isCollidingLeft()
	{
		return cc.isCollidingLeft();
	}
	
	/**
	 * @see CollisionComponent#isCollidingRight()
	 */
	public boolean isCollidingRight()
	{
		return cc.isCollidingRight();
	}

	
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	/**
	 * @see TransformComponent#setXPos(int)
	 */
	public void setXPos(final int xPos)
	{
		tc.setXPos(xPos);
	}
	
	/**
	 * @see TransformComponent#setYPos(int)
	 */
	public void setYPos(final int yPos)
	{
		tc.setYPos(yPos);
	}
	
	/**
	 * @see CollisionComponent#setCollidingTop(boolean)
	 */
	public void setCollidingTop(final boolean top)
	{
		cc.setCollidingTop(top);
	}
	
	/**
	 * @see CollisionComponent#setCollidingBottom(boolean)
	 */
	public void setCollidingBottom(final boolean bottom)
	{
		cc.setCollidingBottom(bottom);
	}
	
	/**
	 * @see CollisionComponent#setCollidingLeft(boolean)
	 */
	public void setCollidingLeft(final boolean left)
	{
		cc.setCollidingLeft(left);
	}
	
	/**
	 * @see CollisionComponent#setCollidingRight(boolean)
	 */
	public void setCollidingRight(final boolean right)
	{
		cc.setCollidingRight(right);
	}
}