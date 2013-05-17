package com.jgefroh.core;

/**
 * The interface for Systems used by the Core.
 * @author Joseph Gefroh
 *
 */
public interface ISystem
{
	/**
	 * Initialize the system.
	 * This is called automatically when the Core begins tracking the system.
	 */
	public void init();
	
	/**
	 * Mark the system as runnable.
	 * This is called automatically when the Core begins tracking the system.
	 */
	public void start();
	
	/**
	 * Execute the system's work if runnable.
	 * This is called automatically every time the Core does work.
	 */
	public void work();
	
	/**
	 * Mark the system as not runnable.
	 * This is called automatically when the Core stops tracking the system.
	 */
	public void stop();
}
