package infopacks;

import components.CameraComponent;
import components.TransformComponent;

import entities.IEntity;

public class CameraInfoPack implements IInfoPack
{
	private IEntity parent;
	private TransformComponent tc;
	private CameraComponent cc;
	public CameraInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReferences();
	}
	
	public long getXPos()
	{
		return tc.getXPos();
	}
	
	public long getYPos()
	{
		return tc.getYPos();
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
		tc = parent.getComponent(TransformComponent.class);
		if(tc!=null&&cc!=null)
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
