package infopacks;

import components.CursorComponent;
import components.PositionComponent;

import entities.IEntity;

public class CursorInfoPack implements IInfoPack
{
	private IEntity parent;
	private CursorComponent cc;
	private PositionComponent pc;
	public CursorInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	
	public void setXPos(final int xPos)
	{
		pc.setLocalX(xPos);
	}
	public void setYPos(final int yPos)
	{
		pc.setLocalY(yPos);
	}
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		cc = parent.getComponent(CursorComponent.class);
		pc = parent.getComponent(PositionComponent.class);
		if(cc!=null&&pc!=null)
		{
			return true;
		}
		return false;
	}

}
