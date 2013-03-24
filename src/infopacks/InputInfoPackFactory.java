package infopacks;

import components.InputComponent;

import entities.IEntity;

public class InputInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(InputComponent.class)!=null)
		{
			return new InputInfoPack(entity);
		}
		return null;
	}

}
