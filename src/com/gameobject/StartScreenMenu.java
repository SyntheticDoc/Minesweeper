package com.gameobject;

import com.minesweeper.StdDraw;

import java.awt.*;

public class StartScreenMenu extends Gameobject {
    MenuButton btn_easy, btn_medium, btn_hard, btn_exit;

    private final int START_SCREEN_X_MAX;
    private final int START_SCREEN_Y_MAX;
    private final Font menuText_normal = new Font("Arial", Font.BOLD, 14);
    private final Font menuText_small = new Font("Arial", Font.BOLD, 12);

    private final String menuText1 = "Choose a difficulty below to start game";
    private final String menuText2 = "Press ESC to end game at any time";
    private final String menuText3 = "Graphics engine by";
    private final String menuText4 = "https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html";

    public StartScreenMenu(int screen_x_max, int screen_y_max) {
        super("StartScreenMenu");
        START_SCREEN_X_MAX = screen_x_max;
        START_SCREEN_Y_MAX = screen_y_max;

        btn_easy = new MenuButton("btn_easy", AssetHandler.btn_easy, AssetHandler.btn_easy_depressed,
                                  START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 100, 130, 30);
        btn_medium = new MenuButton("btn_medium", AssetHandler.btn_medium, AssetHandler.btn_medium_depressed,
                START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 200, 130, 30);
        btn_hard = new MenuButton("btn_hard", AssetHandler.btn_hard, AssetHandler.btn_hard_depressed,
                START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 300, 130, 30);
        btn_exit = new MenuButton("btn_exit", AssetHandler.btn_exit, AssetHandler.btn_exit_depressed,
                START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 400, 130, 30);
    }

    public void buttonReleaseAll() {
        btn_easy.release(1);
        btn_medium.release(1);
        btn_hard.release(1);
        btn_exit.release(1);
    }

    public MenuButton buttonRelease(MouseClickWrapper click) {
        if (btn_easy.isHit(click)) {
            //Debug.print("Clicked: btn_easy");
            btn_easy.release(click.buttonClicked);
            return btn_easy;
        } else if (btn_medium.isHit(click)) {
            //Debug.print("Clicked: btn_medium");
            btn_medium.release(click.buttonClicked);
            return btn_medium;
        } else if (btn_hard.isHit(click)) {
            //Debug.print("Clicked: btn_hard");
            btn_hard.release(click.buttonClicked);
            return btn_hard;
        } else if (btn_exit.isHit(click)) {
            //Debug.print("Clicked: btn_exit");
            btn_exit.release(click.buttonClicked);
            return btn_exit;
        } else {
            return null;
        }
    }

    public MenuButton buttonDepress(MouseClickWrapper click) {
        if (btn_easy.isHit(click)) {
            //Debug.print("Depressed: btn_easy");
            btn_easy.depress(click.buttonClicked);
            return btn_easy;
        } else if (btn_medium.isHit(click)) {
            //Debug.print("Depressed: btn_medium");
            btn_medium.depress(click.buttonClicked);
            return btn_medium;
        } else if (btn_hard.isHit(click)) {
            //Debug.print("Depressed: btn_hard");
            btn_hard.depress(click.buttonClicked);
            return btn_hard;
        } else if (btn_exit.isHit(click)) {
            //Debug.print("Depressed: btn_exit");
            btn_exit.depress(click.buttonClicked);
            return btn_exit;
        } else {
            return null;
        }
    }

    public void draw() {
        StdDraw.setFont(menuText_normal);
        StdDraw.text(START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 20, menuText1);
        StdDraw.text(START_SCREEN_X_MAX / 2.0, START_SCREEN_Y_MAX - 40, menuText2);

        btn_easy.draw();
        btn_medium.draw();
        btn_hard.draw();
        btn_exit.draw();

        StdDraw.setFont(menuText_small);
        StdDraw.text(START_SCREEN_X_MAX / 2.0, 40, menuText3);
        StdDraw.text(START_SCREEN_X_MAX / 2.0, 20, menuText4);
    }
}
