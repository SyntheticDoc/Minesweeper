package com.minesweeper;

import com.gameobject.*;

import java.time.Duration;
import java.time.LocalTime;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class MinesweeperGame {
    GraphicsModule g = new GraphicsModule();
    Game game = new Game();

    private static double mouseLastX = -1d;
    private static double mouseLastY = -1d;

    public void execute() {
        boolean exitGame = false;
        boolean gameRunning = false;
        boolean init = true;
        MenuButton buttonPressed = null;
        MenuButton buttonPressed_prev;
        Gameasset lastPressed = null;
        Gameasset lastPressed_prev = null;
        MouseClickWrapper click;
        String difficulty = "Unknown";

        int flagCount = 0;
        int flagChange = 0;

        LocalTime gameStart = null;
        LocalTime now;
        int seconds = 0;

        game.setGraphicsModule(g);

        // MAIN GAME LOOP
        while(!exitGame) {
            if(gameRunning) {
                if(init) {
                    g.initPlayScreen(difficulty);
                    game.setHeader(g.header);
                    flagCount = 0;
                    flagChange = 0;
                    gameStart = LocalTime.now();
                    now = gameStart;
                    seconds = 0;
                    init = false;
                }

                if(flagCount > 0) {
                    //System.out.println("Beep");
                }

                game.setDigitDisplay_mines(game.getPlayfield().mineCount - flagCount);
                game.setDigitDisplay_time(seconds);
                g.drawPlayScreen(game.getPlayfield());

                if(StdDraw.isMousePressed()) {
                    click = processMouseClick(true);
                    lastPressed = game.buttonDepress(click);

                    if(lastPressed != lastPressed_prev) {
                        if(lastPressed_prev instanceof Field) {
                            ((Field) lastPressed_prev).release();
                        } else if(lastPressed_prev instanceof MenuButton) {
                            ((MenuButton) lastPressed_prev).release();
                        }

                        if(lastPressed == null) {
                            game.buttonReleaseAll();
                        }

                        lastPressed_prev = lastPressed;
                    }

                    if(lastPressed == null) {
                        game.buttonReleaseAll();
                    }
                } else {
                    if(lastPressed != null) {
                        lastPressed_prev = lastPressed;
                        click = processMouseClick(true);
                        lastPressed = game.buttonRelease(click);

                        if(lastPressed != lastPressed_prev) {
                            if (lastPressed instanceof MenuButton && click != null) {
                                // Smiley button was released, reset game
                                init = true;
                            }

                            lastPressed = null;
                        } else {
                            if(lastPressed instanceof Field && click != null) {
                                if(click.buttonClicked == 1) {
                                    ((Field) lastPressed).reveal();
                                } else if(click.buttonClicked == 3) {
                                    flagChange = ((Field) lastPressed).toggleFlag();

                                    if(flagChange != 0) {
                                        flagCount += flagChange;
                                    }
                                }
                            } else if (lastPressed instanceof MenuButton && click != null) {
                                // Smiley button was released, reset game
                                init = true;
                            }

                            lastPressed = null;
                            lastPressed_prev = null;
                        }
                    }
                }

                game.getPlayfield().isVictory();

                if(game.getPlayfield().isGameOver) {
                    game.getHeader().setGameOver(true);
                } else if (!game.getPlayfield().isWon){
                    now = LocalTime.now();
                    seconds = (int) Duration.between(gameStart, now).getSeconds();
                }

                // if escape is pressed while playing, return to main menu
                if(StdDraw.isKeyPressed(VK_ESCAPE)) {
                    gameRunning = false;
                    init = true;
                }

            } else {
                if (init) {
                    g.initStartScreen();
                    game.setHeader(g.header);
                    init = false;
                }

                g.drawStartScreen();

                if(StdDraw.isMousePressed()) {
                    click = processMouseClick(true);
                    buttonPressed = g.startmenu.buttonDepress(click);

                    if(buttonPressed == null) {
                        g.startmenu.buttonReleaseAll();
                    }
                } else {
                    if(buttonPressed != null) {
                        buttonPressed_prev = buttonPressed;
                        buttonPressed = g.startmenu.buttonRelease(processMouseClick(true));

                        if(buttonPressed != buttonPressed_prev) {
                            buttonPressed = null;
                        }
                    }

                    if(buttonPressed != null) {
                        switch (buttonPressed.name) {
                            case "btn_easy":
                                game.newGame("Easy");
                                difficulty = "Easy";
                                gameRunning = true;
                                init = true;
                                break;
                            case "btn_medium":
                                game.newGame("Medium");
                                difficulty = "Medium";
                                gameRunning = true;
                                init = true;
                                break;
                            case "btn_hard":
                                game.newGame("Hard");
                                difficulty = "Hard";
                                gameRunning = true;
                                init = true;
                                break;
                            case "btn_exit":
                                System.out.print("Game ended by user");
                                System.exit(0);
                                break;
                        }
                    }
                }

                // if escape is pressed in main menu, exit game
                if(StdDraw.isKeyPressed(VK_ESCAPE)) {
                    exitGame = true;
                }
            }
        }

        System.out.print("Game ended by user");
        System.exit(0);
    }

    private static MouseClickWrapper processMouseClick() {
        return processMouseClick(false);
    }

    private static MouseClickWrapper processMouseClick(boolean overrideLastPosCheck) {
        double mouseX = StdDraw.mouseX(), mouseY = StdDraw.mouseY();

        if(mouseX == mouseLastX && mouseY == mouseLastY && !overrideLastPosCheck) {
            return null;
        } else {
            mouseLastX = mouseX;
            mouseLastY = mouseY;

            return new MouseClickWrapper(mouseX, mouseY, StdDraw.getMouseButtonMoved());
        }
    }
}
