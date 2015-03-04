package com.mygdx.game;


public class Pacman extends InputActor{
	Pacman(int sprite) {
		super(sprite);
		this.direction=Direction.LEFT;
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
	void move(int dir) {
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
