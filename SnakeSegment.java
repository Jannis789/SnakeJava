import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class SnakeSegment {
    private int x;
    private int y;
    private int width;
    private int height;

    public SnakeSegment(int startX, int startY, int sizeWidth, int sizeHeight) {
        this.y = startY;
        this.x = startX;
        this.width = sizeWidth;
        this.height = sizeHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
