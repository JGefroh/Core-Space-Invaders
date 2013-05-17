package com.jgefroh.core;


public interface IInfoPackFactory
{
	public <T extends IInfoPack>T generate(final IEntity entity);
}
