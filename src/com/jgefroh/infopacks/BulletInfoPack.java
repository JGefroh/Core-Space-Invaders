package com.jgefroh.infopacks;

import com.jgefroh.components.BulletComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class BulletInfoPack implements IInfoPack
{
	private IEntity parent;
	private TransformComponent tc;
	private BulletComponent bc;
	
	public BulletInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public IEntity getOwner()
	{
		return this.parent;
	}	
	@Override
	public boolean updateReferences()
	{
		tc = parent.getComponent(TransformComponent.class);
		bc = parent.getComponent(BulletComponent.class);
		if(bc!=null&&tc!=null)
		{
			return true;
		}
		return false;
	}
	////////////////////
	
	public int getXPos()
	{
		return tc.getXPos();
	}
	
	public int getYPos()
	{
		return tc.getYPos();
	}
	
	public IEntity getBulletOwner()
	{
		return bc.getBulletOwner();
	}

}
