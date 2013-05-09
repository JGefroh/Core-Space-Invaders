package components;

import entities.IEntity;

public class HealthComponent implements IComponent
{
	private IEntity parent;
	private int curHealth;
	
	public HealthComponent(final IEntity parent)
	{
		this.parent = parent;
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
	
	public void setCurHealth(final int curHealth)
	{
		this.curHealth = curHealth;
	}
	
	public int getCurHealth()
	{
		return this.curHealth;
	}
}
