package components;

import entities.IEntity;

/**
 * Contains all of the information necessary for the RenderSystem to render
 * this object on the screen.
 * @author Joseph Gefroh
 *
 */
public class RenderComponent implements IComponent
{
	/**The owner of this component.*/
	private IEntity parent;
	
	/**Flag to tell if the entity is visible or invisible.*/
	private boolean isVisible;
	
	/**The pixel WIDTH of the entity.*/
	private int width;
	
	/**The pixel HEIGHT of the entity.*/
	private int height;
	
	/**The absolute path to the image of the entity.*/
	private String texturePath;
	
	/**The texture ID assigned by the TextureSystem when it loaded the image.*/
	private int textureID;
	
	/**The index of the sprite used to render*/
	private int spriteIndex;
	
	/**
	 * Private constructor to prevent instantiation without a parent.
	 */
	private RenderComponent()
	{
		
	}
	
	/**
	 * Create a new Render component.
	 * @param parent	the IEntity owner of this component
	 */
	public RenderComponent(final IEntity parent, 
			final int spriteIndex,
			final boolean isVisible)
	{
		setParent(parent);
		setSpriteIndex(spriteIndex);
		setVisible(isVisible);
	}
	
	/**
	 * Get the sprite index of this entity.
	 * @return	the sprite index of this entity
	 */
	public int getSpriteIndex()
	{
		return this.spriteIndex;
	}
	
	/**
	 * Set the sprite index of the image being drawn.
	 * @param spriteIndex
	 */
	public void setSpriteIndex(final int spriteIndex)
	{
		this.spriteIndex = spriteIndex;
	}
	
	/**
	 * Get the pixel width of this entity.
	 * @return	the int width of this entity
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Get the pixel height of this entity.
	 * @return	the int height of this entity
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Set the pixel width of this entity.
	 * @param	the new width
	 */
	public void setWidth(final int width)
	{
		this.width = width;
	}
	
	/**
	 * Set the pixel height of this entity.
	 * @param	the new height
	 */
	public void setHeight(final int height)
	{
		this.height = height;
	}
	
	/**
	 * Set the visibility of this entity. Entities with visibility set to
	 * false will be ignored by the default render system.
	 * @param isVisible	the visibility
	 */
	public void setVisible(final boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	/**
	 * Return the visibility flag of this entity.
	 * @return	true if visible, false otherwise.
	 */
	public boolean isVisible()
	{
		return this.isVisible;
	}
	
	/**
	 * Set the ID of the texture.
	 * @param textureID	the integer ID of the texture
	 */
	public void setTextureID(final int textureID)
	{
		this.textureID = textureID;
	}
	
	/**
	 * Return the ID of the texture this entity uses.
	 * @return
	 */
	public int getTextureID()
	{
		
		return this.textureID;
	}
	
	/**
	 * Return the absolute path of the texture 
	 * @return
	 */
	public String getTexturePath()
	{
		return this.texturePath;
	}

	/**
	 * Set the absolute path of the texture.
	 * @param texturePath
	 */
	public void setTexturePath(final String texturePath)
	{
		this.texturePath = texturePath;
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
}
