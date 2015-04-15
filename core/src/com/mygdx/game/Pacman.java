package com.mygdx.game;


public class Pacman extends InputActor{
	Pacman(int sprite) {
		super(sprite);
		this.direction=Direction.LEFT;
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
}
