package com.jgefroh.infopacks;

import com.jgefroh.core.IEntity;

public interface IInfoPackFactory
{
	public <T extends IInfoPack>T generate(final IEntity entity);
}
