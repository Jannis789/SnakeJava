import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
// import java.awt.Dimension;
public class GameWindow {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Moving Cube");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard gameBoard = new GameBoard();
        frame.setPreferredSize(new Dimension(900, 900));
        frame.getContentPane().add(gameBoard);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        GameLoop gameLoop = new GameLoop(gameBoard);
        gameLoop.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::createAndShowGUI);
    }
}