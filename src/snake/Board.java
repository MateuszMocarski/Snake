package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener {

    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean stopped = false;

    private ImageIcon snake_right_face;
    private ImageIcon snake_left_face;
    private ImageIcon snake_up_face;
    private ImageIcon snake_down_face;
    private ImageIcon black_field;

    private int appleAmount = 0;
    private ImageIcon apple;
    private int appleX;
    private int appleY;

    private int lengthOfSnake = 3;

    private Timer timer;
    private int delay = 200;

    private ImageIcon snake_torso;

    private ImageIcon titleImage;

    private int moves = 0;
    private int totalAmountOfEatenApples = 0;

    private int speedFactor = 5;

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
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score: " + totalAmountOfEatenApples, 900, 50);

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
            repaint();
            ActionEvent asd = null;
            actionPerformed(asd);

        }

        if (moves != 0 && !deathCondition() && stopped && e.getKeyCode() == KeyEvent.VK_S) {
            saveGame();
        }

        if ((moves == 0 || stopped) && e.getKeyCode() == KeyEvent.VK_L) {
            loadGame();
            stopped = false;
            this.appleAmount = 0;
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

    public void saveGame() {
        BufferedWriter writer = null;
        try {
            File snakeDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake");
            if (!snakeDirectory.exists()) {
                snakeDirectory.mkdirs();
            }
            File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");
            try {
                snakeSave.createNewFile();
            } catch (IOException ex) {
                ex.getMessage();
            }
            writer = new BufferedWriter(new FileWriter(snakeSave));
            writer.write(Integer.toString(moves));
            writer.newLine();
            writer.write(Integer.toString(lengthOfSnake));
            writer.newLine();
            writer.write(Integer.toString(totalAmountOfEatenApples));
            writer.newLine();
            if (right) {
                writer.write("Right");
                writer.newLine();
            }
            if (left) {
                writer.write("Left");
                writer.newLine();
            }
            if (up) {
                writer.write("Up");
                writer.newLine();
            }
            if (down) {
                writer.write("Down");
                writer.newLine();
            }
            for (int i = 0; i <= lengthOfSnake - 1; i++) {
                writer.write(Integer.toString(snakeXlength[i]) + "|" + Integer.toString(snakeYlength[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public void loadGame() {
        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");
        if (snakeSave.exists()) {
            try {
                FileReader fr = new FileReader(snakeSave);
                BufferedReader br = new BufferedReader(fr);
                this.moves = Integer.parseInt(br.readLine());
                this.lengthOfSnake = Integer.parseInt(br.readLine());
                this.totalAmountOfEatenApples = Integer.parseInt(br.readLine());
                String direction = br.readLine();
                if(direction.equals("Right")){
                    right = true;
                    left = false;
                    up = false;
                    down = false;
                }
                if(direction.equals("Left")){
                    right = false;
                    left = true;
                    up = false;
                    down = false;
                }
                if(direction.equals("Up")){
                    right = false;
                    left = false;
                    up = true;
                    down = false;
                }
                if(direction.equals("Down")){
                    right = false;
                    left = false;
                    up = false;
                    down = true;
                }

                for (int i = 0; i <= lengthOfSnake - 1; i++) {
                    String coordinates = br.readLine();
                    String[] arrayOfCoordinates = coordinates.split("\\|");
                    this.snakeXlength[i] = Integer.parseInt(arrayOfCoordinates[0]);
                    this.snakeYlength[i] = Integer.parseInt(arrayOfCoordinates[1]);

                }
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }
}
