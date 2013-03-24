package components;

import java.util.ArrayList;
import java.util.HashMap;

import data.AnimationData;

import entities.IEntity;

public class AnimationComponent implements IComponent
{
	/**The types of loop that an animation can perform.*/
	public enum LoopType {LOOPDISABLED, LOOPFROMSTART, LOOPSCAN}
	
	/**The owner of this component.*/
	private IEntity parent;
	
	/**The ms time the animation was last updated.*/
	private long lastUpdateTime = 0;
	
	/**The name of the animation that is currently being played.*/
	private String currentAnimation;
	
	/**The name of the animation that was previously playing.*/
	private String previousAnimation;
	
	/**The current frame of the animation.*/
	private int currentFrame;
	
	/**FLAG: indicates whether the animation is currently playing or not.*/
	private boolean isAnimating = true;
	
	/**FLAG: indicates whether the animation is playing backwards or not.*/
	private boolean isReversing = false;
	
	/**Holds all of the animation definitions for this entity.*/
	private HashMap<String, AnimationData> animations;
	////////////////////////////////////
	
	/**
	 * Create a new animation component.
	 * @param entity	the owner of this component
	 * @param now		the current time
	 */
	public AnimationComponent(final IEntity entity, final long now)
	{	
		setParent(entity);
		setLastUpdateTime(now);
		animations = new HashMap<String, AnimationData>();
	}
	
	/**
	 * Advance to the next frame of the animation.
	 */
	public void nextFrame()
	{
		if(isReversing==false)
		{			
			this.currentFrame++;
		}
		else
		{
			this.currentFrame--;
		}
	}

	/**
	 * Define an animation to play.
	 * @param animationName		the unique name of the animation
	 * @param frames			the ordered sprite indexes of the animation
	 * @param delay				the time to wait inbetween frames
	 * @param loopType			the kind of looping to perform at the end
	 * @return					the Animation
	 */
	private AnimationData defineAnimation(final String animationName, final ArrayList<Integer> frames, final int delay, final LoopType loopType)
	{
		AnimationData ad = new AnimationData();
		ad.setName(animationName);
		ad.setLoopType(loopType);
		ad.setFrameDelay(delay);
		ad.setFrames(frames);
		return ad;
	}
	
	public void addAnimation(final String animationName, final ArrayList<Integer> frames, final int delay, final LoopType loopType)
	{
		animations.put(animationName, defineAnimation(animationName, frames, delay, loopType));
		if(currentAnimation==null)
		{
			currentAnimation = animationName;
		}
	}
	
	/**
	 * Get the sprite index for the current frame of the current animation.
	 * @return	the int sprite index
	 */
	public int getFrameSprite()
	{
		return animations.get(currentAnimation).getFrameSprite(currentFrame);
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
	
	public int getCurrentFrame()
	{
		return this.currentFrame;
	}

	public long getLastUpdateTime()
	{
		return this.lastUpdateTime;
	}


	public int getMaxFrame()
	{
		return animations.get(currentAnimation).getMaxFrameNumber()-1;
	}
	
	public long getFrameDelay()
	{
		return animations.get(currentAnimation).getFrameDelay();
	}
	
	public String getCurrentAnimation()
	{
		return this.currentAnimation;
	}
	public boolean isAnimating()
	{
		return this.isAnimating;
	}
	
	public boolean isReversing()
	{
		return this.isReversing;
	}
	public LoopType getLoopType()
	{
		return this.animations.get(currentAnimation).getLoopType();
	}

	public void setAnimating(final boolean isAnimating)
	{
		this.isAnimating = isAnimating;
	}
	public void setCurrentFrame(final int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	public void setReversing(final boolean isReversing)
	{
		this.isReversing = isReversing;
	}
	
	public boolean isValidAnimation(final String animationName)
	{//TODO: check if null check required (use null key?)
		if(animations.get(animationName)!=null)
		{
			return true;
		}
		return false;
	}
	
	
	
	/////////////////////////////////////////////////////
	
	public void setCurrentAnimation(final String currentAnimation)
	{
		if(isValidAnimation(currentAnimation))
		{			
			this.previousAnimation = this.currentAnimation;
			this.currentAnimation = currentAnimation;
		}
	}


	public void setLastUpdateTime(final long lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

}
