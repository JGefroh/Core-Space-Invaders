package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;


public class MovementInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			return new MovementInfoPack(entity);
		}
		return null;
	}

}
