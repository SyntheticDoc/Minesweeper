package com.gameobject;

import com.gameobject.enums.FIELDSTATE;
import com.minesweeper.StdDraw;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Playfield extends Gameobject{
    public final int sizeX;
    public final int sizeY;
    private final double FIELD_SIZE;
    private final double FIELD_HALF_SIZE;
    private final double MAX_X;
    private final double MAX_Y;
    Field[][] playfield;
    int fieldIndex = 0;
    public int mineCount;

    Font gameConditionMessage = new Font("Arial", Font.BOLD, 38);
    Color gameConditionColorGameover = Color.RED;
    Color textBackgroundColorGameover = Color.ORANGE;
    Color gameConditionColorVictory = Color.BLUE;
    Color textBackgroundColorVictory = Color.CYAN;
    private double textPosX, textPosY;

    private boolean minesCreated = false;
    public boolean isGameOver = false;
    public boolean isWon = false;

    public Playfield(String name, int sizeX, int sizeY, int mineCount, double max_x, double max_y, double fieldSize) {
        super(name);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.MAX_X = max_x;
        this.MAX_Y = max_y;
        this.FIELD_SIZE = fieldSize;
        this.mineCount = mineCount;

        FIELD_HALF_SIZE = (FIELD_SIZE / 2) + 1;

        textPosX = MAX_X / 2;
        textPosY = (MAX_Y / 2) - 4;

        generateField();
    }

    public void gameOver() {
        isGameOver = true;

        for(Field[] ff : playfield) {
            for(Field f : ff) {
                f.reveal();
            }
        }
    }

    public Field[] getSurroundingFields(Field f) {
        Field[] result = new Field[8];
        int[] field_indices = getFieldIndices(f);
        int curX = field_indices[1], curY = field_indices[0];
        int index = 0;

        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                if((curX + x) >= 0 && (curX + x) < sizeX && (curY + y) >= 0 && (curY + y) < sizeY && (x != 0 || y != 0)) {
                    result[index] = playfield[curY + y][curX + x];
                    ++index;
                }
            }
        }

        return result;
    }

    private int[] getFieldIndices(Field f) {
        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                if(playfield[y][x].equals(f)) {
                    return new int[]{y, x};
                }
            }
        }

        return null;
    }

    public void reveal(Field f) {
        if(!isGameOver && !isWon) {
            f.reveal();
        }
    }

    public void buttonReleaseAll() {
        if(!isGameOver && !isWon) {
            for (Field[] ff : playfield) {
                for (Field f : ff) {
                    f.release();
                }
            }
        }
    }

    public Field buttonRelease(MouseClickWrapper click) {
        if(!isGameOver && !isWon) {
            for (Field[] ff : playfield) {
                for (Field f : ff) {
                    if (f.isHit(click)) {
                        f.release();

                        if(!minesCreated) {
                            generateMines(f);
                            minesCreated = true;
                        }

                        return f;
                    }
                }
            }
        }

        return null;
    }

    public Field buttonDepress(MouseClickWrapper click) {
        if(!isGameOver && !isWon) {
            for (Field[] ff : playfield) {
                for (Field f : ff) {
                    if (f.isHit(click)) {
                        f.depress(click);
                        return f;
                    }
                }
            }
        }

        return null;
    }

    private void generateField() {
        playfield = new Field[sizeY][sizeX];

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                playfield[y][x] = new Field("Field" + fieldIndex, "field_unrevealed.png",
                        FIELD_HALF_SIZE + (x * FIELD_SIZE), MAX_Y - FIELD_HALF_SIZE - (y * FIELD_SIZE), this);
                playfield[y][x].setState(FIELDSTATE.FIELDUNREVEALED);
                ++fieldIndex;
            }
        }
    }

    private void generateMines(Field f) {
        int mineX, mineY;

        for (int i = 0; i < mineCount; i++) {
            mineX = ThreadLocalRandom.current().nextInt(0, sizeX);
            mineY = ThreadLocalRandom.current().nextInt(0, sizeY);

            // if mine is already set at this position, generate new position
            while (playfield[mineY][mineX].isMine || playfield[mineY][mineX].equals(f)) {
                mineX = ThreadLocalRandom.current().nextInt(0, sizeX);
                mineY = ThreadLocalRandom.current().nextInt(0, sizeY);
            }

            playfield[mineY][mineX].isMine = true;
        }
    }

    public void isVictory() {
        for(Field[] ff : playfield) {
            for(Field f : ff) {
                if(f.getState() == FIELDSTATE.FIELDUNREVEALED || (f.getState() == FIELDSTATE.FIELDFLAGGED && !f.isMine)) {
                    return;
                }
            }
        }

        buttonReleaseAll(); // to release all buttons if some still appear pressed pressed
        isWon = true;
    }

    public void draw() {
        for(Field[] ff : playfield) {
            for(Field f : ff) {
                f.draw();
            }
        }

        if(isWon) {
            StdDraw.setPenColor(textBackgroundColorVictory);
            StdDraw.filledRectangle(MAX_X / 2, MAX_Y / 2, MAX_X, MAX_Y / 10);
            StdDraw.setFont(gameConditionMessage);
            StdDraw.setPenColor(gameConditionColorVictory);
            StdDraw.text(textPosX, textPosY, "VICTORY");
        } else if (isGameOver) {
            StdDraw.setPenColor(textBackgroundColorGameover);
            StdDraw.filledRectangle(MAX_X / 2, MAX_Y / 2, MAX_X, MAX_Y / 10);
            StdDraw.setFont(gameConditionMessage);
            StdDraw.setPenColor(gameConditionColorGameover);
            StdDraw.text(textPosX, textPosY, "GAMEOVER");
        }
    }

    @Override
    public String toString() {
        StringBuilder pf = new StringBuilder();
        final String unrevealed = "[ ]";
        final String flagged = "[F]";
        final String empty = "[-]";
        final String mine = "[m]";
        String print;

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                if(playfield[y][x].isMine) {
                    print = mine;
                } else {
                    switch (playfield[y][x].getState()) {
                        case FIELDMINE:
                        case FIELDMINECLICKED:
                            print = mine;
                            break;
                        case FIELD1:
                            print = "[1]";
                            break;
                        case FIELD2:
                            print = "[2]";
                            break;
                        case FIELD3:
                            print = "[3]";
                            break;
                        case FIELD4:
                            print = "[4]";
                            break;
                        case FIELD5:
                            print = "[5]";
                            break;
                        case FIELD6:
                            print = "[6]";
                            break;
                        case FIELD7:
                            print = "[7]";
                            break;
                        case FIELD8:
                            print = "[8]";
                            break;
                        case FIELDEMPTY:
                            print = empty;
                            break;
                        case FIELDUNREVEALED:
                        case FIELDUNREVEALEDDEPRESSED:
                            print = unrevealed;
                            break;
                        case FIELDFLAGGED:
                        case FIELDFLAGGEDDEPRESSED:
                        case FIELDFlAGGEDWRONG:
                            print = flagged;
                            break;
                        default:
                            throw new IllegalArgumentException("ERROR in Field.setState(): Unknown state " + playfield[y][x].getState());
                    }
                }

                pf.append(print);
            }

            pf.append("\n");
        }

        return "PLAYFIELD:\n" + pf;
    }
}
