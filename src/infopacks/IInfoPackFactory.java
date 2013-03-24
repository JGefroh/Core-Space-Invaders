package infopacks;

import entities.IEntity;

public interface IInfoPackFactory
{
	public <T extends IInfoPack>T generate(final IEntity entity);
}
