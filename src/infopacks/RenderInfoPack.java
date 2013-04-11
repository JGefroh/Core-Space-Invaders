package infopacks;

import components.AnimationComponent;
import components.CollisionComponent;
import components.PositionComponent;
import components.RenderComponent;

import entities.IEntity;

/**
 * This class consolidates data from various components of an entity for easier
 * management and use by the render system.
 * @author Joseph Gefroh
 *
 */
public class RenderInfoPack implements IInfoPack
{
	private IEntity parent;
	private PositionComponent pc;
	private RenderComponent rc;
	
	public RenderInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	
	public int getXPos()
	{
		return pc.getGlobalX();
	}
	public int getYPos()
	{
		return pc.getGlobalY();
	}
	public int getZPos()
	{
		return pc.getGlobalZ();
	}
	public int getWidth()
	{
		return rc.getWidth();
	}
	public int getHeight()
	{
		return rc.getHeight();
	}
	public boolean isVisible()
	{
		return rc.isVisible();
	}
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}
	public int getSpriteIndex()
	{
		return rc.getSpriteIndex();
	}
	public int getTextureID()
	{
		return rc.getTextureID();
	}

	@Override
	public boolean updateReferences()
	{
		pc = parent.getComponent(PositionComponent.class);
		rc = parent.getComponent(RenderComponent.class);
		if(pc!=null&&rc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}
}
