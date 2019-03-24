package view;

import Utilities.saveAndLoad;
import Utilities.Score;
import Utilities.HighScores;
import controller.GameController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
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

public class Board extends JPanel implements KeyListener {

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
    private final Timer timer = controller.getTimer();

    public Board() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        controller.runTimer(this);
        timer.start();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (GameController.getMoves() == 0) {
            SnakeModel.setSnakeXlength(45, 2);
            SnakeModel.setSnakeXlength(70, 1);
            SnakeModel.setSnakeXlength(95, 0);

            SnakeModel.setSnakeYlength(101, 2);
            SnakeModel.setSnakeYlength(101, 1);
            SnakeModel.setSnakeYlength(101, 0);

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
        g.drawString("Length: " + SnakeModel.getLengthOfSnake(), 900, 30);

        //draw speed
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        double speed = 20000 / (double) (GameController.getDelay() - GameController.getSpeedFactor() * AppleModel.getTotalAmountOfEatenApples());
        DecimalFormat df = new DecimalFormat("#.##");
        g.drawString("Speed: " + df.format(speed) + "%", 30, 30);

        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");

        snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
        snake_right_face.paintIcon(this, g, SnakeModel.getSnakeXlength()[0], SnakeModel.getSnakeYlength()[0]);

        for (int a = 0; a < SnakeModel.getLengthOfSnake(); a++) {
            if (a == 0 && SnakeModel.isRight()) {
                snake_right_face = new ImageIcon("src/images/snake_head_right_transparent.png");
                snake_right_face.paintIcon(this, g, SnakeModel.getSnakeXlength()[a], SnakeModel.getSnakeYlength()[a]);
            }
            if (a == 0 && SnakeModel.isLeft()) {
                snake_left_face = new ImageIcon("src/images/snake_head_left_transparent.png");
                snake_left_face.paintIcon(this, g, SnakeModel.getSnakeXlength()[a], SnakeModel.getSnakeYlength()[a]);
            }
            if (a == 0 && SnakeModel.isUp()) {
                snake_up_face = new ImageIcon("src/images/snake_head_up_transparent.png");
                snake_up_face.paintIcon(this, g, SnakeModel.getSnakeXlength()[a], SnakeModel.getSnakeYlength()[a]);
            }
            if (a == 0 && SnakeModel.isDown()) {
                snake_down_face = new ImageIcon("src/images/snake_head_down_transparent.png");
                snake_down_face.paintIcon(this, g, SnakeModel.getSnakeXlength()[a], SnakeModel.getSnakeYlength()[a]);
            }
            if (a != 0) {
                snake_torso = new ImageIcon("src/images/snake_torso_transparent.png");
                snake_torso.paintIcon(this, g, SnakeModel.getSnakeXlength()[a], SnakeModel.getSnakeYlength()[a]);
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
            SnakeModel.setLengthOfSnake(SnakeModel.getLengthOfSnake() + 1);
            appleAmount--;
            AppleModel.setTotalAmountOfEatenApples(AppleModel.getTotalAmountOfEatenApples() + 1);
        }

        //game over info
        if (GameController.deathCondition()) {
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

        if (GameController.getMoves() == 0 && snakeSave.exists()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press 'L' to load game", 400, 350);
        }

        if (GameController.isStopped() && !GameController.deathCondition()) {

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
        return (SnakeModel.getSnakeXlength()[0] == appleX && SnakeModel.getSnakeYlength()[0] == appleY);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        controller.runTimer(this);
        timer.start();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            GameController.setMoves(GameController.getMoves() + 1);
            SnakeModel.setRight(true);
            if (!SnakeModel.isLeft()) {
                SnakeModel.setRight(true);
            } else {
                SnakeModel.setRight(false);
                SnakeModel.setLeft(true);
            }
            SnakeModel.setUp(false);
            SnakeModel.setDown(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            GameController.setMoves(GameController.getMoves() + 1);
            SnakeModel.setLeft(true);
            if (!SnakeModel.isRight()) {
                SnakeModel.setLeft(true);
            } else {
                SnakeModel.setLeft(false);
                SnakeModel.setRight(true);
            }
            SnakeModel.setUp(false);
            SnakeModel.setDown(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            GameController.setMoves(GameController.getMoves() + 1);
            SnakeModel.setDown(true);
            if (!SnakeModel.isUp()) {
                SnakeModel.setDown(true);
            } else {
                SnakeModel.setDown(false);
                SnakeModel.setUp(true);
            }
            SnakeModel.setRight(false);
            SnakeModel.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            GameController.setMoves(GameController.getMoves() + 1);
            SnakeModel.setUp(true);
            if (!SnakeModel.isDown()) {
                SnakeModel.setUp(true);
            } else {
                SnakeModel.setUp(false);
                SnakeModel.setDown(true);
            }
            SnakeModel.setRight(false);
            SnakeModel.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (GameController.isStopped() == false) {
                GameController.setStopped(true);
            } else {
                GameController.setStopped(false);
                ActionEvent asd = null;
                controller.actionPerformed(asd);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            GameController.setMoves(0);
            AppleModel.setTotalAmountOfEatenApples(0);
            SnakeModel.setLengthOfSnake(3);
            SnakeModel.setRight(true);
            SnakeModel.setLeft(false);
            SnakeModel.setUp(false);
            SnakeModel.setDown(false);

            GameController.setStopped(false);
            saveStatus = false;
            repaint();
            ActionEvent asd = null;
            controller.actionPerformed(asd);

        }

        if (GameController.getMoves() != 0 && !GameController.deathCondition() && GameController.isStopped() && e.getKeyCode() == KeyEvent.VK_S) {
            saveAndLoad.saveGame();
        }

        if ((GameController.getMoves() == 0 || GameController.isStopped()) && e.getKeyCode() == KeyEvent.VK_L) {
            saveAndLoad.loadGame();
            GameController.setStopped(false);
            this.appleAmount = 0;
            saveStatus = false;
            ActionEvent asd = null;
            controller.actionPerformed(asd);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
