package components;

import entities.IEntity;

public class PositionComponent implements IComponent
{
	private long id;
	private IEntity parent;
	private int localX;
	private int localY;
	private int localZ;
	private int globalX;
	private int globalY;
	private int globalZ;
	private int lastValidX;
	private int lastValidY;
	private boolean needsCheck;
	
	private PositionComponent()
	{
	}
	public PositionComponent(final IEntity parent)
	{
		setParent(parent);
	}
	public int getLocalX()
	{
		return this.localX;
	}
	public int getLocalY()
	{
		return this.localY;
	}
	public int getLocalZ()
	{
		return this.localZ;
	}	
	public int getGlobalX()
	{
		return this.globalX;
	}
	public int getGlobalY()
	{
		return this.globalY;
	}
	public int getGlobalZ()
	{
		return this.globalZ;
	}
	public boolean needsCheck()
	{
		return this.needsCheck;
	}
	
	public void setLocalX(final int localX)
	{
		this.lastValidX = this.localX;
		this.localX = localX;
	}
	public void setLocalY(final int localY)
	{
		this.localY = localY;
	}
	public void setLocalZ(final int localZ)
	{
		this.localZ = localZ;
	}
	
	public void setGlobalX(final int globalX)
	{
		this.globalX = globalX;
	}
	public void setGlobalY(final int globalY)
	{
		this.globalY = globalY;
	}
	public void setGlobalZ(final int globalZ)
	{
		this.globalZ = globalZ;
	}
	public void setNeedsCheck(final boolean needsCheck)
	{
		this.needsCheck = needsCheck;
	}
	
	public void rollback()
	{
		this.localX = this.lastValidX;
		this.localY = this.lastValidY;
	}
	
	public void approve()
	{
		this.lastValidX = this.localX;
		this.lastValidY = this.localY;
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
