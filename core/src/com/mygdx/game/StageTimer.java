package com.mygdx.game;

public class StageTimer {
	static final int[] modeTimes0 = {420,1200,420,1200,300,1200,300,-1};
	static final int[] modeTimes1 = {420,1200,420,1200,300,61980,1,-1};
	static final int[] modeTimes2 = {300,1200,300,1200,300,62220,1,-1};
	static final int SCATTER = 0;
	int level;
	int ticks;
	int mode;
	int[] timings;
	int timeToNextMode;
	int state;
	StageTimer(int level)
	{
		this.level=level;
		this.ticks=0;
		this.mode=0;
		this.state=0;
		this.timings=StageTimer.getTimingsFromLevel(this.level);
		this.timeToNextMode=this.timings[this.mode];
	}
	static int[] getTimingsFromLevel(int level)
	{
		if (level==1)
			return StageTimer.modeTimes0;
		if (level<=4)
			return StageTimer.modeTimes1;
		return StageTimer.modeTimes2;
	}
	void tick()
	{
		this.ticks++;
		if (this.timeToNextMode!=-1)
		{
			this.timeToNextMode--;
		}
		if (this.timeToNextMode==0)
		{
			this.state++;
			this.mode=1-this.mode;
			this.timeToNextMode=this.timings[this.state];
		}
	}
}
