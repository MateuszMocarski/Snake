
package Utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import view.Board;

/**
 *
 * @author Mateusz
 */
public class HighScores {
    
    public static List addToHighScores(List<Score> listOfScores, Score score) {
        listOfScores.add(score);
        return listOfScores;
    }
    
    public static List sortHighScoresList(List<Score> listOfHighScores) {
        Collections.sort(listOfHighScores);
        return listOfHighScores;
    }
    
    public static void saveHighScoresList(List<Score> listOfHighScores) {
        try {
            BufferedWriter writer = null;

            File snakeDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake");
            if (!snakeDirectory.exists()) {
                snakeDirectory.mkdirs();
            }
            File highScores = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\highscores.txt");
            if (highScores.exists()) {
                highScores.delete();
            }
            try {
                highScores.createNewFile();
            } catch (IOException ex) {
                ex.getMessage();
            }
            writer = new BufferedWriter(new FileWriter(highScores));
            if (listOfHighScores.size() < 3) {
                for (Score score : listOfHighScores) {
                    writer.write(Integer.toString(score.getScore()) + "|" + score.getDate());
                    writer.newLine();
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    writer.write(Integer.toString(listOfHighScores.get(i).getScore()) + "|" + listOfHighScores.get(i).getDate());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
        listOfHighScores.clear();
    }
    
    public static List<Score> loadHighScoresList() {
        List<Score> list = Board.getListOfHighScores();
        File highScores = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Snake\\highscores.txt");
        if (highScores.exists()) {
            try {
                FileReader fr = new FileReader(highScores);
                BufferedReader br = new BufferedReader(fr);
                String scoreString;
                while ((scoreString = br.readLine()) != null) {
                    String[] scores = scoreString.split("\\|");
                    addToHighScores(list, new Score(Integer.parseInt(scores[0]), scores[1]));

                }
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return list;
    }
    
    public static void write3HighestScores(List<Score> listOfScores, Graphics g) {
        if (listOfScores.size() < 3) {
            for (int i = 0; i < listOfScores.size(); i++) {
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Score: " + listOfScores.get(i).getScore() + "   Date: " + listOfScores.get(i).getDate(), 320, 475 + i * 25);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Score: " + listOfScores.get(i).getScore() + "   Date: " + listOfScores.get(i).getDate(), 320, 475 + i * 25);
            }
        }

    }
}
