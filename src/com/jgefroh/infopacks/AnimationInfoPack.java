package com.jgefroh.infopacks;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Acts as an intermediary between the AnimationSystem and an entity's
 * AnimationComponent.
 * @author Joseph Gefroh
 *
 */
public class AnimationInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The owner of this AnimationInfoPack.*/
	private IEntity owner;
	
	/**The AnimationComponent belonging to the owner.*/
	private AnimationComponent ac;
	
	/**The RenderComponent belonging to the owner.*/
	private RenderComponent rc;
	
	
	
	/**
	 * Create a new AnimationInfoPack belonging to a specific entity.
	 * @param owner	the owner of this AnimationInfoPack
	 */
	public AnimationInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	@Override
	public boolean updateReferences()
	{
		ac = owner.getComponent(AnimationComponent.class);
		rc = owner.getComponent(RenderComponent.class);
		if(ac!=null&&rc!=null)
		{
			return true;
		}
		return false;
	}
	
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the owner of this AnimationInfoPack.
	 * @return	the owner of this AnimationInfoPack
	 */
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Get the time the animation was last updated, in milliseconds.
	 * @return
	 */
	public long getLastUpdateTime()
	{
		return ac.getLastUpdateTime();
	}
	
	/**
	 * Get the current frame number of the currently playing animation.
	 * @return	the current frame number
	 */
	public int getCurrentFrame()
	{
		return ac.getCurrentFrame();
	}
	
	/**
	 * Get the sprite that should be displayed.
	 * @return	the sprite
	 */
	public int getAnimationSprite()
	{
		return ac.getAnimationSprite();
	}
	
	/**
	 * Get the time to wait between frame updates.
	 * @return	the time to wait, in ms.
	 */
	public long getInterval()
	{
		return ac.getInterval();
	}
	
	/**
	 * Get the number of frames in the current animation.
	 * @return	the number of frames
	 */
	public int getNumberOfFrames()
	{
		return ac.getNumberOfFrames();
	}
	//////////
	// SETTERS
	//////////
	/**
	 * Set the time the animation was last updated
	 * @param updateTime	the long time the animation was last updated, in ms.
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		ac.setLastUpdateTime(updateTime);
	}
	
	/**
	 * Set the sprite index number on the render component.
	 * @param spriteIndex	the sprite index number of the render component
	 */
	public void setAnimationSprite(final int spriteID)
	{
		rc.setSpriteID(spriteID);
	}
	
	/**
	 * Set the current frame of the animation.
	 * @param currentFrame	the current frame of the animation
	 */
	public void setCurrentFrame(final int currentFrame)
	{
		ac.setCurrentFrame(currentFrame);
	}

}
