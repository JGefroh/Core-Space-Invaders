package systems;

import infopacks.HealthInfoPack;

import java.util.logging.Level;
import java.util.logging.Logger;

import entities.IEntity;

/**
 * @author Joseph Gefroh
 */
public class DamageSystem implements ISystem
{
	private Core core;
	
	//////////
	private final static Logger LOGGER 
		= Logger.getLogger(DamageSystem.class.getName());
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
	
	//////////
	public DamageSystem(final Core core)
	{
		this.core = core;
		initLogger();
	}
	
	public void damage(final int amount, final IEntity source, final IEntity target)
	{
		HealthInfoPack hip = core.getInfoPackFrom(target, HealthInfoPack.class);
		if(hip!=null)
		{
			hip.setCurHealth(hip.getCurHealth()-amount);
		}
	}
	//////////
	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "Starting System: DamageSystem.");
	}

	@Override
	public void work()
	{
		
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "Stopping System: DamageSystem.");
	}

}
