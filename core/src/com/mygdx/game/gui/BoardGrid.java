package com.mygdx.game.gui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Represent Board Grid.
 */

public class BoardGrid {
    private static final int SQUARE_SIZE = 37;
    private static final int WIDTH = 14;
    private static final int HEIGHT = 14;
    private static final float LINE_WIDTH = 3;
    private static final int OFFSET_HEIGHT = 100;
    private static final int OFFSET_WIDTH = 135;

    public int width, height;

    public BoardGrid() {
        width = WIDTH * SQUARE_SIZE;
        height = HEIGHT * SQUARE_SIZE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {

        return height;
    }

    /**
     * Draw the board grid using ShapeRendered to screen.
     *
     * @param renderer ShapeRenderer object
     */
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);

        for (int row = 0; row <= HEIGHT; row++) {
            for (int col = 0; col <= WIDTH; col++) {
                renderer.rectLine(new Vector2(col * SQUARE_SIZE + OFFSET_WIDTH, OFFSET_HEIGHT), new Vector2(col * SQUARE_SIZE + OFFSET_WIDTH, height + OFFSET_HEIGHT), LINE_WIDTH);
                renderer.rectLine(new Vector2(OFFSET_WIDTH, row * SQUARE_SIZE + OFFSET_HEIGHT), new Vector2(width + OFFSET_WIDTH, row * SQUARE_SIZE + OFFSET_HEIGHT), LINE_WIDTH);
            }
        }

    }
}
