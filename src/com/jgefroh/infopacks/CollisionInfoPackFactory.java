package com.jgefroh.infopacks;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;


public class CollisionInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
			&&entity.getComponent(CollisionComponent.class)!=null)
		{
			return new CollisionInfoPack(entity);
		}
		return null;
	}

}
