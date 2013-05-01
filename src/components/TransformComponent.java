package components;

import entities.IEntity;

/**
 * This class contains data related to the object's positioning, rotation,
 * and scale.
 * @author Joseph Gefroh
 */
public class TransformComponent implements IComponent
{
	private IEntity parent;
	
	/**The global X position of the object.*/
	private int xPos;
	
	/**The global Y position of the object.*/
	private int yPos;
	
	/**The global Z position of the object.*/
	private int zPos;
	
	/**The last global X position of the object.*/
	private int lastXPos;
	
	/**The last global Y position of the object.*/
	private int lastYPos;
	
	/**The last global Z position of the object.*/
	private int lastZPos;
	
	/**The global width of the object.*/
	private int width;
	
	/**The global height of the object.*/
	private int height;

	
	public TransformComponent(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public void init()
	{	
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
	
	////////////////////
	public int getXPos()
	{
		return this.xPos;
	}
	public int getYPos()
	{
		return this.yPos;
	}
	public int getZPos()
	{
		return this.zPos;
	}
	public int getLastXPos()
	{
		return this.lastXPos;
	}
	public int getLastYPos()
	{
		return this.lastYPos;
	}
	public int getLastZPos()
	{
		return this.lastZPos;
	}
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}
	

	//////////
	public void setXPos(final int xPos)
	{
		this.lastXPos = this.xPos;
		this.xPos = xPos;
	}
	public void setYPos(final int yPos)
	{
		this.lastYPos = this.yPos;
		this.yPos = yPos;
	}
	public void setZPos(final int zPos)
	{
		this.lastZPos = this.zPos;
		this.zPos = zPos;
	}
	public void setWidth(final int width)
	{
		this.width = width;
	}
	public void setHeight(final int height)
	{
		this.height = height;
	}

}
