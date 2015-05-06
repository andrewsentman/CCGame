package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Pacman extends InputActor{
	int health;
	int maxHealth;
	Pacman(int sprite, int health) {
		super(sprite);
		this.direction=Direction.LEFT;
		this.health=health;
		this.maxHealth=this.health;
	}
	@Override
	void recieveInput(int dir)
	{
		super.recieveInput(dir);
		switch (dir)
		{
		case Direction.UP:
			this.sprite=Constants.SPRITE_PACMAN_U;
			break;
		case Direction.DOWN:
			this.sprite=Constants.SPRITE_PACMAN_D;
			break;
		case Direction.LEFT:
			this.sprite=Constants.SPRITE_PACMAN_L;
			break;
		case Direction.RIGHT:
			this.sprite=Constants.SPRITE_PACMAN_R;
			break;
		}
	}
	void cornerX()
	{
		if (this.pixelX<3) this.pixelX++;
		if (this.pixelX>3) this.pixelX--;
	}
	void cornerY()
	{
		if (this.pixelY<3) this.pixelY++;
		if (this.pixelY>3) this.pixelY--;
	}
	@Override
	void enterTile()
	{
		Stage.evalWarps(this.tileX, this.tileY);
	}
	@Override
	void move(int dir) {
		if (this.halted)
			return;
		if (Stage.get(this.tileX+Direction.oX[dir],this.tileY+Direction.oY[dir])!=1 || 
									((lX[this.pixelX]==dir) || (lY[this.pixelY]==dir)))
		{
			this.pixelX+=Direction.oX[dir];
			this.pixelY+=Direction.oY[dir];
			if (Direction.oX[dir]==0)
				cornerX();
			if (Direction.oY[dir]==0)
				cornerY();
		}
	}
	@Override
	void draw(SpriteBatch batch)
	{
		super.draw(batch);
		if (this.health!=-1)
		{
			batch.draw(GfxManager.get(Constants.SPRITE_DOT),4,Stage.height*8+24,Stage.width*8-8,4);
			int barwidth=(this.health*(Stage.width*8-8))/this.maxHealth;
			batch.draw(GfxManager.get(Constants.SPRITE_POWERUP),4,Stage.height*8+24,barwidth,4);
		}
	}
	void damage(int amt)
	{
		if (InventoryManager.currentItem()==1)
			return;
		this.health-=amt;
		if (this.health<=0)
		{
			Stage.respawn(Stage.curmap);
		}
	}
}
