package com.gameobject;

public class Header extends Gameobject {
    Digital_Display_Digit[] leftDigits = new Digital_Display_Digit[3];
    Digital_Display_Digit[] rightDigits = new Digital_Display_Digit[3];
    MenuButton smiley_happy;
    MenuButton smiley_dead;

    boolean gameOver = false;

    private final int SCREEN_X_MAX, SCREEN_Y_MAX, SCREEN_TOP_OFFSET;
    private final int HEADER_Y_CENTER;
    private final int LEFTDIGITSTART_X = 20;
    private final int RIGHTDIGITSTART_X;
    private final int DIGIT_WIDTH = 20;
    private final int DIGIT_HEIGHT = 35;
    private final int SMILEY_WIDTH = 35;
    private final int SMILEY_HEIGHT = 35;

    public Header(int screen_x_max, int screen_y_max, int screen_top_offset) {
        super("Header");
        this.SCREEN_X_MAX = screen_x_max;
        this.SCREEN_Y_MAX = screen_y_max;
        this.SCREEN_TOP_OFFSET = screen_top_offset;

        RIGHTDIGITSTART_X = SCREEN_X_MAX - LEFTDIGITSTART_X - (2 * DIGIT_WIDTH);
        HEADER_Y_CENTER = SCREEN_Y_MAX + (SCREEN_TOP_OFFSET / 2);

        leftDigits[2] = new Digital_Display_Digit(LEFTDIGITSTART_X, HEADER_Y_CENTER);
        leftDigits[1] = new Digital_Display_Digit(LEFTDIGITSTART_X + DIGIT_WIDTH, HEADER_Y_CENTER);
        leftDigits[0] = new Digital_Display_Digit(LEFTDIGITSTART_X + (2 * DIGIT_WIDTH), HEADER_Y_CENTER);

        rightDigits[2] = new Digital_Display_Digit(RIGHTDIGITSTART_X, HEADER_Y_CENTER);
        rightDigits[1] = new Digital_Display_Digit(RIGHTDIGITSTART_X + DIGIT_WIDTH, HEADER_Y_CENTER);
        rightDigits[0] = new Digital_Display_Digit(RIGHTDIGITSTART_X + (2 * DIGIT_WIDTH), HEADER_Y_CENTER);

        smiley_happy = new MenuButton("smiley", AssetHandler.smiley_happy, AssetHandler.smiley_happy_depressed,
                                      SCREEN_X_MAX / 2.0, HEADER_Y_CENTER, SMILEY_WIDTH, SMILEY_HEIGHT);
        smiley_dead = new MenuButton("smiley", AssetHandler.smiley_dead, AssetHandler.smiley_dead_depressed,
                                     SCREEN_X_MAX / 2.0, HEADER_Y_CENTER, SMILEY_WIDTH, SMILEY_HEIGHT);
    }

    public void setDigitDisplay_mines(int value) {
        setDigits(leftDigits, value);
    }

    public void setDigitDisplay_time(int value) {
        setDigits(rightDigits, value);
    }

    private void setDigits(Digital_Display_Digit[] digits, int value) {
        StringBuilder val = new StringBuilder(String.valueOf(value)).reverse();
        String cur;

        for(int i = 0; i < val.length(); i++) {
            cur = sanitizeValueString(String.valueOf(val.charAt(i)));
            digits[i].set(cur);
        }

        if(val.length() < 3) {
            digits[2].set("empty");
        }

        if(val.length() < 2) {
            digits[1].set("empty");
        }
    }

    private String sanitizeValueString(String cur) {
        if(cur.isEmpty()) {
            return "empty";
        } else if (cur.equals("-")) {
            return "minus";
        } else {
            return cur;
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void buttonReleaseAll() {
        smiley_happy.release();
        smiley_dead.release();
    }

    public MenuButton buttonRelease(MouseClickWrapper click) {
        if(smiley_happy.isHit(click)) {
            smiley_happy.release();
            smiley_dead.release();

            if(gameOver) {
                return smiley_dead;
            } else {
                return smiley_happy;
            }
        } else {
            return null;
        }
    }

    public MenuButton buttonDepress(MouseClickWrapper click) {
        if(smiley_happy.isHit(click)) {
            smiley_happy.depress();
            smiley_dead.depress();

            if(gameOver) {
                return smiley_dead;
            } else {
                return smiley_happy;
            }
        } else {
            return null;
        }
    }

    public boolean isInHeader(MouseClickWrapper click) {
        return click.y > SCREEN_Y_MAX;
    }

    public void draw() {
        for(Digital_Display_Digit d : leftDigits) {
            d.draw();
        }

        for(Digital_Display_Digit d : rightDigits) {
            d.draw();
        }

        if(gameOver) {
            smiley_dead.draw();
        } else {
            smiley_happy.draw();
        }
    }
}
