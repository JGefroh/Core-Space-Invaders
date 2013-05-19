package com.jgefroh.core;
//TODO: Remove

public interface IInfoPackFactory
{
	public <T extends IInfoPack>T generate(final IEntity entity);
}
