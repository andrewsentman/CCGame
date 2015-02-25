package com.mygdx.game;

public class StageTimer {
	int ticks;
	StageTimer(int level)
	{
		this.ticks=0;
	}
	void tick()
	{
		this.ticks++;
	}
}
