package infopacks;

import components.AIComponent;
import components.TransformComponent;

import entities.IEntity;

public class AIInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
				&&entity.getComponent(AIComponent.class)!=null)
		{
			return new AIInfoPack(entity);
		}
		return null;
	}

}
