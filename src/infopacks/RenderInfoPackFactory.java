package infopacks;

import components.AnimationComponent;
import components.PositionComponent;
import components.RenderComponent;

import entities.IEntity;

public class RenderInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(PositionComponent.class)!=null)
		{
			return new RenderInfoPack(entity);
		}
		return null;
	}

}
