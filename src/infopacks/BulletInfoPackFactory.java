package infopacks;

import components.TransformComponent;

import entities.IEntity;

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
