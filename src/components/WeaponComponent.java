package components;

import entities.IEntity;

/**
 * This class contains information related to the firing rate and readiness
 * of an entity's weapons - SPACE INVADERS SPECIFIC
 * @author Joseph Gefroh
 */
public class WeaponComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;
	
	/**Flag to determine if the weapon is ready to be fired.*/
	private boolean isReady;
	
	/**Flag to determine if there is a request to fire the weapon.*/
	private boolean isFireRequested;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new WeaponComponent
	 * @param owner	the owner of the component
	 */
	public WeaponComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{	
		setReady(true);
		setFireRequested(false);
	}

	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * See if the weapon is ready to be fired.
	 * @return	true if the weapon is ready to be fired, false otherwise.
	 */
	public boolean isReady()
	{
		return isReady;
	}
	
	/**
	 * See if there is a request to fire the weapon.
	 * @return	true if there is a fire request, false otherwise.
	 */
	public boolean isFireRequested()
	{
		return isFireRequested;
	}	
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the flag that determines whether the weapon is ready to be fired.
	 * @param isReady	true if the weapon is ready to be fired, false if not
	 */
	public void setReady(final boolean isReady)
	{
		this.isReady = isReady;
	}
	
	/***
	 * Set the flag that determines whether there is a fire request.
	 * @param isFireRequested	true if there is a fire request, false if not
	 */
	public void setFireRequested(final boolean isFireRequested)
	{
		this.isFireRequested = isFireRequested;
	}
}
