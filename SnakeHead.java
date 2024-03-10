import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SnakeHead {
    public static final int BOX_SIZE = 30;
    private int x;
    private int y;
    private GameBoard gameBoard;

    public SnakeHead(int startX, int startY) {
        this.y = startY;
        this.x = startX;
    }

    public void move( int toX, int toY) {
        if (x + toX >= 0  && y + toY >= 0 && x + toX <= 900 && y + toY <= 900)  {
            x += toX;
            y += toY;
        } else {
            Random random = new Random();
            int spawnX = random.nextInt(30) * 30;
            int spawnY = random.nextInt(30) * 30;
            x = spawnX;
            y = spawnY;
            gameBoard.setGameOver();            
        }
    
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setGameBoard(GameBoard gBoard) {
        this.gameBoard = gBoard;
    }
}
