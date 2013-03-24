package infopacks;

import components.CollisionComponent;
import components.PositionComponent;
import components.RenderComponent;

import entities.IEntity;

public class CollisionInfoPack implements IInfoPack
{
	private IEntity parent;
	private PositionComponent pc;
	private RenderComponent rc;
	private CollisionComponent cc;
	public CollisionInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReferences();
	}
	
	public int getXMin()
	{
		return pc.getLocalX();
	}
	
	public int getYMin()
	{
		return pc.getLocalY();
	}
	
	public int getXMax()
	{
		return pc.getLocalX()+rc.getWidth();
	}
	public int getYMax()
	{
		return pc.getLocalY()+rc.getHeight();
	}
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		pc = parent.getComponent(PositionComponent.class);
		cc = parent.getComponent(CollisionComponent.class);
		rc = parent.getComponent(RenderComponent.class);
		if(pc!=null&&cc!=null&&rc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}

	public int getGroup()
	{
		return cc.getGroup();
	}
	public boolean collidesWith(final int group)
	{
		return cc.collidesWith(group);
	}

	public boolean needsCheck()
	{
		return pc.needsCheck();
	}

	public void rollback()
	{
		pc.rollback();
	}
	public void setNeedsCheck(final boolean needsCheck)
	{
		pc.setNeedsCheck(false);
	}
	public void approve()
	{
		pc.approve();
		setNeedsCheck(false);
	}

}
