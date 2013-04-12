package systems;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import components.AnimationComponent;
import components.AnimationComponent.LoopType;
import components.CollisionComponent;
import components.CursorComponent;
import components.GravityComponent;
import components.InputComponent;
import components.PositionComponent;
import components.RenderComponent;
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
	
	public IEntity makePositionable(final IEntity entity, final int xPos, final int yPos)
	{
		PositionComponent pc = new PositionComponent(entity);
		pc.setGlobalX(xPos);
		pc.setGlobalY(yPos);
		entity.addComponent(PositionComponent.class, pc);
		return entity;
	}
	
	public IEntity makeRenderable(final IEntity entity, final int sizeX, final int sizeY, final int textureID)
	{
		RenderComponent rc = new RenderComponent(entity, sizeX, sizeY, 0, true);
		rc.setWidth(sizeX);
		rc.setHeight(sizeY);
		rc.setTextureID(textureID);
		entity.addComponent(RenderComponent.class, rc);
		return entity;
	}
	
	public IEntity makeCollidable(final IEntity entity, final int group)
	{
		CollisionComponent cc = new CollisionComponent(entity);
		cc.setGroup(group);
		entity.addComponent(CollisionComponent.class, cc);
		return entity;
	}
	public IEntity makeControllable(final IEntity entity, final String ... listenFor)
	{
		InputComponent ic = new InputComponent(entity);
		for(String each:listenFor)
		{
			ic.setInterested(each, true);
		}
		ic.setEnabled(true);
		entity.addComponent(InputComponent.class, ic);
		return entity;
	}
	
	public IEntity makeMovable(final IEntity entity, final int xVel, final int yVel)
	{
		VelocityComponent vc = new VelocityComponent(entity);
		vc.setXVelocity(xVel);
		vc.setYVelocity(yVel);
		vc.setMoveRequested(true);
		entity.addComponent(VelocityComponent.class, vc);
		return entity;
	}
	
	public IEntity makeAnimated(final IEntity entity)
	{
		AnimationComponent ac 
		= new AnimationComponent(entity, 
				core.getSystem(TimerSystem.class).getNow());
		entity.addComponent(AnimationComponent.class, ac);
		return entity;
	}
	
	public IEntity addAnimation(final IEntity entity, final String animationName, final int delay, final LoopType loopType, final ArrayList<Integer> frames)
	{
		AnimationComponent ac = entity.getComponent(AnimationComponent.class);
		if(ac!=null)
		{
			ac.addAnimation(animationName, frames, delay, loopType);
		}
		else
		{
			makeAnimated(entity);
			addAnimation(entity, animationName, delay, loopType, frames);
		}
		return entity;
	}
	
	public IEntity makeBlock(final int x, final int y)
	{
		IEntity block = new Entity();
		makeRenderable(block, 16, 16, -1);
		makePositionable(block, x, y);
		makeMovable(block, 0, 0);
		GravityComponent gc = new GravityComponent(block);
		gc.setUpdateInterval(200);
		gc.setGravity(1);
		block.addComponent(GravityComponent.class, gc);
		core.addEntity(block);
		return block;
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
	
	public IEntity createCursor()
	{
		IEntity cursor = createEntity();
		cursor.setName("Cursor");
		makePositionable(cursor, 0, 0);
		makeRenderable(cursor, 16, 16, 1);
		makeControllable(cursor);
		cursor.addComponent(CursorComponent.class,new CursorComponent());
		core.addEntity(cursor);
		Mouse.setGrabbed(true);
		return cursor;
	}


}
