package infopacks;

import entities.IEntity;

/**
 * Provides an interface to deal with sending data to a system
 * @author Joseph Gefroh
 *
 */
public interface IInfoPack
{
	public IEntity getParent();
	
	/**
	 * Update the component references for this object.
	 * @return
	 */
	public boolean updateReferences();
}
