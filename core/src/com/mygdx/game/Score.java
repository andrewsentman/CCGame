package com.mygdx.game;

public class Score {
	int dots;
	int score;
	int powerups;
	Score()
	{
		dots=0;
		score=0;
		powerups=0;
	}
	void eatDot()
	{
		this.dots++;
		this.score+=10;
	}
	void eatPowerup()
	{
		this.powerups++;
		this.score+=50;
	}
}
