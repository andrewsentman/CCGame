package com.mygdx.game;


public class Inky extends Ghost{

	Blinky blinky;

	Inky(ActorManager manager, int sprite, Pacman pacman, StageTimer timer, Blinky blinky) {
		super(manager,sprite, pacman,timer);
		this.blinky=blinky;
	}
	@Override
	void updateTargetScatter()
	{	this.targetX=27;
		this.targetY=-1;
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
