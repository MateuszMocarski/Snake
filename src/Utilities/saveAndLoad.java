package Utilities;

import controller.GameController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.AppleModel;
import model.SnakeModel;
import view.Board;

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
            writer.write(Integer.toString(GameController.getMoves()));
            writer.newLine();
            writer.write(Integer.toString(SnakeModel.getLengthOfSnake()));
            writer.newLine();
            writer.write(Integer.toString(AppleModel.getTotalAmountOfEatenApples()));
            writer.newLine();
            if (SnakeModel.isRight()) {
                writer.write("Right");
                writer.newLine();
            }
            if (SnakeModel.isLeft()) {
                writer.write("Left");
                writer.newLine();
            }
            if (SnakeModel.isUp()) {
                writer.write("Up");
                writer.newLine();
            }
            if (SnakeModel.isDown()) {
                writer.write("Down");
                writer.newLine();
            }
            for (int i = 0; i <= SnakeModel.getLengthOfSnake() - 1; i++) {
                writer.write(Integer.toString(SnakeModel.getSnakeXlength()[i]) + "|" + Integer.toString(SnakeModel.getSnakeYlength()[i]));
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
                GameController.setMoves(Integer.parseInt(br.readLine()));
                SnakeModel.setLengthOfSnake(Integer.parseInt(br.readLine()));
                AppleModel.setTotalAmountOfEatenApples(Integer.parseInt(br.readLine()));
                String direction = br.readLine();
                if (direction.equals("Right")) {
                    SnakeModel.setRight(true);
                    SnakeModel.setLeft(false);
                    SnakeModel.setUp(false);
                    SnakeModel.setDown(false);
                }
                if (direction.equals("Left")) {
                    SnakeModel.setRight(false);
                    SnakeModel.setLeft(true);
                    SnakeModel.setUp(false);
                    SnakeModel.setDown(false);
                }
                if (direction.equals("Up")) {
                    SnakeModel.setRight(false);
                    SnakeModel.setLeft(false);
                    SnakeModel.setUp(true);
                    SnakeModel.setDown(false);
                }
                if (direction.equals("Down")) {
                    SnakeModel.setRight(false);
                    SnakeModel.setLeft(false);
                    SnakeModel.setUp(false);
                    SnakeModel.setDown(true);
                }

                for (int i = 0; i <= SnakeModel.getLengthOfSnake() - 1; i++) {
                    String coordinates = br.readLine();
                    String[] arrayOfCoordinates = coordinates.split("\\|");
                    SnakeModel.setSnakeXlength(Integer.parseInt(arrayOfCoordinates[0]), i);
                    SnakeModel.setSnakeYlength(Integer.parseInt(arrayOfCoordinates[1]), i);

                }
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }
}
