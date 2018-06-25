package com.mygdx.game.gui.intersection;

public enum IntersectionValue {
    EMPTY(0),
    WHITE(1),
    BLACK(-1);

    private int value;

    IntersectionValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
