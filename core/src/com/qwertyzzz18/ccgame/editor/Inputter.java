package com.qwertyzzz18.ccgame.editor;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inputter {

    private String[][] ps;
    private Tile       tile;
    private int        curField;
    private int        returnstate;
    public boolean     enabled;
    private String     inputstr;

    public Inputter() {
        this.enabled = false;
    }

    public void begin(Tile tile, String[][] ps, int returnstate) {
        this.tile = tile;
        this.ps = ps;
        this.returnstate = returnstate;
        this.curField = 0;
        this.enabled = true;
        this.inputstr="";
        if (this.ps.length==0)
        {
            this.curField=0;
            this.inputstr=this.ps[this.curField][2];
            MyGdxGame.state=this.returnstate;
            this.enabled=false;
        }
        if (this.tile.getParam(ps[curField][1])!=null)
        {
            this.inputstr=this.tile.getParam(ps[curField][1]);
        }
    }

    public void keyDown(int keycode) {
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
    public void keyTyped(char character) {
        if (character == '\b')
            return;
        if (character == '\n')
            return;
        if (character == '\r')
            return;
        inputstr += character;
    }

    public void finishInput() {
        tile.setParam(ps[curField][1], inputstr);
        curField++;
        if (curField==ps.length)
        {
            this.curField=0;
            this.inputstr="";
            MyGdxGame.state=this.returnstate;
            this.enabled=false;
        }
        if (this.tile.getParam(ps[curField][1])!=null)
        {
            this.inputstr=this.tile.getParam(ps[curField][1]);
        }
    }

    public void draw(SpriteBatch batch) {
        if (!this.enabled)
            return;
        MyGdxGame.glyph.setText(MyGdxGame.font, ps[curField][0]+'>' + inputstr);
        batch.draw(MyGdxGame.buttonRegion, 10, MyGdxGame.SCREEN_HEIGHT - 36, 35, 20);
        MyGdxGame.font.draw(batch, "Edit", 16, MyGdxGame.SCREEN_HEIGHT - 20);
        batch.draw(MyGdxGame.buttonRegion, 55, MyGdxGame.SCREEN_HEIGHT - 36, MyGdxGame.glyph.width + (MyGdxGame.ticks % 60 < 30 ? 10 : 15), 20);
        MyGdxGame.font.draw(batch, MyGdxGame.glyph, 61, MyGdxGame.SCREEN_HEIGHT - 20);
    }

}
