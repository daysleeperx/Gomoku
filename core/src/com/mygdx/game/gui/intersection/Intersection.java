package com.mygdx.game.gui.intersection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Represent Intersection class.
 */
public class Intersection extends Actor {
    private int x, y, width, height;
    private ShapeRenderer shape;
    private boolean clicked;
    private IntersectionValue value;

    public Intersection(int x, int y) {
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

    public boolean isClicked() {
        return clicked;
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
//        batch.end();
//
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        shape.setColor(Color.WHITE);
//        shape.rect(getX(), getY(), width, height);
//        shape.end();
//
//        batch.begin();

        if (!isEmpty()) {
            batch.end();
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor((value == IntersectionValue.WHITE) ? Color.WHITE: Color.BLACK);
            shape.circle(getX() + width / 2, getY() + height / 2, 15);
            shape.end();

            batch.begin();
        }
    }
}
