package com.mygdx.game;


public class Blinky extends Ghost {

	Blinky(ActorManager manager, int sprite, Pacman pacman, StageTimer timer) {
		super(manager,sprite, pacman, timer);
	}
	@Override
	void updateTargetScatter()
	{	this.targetX=25;
		this.targetY=35;
	}
	@Override
	void updateTarget()
	{
		this.targetX=this.pacman.tileX;
		this.targetY=this.pacman.tileY;
	}

}
