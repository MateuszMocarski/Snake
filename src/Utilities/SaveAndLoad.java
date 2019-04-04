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

public class SaveAndLoad {

    public static void saveGame(SnakeModel snake) {
        GameController controller = new GameController();
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
            writer.write(Integer.toString(controller.getMoves()));
            writer.newLine();
            writer.write(Integer.toString(snake.getLengthOfSnake()));
            writer.newLine();
            writer.write(Integer.toString(AppleModel.getTotalAmountOfEatenApples()));
            writer.newLine();
            if (snake.isRight()) {
                writer.write("Right");
                writer.newLine();
            }
            if (snake.isLeft()) {
                writer.write("Left");
                writer.newLine();
            }
            if (snake.isUp()) {
                writer.write("Up");
                writer.newLine();
            }
            if (snake.isDown()) {
                writer.write("Down");
                writer.newLine();
            }
            for (int i = 0; i <= snake.getLengthOfSnake() - 1; i++) {
                writer.write(Integer.toString(snake.getSnakeXlength()[i]) + "|" + Integer.toString(snake.getSnakeYlength()[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static SnakeModel loadGame() {
        GameController controller = new GameController();
        SnakeModel snake = controller.getSnakeModel();
        File snakeSave = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\save.txt");
        if (snakeSave.exists()) {
            try {
                FileReader fr = new FileReader(snakeSave);
                BufferedReader br = new BufferedReader(fr);
                controller.setMoves(Integer.parseInt(br.readLine()));
                snake.setLengthOfSnake(Integer.parseInt(br.readLine()));
                AppleModel.setTotalAmountOfEatenApples(Integer.parseInt(br.readLine()));
                String direction = br.readLine();
                if (direction.equals("Right")) {
                    snake.setRight(true);
                    snake.setLeft(false);
                    snake.setUp(false);
                    snake.setDown(false);
                }
                if (direction.equals("Left")) {
                    snake.setRight(false);
                    snake.setLeft(true);
                    snake.setUp(false);
                    snake.setDown(false);
                }
                if (direction.equals("Up")) {
                    snake.setRight(false);
                    snake.setLeft(false);
                    snake.setUp(true);
                    snake.setDown(false);
                }
                if (direction.equals("Down")) {
                    snake.setRight(false);
                    snake.setLeft(false);
                    snake.setUp(false);
                    snake.setDown(true);
                }

                for (int i = 0; i <= snake.getLengthOfSnake() - 1; i++) {
                    String coordinates = br.readLine();
                    String[] arrayOfCoordinates = coordinates.split("\\|");
                    snake.setSnakeXlength(Integer.parseInt(arrayOfCoordinates[0]), i);
                    snake.setSnakeYlength(Integer.parseInt(arrayOfCoordinates[1]), i);

                }
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return snake;
    }
}
