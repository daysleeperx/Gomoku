package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.gui.BoardGrid;

public class GdxGame extends ApplicationAdapter {
	Stage stage;
	ShapeRenderer shape;
	BoardGrid grid;

	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		shape = new ShapeRenderer();
		shape.setAutoShapeType(true);
		grid = new BoardGrid();
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(250 / 255f, 152 / 255f, 68 / 255f, 1);
		shape.rect(110, 70, grid.getWidth() * 1.1f, grid.getHeight() * 1.1f);
		shape.end();

		shape.begin();
		shape.setColor(Color.BLACK);
		grid.render(shape);
		shape.end();
	}
	
	@Override
	public void dispose () {
		shape.dispose();

	}
}
