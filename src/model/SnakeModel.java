package model;

import Utilities.BoardField;

public class SnakeModel {

    private final BoardField[] snakeLength = {new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0), new BoardField(0, 0)};

    public void snakeLengthInit() {
        for (BoardField bf : snakeLength) {
            bf = new BoardField(0, 0);
        }
    }

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private int lengthOfSnake = 3;

    public BoardField[] getSnakeLength() {
        return snakeLength;
    }

    
    public int getSnakeXlength(int r) {
        return snakeLength[r].getBoardFieldX();
    }

    public void setSnakeXlength(int snakeXlength, int i) {
        this.snakeLength[i].setBoardFieldX(snakeXlength);
    }

    public int getSnakeYlength(int r) {
        return snakeLength[r].getBoardFieldY();
    }

    public void setSnakeYlength(int snakeYlength, int i) {
        this.snakeLength[i].setBoardFieldY(snakeYlength);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public int getLengthOfSnake() {
        return lengthOfSnake;
    }

    public void setLengthOfSnake(int lengthOfSnake) {
        this.lengthOfSnake = lengthOfSnake;
    }
}
