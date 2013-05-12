package infopacks;

import entities.IEntity;

/**
 * Provides an interface to deal with sending data to a system
 * @author Joseph Gefroh
 *
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
