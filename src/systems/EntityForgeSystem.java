package systems;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import components.AnimationComponent;
import components.AnimationComponent.LoopType;
import components.CameraComponent;
import components.CollisionComponent;
import components.CursorComponent;
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
	
	public IEntity makeRenderable(final IEntity entity, final int sizeX, final int sizeY, final String texturePath)
	{
		RenderComponent rc = new RenderComponent(entity, sizeX, sizeY, 0, true);
		rc.setWidth(sizeX);
		rc.setHeight(sizeY);
		rc.setTexturePath(texturePath);
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
	public void createCursor()
	{
		IEntity cursor = createEntity();
		cursor.setName("Cursor");
		makePositionable(cursor,0, 0);
		makeRenderable(cursor, 16, 16, "res/gui.png");
		makeAnimated(cursor);
		cursor.addComponent(CursorComponent.class,new CursorComponent());
		ArrayList<Integer> positions = new ArrayList<Integer>();
		positions.add(0);
		addAnimation(cursor, "MOVERIGHT", 1000, LoopType.LOOPDISABLED, positions);
		core.addEntity(cursor);
		Mouse.setGrabbed(true);
		cursor.getComponent(PositionComponent.class).setLocalZ(-100);
	}
	
	public void createPlayer()
	{
		IEntity player = createEntity();
		makePositionable(player, 0, 0);
		makeMovable(player, 0, 0);
		makeControllable(player);
		makeRenderable(player, 64, 64, "res/player.png");
		makeAnimated(player);
		makeCollidable(player, 1);
		
		
		ArrayList<Integer> positions = new ArrayList<Integer>();
		
		positions.add(0);
		positions.add(1);
		addAnimation(player, "MOVERIGHT", 200, LoopType.LOOPFROMSTART, positions);
		
		positions = new ArrayList<Integer>();
		positions.add(2);
		positions.add(3);
		addAnimation(player, "MOVELEFT", 200, LoopType.LOOPFROMSTART, positions);
		
		positions = new ArrayList<Integer>();
		positions.add(4);
		positions.add(5);
		addAnimation(player, "IDLE", 200, LoopType.LOOPFROMSTART, positions);
		
		player.getComponent(InputComponent.class).setInterested("MOVERIGHT", true);
		player.getComponent(InputComponent.class).setInterested("MOVELEFT", true);
		player.getComponent(InputComponent.class).setInterested("MOVEUP", true);
		player.getComponent(InputComponent.class).setInterested("MOVEDOWN", true);
		player.getComponent(InputComponent.class).setInterested("STOPX", true);
		player.getComponent(InputComponent.class).setInterested("STOPY", true);
		core.addEntity(player);
		player.getComponent(PositionComponent.class).setLocalZ(-1);

	}
	
	public void createCamera()
	{
		IEntity camera = createEntity();
		camera.setName("camera");
		makePositionable(camera, 0, 0);
		makeMovable(camera, 0, 0);
		makeControllable(camera);
		camera.addComponent(CameraComponent.class, new CameraComponent(camera));
		camera.getComponent(InputComponent.class).setInterested("MOVECRIGHT", true);
		camera.getComponent(InputComponent.class).setInterested("MOVECLEFT", true);
		camera.getComponent(InputComponent.class).setInterested("MOVECUP", true);
		camera.getComponent(InputComponent.class).setInterested("MOVECDOWN", true);
		camera.getComponent(InputComponent.class).setInterested("STOPCX", true);
		camera.getComponent(InputComponent.class).setInterested("STOPCY", true);
		camera.getComponent(CameraComponent.class).setActive(true);
		
		camera.getComponent(CameraComponent.class).setWidth(2560);
		camera.getComponent(CameraComponent.class).setHeight(1440);

		
		core.addEntity(camera);
	}

}
