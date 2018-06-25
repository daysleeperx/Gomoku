package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.gui.board.BoardFrame;
import com.mygdx.game.gui.board.BoardGrid;
import com.mygdx.game.gui.intersection.Intersection;
import com.mygdx.game.gui.intersection.IntersectionValue;

import static com.mygdx.game.gui.board.BoardGrid.SQUARE_SIZE;

public class GdxGame extends ApplicationAdapter implements InputProcessor {
    private Stage stage;
    private BoardFrame boardFrame;
    private BoardGrid grid;
    private IntersectionValue sideToMove;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        boardFrame = new BoardFrame();
        grid = new BoardGrid();
        sideToMove = IntersectionValue.WHITE;
        boardFrame.setPosition(Gdx.graphics.getWidth() / 2 - boardFrame.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - boardFrame.getHeight() / 2);
        grid.setPosition(Gdx.graphics.getWidth() / 2 - grid.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - grid.getHeight() / 2);
        stage.addActor(boardFrame);
        stage.addActor(grid);

        // add intersections
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                stage.addActor(new Intersection((int) (grid.getX() + col * SQUARE_SIZE), (int) (grid.getY() + row * SQUARE_SIZE)));
            }
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 coord = stage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
        Actor currentActor = stage.hit(coord.x, coord.y, false);

        if (currentActor != null && currentActor instanceof Intersection) {
            if (((Intersection) currentActor).isEmpty()) {
                ((Intersection) currentActor).setValue(sideToMove);
                sideToMove = (sideToMove == IntersectionValue.WHITE) ? IntersectionValue.BLACK: IntersectionValue.WHITE;
            }
        }
        return true;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


