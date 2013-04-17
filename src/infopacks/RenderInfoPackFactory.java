package infopacks;

import components.RenderComponent;
import components.TransformComponent;

import entities.IEntity;

public class RenderInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new RenderInfoPack(entity);
		}
		return null;
	}

}
