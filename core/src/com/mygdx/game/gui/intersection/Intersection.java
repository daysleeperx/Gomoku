package com.mygdx.game.gui.intersection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Represent Intersection class.
 * The intersections hold the stones in the game.
 */
public class Intersection extends Actor {
    /**
     * Dimensions.
     */
    private int x, y, width, height;
    /**
     * ShapeRenderer object used to draw the stones.
     */
    private ShapeRenderer shape;
    /**
     * Current value of intersection. Can be white, black or empty.
     */
    private IntersectionValue value;
    /**
     * Row and column used for intersections array.
     */
    private int row, col;

    /**
     * Class constructor.
     *
     * @param y int
     * @param x int
     */
    public Intersection(int y, int x) {
        this.width = 20;
        this.height = 20;
        this.x = x;
        this.y = y;
        this.value = IntersectionValue.EMPTY;
        this.shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        setPosition(this.x, this.y);
        setBounds(x - width / 2, y - height / 2, width, height);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public IntersectionValue getValue() {
        return value;
    }

    public void setValue(IntersectionValue value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == IntersectionValue.EMPTY;
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

        // draws the marked intersections in the middle and corners of the board
        if ((row == 3 && col == 3) || (row == 3 && col == 11)
                || (row == 7 && col == 7) || (row == 11 && col == 3)
                || (row == 11 && col == 11)) {
            shape.setColor(Color.BLACK);
            shape.rect(getX() + width / 4, getY() + height / 4, width / 2, height / 2);
        }

        if (!isEmpty()) {
            shape.setColor((value == IntersectionValue.WHITE) ? Color.WHITE : Color.BLACK);
            shape.circle(getX() + width / 2, getY() + height / 2, 18);
        }
        shape.end();
        batch.begin();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", y, x);
    }
}
