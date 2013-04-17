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
	private long xPos;
	
	/**The global Y position of the object.*/
	private long yPos;
	
	/**The Z position of the object.*/
	private long zPos;
	
	/**The rotation of the object on the X axis.*/
	private long xRot;
	
	/**The rotation of the object on the Y axis.*/
	private long yRot;
	
	/**The rotation of the object on the Z axis.*/
	private long zRot;
	
	/**The scale of the object's size on the X axis.*/
	private long xScale;
	
	/**The scale of the object's size on the Y axis.*/
	private long yScale;
	
	/**The scale of the object's size on the Z axis.*/
	private long zScale;
	
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
	public long getXPos()
	{
		return this.xPos;
	}
	public long getYPos()
	{
		return this.yPos;
	}
	public long getZPos()
	{
		return this.zPos;
	}
	public long getXRot()
	{
		return this.xRot;
	}
	public long getYRot()
	{
		return this.yRot;
	}
	public long getZRot()
	{
		return this.zRot;
	}
	public long getXScale()
	{
		return this.xScale;
	}
	public long getYScale()
	{
		return this.yScale;
	}
	public long getZScale()
	{
		return this.zScale;
	}

	//////////
	public void setXPos(final long xPos)
	{
		this.xPos = xPos;
	}
	public void setYPos(final long yPos)
	{
		this.yPos = yPos;
	}
	public void setZPos(final long zPos)
	{
		this.zPos = zPos;
	}
	public void setXRot(final long xRot)
	{
		this.xRot = xRot;
	}
	public void setYRot(final long yRot)
	{
		this.yRot = yRot;
	}
	public void setZRot(final long zRot)
	{
		this.zRot = zRot;
	}
	public void setXScale(final long xScale)
	{
		this.xScale = xScale;
	}
	public void setYScale(final long yScale)
	{
		this.yScale = yScale;
	}
	public void setZScale(final long zScale)
	{
		this.zScale = zScale;
	}

}
