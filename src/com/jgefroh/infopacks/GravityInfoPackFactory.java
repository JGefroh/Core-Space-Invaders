package com.jgefroh.infopacks;

import com.jgefroh.components.GravityComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;


public class GravityInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(GravityComponent.class)!=null
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			return new GravityInfoPack(entity);
		}
		return null;
	}

}
