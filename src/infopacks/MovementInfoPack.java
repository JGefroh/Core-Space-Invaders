package infopacks;

import components.PositionComponent;
import components.VelocityComponent;

import entities.IEntity;

public class MovementInfoPack implements IInfoPack
{
	private IEntity parent;
	private PositionComponent pc;
	private VelocityComponent vc;
	
	public MovementInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReference();
	}
	public IEntity getParent()
	{
		return this.parent;
	}
	public int getPosX()
	{
		return pc.getGlobalX();
	}
	public int getPosY()
	{
		return pc.getGlobalY();
	}
	public int getVelocityX()
	{
		return vc.getXVelocity();
	}
	public int getVelocityY()
	{
		return vc.getYVelocity();
	}
	public boolean isMoveRequested()
	{
		return vc.isMoveRequested();
	}
	public void setMoveRequested(final boolean setMoveRequested)
	{
		vc.setMoveRequested(setMoveRequested);
	}
	public void setNeedsCheck(final boolean needsCheck)
	{
		pc.setNeedsCheck(needsCheck);
	}
	public boolean needsCheck()
	{
		return pc.needsCheck();
	}
	public void setYVelocity(final int yVelocity)
	{
		vc.setYVelocity(yVelocity);
	}
	public void setXVelocity(final int xVelocity)
	{
		vc.setXVelocity(xVelocity);
	}
	public void setPosX(final int posX)
	{
		pc.setGlobalX(posX);
	}
	public void setPosY(final int posY)
	{
		pc.setGlobalY(posY);
	}
	public void setPos(final int posX, final int posY)
	{
		setPosX(posX);
		setPosY(posY);
	}
	public boolean updateReference()
	{
		pc = parent.getComponent(PositionComponent.class);
		vc = parent.getComponent(VelocityComponent.class);
		if(pc!=null&&vc!=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean updateReferences()
	{
		return true;
	}
}
