package model;

public class SnakeModel {

    private final int[] snakeXlength = new int[83];
    private final int[] snakeYlength = new int[83];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private int lengthOfSnake = 3;

    public int[] getSnakeXlength() {
        return snakeXlength;
    }

    public void setSnakeXlength(int snakeXlength, int i) {
        this.snakeXlength[i] = snakeXlength;
    }

    public int[] getSnakeYlength() {
        return snakeYlength;
    }
    
    public void setSnakeYlength(int snakeYlength, int i) {
        this.snakeYlength[i] = snakeYlength;
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
