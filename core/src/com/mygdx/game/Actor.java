package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Actor {

	int pixelX;
	int pixelY;
	int tileX;
	int tileY;
	int direction;
	int sprite;
	boolean collisions=true;
	static final int[] lX= {0,0,0,4,2,2,2,2};
	static final int[] lY= {3,3,3,4,1,1,1,1};

	public Actor(int sprite) {
		this.sprite=sprite;
	}

	void put(int tx, int ty, int px, int py) {
		this.tileX=tx;
		this.tileY=ty;
		this.pixelX=px;
		this.pixelY=py;
	}

	int getScreenX() {
		return this.tileX*8+this.pixelX-3;
	}

	int getScreenY() {
		return this.tileY*8+this.pixelY-3;
	}
	
	void tickAI()
	{
		
	}
	void enterTile()
	{
		
	}
	void tick() {
		this.tickAI();
		this.move(this.direction);
		if (this.pixelY<0)
		{
			this.pixelY=7;
			this.tileY-=1;
			this.enterTile();
		}
		if (this.pixelX<0)
		{
			this.pixelX=7;
			this.tileX-=1;
			this.enterTile();
		}
		if (this.pixelY>7)
		{
			this.pixelY=0;
			this.tileY+=1;
			this.enterTile();
		}
		if (this.pixelX>7)
		{
			this.pixelX=0;
			this.tileX+=1;
			this.enterTile();
		}
		this.checkDelete();
	}
	void move(int direction)
	{
	}
	void checkDelete()
	{
		if ((this.tileX<-1)||(this.tileY<-1)||(this.tileX>Stage.width)||(this.tileY>Stage.height))
		{
			this.kill();
		}
	}
	void kill()
	{
		ActorManager.deleteActor(this);
	}
	void draw(SpriteBatch batch)
	{
		batch.draw(GfxManager.get(this.sprite), this.getScreenX(), this.getScreenY());
	}

}