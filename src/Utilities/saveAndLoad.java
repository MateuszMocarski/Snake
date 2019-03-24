package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import snake.Board;

public class saveAndLoad {

    public static void saveGame() {
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
            writer.write(Integer.toString(Board.getMoves()));
            writer.newLine();
            writer.write(Integer.toString(Board.getLengthOfSnake()));
            writer.newLine();
            writer.write(Integer.toString(Board.getTotalAmountOfEatenApples()));
            writer.newLine();
            if (Board.isRight()) {
                writer.write("Right");
                writer.newLine();
            }
            if (Board.isLeft()) {
                writer.write("Left");
                writer.newLine();
            }
            if (Board.isUp()) {
                writer.write("Up");
                writer.newLine();
            }
            if (Board.isDown()) {
                writer.write("Down");
                writer.newLine();
            }
            for (int i = 0; i <= Board.getLengthOfSnake() - 1; i++) {
                writer.write(Integer.toString(Board.getSnakeXlength()[i]) + "|" + Integer.toString(Board.getSnakeYlength()[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static void loadGame() {
        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");
        if (snakeSave.exists()) {
            try {
                FileReader fr = new FileReader(snakeSave);
                BufferedReader br = new BufferedReader(fr);
                Board.setMoves(Integer.parseInt(br.readLine()));
                Board.setLengthOfSnake(Integer.parseInt(br.readLine()));
                Board.setTotalAmountOfEatenApples(Integer.parseInt(br.readLine()));
                String direction = br.readLine();
                if (direction.equals("Right")) {
                    Board.setRight(true);
                    Board.setLeft(false);
                    Board.setUp(false);
                    Board.setDown(false);
                }
                if (direction.equals("Left")) {
                    Board.setRight(false);
                    Board.setLeft(true);
                    Board.setUp(false);
                    Board.setDown(false);
                }
                if (direction.equals("Up")) {
                    Board.setRight(false);
                    Board.setLeft(false);
                    Board.setUp(true);
                    Board.setDown(false);
                }
                if (direction.equals("Down")) {
                    Board.setRight(false);
                    Board.setLeft(false);
                    Board.setUp(false);
                    Board.setDown(true);
                }

                for (int i = 0; i <= Board.getLengthOfSnake() - 1; i++) {
                    String coordinates = br.readLine();
                    String[] arrayOfCoordinates = coordinates.split("\\|");
                    Board.setSnakeXlength(Integer.parseInt(arrayOfCoordinates[0]), i);
                    Board.setSnakeYlength(Integer.parseInt(arrayOfCoordinates[1]), i);

                }
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }
}
