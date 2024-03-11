import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SnakeHead {
    public static final int headSize = 30;
    private int x;
    private int y;
    private boolean reachedBounds = false;

    public SnakeHead(int startX, int startY) {
        this.y = startY;
        this.x = startX;
    }

    public void move( int toX, int toY) {
        if (x >= 0  && x <= 900 && y >= 0 && y <= 900)  {
            x += toX;
            y += toY;
        } else {
            reachedBounds= true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getSize() {
        return headSize;
    }
    
    public boolean hasReachedBounds() {
        if (reachedBounds) {
            reachedBounds = false;
            return true;
        } 
        return false;
    }
}
