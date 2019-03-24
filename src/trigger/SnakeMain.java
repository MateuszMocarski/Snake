package trigger;

import java.awt.Color;
import javax.swing.JFrame;
import view.Board;

public class SnakeMain {

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
        frame.setTitle("Snake v2");
        frame.add(board);

    }
}
