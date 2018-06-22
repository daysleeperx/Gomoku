package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.gui.board.Board;
import com.mygdx.game.gui.board.BoardGrid;

public class GdxGame extends ApplicationAdapter {
	private Stage stage;
	private Board board;
	private BoardGrid grid;

	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		board = new Board();
		grid = new BoardGrid();
		board.setPosition(Gdx.graphics.getWidth() / 2 - board.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - board.getHeight() / 2);
		grid.setPosition(Gdx.graphics.getWidth() / 2 - grid.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - grid.getHeight() / 2 );
		stage.addActor(board);
		stage.addActor(grid);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();

	}
}
