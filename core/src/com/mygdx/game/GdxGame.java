package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.MenuScreen;

/**
 * Main Gomoku Game class.
 */

public class GdxGame extends com.badlogic.gdx.Game {
    /**
     * Game skin associated with the game.
     */
    public static Skin gameSkin;


    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        gameSkin = new Skin(Gdx.files.internal("uiskin.json"));
        this.setScreen(new MenuScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}





