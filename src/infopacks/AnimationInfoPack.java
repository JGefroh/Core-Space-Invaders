package infopacks;

import components.AnimationComponent;
import components.AnimationComponent.LoopType;
import components.RenderComponent;

import entities.IEntity;

/**
 * Acts as an intermediary between the AnimationSystem and an entity's
 * AnimationComponent.
 * @author Joseph Gefroh
 *
 */
public class AnimationInfoPack implements IInfoPack
{
	/**The parent/owner of this AnimationInfoPack.*/
	private IEntity parent;
	
	/**The AnimationComponent belonging to the parent.*/
	private AnimationComponent ac;
	
	/**The RenderComponent belonging to the parent.*/
	private RenderComponent rc;
	
	/**
	 * Create a new AnimationInfoPack belonging to a specific entity.
	 * @param parent	the owner of this AnimationInfoPack
	 */
	public AnimationInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Get the parent of this AnimationInfoPack.
	 * @return	the parent of this AnimationInfoPack
	 */
	public IEntity getParent()
	{
		return this.parent;
	}
	
	/**
	 * Check to see if the parent is animating.
	 * @return	true if animating, false otherwise.
	 */
	public boolean isAnimating()
	{
		return ac.isAnimating();
	}
	
	/**
	 * Check to see if the parent's animation is reversing.
	 * @return true if reversing, false otherwise.
	 */
	public boolean isReversing()
	{
		return ac.isReversing();
	}
	
	/**
	 * Get the time between frames for the currently played animation.
	 * @return	the time between frames, in milliseconds.
	 */
	public long getFrameDelay()
	{
		return ac.getFrameDelay();
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
	 * Get the highest frame number of the currently playing animation.
	 * @return	the highest frame number
	 */
	public int getMaxFrame()
	{
		return ac.getMaxFrame();
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
	 * Set the time the animation was last updated
	 * @param updateTime	the long time the animation was last updated, in ms.
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		ac.setLastUpdateTime(updateTime);
	}
	
	/**
	 * Get the sprite associated with the current frame of the animation.
	 * @return	the sprite index number of the current animation frame.
	 */
	public int getFrameSprite()
	{
		return ac.getFrameSprite();
	}
	
	/**
	 * Set the sprite index number on the render component.
	 * @param spriteIndex	the sprite index number of the render component
	 */
	public void setSpriteIndex(final int spriteIndex)
	{
		rc.setSpriteIndex(spriteIndex);
	}
	
	/**
	 * Advance to the next frame.
	 */
	public void nextFrame()
	{
		ac.nextFrame();
	}
	
	/**
	 * Get the loop type.
	 * @return	the loop type of the animation
	 */
	public LoopType getLoopType()
	{
		return ac.getLoopType();
	}
	
	/**
	 * Set whether the animation is reversing or not.
	 * @param isReversing
	 */
	public void setReversing(final boolean isReversing)
	{
		ac.setReversing(isReversing);
	}
	
	@Override
	public boolean updateReferences()
	{
		ac = parent.getComponent(AnimationComponent.class);
		rc = parent.getComponent(RenderComponent.class);
		if(ac!=null&&rc!=null)
		{
			return true;
		}
		return false;
	}
	
	public void setAnimating(final boolean isAnimating)
	{
		ac.setAnimating(isAnimating);
	}
	public void setCurrentFrame(final int currentFrame)
	{
		ac.setCurrentFrame(0);
	}

}
