package systems;

/**
 * The interface for Systems used by the Core.
 * @author Joseph Gefroh
 *
 */
public interface ISystem
{
	/**
	 * Set up the system. 
	 * This is called automatically when the Core begins tracking the system.
	 */
	public void start();
	
	/**
	 * Execute the system's work.
	 * This is called automatically every time the Core does work.
	 */
	public void work();
	
	/**
	 * Stop the system.
	 * This is called automatically when the Core stops tracking the system.
	 */
	public void stop();
}
