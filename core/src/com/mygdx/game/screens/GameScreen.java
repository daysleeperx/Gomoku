package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GdxGame;
import com.mygdx.game.board.Board;
import com.mygdx.game.game.Game;
import com.mygdx.game.gui.board.BoardFrame;
import com.mygdx.game.gui.board.BoardGrid;
import com.mygdx.game.gui.intersection.Intersection;
import com.mygdx.game.gui.intersection.IntersectionValue;
import com.mygdx.game.move.Coordinate;
import com.mygdx.game.player.Computer;

import static com.mygdx.game.gui.board.BoardGrid.SQUARE_SIZE;

/**
 * Main Game Screen.
 */

public class GameScreen implements InputProcessor, Screen {
    /**
     * Current Game,
     */
    private Game logicGame;
    private com.badlogic.gdx.Game game;
    private Stage stage;
    private IntersectionValue sideToMove;
    /**
     * Current player (Human):
     */
    private IntersectionValue currentPlayer;

    /**
     * Computer opponent.
     */
    private Computer computer;

    /**
     * Board associated with the logicGame.
     */
    private Board board;

    /**
     * Array of intersections to make/ keep track of changes.
     */
    private Intersection[][] intersections;

    public GameScreen(com.badlogic.gdx.Game aGame, IntersectionValue player) {
        game = aGame;
        logicGame = new Game();
        logicGame.createGame();
        board = logicGame.getBoard();
        intersections = new Intersection[board.getWidth()][board.getHeight()];
        computer = new Computer();
        currentPlayer = player;
        stage = new Stage(new ScreenViewport());
        BoardFrame boardFrame = new BoardFrame();
        BoardGrid grid = new BoardGrid();
        sideToMove = IntersectionValue.WHITE;
        boardFrame.setPosition(Gdx.graphics.getWidth() / 2 - boardFrame.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - boardFrame.getHeight() / 2);
        grid.setPosition(Gdx.graphics.getWidth() / 2 - grid.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - grid.getHeight() / 2);
        stage.addActor(boardFrame);
        stage.addActor(grid);


        TextButton optionsButton = new TextButton("Back", MenuScreen.textButtonStyle);
        optionsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);


        // add intersections
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getHeight(); col++) {
                Intersection current = new Intersection((int) (grid.getY() + row * SQUARE_SIZE), (int) (grid.getX() + col * SQUARE_SIZE));
                stage.addActor(current);
                intersections[row][col] = current;
                current.setRow(row);
                current.setCol(col);
            }
        }
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

        // player makes move
        if (!logicGame.isGameOver() && sideToMove == currentPlayer && currentActor != null && currentActor instanceof Intersection) {
            if (((Intersection) currentActor).isEmpty()) {
                ((Intersection) currentActor).setValue(currentPlayer);
                board.setValue(((Intersection) currentActor).getRow(), ((Intersection) currentActor).getCol(), currentPlayer.getValue());
                sideToMove = (sideToMove == IntersectionValue.WHITE) ? IntersectionValue.BLACK : IntersectionValue.WHITE;
                board.printBoard();
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

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

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

        // if side to move is not current player - make computer move
        if (!logicGame.isGameOver()) {
            if (sideToMove != currentPlayer) {
                Coordinate move = computer.getMove(board, sideToMove.getValue());
                Intersection intersection = intersections[move.getRow()][move.getCol()];
                intersection.setValue((currentPlayer == IntersectionValue.WHITE) ? IntersectionValue.BLACK : IntersectionValue.WHITE);
                board.setValue(move.getRow(), move.getCol(), currentPlayer.getValue() * -1);
                sideToMove = (sideToMove == IntersectionValue.WHITE) ? IntersectionValue.BLACK : IntersectionValue.WHITE;
                board.printBoard();
            }
        } else {
            new Dialog(logicGame.getStatus().toString().replace("_", " "), GdxGame.gameSkin) {
                {
                    button("").addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            game.setScreen(new GameScreen(game, currentPlayer));
                        }
                    });
                }

                /**
                 * Called when a button is clicked. The dialog will be hidden after this method returns unless {@link #cancel()} is called.
                 *
                 * @param object The object specified when the button was added.
                 */
                @Override
                protected void result(Object object) {
                    game.setScreen(new GameScreen(game, IntersectionValue.WHITE));
                }
            }.show(stage);
        }

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
        game.dispose();
    }
}

