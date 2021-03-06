package controller;

import Utilities.BoardField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.AppleModel;
import model.SnakeModel;
import view.Board;

public final class GameController implements ActionListener {

    private int delay;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    private int totalAmountOfEatenApples = 0;

    public int getTotalAmountOfEatenApples() {
        return totalAmountOfEatenApples;
    }

    public void setTotalAmountOfEatenApples(int totalAmountOfEatenApples) {
        this.totalAmountOfEatenApples = totalAmountOfEatenApples;
    }

    private final int speedFactor = 5;

    public int getSpeedFactor() {
        return speedFactor;
    }
    
    private int moves = 0;

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    private final Timer timer = new Timer(delay, this);

    public Timer getTimer() {
        return timer;
    }

    public void runTimer(Board board) {
        timer.start();
        board.repaint();
    }
    private boolean stopped = false;

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private SnakeModel snake = new SnakeModel();

    public SnakeModel getSnakeModel() {
        return this.snake;
    }

    public void setNewSnakeModel() {
        snake = new SnakeModel();
    }

    public GameController() {
        setDelay(200);
        setMoves(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!stopped) {

            boolean gameOver = deathCondition();
            timer.setDelay(delay - speedFactor * this.getTotalAmountOfEatenApples());
            timer.start();
            if (gameOver) {
                snake.setRight(false);
                snake.setLeft(false);
                snake.setUp(false);
                snake.setDown(false);
                timer.stop();
            }
            BoardField temp = new BoardField(snake.getSnakeElement().get(0).getBoardFieldX(), snake.getSnakeElement().get(0).getBoardFieldY());
            if (snake.isRight()) {

                snake.getSnakeElement().get(0).setBoardFieldX(snake.getSnakeElement().get(0).getBoardFieldX() + 1);

                if (snake.getSnakeElement().get(0).getBoardFieldX() > Board.getBoardWidth() - 1) {
                    snake.getSnakeElement().get(0).setBoardFieldX(0);
                }
            }

            if (snake.isLeft()) {

                snake.getSnakeElement().get(0).setBoardFieldX(snake.getSnakeElement().get(0).getBoardFieldX() - 1);

                if (snake.getSnakeElement().get(0).getBoardFieldX() < 0) {
                    snake.getSnakeElement().get(0).setBoardFieldX(Board.getBoardWidth() - 1);
                }
            }

            if (snake.isUp()) {

                snake.getSnakeElement().get(0).setBoardFieldY(snake.getSnakeElement().get(0).getBoardFieldY() - 1);

                if (snake.getSnakeElement().get(0).getBoardFieldY() < 0) {
                    snake.getSnakeElement().get(0).setBoardFieldY(Board.getBoardHeight() - 1);
                }
            }

            if (snake.isDown()) {

                snake.getSnakeElement().get(0).setBoardFieldY(snake.getSnakeElement().get(0).getBoardFieldY() + 1);

                if (snake.getSnakeElement().get(0).getBoardFieldY() > Board.getBoardHeight() - 1) {
                    snake.getSnakeElement().get(0).setBoardFieldY(0);
                }
            }
            snake.getSnakeElement().remove(snake.getLengthOfSnake() - 1);
            snake.getSnakeElement().add(1, temp);
        }

    }

    public boolean deathCondition() {
        if (moves == 0) {
            return false;
        }
        for (int i = 1; i <= snake.getLengthOfSnake() - 1; i++) {
            if (snake.getSnakeElement().get(0).equals(snake.getSnakeElement().get(i))) {
                stopped = true;
                return true;
            }
        }
        return false;
    }

    public boolean isAppleEaten(AppleModel appleModel) {
        return (snake.getSnakeElement().get(0).equals(appleModel.getAppleLocation()));
    }

    public boolean appleNotOnSnake(AppleModel appleModel) {
        if (moves == 0) {
            return false;
        }
        for (int i = 1; i <= snake.getLengthOfSnake() - 1; i++) {
            if (snake.getSnakeElement().get(i).equals(appleModel.getAppleLocation())) {
                return true;
            }
        }
        return false;
    }

    public void appleEaten() {
        snake.getSnakeElement().add(new BoardField(snake.getSnakeXlength(snake.getLengthOfSnake() - 1), snake.getSnakeYlength(snake.getLengthOfSnake() - 1)));
    }

//    public void snakeLocWhisperer(){
//        for(int i = 0; i < snake.getLengthOfSnake()-1; i++){
//            System.out.println(snake.getSnakeLength().get(i).toString());
//        }
//        System.out.println("=========================================");
//    }
//    
//    public void switcher(){
//        snake.setRight(false);
//        snake.setDown(true);
//    }
}
