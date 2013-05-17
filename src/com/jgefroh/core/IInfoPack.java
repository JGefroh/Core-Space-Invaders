package com.jgefroh.core;


/**
 * Provides an interface to deal with sending data to a system
 * @author Joseph Gefroh
 * @version 0.1.0
 * @since	16MAY13
 */
public interface IInfoPack
{
	/**Get the owner of this info pack.*/
	public IEntity getOwner();
	
	/**
	 * Update the component references for this object.
	 * @return
	 */
	public boolean updateReferences();
}
