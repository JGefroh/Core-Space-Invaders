package infopacks;

import components.HealthComponent;

import entities.IEntity;

public class HealthInfoPack implements IInfoPack
{
	private IEntity parent;
	private HealthComponent hc;
	public HealthInfoPack(final IEntity parent)
	{
		this.parent = parent;
		updateReferences();
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		hc = parent.getComponent(HealthComponent.class);
		if(hc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}
	//////////
	public int getCurHealth()
	{
		return hc.getCurHealth();
	}
	///////////
	public void setCurHealth(final int curHealth)
	{
		hc.setCurHealth(curHealth);
	}
	////

}
