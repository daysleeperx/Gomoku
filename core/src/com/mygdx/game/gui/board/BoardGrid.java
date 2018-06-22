package com.mygdx.game.gui.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Represent Board Grid.
 */
public class BoardGrid extends Actor {
    private static final int SQUARE_SIZE = 39;
    private static final int NUMBER_OF_SQUARES = 14;
    private static final int LINE_WIDTH = 3;
    private ShapeRenderer shape;
    private int width, height;

    public BoardGrid() {
        this.width = SQUARE_SIZE * NUMBER_OF_SQUARES;
        this.height = SQUARE_SIZE * NUMBER_OF_SQUARES;
        this.shape = new ShapeRenderer();

        shape.setAutoShapeType(true);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Draws the actor. The batch is configured to draw in the parent's coordinate system.
     * {@link Batch#draw(TextureRegion, float, float, float, float, float, float, float, float, float)
     * This draw method} is convenient to draw a rotated and scaled TextureRegion. {@link Batch#begin()} has already been called on
     * the batch. If {@link Batch#end()} is called to draw without the batch then {@link Batch#begin()} must be called before the
     * method returns.
     * <p>
     * The default implementation does nothing.
     *
     * @param batch
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLACK);
        for (int row = 0; row <= NUMBER_OF_SQUARES; row++) {
            for (int col = 0; col <= NUMBER_OF_SQUARES; col++) {
                shape.rectLine(getX(), getY() + row * SQUARE_SIZE,
                        getX() + width, getY() + row * SQUARE_SIZE, LINE_WIDTH);
                shape.rectLine(getX() + col * SQUARE_SIZE, getY(),
                        getX() + col * SQUARE_SIZE, getY() + height, LINE_WIDTH);
            }
        }
        shape.end();
        shape.setColor(Color.WHITE); // reset color

        batch.begin();
    }
}
