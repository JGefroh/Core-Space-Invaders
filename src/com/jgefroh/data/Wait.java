package com.jgefroh.data;



/**
 * @author Joseph Gefroh
 */
public class Wait
{
	//////////
	// DATA
	//////////
	private long startTime;
	private long timeToWait;
	private String response;
	
	//////////
	// INIT
	//////////
	/**
	 *
	 */
	public Wait(final String response, final long startTime, final long timeToWait)
	{
		this.response = response;
		this.startTime = startTime;
		this.timeToWait = timeToWait;
	}
	
	//////////
	// GETTERS
	//////////
	public String getResponse()
	{
		return this.response;
	}
	
	public long getStartTime()
	{
		return this.startTime;
	}
	
	public long getTimeToWait()
	{
		return this.timeToWait;
	}
	
	//////////
	// SETTER
	//////////
	public void setStartTime(final long startTime)
	{
		this.startTime = startTime;
	}
	public void setTimeToWait(final long timeToWait)
	{
		this.timeToWait = timeToWait;
	}
	//////////
	// METHODS
	//////////

}
