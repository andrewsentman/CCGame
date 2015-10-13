package com.qwertyzzz18.ccgame.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener, InputProcessor {

    private static final int STATE_MAIN           = 0;
    private static final int STATE_EDITSTAGE      = 1;
    private static final int STATE_MAPSETTINGS    = 2;
    private static final int STATE_NEWSTAGE       = 3;
    private static final int STATE_EDITNEWSTAGE   = 4;
    private static final int STATE_STAGE_MAIN     = 5;
    private static final int STATE_STAGE_EDITTILE = 6;

    static int SCREEN_WIDTH  = 1200;
    static int SCREEN_HEIGHT = 600;

    private SpriteBatch   batch;
    private Texture       button;
    static TextureRegion buttonRegion;
    private Texture       grid;
    private TextureRegion gridRegion;
    private Texture       sel;
    private TextureRegion selRegion;
    private GameMap       map;
    static int           ticks           = 0;
    private int           curstageX;
    private int           curstageY;
    private Stage curstage;
    private int           lastTouchX;
    private int           lastTouchY;
    private int           editField;
    private int           spritePalScroll = 0;
    private boolean       isInputting     = false;
    private String        inputstr;
    static int           state;
    static BitmapFont    font;
    private BitmapFont    font2;
    static GlyphLayout   glyph;
    private int           lastTouchButton;
    private Texture       spritePalFB;
    private boolean       gestureScroll;
    private int           selSprite       = 0;
    private Texture       spriteMapFB;
    private int           curType         = 1;
    private int           curtileY;
    private int           curtileX;
    private Tile curtile;
    private Inputter inputter;

    @Override
    public void create() {

        Gdx.graphics.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        state = STATE_MAIN;
        // state = STATE_STAGE_MAIN;
        // curstageX = 6;
        // curstageY = 7;
        button = new Texture("stageicon.png");
        buttonRegion = new TextureRegion(button);
        grid = new Texture("stagegrid.png");
        gridRegion = new TextureRegion(grid);
        sel = new Texture("stagesel.png");
        selRegion = new TextureRegion(sel);
        font = new BitmapFont();
        font.setColor(Color.RED);
        font2 = new BitmapFont();
        font2.setColor(Color.WHITE);
        glyph = new GlyphLayout();
        batch = new SpriteBatch();
        inputter = new Inputter();
        load();
        SpriteManager.init();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        ticks++;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        switch (state)
        {
            case STATE_MAIN:
                drawStateMain();
                break;
            case STATE_NEWSTAGE:
                drawStateNewstage();
                break;
            case STATE_EDITSTAGE:
            case STATE_EDITNEWSTAGE:
                drawStateEditstage();
                break;
            case STATE_MAPSETTINGS:
                drawStateMapsetting();
                break;
            case STATE_STAGE_MAIN:
                drawStateStageMain();
                break;
            case STATE_STAGE_EDITTILE:
                drawStateStageEdittile();
        }
        batch.end();
    }

    private void drawStateMain() {
        map.draw(batch);
        batch.draw(buttonRegion, 10, SCREEN_HEIGHT - 36, 80, 20);
        font.draw(batch, "New Stage", 16, SCREEN_HEIGHT - 20);
        batch.draw(buttonRegion, 100, SCREEN_HEIGHT - 36, 95, 20);
        font.draw(batch, "Map Settings", 107, SCREEN_HEIGHT - 20);
        batch.draw(buttonRegion, 205, SCREEN_HEIGHT - 36, 45, 20);
        font.draw(batch, "Load", 212, SCREEN_HEIGHT - 20);
        batch.draw(buttonRegion, 260, SCREEN_HEIGHT - 36, map.isDirty() ? 57 : 45, 20);
        font.draw(batch, map.isDirty() ? "*Save*" : "Save", 267, SCREEN_HEIGHT - 20);
    }

    private void drawStateNewstage() {
        map.draw(batch);
        font2.draw(batch, "Click on a grid intersection to create a new map", 16, SCREEN_HEIGHT - 20);
        for (int i = 0; i < map.getWidth(); i++)
        {
            batch.draw(gridRegion, 19 + 16 * i, 19, 2, 16 * (map.getHeight() - 1));
        }
        for (int i = 0; i < map.getHeight(); i++)
        {
            batch.draw(gridRegion, 19, 19 + 16 * i, 16 * (map.getWidth() - 1), 2);
        }
    }

    private void drawStateEditstage() {
        String promptstr;
        map.draw(batch);
        batch.draw(selRegion, 16 * curstageX + 16, 16 * curstageY + 16);
        promptstr = "ERROR MyGDXGame.java:EP000"; // EP000
        switch (editField)
        {
            case 0:
                promptstr = "Level Name>";
                break;
            case 1:
                promptstr = "Level Width>";
                break;
            case 2:
                promptstr = "Level Height>";
                break;
        }
        glyph.setText(font, promptstr + inputstr);
        batch.draw(buttonRegion, 10, SCREEN_HEIGHT - 36, 35, 20);
        font.draw(batch, "Edit", 16, SCREEN_HEIGHT - 20);
        batch.draw(buttonRegion, 55, SCREEN_HEIGHT - 36, glyph.width + (ticks % 60 < 30 ? 10 : 15), 20);
        font.draw(batch, glyph, 61, SCREEN_HEIGHT - 20);
    }

    private void drawStateMapsetting() {
        String promptstr;
        promptstr = "ERROR MyGDXGame.java:EP000"; // EP000
        switch (editField)
        {
            case 0:
                promptstr = "Map Width>";
                break;
            case 1:
                promptstr = "Map Height>";
                break;
        }
        glyph.setText(font, promptstr + inputstr);
        batch.draw(buttonRegion, 10, SCREEN_HEIGHT - 36, glyph.width + (ticks % 60 < 30 ? 10 : 15), 20);
        font.draw(batch, glyph, 16, SCREEN_HEIGHT - 20);
    }

    private void drawStateStageMain() {
        batch.draw(gridRegion, SCREEN_WIDTH - 197, 0, 2, SCREEN_HEIGHT);
        Stage cstage = curstage;
        int screenwidth = 16 + 37 * cstage.getWidth();
        int screenheight = 16 + 37 * cstage.getHeight();
        if (spriteMapFB == null)
        {
            spriteMapFB = drawSpriteMap(cstage, screenwidth, screenheight);
        }

        batch.draw(spriteMapFB, 0, 0, screenwidth, screenheight, 0, 0, screenwidth, screenheight, false, true);

        ArrayList<Integer> st0 = SpriteManager.getSpritesType(this.curType);
        screenwidth = 195;
        screenheight = 40 * (st0.size() / 5 + (5 - ((st0.size() + 1) % 5)));
        if (spritePalFB == null)
        {
            spritePalFB = drawSpritePalette(screenwidth, screenheight, st0);
        }

        batch.draw(spritePalFB, SCREEN_WIDTH - 195, SCREEN_HEIGHT - screenheight - spritePalScroll, screenwidth,
                screenheight, 0, 0, screenwidth, screenheight, false, true);

        batch.draw(buttonRegion, 10, SCREEN_HEIGHT - 36, 45, 20);
        font.draw(batch, "Back", 16, SCREEN_HEIGHT - 20);

    }
    
    private void drawStateStageEdittile() {
        TextureRegion cspr = SpriteManager.getSprite(curtile.getTid());
        
        int screenwidth = 16 + 37 * curstage.getWidth();
        int screenheight = 16 + 37 * curstage.getHeight();
        if (spriteMapFB == null)
        {
            spriteMapFB = drawSpriteMap(curstage, screenwidth, screenheight);
        }
        batch.enableBlending();
        Color c = batch.getColor();
        batch.setColor(c.r,c.g,c.b,0.5f);
        batch.draw(spriteMapFB, 0, 0, screenwidth, screenheight, 0, 0, screenwidth, screenheight, false, true);
        c=batch.getColor();
        batch.setColor(c.r,c.g,c.b,1f);
        batch.draw(cspr, 16 + 37 * this.curtileX, 16 + 37 * this.curtileY, 0, 0, 35, 35, 1f, 1f, 0f);
        inputter.draw(batch);
        
    }

    private Texture drawSpritePalette(int screenwidth, int screenheight, ArrayList<Integer> st0) {
        SpriteBatch fb = new SpriteBatch();

        Matrix4 projectionMatrix = new Matrix4();
        projectionMatrix.setToOrtho2D(0, 0, screenwidth, screenheight);
        fb.setProjectionMatrix(projectionMatrix);

        FrameBuffer fbo = new FrameBuffer(Format.RGBA8888, screenwidth, screenheight, false);

        fbo.begin();

        fb.enableBlending();
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE,
                GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fb.begin();

        fb.draw(buttonRegion, 40 * ((selSprite + 1) % 5) - 1, screenheight - 40 * (((selSprite + 1) + 5) / 5) - 1, 0, 0,
                37, 37, 1f, 1f, 0f);

        for (int i = 1; i < st0.size() + 1; i++)
        {
            TextureRegion cspr = SpriteManager.getSprite(st0.get(i - 1));
            fb.draw(cspr, 40 * (i % 5), screenheight - 40 * ((i + 5) / 5), 0, 0, 35, 35, 1f, 1f, 0f);
        }

        fb.end();

        fbo.end();

        return fbo.getColorBufferTexture();
    }

    private Texture drawSpriteMap(Stage cstage, int screenwidth, int screenheight) {
        SpriteBatch fb = new SpriteBatch();

        Matrix4 projectionMatrix = new Matrix4();
        projectionMatrix.setToOrtho2D(0, 0, screenwidth, screenheight);
        fb.setProjectionMatrix(projectionMatrix);

        FrameBuffer fbo = new FrameBuffer(Format.RGBA8888, screenwidth, screenheight, false);

        fbo.begin();

        fb.enableBlending();
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE,
                GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fb.begin();

        for (int i = 0; i < cstage.getWidth() + 1; i++)
        {
            fb.draw(gridRegion, 14 + 37 * i, 14, 2, 37 * (cstage.getHeight()));
        }
        for (int i = 0; i < cstage.getHeight() + 1; i++)
        {
            fb.draw(gridRegion, 14, 14 + 37 * i, 37 * (cstage.getWidth()), 2);
        }

        for (int x = 0; x < cstage.getWidth(); x++)
        {
            for (int y = 0; y < cstage.getHeight(); y++)
            {
                Tile ct = cstage.getTile(x, y);
                if (ct != null)
                    fb.draw(SpriteManager.getSprite(ct.getTid()), 16 + 37 * x, 16 + 37 * y, 0, 0, 35, 35, 1f, 1f, 0f);
            }
        }

        fb.end();

        fbo.end();

        return fbo.getColorBufferTexture();
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        StringWriter json = new StringWriter();
        try
        {
            mapper.writeValue(json, map);
        } catch (JsonGenerationException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (JsonMappingException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(json);
        PrintStream output;
        try
        {
            output = new PrintStream(new File("map.json"));
            output.println(json);
            map.clean();
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void load() {
        try
        {
            String json = new String(Files.readAllBytes(Paths.get(("map.json"))));
            System.out.println(json);
            Gson gson = new Gson();
            map = gson.fromJson(json, GameMap.class);
            map.clean();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void finishInput() {
        switch (state)
        {
            case STATE_EDITSTAGE:
            case STATE_EDITNEWSTAGE:
                switch (editField)
                {
                    case 0:
                        curstage.setName(inputstr);
                        editField++;
                        inputstr = "";
                        return;
                    case 1:
                        curstage.setWidth(Integer.parseInt(inputstr));
                        editField++;
                        inputstr = "";
                        return;
                    case 2:
                        curstage.setHeight(Integer.parseInt(inputstr));
                        curstage.resize();
                        state = STATE_MAIN;
                }

                state = STATE_MAIN;
                break;
            case STATE_MAPSETTINGS:
                switch (editField)
                {
                    case 0:
                        map.setWidth(Integer.parseInt(inputstr));
                        editField++;
                        inputstr = "";
                        return;
                    case 1:
                        map.setHeight(Integer.parseInt(inputstr));
                        map.resize();
                        state = STATE_MAIN;
                }
        }
        editField = 0;
        isInputting = false;
        inputstr = "";
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isInputting)
        {
            switch (keycode)
            {
                case Keys.BACKSPACE:
                    if (inputstr.length() > 0)
                        inputstr = inputstr.substring(0, inputstr.length() - 1);
                    break;
                case Keys.ENTER:
                    finishInput();
                    break;
            }
        }
        if (this.inputter.enabled)
        {
            this.inputter.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == '\b')
            return false;
        if (character == '\n')
            return false;
        if (character == '\r')
            return false;
        if (isInputting)
            inputstr += character;
        if (inputter.enabled)
            inputter.keyTyped(character);
        if (state == STATE_STAGE_MAIN)
        {
            if ((character >= '0') && (character <= '9'))
            {
                this.curType = character - '0';
                System.out.println(curType);
                this.spritePalFB = null;
                this.spritePalScroll = 0;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.lastTouchButton = button;
        this.lastTouchX = screenX;
        this.lastTouchY = screenY;
        this.gestureScroll = false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT)
        {
            if (!gestureScroll)
            {
                processLeftTap(screenX, screenY);
            }
        }
        if (button == Buttons.RIGHT)
        {
            if (!gestureScroll)
            {
                processRightTap(screenX, screenY);
            }
        }
        return false;
    }

    private void processLeftTap(int screenX, int screenY) {
        switch (state)
        {
            case STATE_MAIN:
                processLeftTapStateMain(screenX, screenY);
                break;
            case STATE_NEWSTAGE:
                processLeftTapStateNewstage(screenX, screenY);
                break;
            case STATE_EDITSTAGE:
                processLeftTapStateEditstage(screenX, screenY);
                break;
            case STATE_STAGE_MAIN:
                processLeftTapStateStageMain(screenX, screenY);
                break;
        }
    }

    private void processRightTap(int screenX, int screenY) {
        switch (state)
        {
            case STATE_STAGE_MAIN:
                processRightTapStateStageMain(screenX, screenY);
                break;
        }
    }

    private void processLeftTapStateMain(int screenX, int screenY) {
        if ((screenY >= 16) && (screenY <= 36))
        {
            if ((screenX >= 10) && (screenX <= 90))
            {
                state = STATE_NEWSTAGE;
            }
            if ((screenX >= 100) && (screenX <= 195))
            {
                inputstr = "";
                isInputting = true;
                state = STATE_MAPSETTINGS;
            }
            if ((screenX >= 205) && (screenX <= 250))
            {
                load();
            }
            if ((screenX >= 260) && (screenX <= 260 + (map.isDirty() ? 57 : 45)))
            {
                save();
            }

        }
        if ((screenX >= 12) && (screenX <= 12 + 16 * map.getWidth()))
        {
            if ((SCREEN_HEIGHT - screenY >= 12) && (SCREEN_HEIGHT - screenY <= 12 + 16 * map.getHeight()))
            {
                int x = (screenX - 12) / 16;
                int y = (SCREEN_HEIGHT - screenY - 12) / 16;
                if (map.getStage(x, y) == null)
                    return;
                curstageX = x;
                curstageY = y;
                curstage=map.getStage(curstageX, curstageY);
                inputstr = curstage.getName();
                isInputting = true;
                state = STATE_EDITSTAGE;
            }
        }
    }

    private void processLeftTapStateNewstage(int screenX, int screenY) {
        if ((screenX >= 12) && (screenX <= 12 + 16 * map.getWidth()))
        {
            if ((SCREEN_HEIGHT - screenY >= 12) && (SCREEN_HEIGHT - screenY <= 12 + 16 * map.getHeight()))
            {
                int x = (screenX - 12) / 16;
                int y = (SCREEN_HEIGHT - screenY - 12) / 16;
                if (map.getStage(x, y) != null)
                    return;
                Stage st = new Stage(map.getNextID(), x, y, "UNNAMED", 0, 0);
                for (int i = 0; x < st.getWidth(); x++)
                {
                    for (int j = 0; y < st.getHeight(); y++)
                    {
                        st.setTile(i, j, new Tile(i));
                    }
                }
                map.addStage(st);

                curstageX = x;
                curstageY = y;
                curstage=map.getStage(curstageX, curstageY);
                inputstr = "";
                isInputting = true;
                state = STATE_EDITNEWSTAGE;
            }
        }
    }

    private void processLeftTapStateEditstage(int screenX, int screenY) {
        if ((screenY >= 16) && (screenY <= 36))
        {
            if ((screenX >= 10) && (screenX <= 45))
            {
                state = STATE_STAGE_MAIN;
            }
        }
    }

    private void processLeftTapStateStageMain(int screenX, int screenY) {
        if ((screenY >= 16) && (screenY <= 36))
        {
            if ((screenX >= 10) && (screenX <= 55))
            {
                state = STATE_MAIN;
            }
        }
        if (screenX > SCREEN_WIDTH - 195)
        {
            this.selSprite = ((screenX - (SCREEN_WIDTH - 195)) / 40 + 5 * ((screenY - spritePalScroll) / 40)) - 1;
            this.spritePalFB = null;
        }
        if ((screenX > 16) && (SCREEN_HEIGHT - screenY > 16)
                && (screenX < 16 + 37 * curstage.getWidth())
                && (SCREEN_HEIGHT - screenY < 16 + 37 * curstage.getHeight()))
        {
            int tx = (screenX - 16) / 37;
            int ty = (SCREEN_HEIGHT - screenY - 16) / 37;
            Tile t;
            if (this.selSprite == -1)
                t = null;
            else
                t = new Tile(SpriteManager.getSpritesType(this.curType).get(this.selSprite));
            curstage.setTile(tx, ty, t);
            this.spriteMapFB = null;
        }
    }

    private void processRightTapStateStageMain(int screenX, int screenY) {
        if ((screenX > 16) && (SCREEN_HEIGHT - screenY > 16)
                && (screenX < 16 + 37 * curstage.getWidth())
                && (SCREEN_HEIGHT - screenY < 16 + 37 * curstage.getHeight()))
        {
            int tx = (screenX - 16) / 37;
            int ty = (SCREEN_HEIGHT - screenY - 16) / 37;
            this.curtileX = tx;
            this.curtileY = ty;
            this.curtile=curstage.getTile(this.curtileX, this.curtileY);
            String[][] ps=ParamSets.sets[SpriteManager.getSpriteParamSet(this.curtile.getTid())];
            state = STATE_STAGE_EDITTILE;
            this.inputter.begin(this.curtile, ps, STATE_STAGE_MAIN);
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        switch (state)
        {
            case STATE_STAGE_MAIN:
                if (lastTouchButton == Buttons.LEFT)
                {
                    if (lastTouchX > SCREEN_WIDTH - 195)
                    {
                        if (Math.abs(screenY - lastTouchY) > 70)
                        {
                            gestureScroll = true;
                            lastTouchY = screenY;
                        }
                        if (gestureScroll)
                        {
                            spritePalScroll += (screenY - lastTouchY);
                            lastTouchY = screenY;
                        }
                    }
                }

        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, width, height);
        batch.setProjectionMatrix(matrix);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
