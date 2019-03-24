package model;

public class SnakeModel {

    private static final int[] snakeXlength = new int[750];
    private static final int[] snakeYlength = new int[750];

    private static boolean left = false;
    private static boolean right = false;
    private static boolean up = false;
    private static boolean down = false;

    private static int lengthOfSnake = 3;

    public static int[] getSnakeXlength() {
        return snakeXlength;
    }

    public static void setSnakeXlength(int snakeXlength, int i) {
        SnakeModel.snakeXlength[i] = snakeXlength;
    }

    public static int[] getSnakeYlength() {
        return snakeYlength;
    }
    
    public static void setSnakeYlength(int snakeYlength, int i) {
        SnakeModel.snakeYlength[i] = snakeYlength;
    }

    public static boolean isLeft() {
        return left;
    }
    
     public static void setLeft(boolean left) {
        SnakeModel.left = left;
    }

    public static boolean isRight() {
        return right;
    }
    
    public static void setRight(boolean right) {
        SnakeModel.right = right;
    }

    public static boolean isUp() {
        return up;
    }
    
    public static void setUp(boolean up) {
        SnakeModel.up = up;
    }

    public static boolean isDown() {
        return down;
    }
    
    public static void setDown(boolean down) {
        SnakeModel.down = down;
    }

    public static int getLengthOfSnake() {
        return lengthOfSnake;
    }
    
    public static void setLengthOfSnake(int lengthOfSnake) {
        SnakeModel.lengthOfSnake = lengthOfSnake;
    }
}
