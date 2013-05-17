package com.jgefroh.components;

import java.util.ArrayList;
import java.util.HashMap;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.AnimationData;



/**
 * This class contains data necesary to keep track of the current animation.
 * @author Joseph Gefroh
 */
public class AnimationComponent implements IComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The types of loop that an animation can perform.*/
	public enum LoopType {LOOPDISABLED, LOOPFROMSTART, LOOPSCAN}
	
	/**The owner of this component.*/
	private IEntity owner;
	
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

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new animation component.
	 * @param entity	the owner of this component
	 */
	public AnimationComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
		animations = new HashMap<String, AnimationData>();
	}
	
	
	//////////
	// METHODS
	//////////
	/**
	 * Check to see if the given animation name is a valid animation.
	 * @param animationName	the name of the animation to check
	 * @return
	 */
	public boolean isValidAnimation(final String animationName)
	{//TODO: check if null check required (use null key?)
		if(animations.get(animationName)!=null)
		{
			return true;
		}
		return false;
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
	 * Create an animation.
	 * @param animationName	the unique name of the animation
	 * @param frames		the ordered sprite indexes of the animation
	 * @param delay			the time to wait in between frames
	 * @param loopType		the kind of looping to perform at the end
	 */
	public void addAnimation(final String animationName, 
								final ArrayList<Integer> frames,
								final int delay, final LoopType loopType)
	{
		animations.put(animationName, defineAnimation(animationName, frames, delay, loopType));
		if(currentAnimation==null)
		{
			currentAnimation = animationName;
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
	private AnimationData defineAnimation(final String animationName,
											final ArrayList<Integer> frames,
											final int delay,
											final LoopType loopType)
	{
		AnimationData ad = new AnimationData();
		ad.setName(animationName);
		ad.setLoopType(loopType);
		ad.setFrameDelay(delay);
		ad.setFrames(frames);
		return ad;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Get the number of the current animation frame.
	 * @return	the number of the current animation frame
	 */
	public int getCurrentFrame()
	{
		return this.currentFrame;
	}
	
	/**
	 * Get the time the component was last updated.
	 * @return	the time the component was last updated, in ms
	 */
	public long getLastUpdateTime()
	{
		return this.lastUpdateTime;
	}
	
	/**
	 * Get the maximum frame number of the current animation.
	 * @return	the highest frame number of the current animation
	 */
	public int getMaxFrame()
	{
		return animations.get(currentAnimation).getMaxFrameNumber()-1;
	}
	
	/**
	 * Get the time to wait before changing frames.
	 * @return	the time to wait between frames, in ms
	 */
	public long getFrameDelay()
	{
		return animations.get(currentAnimation).getFrameDelay();
	}
	
	/**
	 * Get the name of the current animation.
	 * @return	the name of the current animation
	 */
	public String getCurrentAnimation()
	{
		return this.currentAnimation;
	}
	
	/**
	 * Get whether the component is currently animating.
	 * @return	true if the component is current animating, false otherwise
	 */
	public boolean isAnimating()
	{
		return this.isAnimating;
	}
	
	/**
	 * Get whether the animation is playing forward or in reverse.
	 * @return	true if the animation is playing forward, false if reverse
	 */
	public boolean isReversing()
	{
		return this.isReversing;
	}
	public LoopType getLoopType()
	{
		return this.animations.get(currentAnimation).getLoopType();
	}
	
	/**
	 * Get the sprite index for the current frame of the current animation.
	 * @return	the int sprite index
	 */
	public int getFrameSprite()
	{
		return animations.get(currentAnimation).getFrameSprite(currentFrame);
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the animation flag.
	 * @param isAnimating	true if the animation is playing, false otherwise
	 */
	public void setAnimating(final boolean isAnimating)
	{
		this.isAnimating = isAnimating;
	}
	
	/**
	 * Set the current frame of the animation.
	 * @param currentFrame	the current frame number of the animation
	 */
	public void setCurrentFrame(final int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	
	/**
	 * Set the reversal flag
	 * @param isReversing true if playing in reverse, false otherwise
	 */
	public void setReversing(final boolean isReversing)
	{
		this.isReversing = isReversing;
	}
	
	/**
	 * Set the current animation that is playing.
	 * @param currentAnimation the name of the current animation
	 */
	public void setCurrentAnimation(final String currentAnimation)
	{
		if(isValidAnimation(currentAnimation))
		{			
			this.previousAnimation = this.currentAnimation;
			this.currentAnimation = currentAnimation;
		}
	}
	
	/**
	 * set the time the animation was last updated
	 * @param lastUpdateTime the time the animation was last updated, in ms
	 */
	public void setLastUpdateTime(final long lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
}
