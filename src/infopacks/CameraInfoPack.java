package infopacks;

import components.CameraComponent;
import components.CollisionComponent;
import components.PositionComponent;
import components.RenderComponent;

import entities.IEntity;

public class CameraInfoPack implements IInfoPack
{
	private IEntity parent;
	private PositionComponent pc;
	private CameraComponent cc;
	public CameraInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReferences();
	}
	
	public int getXPos()
	{
		return pc.getGlobalX();
	}
	
	public int getYPos()
	{
		return pc.getGlobalY();
	}
	
	public int getHeight()
	{
		return cc.getHeight();
	}
	
	public int getWidth()
	{
		return cc.getWidth();
	}
	
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		cc = parent.getComponent(CameraComponent.class);
		pc = parent.getComponent(PositionComponent.class);
		if(pc!=null&&cc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}

	public boolean isActive()
	{
		return cc.isActive();
	}
}
