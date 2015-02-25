	

    package com.mygdx.game;
     
    import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
     
    public class MyGdxGame extends ApplicationAdapter {
            SpriteBatch batch;
            TextureAtlas atlas;
            AtlasRegion stageSprite;
            Pacman pacman;
            Blinky blinky;
            Pinky pinky;
            Inky inky;
            Clyde clyde;
            
            StageTimer timer;
            
            BitmapFont pfont;
            BitmapFont bfont;
            BitmapFont kfont;
            BitmapFont ifont;
            BitmapFont cfont;
            BitmapFont mfont;
            
            Score score;
           
            float screenWidth = 0;
            float screenHeight = 0;
            
            int u=0;
           
            Vector2 pixelLocation = new Vector2(0, 0);
			ActorManager actorManager;
			GfxManager gfxManager;
           
            @Override
            public void create () {
            		pfont = new BitmapFont();
                	pfont.setColor(Color.YELLOW);
                	bfont = new BitmapFont();
                	bfont.setColor(Color.RED);
                	kfont = new BitmapFont();
                	kfont.setColor(Color.MAGENTA);
                	ifont = new BitmapFont();
                	ifont.setColor(Color.CYAN);
                	cfont = new BitmapFont();
                	cfont.setColor(Color.ORANGE);
                	mfont = new BitmapFont();
                	mfont.setColor(Color.WHITE);
            	
                	Gdx.graphics.setDisplayMode(500, 600, false);
            		screenWidth = Gdx.graphics.getWidth();
                    screenHeight = Gdx.graphics.getHeight();
                    
                    Stage.resetStage();
                    
                    atlas = new TextureAtlas(Gdx.files.internal("sprites.pack"));
                   
                    batch = new SpriteBatch();
                    stageSprite = atlas.findRegion("stage");
                    
                    gfxManager=new GfxManager();
                    
                    gfxManager.add(atlas.findRegion("pacmanc"), Constants.SPRITE_PACMAN);
                    gfxManager.add(atlas.findRegion("blinkyu"), Constants.SPRITE_BLINKY);
                    gfxManager.add(atlas.findRegion("pinkyu"), Constants.SPRITE_PINKY);
                    gfxManager.add(atlas.findRegion("inkyu"), Constants.SPRITE_INKY);
                    gfxManager.add(atlas.findRegion("clydeu"), Constants.SPRITE_CLYDE);
                    gfxManager.add(atlas.findRegion("dot"), Constants.SPRITE_DOT);
                    gfxManager.add(atlas.findRegion("powerup"), Constants.SPRITE_POWERUP);
                    
                    actorManager=new ActorManager(gfxManager);
                    
                    timer = new StageTimer(1);
                    
                    score = new Score();
                    
                    pacman = new Pacman(actorManager,Constants.SPRITE_PACMAN);
                    blinky = new Blinky(actorManager,Constants.SPRITE_BLINKY, pacman,timer);
                    pinky = new Pinky(actorManager,Constants.SPRITE_PINKY, pacman,timer);
                    inky = new Inky(actorManager,Constants.SPRITE_INKY, pacman,timer,blinky);
                    clyde = new Clyde(actorManager,Constants.SPRITE_CLYDE, pacman,timer);
                    
                    actorManager.addInputActor(pacman, 0);
                    actorManager.add(blinky);
                    actorManager.add(pinky);
                    actorManager.add(inky);
                    actorManager.add(clyde);
                   
                    blinky.put(14,19,3,3);
                    pinky.put(14,19,3,3);
                    inky.put(14,19,3,3);
                    clyde.put(14,19,3,3);
                    pacman.put(14,7,3,3);
            }
           
            public void update () {
            		u+=1;
            		if (u%10>0)
            		{
            			if (Gdx.input.isKeyPressed(Keys.SPACE)) return;
            		}
                    if(Gdx.input.isKeyPressed(Keys.UP))
                    {
                            actorManager.sendInput(0, Direction.UP);
                    }
                   
                    if(Gdx.input.isKeyPressed(Keys.DOWN))
                    {
                    		actorManager.sendInput(0, Direction.DOWN);
                    }
                   
                    if(Gdx.input.isKeyPressed(Keys.RIGHT))
                    {
                    		actorManager.sendInput(0, Direction.RIGHT);
                    }
                   
                    if(Gdx.input.isKeyPressed(Keys.LEFT))
                    {
                    		actorManager.sendInput(0, Direction.LEFT);
                    }
                    
                    if(Gdx.input.isKeyPressed(Keys.SPACE))
                    {
                    		actorManager.sendInput(0, Direction.ATTACK);
                    }
                    timer.tick();
                    actorManager.tick();
                    updateScoring();
            }
            void updateScoring()
            {
            	if (Stage.get(pacman.tileX, pacman.tileY)==2)
            	{
            		Stage.set(pacman.tileX, pacman.tileY,0);
            		score.eatDot();
            	}
            	if (Stage.get(pacman.tileX, pacman.tileY)==3)
            	{
            		Stage.set(pacman.tileX, pacman.tileY,0);
            		score.eatPowerup();
            	}
            }
           
            @Override
            public void render () {
                    update();
                   
                    Gdx.gl.glClearColor(0, 0, 0, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                   
                    batch.begin();
                    
                    batch.draw(stageSprite, 0,8);
                    
                    actorManager.draw(batch);
                    
                    //batch.draw(pacmanSprite, pacman.getScreenX(), pacman.getScreenY());
                    //batch.draw(blinkySprite, blinky.getScreenX(), blinky.getScreenY());
                    //batch.draw(pinkySprite, pinky.getScreenX(), pinky.getScreenY());
                    //batch.draw(inkySprite, inky.getScreenX(), inky.getScreenY());
                    //batch.draw(clydeSprite, clyde.getScreenX(), clyde.getScreenY());
                    
                    pfont.draw(batch, ""+pacman.tileX, 300, 540);
                    pfont.draw(batch, ""+pacman.tileY, 300, 500);
                    pfont.draw(batch, ""+pacman.pixelX, 340, 540);
                    pfont.draw(batch, ""+pacman.pixelY, 340, 500);
                    pfont.draw(batch, Direction.names[pacman.direction], 320, 520);
                    pfont.draw(batch, ""+Stage.get(pacman.tileX, pacman.tileY), 420, 520);
                    pfont.draw(batch, ""+Stage.get(pacman.tileX-1, pacman.tileY), 400, 520);
                    pfont.draw(batch, ""+Stage.get(pacman.tileX+1, pacman.tileY), 440, 520);
                    pfont.draw(batch, ""+Stage.get(pacman.tileX, pacman.tileY-1), 420, 500);
                    pfont.draw(batch, ""+Stage.get(pacman.tileX, pacman.tileY+1), 420, 540);
                    
                    bfont.draw(batch, ""+blinky.tileX, 300, 440);
                    bfont.draw(batch, ""+blinky.tileY, 300, 400);
                    bfont.draw(batch, ""+blinky.pixelX, 340, 440);
                    bfont.draw(batch, ""+blinky.pixelY, 340, 400);
                    bfont.draw(batch, Direction.names[blinky.direction], 320, 420);
                    bfont.draw(batch, ""+Stage.get(blinky.tileX, blinky.tileY), 420, 420);
                    bfont.draw(batch, ""+Stage.get(blinky.tileX-1, blinky.tileY), 400, 420);
                    bfont.draw(batch, ""+Stage.get(blinky.tileX+1, blinky.tileY), 440, 420);
                    bfont.draw(batch, ""+Stage.get(blinky.tileX, blinky.tileY-1), 420, 400);
                    bfont.draw(batch, ""+Stage.get(blinky.tileX, blinky.tileY+1), 420, 440);
                    bfont.draw(batch, "x", blinky.targetX*8, blinky.targetY*8+20);
                    
                    kfont.draw(batch, ""+pinky.tileX, 300, 340);
                    kfont.draw(batch, ""+pinky.tileY, 300, 300);
                    kfont.draw(batch, ""+pinky.pixelX, 340, 340);
                    kfont.draw(batch, ""+pinky.pixelY, 340, 300);
                    kfont.draw(batch, Direction.names[pinky.direction], 320, 320);
                    kfont.draw(batch, ""+Stage.get(pinky.tileX, pinky.tileY), 420, 320);
                    kfont.draw(batch, ""+Stage.get(pinky.tileX-1, pinky.tileY), 400, 320);
                    kfont.draw(batch, ""+Stage.get(pinky.tileX+1, pinky.tileY), 440, 320);
                    kfont.draw(batch, ""+Stage.get(pinky.tileX, pinky.tileY-1), 420, 300);
                    kfont.draw(batch, ""+Stage.get(pinky.tileX, pinky.tileY+1), 420, 340);
                    kfont.draw(batch, "x", pinky.targetX*8, pinky.targetY*8+20);
                    
                    ifont.draw(batch, ""+inky.tileX, 300, 240);
                    ifont.draw(batch, ""+inky.tileY, 300, 200);
                    ifont.draw(batch, ""+inky.pixelX, 340, 240);
                    ifont.draw(batch, ""+inky.pixelY, 340, 200);
                    ifont.draw(batch, Direction.names[inky.direction], 320, 220);
                    ifont.draw(batch, ""+Stage.get(inky.tileX, inky.tileY), 420, 220);
                    ifont.draw(batch, ""+Stage.get(inky.tileX-1, inky.tileY), 400, 220);
                    ifont.draw(batch, ""+Stage.get(inky.tileX+1, inky.tileY), 440, 220);
                    ifont.draw(batch, ""+Stage.get(inky.tileX, inky.tileY-1), 420, 200);
                    ifont.draw(batch, ""+Stage.get(inky.tileX, inky.tileY+1), 420, 240);
                    ifont.draw(batch, "x", inky.targetX*8, inky.targetY*8+20);
                    
                    ifont.draw(batch, "o", (pacman.tileX + 2*Direction.oX[pacman.direction])*8, (pacman.tileY + 2*Direction.oY[pacman.direction])*8+20);
                   
                    cfont.draw(batch, ""+clyde.tileX, 300, 140);
                    cfont.draw(batch, ""+clyde.tileY, 300, 100);
                    cfont.draw(batch, ""+clyde.pixelX, 340, 140);
                    cfont.draw(batch, ""+clyde.pixelY, 340, 100);
                    cfont.draw(batch, Direction.names[clyde.direction], 320, 120);
                    cfont.draw(batch, ""+Stage.get(clyde.tileX, clyde.tileY), 420, 120);
                    cfont.draw(batch, ""+Stage.get(clyde.tileX-1, clyde.tileY), 400, 120);
                    cfont.draw(batch, ""+Stage.get(clyde.tileX+1, clyde.tileY), 440, 120);
                    cfont.draw(batch, ""+Stage.get(clyde.tileX, clyde.tileY-1), 420, 100);
                    cfont.draw(batch, ""+Stage.get(clyde.tileX, clyde.tileY+1), 420, 140);
                    cfont.draw(batch, "x", clyde.targetX*8, clyde.targetY*8+20);
                    
                    //mfont.draw(batch, ""+timer.mode, 0,580);
                    mfont.draw(batch, ""+timer.ticks/60, 60,580);
                    mfont.draw(batch, ""+timer.ticks%60, 100,580);
                    

                    mfont.draw(batch, "Score:"+score.score,0,420);
                    mfont.draw(batch, "Dots:"+score.dots,0,400);
                    mfont.draw(batch, "Power:"+score.powerups,0,380);
                    
                    mfont.draw(batch, "Spr:"+actorManager.actors.size(),0,500);
                    
                    for (int x=0; x<28; x++)
                    {
                    	for (int y=0; y<31; y++)
                    	{
                    		if (Stage.get(x,y)==2)
                    			batch.draw(gfxManager.get(Constants.SPRITE_DOT), x*8+3, y*8+11);
                    		if (Stage.get(x,y)==3)
                    			batch.draw(gfxManager.get(Constants.SPRITE_POWERUP), x*8, y*8+8);
                    	}
                    }
                    
                    batch.end();
                    ShapeRenderer sr = new ShapeRenderer();
                    sr.setColor(Color.CYAN);

                    sr.begin(ShapeType.Line);
                    sr.line(inky.targetX*8+3,inky.targetY*8+11,blinky.tileX*8+3,blinky.tileY*8+11);
                    sr.end();
                    
            }
    }

