package infopacks;

import components.CollisionComponent;
import components.RenderComponent;
import components.TransformComponent;

import entities.IEntity;

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
