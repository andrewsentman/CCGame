package com.mygdx.game;


public class Inky extends Enemy{

	Blinky blinky;

	Inky(int sprite, int health, float tpm, Blinky blinky) {
		super(sprite, health, tpm);
		this.blinky=blinky;
	}
	@Override
	void updateTarget()
	{
		int ptX=this.pacman.tileX + 2*Direction.oX[this.pacman.direction];
		int ptY=this.pacman.tileY + 2*Direction.oY[this.pacman.direction];
		int otX=ptX-this.blinky.tileX;
		int otY=ptY-this.blinky.tileY;
		this.targetX=ptX+otX;
		this.targetY=ptY+otY;
	}

}
