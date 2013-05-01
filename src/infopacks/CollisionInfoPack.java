package infopacks;

import components.CollisionComponent;
import components.RenderComponent;
import components.TransformComponent;

import entities.IEntity;

public class CollisionInfoPack implements IInfoPack
{
	private IEntity parent;
	private TransformComponent tc;
	private CollisionComponent cc;
	public CollisionInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReferences();
	}
	
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		tc = parent.getComponent(TransformComponent.class);
		cc = parent.getComponent(CollisionComponent.class);
		if(tc!=null&&cc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}
	////////////////////
	public int getGroup()
	{
		return cc.getCollisionGroup();
	}
	public int getXPos()
	{
		return tc.getXPos();
	}
	public int getYPos()
	{
		return tc.getYPos();
	}
	public int getLastXPos()
	{
		return tc.getLastXPos();
	}
	public int getLastYPos()
	{
		return tc.getLastYPos();
	}
	public int getWidth()
	{
		return tc.getWidth();
	}
	public int getHeight()
	{
		return tc.getHeight();
	}
	public boolean isCollidingTop()
	{
		return cc.isCollidingTop();
	}
	public boolean isCollidingBottom()
	{
		return cc.isCollidingBottom();
	}
	public boolean isCollidingLeft()
	{
		return cc.isCollidingLeft();
	}
	public boolean isCollidingRight()
	{
		return cc.isCollidingRight();
	}
	////////////////////
	public void setXPos(final int xPos)
	{
		tc.setXPos(xPos);
	}
	public void setYPos(final int yPos)
	{
		tc.setYPos(yPos);
	}
	public void setCollidingTop(final boolean top)
	{
		cc.setCollidingTop(top);
	}
	public void setCollidingBottom(final boolean bottom)
	{
		cc.setCollidingBottom(bottom);
	}
	public void setCollidingLeft(final boolean left)
	{
		cc.setCollidingLeft(left);
	}
	public void setCollidingRight(final boolean right)
	{
		cc.setCollidingRight(right);
	}
}
