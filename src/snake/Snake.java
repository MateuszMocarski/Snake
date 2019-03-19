package snake;

import java.awt.Color;
import javax.swing.JFrame;

public class Snake {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Board board = new Board();

        
        runGame(frame, board);
    }

    public static void runGame(JFrame frame, Board board) {
        frame.setBounds(10, 10, 1000, 800);
        frame.setBackground(Color.BLACK);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Snake v1");
        frame.add(board);

    }
}
