package data;

public class TextureCoordinateData
{
	private float xMin;
	private float xMax;
	private float yMin;
	private float yMax;
	private int spriteNum;
	
	public TextureCoordinateData()
	{	
	}
	public TextureCoordinateData(final int spriteNum, 
			final float xMin, final float xMax,
			final float yMin, final float yMax)
	{
		this.spriteNum = spriteNum;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	public float getXMin()
	{
		return this.xMin;
	}
	
	public float getXMax()
	{
		return this.xMax;
	}
	
	public float getYMin()
	{
		return this.yMin;
	}
	
	public float getYMax()
	{
		return this.yMax;
	}
	
	public int getSpriteNum()
	{
		return this.spriteNum;
	}
	
	public void setXMin(final float xMin)
	{
		this.xMin = xMin;
	}
	
	public void setXMax(final float xMax)
	{
		this.xMax = xMax;
	}
	
	public void setYMin(final float yMin)
	{
		this.yMin = yMin;
	}
	
	public void setYMax(final float yMax)
	{
		this.yMax = yMax;
	}
	
	public void setSpriteNum(final int spriteNum)
	{
		this.spriteNum = spriteNum;
	}
	public void setCoords(final float xMin, final float xMax,
			final float yMin, final float yMax)
	{
		setXMin(xMin);
		setXMax(xMax);
		setYMin(yMin);
		setYMax(yMax);
	}
	
}
