package com.gameobject;

public class MouseClickWrapper {
    public double x, y;
    public int buttonClicked;

    public MouseClickWrapper(double mouseX, double mouseY, int buttonClicked) {
        x = mouseX;
        y = mouseY;
        this.buttonClicked = buttonClicked;
    }

    @Override
    public String toString() {
        return "MouseClick[x = " + x + ", y = " + y + ", buttonClicked = " + buttonClicked + "]";
    }
}
