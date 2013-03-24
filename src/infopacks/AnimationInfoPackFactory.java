package infopacks;

import components.AnimationComponent;
import components.RenderComponent;

import entities.IEntity;

public class AnimationInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(AnimationComponent.class)!=null
				&&entity.getComponent(RenderComponent.class)!=null)
		{
			return new AnimationInfoPack(entity);
		}
		return null;
	}

}
