package com.mygdx.game;


public class Ghost extends PhysicsActor {
	int targetX;
	int targetY;
	Pacman pacman;
	StageTimer timer;
	static int activeCounter=0;
	Ghost(ActorManager manager, int sprite, Pacman pacman, StageTimer timer)
	{
		super(manager,sprite);
		this.direction=Direction.RIGHT;
		this.pacman=pacman;
		this.timer=timer;
	}
	@Override
	void tickAI()
	{
		if ((this.pixelX!=3) || (this.pixelY!=3))
		{
			return;
		}
		updateTarget();
		updateDirection();
	}
	void updateTargetScatter() 
	{
	}
	void updateTarget()
	{
	}
	void updateDirection()
	{
		double du=0;
		double dd=0;
		double dl=0;
		double dr=0;
		
		du=distanceToTarget(0,1);
		dd=distanceToTarget(0,-1);
		dl=distanceToTarget(-1,0);
		dr=distanceToTarget(1,0);
		
		if ((Stage.get(this.tileX,this.tileY+1)==1) || (this.direction==Direction.DOWN))
			du=Double.MAX_VALUE;
		if ((Stage.get(this.tileX,this.tileY-1)==1) || (this.direction==Direction.UP))
			dd=Double.MAX_VALUE;
		if ((Stage.get(this.tileX-1,this.tileY)==1) || (this.direction==Direction.RIGHT))
			dl=Double.MAX_VALUE;
		if ((Stage.get(this.tileX+1,this.tileY)==1) || (this.direction==Direction.LEFT))
			dr=Double.MAX_VALUE;
		
		double minval=Math.min(Math.min(du,dd), Math.min(dl, dr));
		if (dr==minval)
			this.direction=Direction.RIGHT;
		if (dd==minval)
			this.direction=Direction.DOWN;
		if (dl==minval)
			this.direction=Direction.LEFT;
		if (du==minval)
			this.direction=Direction.UP;
	}
	static double distance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
	}
	double distanceToTarget(int oX, int oY)
	{
		return Ghost.distance(this.targetX,this.targetY,this.tileX+oX,this.tileY+oY);
	}
}
