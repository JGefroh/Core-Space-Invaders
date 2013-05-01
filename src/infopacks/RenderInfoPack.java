package infopacks;

import components.RenderComponent;
import components.TransformComponent;

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
	private TransformComponent tc;
	private RenderComponent rc;
	
	public RenderInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	
	public long getXPos()
	{
		return tc.getXPos();
	}
	public long getYPos()
	{
		return tc.getYPos();
	}
	public long getZPos()
	{
		return tc.getZPos();
	}
	public int getWidth()
	{
		return tc.getWidth();
	}
	public int getHeight()
	{
		return tc.getHeight();
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
		tc = parent.getComponent(TransformComponent.class);
		rc = parent.getComponent(RenderComponent.class);
		if(tc!=null&&rc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}
}
