package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Clyde extends Ghost {

	Clyde(TextureRegion sprite, Pacman pacman, StageTimer timer) {
		super(sprite, pacman, timer);
	}
	@Override
	void updateTargetScatter()
	{	this.targetX=0;
		this.targetY=-1;
	}
	@Override
	void updateTarget()
	{
		if (Ghost.distance(this.tileX,this.tileY,this.pacman.tileX,this.pacman.tileY)<8)
		{
			this.targetX=0;
			this.targetY=-1;
		}
		else
		{
			this.targetX=this.pacman.tileX;
			this.targetY=this.pacman.tileY;
		}
	}

}
