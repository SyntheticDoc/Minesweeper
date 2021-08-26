package com.gameobject;

import com.minesweeper.StdDraw;

public class MenuButton extends Gameasset {
    boolean depressed = false;
    String assetFile_normal;
    String assetFile_depressed;

    public MenuButton(String name, String assetFile_normal, String assetFile_depressed, double posX, double posY, double width, double height) {
        super(name, assetFile_normal, posX, posY, width, height);
        this.assetFile_normal = assetFile_normal;
        this.assetFile_depressed = assetFile_depressed;
    }

    public void depress() {
        if(!depressed) {
            depressed = true;
            assetFile = assetFile_depressed;
        }
    }

    public void release() {
        if(depressed) {
            depressed = false;
            assetFile = assetFile_normal;
        }
    }
}
