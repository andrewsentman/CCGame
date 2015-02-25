package com.mygdx.game;


public class Actor {

	int pixelX;
	int pixelY;
	int tileX;
	int tileY;
	int direction;
	int sprite;
	boolean collisions=true;
	ActorManager manager;
	static final int[] lX= {0,0,0,4,2,2,2,2};
	static final int[] lY= {3,3,3,4,1,1,1,1};

	public Actor(ActorManager manager, int sprite) {
		this.sprite=sprite;
		this.manager=manager;
	}

	void put(int tx, int ty, int px, int py) {
		this.tileX=tx;
		this.tileY=ty;
		this.pixelX=px;
		this.pixelY=py;
	}

	int getScreenX() {
		return this.tileX*8+this.pixelX-7;
	}

	int getScreenY() {
		return this.tileY*8+this.pixelY;
	}
	
	void tickAI()
	{
		
	}

	void tick() {
		this.tickAI();
		this.move(this.direction);
		if (this.pixelY<0)
		{
			this.pixelY=7;
			this.tileY-=1;
		}
		if (this.pixelX<0)
		{
			this.pixelX=7;
			this.tileX-=1;
		}
		if (this.pixelY>7)
		{
			this.pixelY=0;
			this.tileY+=1;
		}
		if (this.pixelX>7)
		{
			this.pixelX=0;
			this.tileX+=1;
		}
	}
	void move(int direction)
	{
	}

}