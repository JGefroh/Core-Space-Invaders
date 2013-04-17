package systems;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import components.AnimationComponent;
import components.AnimationComponent.LoopType;
import components.CollisionComponent;
import components.CursorComponent;
import components.GravityComponent;
import components.InputComponent;
import components.RenderComponent;
import components.TransformComponent;
import components.VelocityComponent;

import entities.Entity;
import entities.IEntity;

public class EntityForgeSystem implements ISystem
{
	private Core core;
	public EntityForgeSystem(final Core core)
	{
		this.core = core;
	}
	
	public IEntity createEntity()
	{
		return new Entity();
	}
	
	@Override
	public void start()
	{	
	}

	@Override
	public void work()
	{//Go through creation queue and create all the required entities.
		
	}

	@Override
	public void stop()
	{
	}
}
