package com.minesweeper;

import com.gameobject.*;

import java.awt.*;

public class GraphicsModule {
    private final int START_SCREEN_X_MAX = 500;
    private final int START_SCREEN_Y_MAX = 500;
    private final int FIELD_SIZE = 25;
    private final int GAME_SCREEN_EASY_X_MAX = 9*FIELD_SIZE;
    private final int GAME_SCREEN_EASY_Y_MAX = 9*FIELD_SIZE;
    private final int GAME_SCREEN_MEDIUM_X_MAX = 16*FIELD_SIZE;
    private final int GAME_SCREEN_MEDIUM_Y_MAX = 16*FIELD_SIZE;
    private final int GAME_SCREEN_HARD_X_MAX = 30*FIELD_SIZE;
    private final int GAME_SCREEN_HARD_Y_MAX = 16*FIELD_SIZE;
    private final int SCREEN_TOP_OFFSET = 50;
    private String difficulty;

    private int backgroundRGB = 230;

    Header header;
    StartScreenMenu startmenu;

    public GraphicsModule() {
        StdDraw.enableDoubleBuffering();
    }

    public void initStartScreen() {
        StdDraw.setCanvasSize(START_SCREEN_X_MAX, START_SCREEN_Y_MAX + SCREEN_TOP_OFFSET);
        StdDraw.setXscale(0, START_SCREEN_X_MAX);
        StdDraw.setYscale(0, START_SCREEN_Y_MAX + SCREEN_TOP_OFFSET);

        header = new Header(START_SCREEN_X_MAX, START_SCREEN_Y_MAX, SCREEN_TOP_OFFSET);
        startmenu = new StartScreenMenu(START_SCREEN_X_MAX, START_SCREEN_Y_MAX);

        drawStartScreen();
    }

    public void drawStartScreen() {
        refresh();
        drawHeader();
        startmenu.draw();

        StdDraw.show();
    }

    public void initPlayScreen(String difficulty) {
        int max_x, max_y;

        switch(difficulty) {
            case "Easy":
                max_x = GAME_SCREEN_EASY_X_MAX;
                max_y = GAME_SCREEN_EASY_Y_MAX;
                break;
            case "Medium":
                max_x = GAME_SCREEN_MEDIUM_X_MAX;
                max_y = GAME_SCREEN_MEDIUM_Y_MAX;
                break;
            case "Hard":
                max_x = GAME_SCREEN_HARD_X_MAX;
                max_y = GAME_SCREEN_HARD_Y_MAX;
                break;
            default:
                throw new IllegalArgumentException("ERROR in GraphicsModule.initPlayScreen(): Unknown difficulty " + difficulty);
        }

        StdDraw.setCanvasSize(max_x, max_y + SCREEN_TOP_OFFSET);
        StdDraw.setXscale(0, max_x);
        StdDraw.setYscale(0, max_y + SCREEN_TOP_OFFSET);

        Digital_Display_Digit.resetDigitNum();
        header = new Header(max_x, max_y, SCREEN_TOP_OFFSET);
    }

    public void drawPlayScreen(Playfield playfield) {
        refresh();
        drawHeader();
        playfield.draw();

        StdDraw.show();
    }

    private void drawHeader() {
        header.draw();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.003);
        StdDraw.line(0, START_SCREEN_Y_MAX, START_SCREEN_X_MAX, START_SCREEN_Y_MAX);
    }

    private void refresh() {
        StdDraw.clear(new Color(backgroundRGB, backgroundRGB, backgroundRGB));
    }

    public Playfield getNewPlayfield(int[] d, String difficulty) {
        int max_x, max_y;

        switch(difficulty) {
            case "Easy":
                max_x = GAME_SCREEN_EASY_X_MAX;
                max_y = GAME_SCREEN_EASY_Y_MAX;
                break;
            case "Medium":
                max_x = GAME_SCREEN_MEDIUM_X_MAX;
                max_y = GAME_SCREEN_MEDIUM_Y_MAX;
                break;
            case "Hard":
                max_x = GAME_SCREEN_HARD_X_MAX;
                max_y = GAME_SCREEN_HARD_Y_MAX;
                break;
            default:
                throw new IllegalArgumentException("ERROR in GraphicsModule.initPlayScreen(): Unknown difficulty " + difficulty);
        }

        return new Playfield("Playfield", d[0], d[1], d[2], max_x, max_y, FIELD_SIZE);
    }
}
