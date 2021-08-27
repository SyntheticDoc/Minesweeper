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

    public void depress(int mouseButton) {
        if(!depressed && mouseButton == 1) {
            depressed = true;
            assetFile = assetFile_depressed;
        }
    }

    public void release(int mouseButton) {
        if(depressed && mouseButton == 1) {
            depressed = false;
            assetFile = assetFile_normal;
        }
    }
}
