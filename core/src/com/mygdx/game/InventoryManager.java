package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InventoryManager {
	static int[] items = {-1,-1,-1,-1};
	static int[] qtys  = { 0, 0, 0, 0};
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
	static int currentItem()
	{
		return items[active];
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
	static boolean pickUp(int id) {
		for (int i=0; i<4; i++)
		{
			if (items[i]==id)
			{
				qtys[i]+=1;
				return true;
			}
			if (items[i]==-1)
			{
				items[i]=id;
				qtys[i]=1;
				return true;
			}
		}
		return false;
	}
}
