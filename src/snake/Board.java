package snake;

import Utilities.saveAndLoad;
import Utilities.Score;
import Utilities.HighScores;
import static Utilities.HighScores.loadHighScoresList;
import static Utilities.HighScores.saveHighScoresList;
import static Utilities.HighScores.sortHighScoresList;
import static Utilities.HighScores.write3HighestScores;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener {

    private static int[] snakeXlength = new int[750];
    private static int[] snakeYlength = new int[750];

    private static boolean left = false;
    private static boolean right = false;
    private static boolean up = false;
    private static boolean down = false;
    private boolean stopped = false;

    private ImageIcon snake_right_face;
    private ImageIcon snake_left_face;
    private ImageIcon snake_up_face;
    private ImageIcon snake_down_face;

    public static List<Score> getListOfHighScores() {
        return listOfHighScores;
    }


    public static int[] getSnakeXlength() {
        return snakeXlength;
    }

    public static int[] getSnakeYlength() {
        return snakeYlength;
    }

    public static boolean isLeft() {
        return left;
    }

    public static boolean isRight() {
        return right;
    }

    public static boolean isUp() {
        return up;
    }

    public static boolean isDown() {
        return down;
    }

    public static int getLengthOfSnake() {
        return lengthOfSnake;
    }

    public static int getMoves() {
        return moves;
    }

    public static int getTotalAmountOfEatenApples() {
        return totalAmountOfEatenApples;
    }

    public static void setSnakeXlength(int snakeXlength, int i) {
        Board.snakeXlength[i] = snakeXlength;
    }

    public static void setSnakeYlength(int snakeYlength, int i) {
        Board.snakeYlength[i] = snakeYlength;
    }

    public static void setLeft(boolean left) {
        Board.left = left;
    }

    public static void setRight(boolean right) {
        Board.right = right;
    }

    public static void setUp(boolean up) {
        Board.up = up;
    }

    public static void setDown(boolean down) {
        Board.down = down;
    }

    public static void setLengthOfSnake(int lengthOfSnake) {
        Board.lengthOfSnake = lengthOfSnake;
    }

    public static void setMoves(int moves) {
        Board.moves = moves;
    }

    public static void setTotalAmountOfEatenApples(int totalAmountOfEatenApples) {
        Board.totalAmountOfEatenApples = totalAmountOfEatenApples;
    }

    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int appleAmount = 0;
    private ImageIcon apple;
    private int appleX;
    private int appleY;

    private static int lengthOfSnake = 3;

    private final Timer timer;
    private final int delay = 200;

    private ImageIcon snake_torso;

    private ImageIcon titleImage;

    private static int moves = 0;
    private static int totalAmountOfEatenApples = 0;
    private int totalScore;

    private boolean saveStatus = false;

    private static List<Score> listOfHighScores = new ArrayList<>();

    private final int speedFactor = 5;

    public Board() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {

        if (moves == 0) {
            snakeXlength[2] = 45;
            snakeXlength[1] = 70;
            snakeXlength[0] = 95;

            snakeYlength[2] = 101;
            snakeYlength[1] = 101;
            snakeYlength[0] = 101;
        }
        //title image border
        g.setColor(Color.white);
        g.drawRect(19, 10, 951, 51);

        //title image
        titleImage = new ImageIcon("src/images/title.jpg");
        titleImage.paintIcon(this, g, 20, 11);

        //board background border
        g.setColor(Color.white);
        g.drawRect(19, 75, 951, 676);

        //draw background
        g.setColor(Color.black);
        g.fillRect(20, 76, 950, 675);

        //draw score
        totalScore = 5 * totalAmountOfEatenApples;
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score: " + totalScore, 900, 50);

        //draw length
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + lengthOfSnake, 900, 30);

        //draw speed
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        double speed = 20000 / (double) (delay - speedFactor * totalAmountOfEatenApples);
        DecimalFormat df = new DecimalFormat("#.##");
        g.drawString("Speed: " + df.format(speed) + "%", 30, 30);

        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");

        snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
        snake_right_face.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);

        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0 && right) {
                snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
                snake_right_face.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
            if (a == 0 && left) {
                snake_left_face = new ImageIcon("src/images/snake_head_left_transparent.png");
                snake_left_face.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
            if (a == 0 && up) {
                snake_up_face = new ImageIcon("src/images/snake_head_up_transparent.png");
                snake_up_face.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
            if (a == 0 && down) {
                snake_down_face = new ImageIcon("src/images/snake_head_down_transparent.png");
                snake_down_face.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
            if (a != 0) {
                snake_torso = new ImageIcon("src/images/snake_torso_transparent.png");
                snake_torso.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
        }
        if (appleAmount == 0) {
            appleX = randomizeAppleXLocation();
            appleY = randomizeAppleYLocation();
            appleAmount++;
        }
        apple = new ImageIcon("src/images/apple_transparent.png");
        apple.paintIcon(this, g, appleX, appleY);

        if (isAppleEaten()) {
            lengthOfSnake++;
            appleAmount--;
            totalAmountOfEatenApples++;
        }

        //game over info
        if (deathCondition()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 350, 350);

            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'R' to restart game", 390, 400);
            if (snakeSave.exists()) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press 'L' to load game", 400, 425);
            }
            File highScores = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\highscores.txt");
            if (!saveStatus) {
                if (highScores.exists()) {
                    listOfHighScores = loadHighScoresList();
                    saveHighScoresList(sortHighScoresList(HighScores.addToHighScores(listOfHighScores, new Score(totalScore, sdf.format(Calendar.getInstance().getTime())))));
                } else {
                    saveHighScoresList(sortHighScoresList(HighScores.addToHighScores(listOfHighScores, new Score(totalScore, sdf.format(Calendar.getInstance().getTime())))));
                }
                saveStatus = true;
            }
            write3HighestScores(loadHighScoresList(), g);
            listOfHighScores.clear();

        }

        if (moves == 0 && snakeSave.exists()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'L' to load game", 400, 350);
        }

        if (stopped && !deathCondition()) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'S' to save game", 400, 350);

            if (snakeSave.exists()) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press 'L' to load game", 402, 375);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'R' to restart game", 392, 400);

            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press SPACE to resume game", 360, 425);
        }
    }

    public int randomizeAppleXLocation() {
        int locationX = 45;
        Random rand = new Random();
        int number = rand.nextInt(36);
        locationX += 25 * number;
        return locationX;
    }

    public int randomizeAppleYLocation() {
        int locationY = 101;
        Random rand = new Random();
        int number = rand.nextInt(26);
        locationY += 25 * number;
        return locationY;
    }

    public boolean isAppleEaten() {
        return (snakeXlength[0] == appleX && snakeYlength[0] == appleY);
    }

    public boolean deathCondition() {
        if (moves == 0) {
            return false;
        }
        for (int i = 1; i <= lengthOfSnake - 1; i++) {
            if (snakeXlength[0] == snakeXlength[i] && snakeYlength[0] == snakeYlength[i]) {
                stopped = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                left = true;
            } else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                down = true;
            } else {
                down = false;
                up = true;
            }
            right = false;
            left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                up = true;
            } else {
                down = true;
                up = false;
            }
            right = false;
            left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (stopped == false) {
                stopped = true;
            } else {
                stopped = false;
                ActionEvent asd = null;
                actionPerformed(asd);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            moves = 0;
            totalAmountOfEatenApples = 0;
            lengthOfSnake = 3;
            right = true;
            left = false;
            up = false;
            down = false;
            stopped = false;
            saveStatus = false;
            repaint();
            ActionEvent asd = null;
            actionPerformed(asd);

        }

        if (moves != 0 && !deathCondition() && stopped && e.getKeyCode() == KeyEvent.VK_S) {
            saveAndLoad.saveGame();
        }

        if ((moves == 0 || stopped) && e.getKeyCode() == KeyEvent.VK_L) {
            saveAndLoad.loadGame();
            stopped = false;
            this.appleAmount = 0;
            saveStatus = false;
            ActionEvent asd = null;
            actionPerformed(asd);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean gameOver = deathCondition();
        timer.setDelay(delay - speedFactor * totalAmountOfEatenApples);
        timer.start();
        if (gameOver) {
            right = false;
            left = false;
            up = false;
            down = false;
            timer.stop();

        }

        if (right) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeYlength[r + 1] = snakeYlength[r];
            }
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeXlength[r] = snakeXlength[r] + 25;
                } else {
                    snakeXlength[r] = snakeXlength[r - 1];
                }
                if (snakeXlength[r] > 950) {
                    snakeXlength[r] = 45;
                }
            }
            repaint();
        }
        if (left) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeYlength[r + 1] = snakeYlength[r];
            }
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeXlength[r] = snakeXlength[r] - 25;
                } else {
                    snakeXlength[r] = snakeXlength[r - 1];
                }
                if (snakeXlength[r] < 20) {
                    snakeXlength[r] = 925;
                }
            }
            repaint();
        }
        if (up) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeXlength[r + 1] = snakeXlength[r];
            }
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeYlength[r] = snakeYlength[r] - 25;
                } else {
                    snakeYlength[r] = snakeYlength[r - 1];
                }
                if (snakeYlength[r] < 76) {
                    snakeYlength[r] = 726;
                }
            }
            repaint();
        }
        if (down) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeXlength[r + 1] = snakeXlength[r];
            }
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeYlength[r] = snakeYlength[r] + 25;
                } else {
                    snakeYlength[r] = snakeYlength[r - 1];
                }
                if (snakeYlength[r] > 750) {
                    snakeYlength[r] = 76;
                }
            }
            repaint();
        }
        if (stopped) {
            timer.stop();
        }
    }

    

    

    

    

    

    

    
}
