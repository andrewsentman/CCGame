package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Actor {

	int pixelX;
	int pixelY;
	int tileX;
	int tileY;
	int direction;
	TextureRegion sprite;
	static final int[] lX= {0,0,0,4,2,2,2,2};
	static final int[] lY= {3,3,3,4,1,1,1,1};

	public Actor(TextureRegion sprite) {
		this.sprite=sprite;
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
		if (this.tileX<0)
		{
			this.tileX=27;
		}
		if (this.tileX>27)
		{
			this.tileX=0;
		}
	}
	void move(int dir) {
		if (Stage.get(this.tileX+Direction.oX[dir],this.tileY+Direction.oY[dir])!=1 || 
									((lX[this.pixelX]==dir) || (lY[this.pixelY]==dir)))
		{
			this.pixelX+=Direction.oX[dir];
			this.pixelY+=Direction.oY[dir];
			//if (Direction.oX[dir]==0)
			//	cornerX();
			//if (Direction.oY[dir]==0)
			//	cornerY();
		}
	}

}