package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Actor {

	int pixelX;
	int pixelY;
	int tileX;
	int tileY;
	int direction;
	int sprite;
	boolean collisions=true;
	static ArrayList<Integer> lX = new ArrayList<Integer>();//= {0,0,0,0,0,0,0,0,0,0,0,4,2,2,2,2,2,2,2,2,2,2,2,2};
	static ArrayList<Integer> lY = new ArrayList<Integer>();//= {3,3,3,3,3,3,3,3,3,3,3,4,1,1,1,1,1,1,1,1,1,1,1,1};

	public Actor(int sprite) {
		for (int i=0; i<Constants.TILE_SIZE; i++)
		{
			if (i<(Constants.TILE_SIZE/2)) {
				lX.add(0);
				lY.add(3);
			} else if (i>(Constants.TILE_SIZE/2)) {
				lX.add(2);
				lY.add(1);
			} else {
				lX.add(4);
				lY.add(4);
			}
		}
		this.sprite=sprite;
	}

	void put(int tx, int ty, int px, int py) {
		this.tileX=tx;
		this.tileY=ty;
		this.pixelX=px;
		this.pixelY=py;
	}

	int getScreenX() {
		return this.tileX*Constants.TILE_SIZE+this.pixelX-((Constants.TILE_SIZE/2)-1);
	}

	int getScreenY() {
		return this.tileY*Constants.TILE_SIZE+this.pixelY-((Constants.TILE_SIZE/2)-1);
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
			this.pixelY=(Constants.TILE_SIZE-1);
			this.tileY-=1;
			this.enterTile();
		}
		if (this.pixelX<0)
		{
			this.pixelX=(Constants.TILE_SIZE-1);
			this.tileX-=1;
			this.enterTile();
		}
		if (this.pixelY>(Constants.TILE_SIZE-1))
		{
			this.pixelY=0;
			this.tileY+=1;
			this.enterTile();
		}
		if (this.pixelX>(Constants.TILE_SIZE-1))
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