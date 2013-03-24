package infopacks;

import components.CollisionComponent;
import components.PositionComponent;
import components.RenderComponent;

import entities.IEntity;

public class CollisionInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(RenderComponent.class)!=null
			&&entity.getComponent(PositionComponent.class)!=null
			&&entity.getComponent(CollisionComponent.class)!=null)
		{
			System.out.println("Made new CollisionInfoPack for " + entity);
			return new CollisionInfoPack(entity);
		}
		return null;
	}

}
