package model;

import Utilities.BoardField;
import java.util.ArrayList;
import java.util.List;

public final class SnakeModel {

    private final List<BoardField> snakeElements = new ArrayList<>();

    public SnakeModel() {
        this.getSnakeElement().clear();
        this.getSnakeElement().add(new BoardField(3, 1));
        this.getSnakeElement().add(new BoardField(2, 1));
        this.getSnakeElement().add(new BoardField(1, 1));
        this.getSnakeElement().add(new BoardField(0, 0));
    }

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int lengthOfSnake = 3;

    public List<BoardField> getSnakeElement() {
        return snakeElements;
    }

    public void setSnakeLength(int i, BoardField bf) {
        this.snakeElements.set(i, bf);
    }

    public int getSnakeXlength(int r) {
        return snakeElements.get(r).getBoardFieldX();
    }

    public void setSnakeXlength(int snakeXlength, int i) {
        this.snakeElements.get(i).setBoardFieldX(snakeXlength);
    }

    public int getSnakeYlength(int r) {
        return snakeElements.get(r).getBoardFieldY();
    }

    public void setSnakeYlength(int snakeYlength, int i) {
        this.snakeElements.get(i).setBoardFieldY(snakeYlength);
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
