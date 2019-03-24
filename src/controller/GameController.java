package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.AppleModel;
import model.SnakeModel;
import view.Board;

public class GameController implements ActionListener {

    private static int delay;

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delay) {
        GameController.delay = delay;
    }
    private static final int speedFactor = 5;

    public static int getSpeedFactor() {
        return speedFactor;
    }

    private static int moves = 0;

    public static int getMoves() {
        return moves;
    }

    public static void setMoves(int moves) {
        GameController.moves = moves;
    }

    private final Timer timer = new Timer(delay, this);

    public Timer getTimer() {
        return timer;
    }

    public void runTimer(Board board) {
        timer.start();
        board.repaint();
    }
    private static boolean stopped = false;

    public static boolean isStopped() {
        return stopped;
    }

    public static void setStopped(boolean stopped) {
        GameController.stopped = stopped;
    }

    public GameController() {
        setDelay(200);
        setMoves(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Board board = new Board();  
        boolean gameOver = deathCondition();
        timer.setDelay(delay - speedFactor * AppleModel.getTotalAmountOfEatenApples());
        timer.start();
        if (gameOver) {
            SnakeModel.setRight(false);
            SnakeModel.setLeft(false);
            SnakeModel.setUp(false);
            SnakeModel.setDown(false);
            timer.stop();

        }
        if (SnakeModel.isRight()) {
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r], r + 1);
            }
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                if (r == 0) {
                    SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r] + 25, r);
                } else {
                    SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r - 1], r);
                }
                if (SnakeModel.getSnakeXlength()[r] > 950) {
                    SnakeModel.setSnakeXlength(45, r);
                }
            }
            //board.repaint();
        }
        if (SnakeModel.isLeft()) {
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r], r + 1);
            }
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                if (r == 0) {
                    SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r] - 25, r);
                } else {
                    SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r - 1], r);
                }
                if (SnakeModel.getSnakeXlength()[r] < 20) {
                    SnakeModel.setSnakeXlength(925, r);
                }
            }
            //board.repaint();
        }

        if (SnakeModel.isUp()) {
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r], r + 1);
            }
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                if (r == 0) {
                    SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r] - 25, r);
                } else {
                    SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r - 1], r);
                }
                if (SnakeModel.getSnakeYlength()[r] < 76) {
                    SnakeModel.setSnakeYlength(726, r);
                }
            }
            //board.repaint();
        }
        if (SnakeModel.isDown()) {
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                SnakeModel.setSnakeXlength(SnakeModel.getSnakeXlength()[r], r + 1);
            }
            for (int r = SnakeModel.getLengthOfSnake() - 1; r >= 0; r--) {
                if (r == 0) {
                    SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r] + 25, r);
                } else {
                    SnakeModel.setSnakeYlength(SnakeModel.getSnakeYlength()[r - 1], r);
                }
                if (SnakeModel.getSnakeYlength()[r] > 750) {
                    SnakeModel.setSnakeYlength(76, r);
                }
            }
            //board.repaint();
        }

        if (stopped) {
            timer.stop();
        }
    }

    public static boolean deathCondition() {
        if (moves == 0) {
            return false;
        }
        for (int i = 1; i <= SnakeModel.getLengthOfSnake() - 1; i++) {
            if (SnakeModel.getSnakeXlength()[0] == SnakeModel.getSnakeXlength()[i] && SnakeModel.getSnakeYlength()[0] == SnakeModel.getSnakeYlength()[i]) {
                stopped = true;
                return true;
            }
        }
        return false;
    }

}
