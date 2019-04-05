package view;

import Utilities.Score;
import Utilities.HighScores;
import Utilities.SaveAndLoad;
import controller.GameController;
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
import model.AppleModel;
import model.SnakeModel;

public class Board extends JPanel implements KeyListener, ActionListener {

    private ImageIcon snake_right_face;
    private ImageIcon snake_left_face;
    private ImageIcon snake_up_face;
    private ImageIcon snake_down_face;

    public static List<Score> getListOfHighScores() {
        return listOfHighScores;
    }
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int appleAmount = 0;
    private ImageIcon apple;
    private int appleX;
    private int appleY;

    private ImageIcon snake_torso;

    private ImageIcon titleImage;

    private int totalScore;

    private boolean saveStatus = false;

    private static List<Score> listOfHighScores = new ArrayList<>();
    
    private GameController controller = new GameController();
    private SnakeModel snake = controller.getSnakeModel();
    private final Timer repaintTimer = new Timer(10, this);

    public Board() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        controller.runTimer(this);
        //timer.start();
        repaintTimer.start();
    }



    @Override
    public void paint(Graphics g) {
        if (controller.getMoves() == 0) {
            snake.setSnakeXlength(45, 2);
            snake.setSnakeXlength(70, 1);
            snake.setSnakeXlength(95, 0);

            snake.setSnakeYlength(101, 2);
            snake.setSnakeYlength(101, 1);
            snake.setSnakeYlength(101, 0);

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
        totalScore = 5 * AppleModel.getTotalAmountOfEatenApples();
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score: " + totalScore, 900, 50);

        //draw length
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + snake.getLengthOfSnake(), 900, 30);

        //draw speed
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        double speed = 20000 / (double) (controller.getDelay() - controller.getSpeedFactor() * AppleModel.getTotalAmountOfEatenApples());
        DecimalFormat df = new DecimalFormat("#.##");
        g.drawString("Speed: " + df.format(speed) + "%", 30, 30);

        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");

        snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
        snake_right_face.paintIcon(this, g, snake.getSnakeXlength(0), snake.getSnakeYlength(0));

        for (int a = 0; a < snake.getLengthOfSnake(); a++) {
            if (a == 0 && snake.isRight()) {
                snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
                snake_right_face.paintIcon(this, g, snake.getSnakeXlength(a), snake.getSnakeYlength(a));
            }
            if (a == 0 && snake.isLeft()) {
                snake_left_face = new ImageIcon("src/images/snake_head_left_transparent.png");
                snake_left_face.paintIcon(this, g, snake.getSnakeXlength(a), snake.getSnakeYlength(a));
            }
            if (a == 0 && snake.isUp()) {
                snake_up_face = new ImageIcon("src/images/snake_head_up_transparent.png");
                snake_up_face.paintIcon(this, g, snake.getSnakeXlength(a), snake.getSnakeYlength(a));
            }
            if (a == 0 && snake.isDown()) {
                snake_down_face = new ImageIcon("src/images/snake_head_down_transparent.png");
                snake_down_face.paintIcon(this, g, snake.getSnakeXlength(a), snake.getSnakeYlength(a));
            }
            if (a != 0) {
                snake_torso = new ImageIcon("src/images/snake_torso_transparent.png");
                snake_torso.paintIcon(this, g, snake.getSnakeXlength(a), snake.getSnakeYlength(a));
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
            snake.setLengthOfSnake(snake.getLengthOfSnake() + 1);
            appleAmount--;
            AppleModel.setTotalAmountOfEatenApples(AppleModel.getTotalAmountOfEatenApples() + 1);
        }

        //game over info
        if (controller.deathCondition()) {
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
                    listOfHighScores = HighScores.loadHighScoresList();
                    HighScores.saveHighScoresList(HighScores.sortHighScoresList(HighScores.addToHighScores(listOfHighScores, new Score(totalScore, sdf.format(Calendar.getInstance().getTime())))));
                } else {
                    HighScores.saveHighScoresList(HighScores.sortHighScoresList(HighScores.addToHighScores(listOfHighScores, new Score(totalScore, sdf.format(Calendar.getInstance().getTime())))));
                }
                saveStatus = true;
            }
            HighScores.write3HighestScores(HighScores.loadHighScoresList(), g);
            listOfHighScores.clear();

        }

        if (controller.getMoves() == 0 && snakeSave.exists()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'L' to load game", 400, 350);
        }

        if (controller.isStopped() && !controller.deathCondition()) {

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
        return (snake.getSnakeXlength(0) == appleX && snake.getSnakeYlength(0) == appleY);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        controller.runTimer(this);
        repaintTimer.start();
        //timer.start();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            controller.setMoves(controller.getMoves() + 1);
            snake.setRight(true);
            if (!snake.isLeft()) {
                snake.setRight(true);
            } else {
                snake.setRight(false);
                snake.setLeft(true);
            }
            snake.setUp(false);
            snake.setDown(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            controller.setMoves(controller.getMoves() + 1);
            snake.setLeft(true);
            if (!snake.isRight()) {
                snake.setLeft(true);
            } else {
                snake.setLeft(false);
                snake.setRight(true);
            }
            snake.setUp(false);
            snake.setDown(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            controller.setMoves(controller.getMoves() + 1);
            snake.setDown(true);
            if (!snake.isUp()) {
                snake.setDown(true);
            } else {
                snake.setDown(false);
                snake.setUp(true);
            }
            snake.setRight(false);
            snake.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            controller.setMoves(controller.getMoves() + 1);
            snake.setUp(true);
            if (!snake.isDown()) {
                snake.setUp(true);
            } else {
                snake.setUp(false);
                snake.setDown(true);
            }
            snake.setRight(false);
            snake.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (controller.isStopped() == false) {
                controller.setStopped(true);
            } else {
                controller.setStopped(false);
                ActionEvent asd = null;
                controller.actionPerformed(asd);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            controller.setMoves(0);
            AppleModel.setTotalAmountOfEatenApples(0);
            snake.setLengthOfSnake(3);
            snake.setRight(true);
            snake.setLeft(false);
            snake.setUp(false);
            snake.setDown(false);

            controller.setStopped(false);
            saveStatus = false;
            repaint();
            ActionEvent asd = null;
            controller.actionPerformed(asd);

        }

        if (controller.getMoves() != 0 && !controller.deathCondition() && controller.isStopped() && e.getKeyCode() == KeyEvent.VK_S) {
            SaveAndLoad.saveGame(snake, controller);
        }

        if ((controller.getMoves() == 0 || controller.isStopped()) && e.getKeyCode() == KeyEvent.VK_L) {
            this.snake = SaveAndLoad.loadGame(controller);
            controller.setStopped(false);
            this.appleAmount = 0;
            saveStatus = false;
            ActionEvent asd = null;
            controller.actionPerformed(asd);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

}
