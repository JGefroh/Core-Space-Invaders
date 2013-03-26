package components;

import entities.IEntity;

public class CameraComponent implements IComponent
{
	private IEntity parent;
	
	/**Determines whether the camera is being used at the moment.*/
	private boolean isActive;
	
	
	/**The X dimension of the camera.*/
	private int width;
	
	/**The Y dimension of the camera.*/
	private int height;
	
	public CameraComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}
	
	public void setActive(final boolean isActive)
	{
		this.isActive = isActive;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void setWidth(final int width)
	{
		this.width = width;
	}
	
	public void setHeight(final int height)
	{
		this.height = height;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(final IEntity parent)
	{
		this.parent = parent;
	}

	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

}
