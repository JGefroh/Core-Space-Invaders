package components;

import java.util.ArrayList;

import entities.IEntity;

/**
 * This class contains all of the data necessary for an entity
 * to react to input.
 * @author Joseph Gefroh
 */
public class InputComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Contains all of the commands this entity responds to.*/
	private ArrayList<String> commandList; //TODO: Do without array list?

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new InputComponent.
	 * @param owner	the IEntity owner of this component
	 */
	public InputComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		commandList = new ArrayList<String>();
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
	 * Check to see if the entity is interested in the input command.
	 * @param command	the String command of the input
	 * @return	true if it is interested, false otherwise.
	 */
	public boolean checkInterested(final String command)
	{
		if(commandList.contains(command))
		{
			return true;
		}
		return false;
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
	 * Make the entity respond to the command.
	 * @param command the input command
	 */
	public void setInterested(final String command)
	{
		if(checkInterested(command)==false)
		{
			commandList.add(command);
		}
	}
	
	/**
	 * Make the entity ignore the command if it is not already.
	 * @param command the input command
	 */
	public void setUninterested(final String command)
	{
		commandList.remove(command);
	}
}
