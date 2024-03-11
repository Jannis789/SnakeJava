import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
// import java.awt.Dimension;
public class GameWindow {
    private static Game game;
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Snake in Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 900));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        GameBoard gameBoard = new GameBoard();
        frame.add(gameBoard);
        game = new Game(gameBoard);
        gameBoard.setGame(game);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::createAndShowGUI);
    }
}