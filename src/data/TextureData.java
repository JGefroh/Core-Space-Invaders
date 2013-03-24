package data;

import org.newdawn.slick.opengl.Texture;

/**
 * Contains data necessary to treat an image or texture as a sprite sheet.
 * It also calculates sprite locations within a given sheet.
 * @author Joseph Gefroh
 * TODO: Move calculation out of metadata.
 */
public class TextureData
{
	/**The number of columns of sprites this texture contains.*/
	private int numCols;
	
	/**The number of rows of sprites this texture contains.*/
	private int numRows;
	
	/**The total number of sprite cells on this sheet.*/
	private int numSprites;
	
	/**The texture this data is associated with.*/
	private Texture texture;
	
	/**The pixel width of the source image.*/
	private int imageWidth;	
	
	/**The pixel height of the source image.*/
	private int imageHeight;
	
	/**The converted power-of-2 width of the texture.*/
	private int textureWidth;

	/**The converted power-of-2 height of the texture.*/
	private int textureHeight;
	
	/**The normalized width of the texture (0<=width<=1)*/
	private float normalWidth;
	
	/**The normalized height of the texture (0<=height<=1)*/
	private float normalHeight;
	
	/**Contains the texture coordinates of each sprite on the sheet.*/
	private TextureCoordinateData[] spriteCoords;
	
	/**
	 * Create a new texture data object for a texture.
	 * @param texture	the Texture object this data is associated with
	 * @param numCols	the number of columns of sprites the texture has
	 * @param numRows	the number of rows of sprites the texture has
	 */
	public TextureData(final Texture texture, final int numCols, final int numRows)
	{
		this.texture = texture;
		this.normalWidth = texture.getWidth();
		this.normalHeight = texture.getHeight();
		this.numRows = numRows;
		this.numCols = numCols;
		this.numSprites = numRows*numCols;
		spriteCoords = new TextureCoordinateData[numSprites];
	}
	
	/**
	 * Store the texture coordinate data
	 * @param data	the data to store
	 */
	public void setCoords(final TextureCoordinateData data)
	{
		spriteCoords[data.getSpriteNum()] = data;
	}
	
	/**
	 * Set the coordinates for a specific sprite.
	 * @param spriteNum		the index number of the sprite
	 * @param xMin			the xMin texture coordinate
	 * @param xMax			the xMax texture coordinate
	 * @param yMin			the yMin texture coordinate
	 * @param yMax			the yMax texture coordinate
	 */
	public void setCoords(final int spriteNum, 
			final float xMin, final float xMax, 
			final float yMin, final float yMax)
	{
		if(isValidSpriteNum(spriteNum))
		{//If the sprite number is a valid sprite number
			if(spriteCoords[spriteNum]==null)
			{//If no coordinates have been entered so far
				spriteCoords[spriteNum] 
						= new TextureCoordinateData(spriteNum, xMin, xMax, yMin, yMax);
			}
			else
			{//Set the existing coordinates to the new coordinates
				spriteCoords[spriteNum].setCoords(xMin, xMax, yMin, yMax);
			}
		}
	}
	
	/**
	 * Set the number of columns the sprite sheet has.
	 * @param numCols	the number of columns the sheet has
	 */
	public void setNumCols(final int numCols)
	{
		this.numCols = numCols;
	}
	
	/**
	 * Set the number of rows of sprites the texture has.
	 * @param numRows	the number of rows the texture has
	 */
	public void setNumRows(final int numRows)
	{
		this.numRows = numRows;
	}
	
	/**
	 * Get the number of column of sprites the texture has
	 * @return	the number of columns of sprites the texture has
	 */
	public int getNumCols()
	{
		return this.numCols;
	}
	
	/**
	 * Get the number of rows of sprites the texture has
	 * @return	the number of rows of the sprites the texture has
	 */
	public int getNumRows()
	{
		return this.numRows;
	}
	
	/**
	 * Get the normal width of the texture
	 * @return	the normal width of the texture
	 */
	public float getNormalWidth()
	{
		return this.normalWidth;
	}
	
	/**
	 * Get the normal height of the texture
	 * @return	the normal height of the texture
	 */
	public float getNormalHeight()
	{
		return this.normalHeight;
	}
	
	
	/**
	 * Get the ID of the owner of this data.
	 * @return	get the ID of the owner of this data
	 */
	public int getTextureID()
	{
		return this.texture.getTextureID();
	}
	/**
	 * Get the coordinates for a given sprite number.
	 */
	public TextureCoordinateData getCoordinatesFor(final int spriteNum)
	{
		if(isValidSpriteNum(spriteNum))
		{
			return this.spriteCoords[spriteNum];
		}
		return null;
	}
	/**
	 * Check to see if the sprite number is a valid index.
	 * @param spriteNum	the number to check
	 * @return	true if valid, false otherwise.
	 */
	private boolean isValidSpriteNum(final int spriteNum)
	{
		if(spriteNum>=0&&spriteNum<spriteCoords.length)
		{
			return true;
		}
		return false;
	}
}
