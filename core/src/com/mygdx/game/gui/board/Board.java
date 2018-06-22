package com.mygdx.game.gui.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Represent Board frame.
 */
public class Board extends Actor {

    private ShapeRenderer shape;
    private float width, height;

    public Board() {
        this.shape = new ShapeRenderer();
        this.shape.setAutoShapeType(true);

        this.width = 600;
        this.height = 600;
        setSize(width, height);
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
        shape.setColor(250 / 255f, 152 / 255f, 68 / 255f, 1);
        shape.rect(getX(), getY(), width, height);
        shape.end();
        shape.setColor(Color.WHITE); // reset color

        batch.begin();
    }
}
