package com.gameobject;

import com.minesweeper.StdDraw;

public class Gameasset extends Gameobject {
    public String assetFile;
    public double posX, posY;
    public double width, height;
    private double halfWidth, halfHeight;

    protected Gameasset(String name, String assetFile, double posX, double posY, double width, double height) {
        super(name);
        this.assetFile = assetFile;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        halfWidth = width / 2;
        halfHeight = height / 2;
    }

    public void draw() {
        StdDraw.picture(posX, posY, assetFile);
    }

    public boolean isHit(MouseClickWrapper click) {
        return isHit(click.x, click.y);
    }

    public boolean isHit(double mouseX, double mouseY) {
        return mouseX >= (posX - halfWidth) && mouseX <= (posX + halfWidth) &&
                mouseY >= (posY - halfHeight) && mouseY <= (posY + halfHeight);
    }

    @Override
    protected String enumerateAttributes() {
        return super.enumerateAttributes() + ",assetFile=" + assetFile + ",posX=" + posX + ",posY=" + posY +
                ",width=" + width + ",height=" + height;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
