package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.gui.intersection.IntersectionValue;

public class MenuScreen implements Screen {
    private Stage stage;
    private Game game;

    public MenuScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Image img = new Image(new Texture("gomoku.png"));
        stage.addActor(img);
        img.setPosition(Gdx.graphics.getWidth() / 2 - img.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("myfont2.fnt"));
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.overFontColor = new Color(231 / 255f, 135 / 255f, 115 / 255f, 1);
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.checkedFontColor = new Color(231 / 255f, 135 / 255f, 115 / 255f, 1);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("myfont2.fnt"));
        labelStyle.fontColor = new Color(100 / 255f, 114 / 255f, 234 / 255f, 1);

        Label label = new Label("Choose color!", labelStyle);
        label.setPosition(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2,
                Gdx.graphics.getHeight() / 3 + label.getHeight());
        stage.addActor(label);


        Table table = new Table();

        final TextButton black = new TextButton("Black", textButtonStyle);
//        black.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        stage.addActor(black);


        final TextButton white = new TextButton("White", textButtonStyle);
        white.setChecked(true);
//        white.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        stage.addActor(white);

        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>();
        buttonGroup.add(white);
        buttonGroup.add(black);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setUncheckLast(true);

        table.add(white).pad(10);
        table.add(black);
        table.align(Align.center|Align.top);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3);
        stage.addActor(table);


        TextButton playButton = new TextButton("Play!", textButtonStyle);
        playButton.setWidth(100);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 5 - playButton.getHeight() / 2);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, (black.isChecked()) ? IntersectionValue.BLACK : IntersectionValue.WHITE));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        TextButton optionsButton = new TextButton("Exit", textButtonStyle);
        optionsButton.setWidth(Gdx.graphics.getWidth() / 2);
        optionsButton.setPosition(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2, Gdx.graphics.getHeight() / 7 - optionsButton.getHeight());
        optionsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
