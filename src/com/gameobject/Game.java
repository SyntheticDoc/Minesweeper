package com.gameobject;

import com.minesweeper.GraphicsModule;

public class Game {
    private Header header;
    private Playfield playfield;
    private GraphicsModule g;
    public String difficulty;

    public void newGame(String difficulty) {
        int[] d = getDifficulty(difficulty);
        this.difficulty = difficulty;

        playfield = g.getNewPlayfield(d, difficulty);
        header.setGameOver(false);
        debugPlayfield();
    }

    public void setGraphicsModule(GraphicsModule g) {
        this.g = g;
    }

    public void setDigitDisplay_mines(int value) {
        header.setDigitDisplay_mines(value);
    }

    public void setDigitDisplay_time(int value) {
        header.setDigitDisplay_time(value);
    }

    public boolean isInHeader(MouseClickWrapper click) {
        return header.isInHeader(click);
    }

    public void setHeader(Header h) {
        if(h != null) {
            header = h;
        } else {
            throw new NullPointerException("ERROR in Game.setHeader(): Header was null");
        }
    }

    public Header getHeader() {
        return header;
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public void setPlayfield(Playfield p) {
        if(p != null) {
            playfield = p;
        } else {
            throw new NullPointerException("ERROR in Game.setPlayfield(): Playfield was null");
        }
    }

    public void buttonReleaseAll() {
        header.buttonReleaseAll();
        playfield.buttonReleaseAll();
    }

    public boolean header_release(MouseClickWrapper click) {
        if(click == null) {
            return false;
        }

        return header.buttonRelease(click) != null;
    }

    public Gameasset buttonRelease(MouseClickWrapper click) {
        if(click == null) {
            return null;
        }

        if(isInHeader(click)) {
            if(header_release(click)) {
                newGame(difficulty);

                if(playfield.isGameOver) {
                    return header.smiley_dead;
                } else {
                    return header.smiley_happy;
                }
            } else {
                return null;
            }
        } else if(!playfield.isGameOver) {
            Field result = playfield.buttonRelease(click);
            debugPlayfield();

            if (playfield.isGameOver) {
                header.setGameOver(true);
            }
            return result;
        } else {
            return null;
        }
    }

    public Gameasset buttonDepress(MouseClickWrapper click) {
        if(click == null) {
            return null;
        }

        if(header.isInHeader(click)) {
            return header.buttonDepress(click);
        } else {
            if (!playfield.isGameOver) {
                return playfield.buttonDepress(click);
            } else {
                return null;
            }
        }
    }

    public void debugPlayfield() {
        Debug.print(playfield);
    }

    private int[] getDifficulty(String difficulty) {
        int[] result = new int[3];
        // result[0] - Number of fields in x-Direction
        // result[1] - Number of fields in y-Direction
        // result[2] - Number of mines

        switch(difficulty) {
            case "Easy":
                result[0] = 9;
                result[1] = 9;
                result[2] = 10;
                break;
            case "Medium":
                result[0] = 16;
                result[1] = 16;
                result[2] = 40;
                break;
            case "Hard":
                result[0] = 30;
                result[1] = 16;
                result[2] = 99;
                break;
            default:
                throw new IllegalArgumentException("ERROR in Main.getDifficulty(): Unknown difficulty setting " + difficulty);
        }

        return result;
    }
}
