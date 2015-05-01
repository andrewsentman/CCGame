package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class Enemy extends PhysicsActor {
	int targetX;
	int targetY;
	Pacman pacman;
	static int activeCounter=0;
	int health;
	int maxHealth;
	float tpm=2;
	float ticks;
	Enemy(int sprite, int health, float tpm)
	{
		super(sprite);
		this.direction=Direction.RIGHT;
		this.pacman=(Pacman) ActorManager.inputActors.get(0);
		this.maxHealth=health;
		this.health=this.maxHealth;
		this.tpm=tpm;
		this.ticks=0;
	}
	@Override
	void tick()
	{
		if ((ActorManager.inputActors.get(0).tileX==this.tileX)&&(ActorManager.inputActors.get(0).tileY==this.tileY))
		{
			if (StageTimer.ticks%6==0)
			{
				((Pacman)ActorManager.inputActors.get(0)).damage(1);
			}
		}
		if (this.tpm==0)
			return;
		this.ticks+=1;
		while (this.ticks>=this.tpm)
		{
			this.ticks-=this.tpm;
			super.tick();
		}
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
	void damage(int amt)
	{
		this.health-=amt;
		if (this.health<=0)
		{
			this.kill();
		}
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
		if (this.tileX==this.targetX && this.tileY==this.targetY)
			this.direction=Direction.NONE;
	}
	static double distance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
	}
	double distanceToTarget(int oX, int oY)
	{
		return Enemy.distance(this.targetX,this.targetY,this.tileX+oX,this.tileY+oY);
	}
	@Override
	void draw(SpriteBatch batch)
	{
		super.draw(batch);
		if (this.health!=-1)
		{
			batch.draw(GfxManager.get(Constants.SPRITE_DOT),this.getScreenX()-4,this.getScreenY()+12,16,2);
			int barwidth=(this.health*16)/this.maxHealth;
			batch.draw(GfxManager.get(Constants.SPRITE_POWERUP),this.getScreenX()-4,this.getScreenY()+12,barwidth,2);
		}
		
		//BitmapFont font = new BitmapFont();
    	//font.setColor(Color.RED);
    	//font.draw(batch, ""+this.health, this.getScreenX(), this.getScreenY()+8);
	}
}
