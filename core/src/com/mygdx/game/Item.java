package com.mygdx.game;

public class Item extends Actor {
	int id;
	Item(int id)
	{
		super(Constants.ITEM_SPRITES[id]);
		this.id=id;
	}
	@Override
	void tick() {
		Actor a = ActorManager.inputActors.get(0);
		if (a.tileX==this.tileX && a.tileY==this.tileY)
		{
			if (InventoryManager.pickUp(this.id))
			{
				this.kill();
			}
		}
	}
}
