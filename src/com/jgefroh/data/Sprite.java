package com.jgefroh.data;


/**
 * Contains data related to a texture.
 * @author Joseph Gefroh
 */
public class Sprite
{
	//////////
	// DATA
	//////////
	/**The ID of the sprite.*/
	private int spriteID;
	
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	private float uMin;
	private float uMax;
	private float vMin;
	private float vMax;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new texture data object for a texture.
	 */
	public Sprite()
	{
	}
	
	//////////
	// GETTERS
	//////////
	public int getSpriteID()
	{
		return this.spriteID;
	}
	public int getXMin()
	{
		return this.xMin;
	}
	public int getXMax()
	{
		return this.xMax;
	}
	public int getYMin()
	{
		return this.yMin;
	}
	public int getYMax()
	{
		return this.yMax;
	}
	public float getUMin()
	{
		return this.uMin;
	}
	public float getUMax()
	{
		return this.uMax;
	}
	public float getVMin()
	{
		return this.vMin;
	}
	public float getVMax()
	{
		return this.vMax;
	}
	
	//////////
	// SETTERS
	//////////
	public void setSpriteID(final int spriteID)
	{
		this.spriteID = spriteID;
	}
	public void setXMin(final int xMin)
	{
		this.xMin = xMin;
	}
	public void setXMax(final int xMax)
	{
		this.xMax = xMax;
	}
	public void setYMin(final int yMin)
	{
		this.yMin = yMin;
	}
	public void setYMax(final int yMax)
	{
		this.yMax = yMax;
	}
	public void setUMin(final float uMin)
	{
		this.uMin = uMin;
	}
	public void setUMax(final float uMax)
	{
		this.uMax = uMax;
	}
	public void setVMin(final float vMin)
	{
		this.vMin = vMin;
	}
	public void setVMax(final float vMax)
	{
		this.vMax = vMax;
	}
	//////////
	// METHODS
	//////////
	public String toString()
	{
		return spriteID+","+xMin+","+xMax+","+yMin+","+yMax;
	}
}
