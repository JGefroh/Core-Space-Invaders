package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;


public class BulletInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getName().equalsIgnoreCase("bullet")
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new BulletInfoPack(entity);
		}
		return null;
	}

}
