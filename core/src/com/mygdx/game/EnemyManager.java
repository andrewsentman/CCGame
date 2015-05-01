package com.mygdx.game;

public class EnemyManager {
	public static Enemy getEnemy(int id)
	{
		switch (id)
		{
		case 0:
			return new HealthTest(Constants.SPRITE_INKY, 5, 0);
		case 1:
			return new Blinky(Constants.SPRITE_INKY, 1, 4f);
		default:
			return null;
		}
	}
}
