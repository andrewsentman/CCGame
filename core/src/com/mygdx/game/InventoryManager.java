package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InventoryManager {
	static int[] items = { 0, 1, 2, 3};
	static int[] qtys  = { 1, 1, 1, 1};
	static int active=0;
	static void nextItem()
	{
		active+=1;
		active%=4;
	}
	static void prevItem()
	{
		active+=3;
		active%=4;
	}
	static void draw(SpriteBatch batch)
	{
		batch.draw(GfxManager.get(Constants.ITEM_BORDER),14*active,Stage.height*8+4);
		for (int i=0;i<4;i++)
		{
			if (qtys[i]>0)
				batch.draw(GfxManager.get(Constants.ITEM_SPRITES[items[i]]),4+14*i,Stage.height*8+8);
		}
	}
}
