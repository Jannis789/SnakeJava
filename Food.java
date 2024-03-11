import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Food {
    public static final int foodSize = 30;
    private int x;
    private int y;

    public Food(int startX, int startY) {
        this.y = startY;
        this.x = startX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getSize() {
        return foodSize;
    }
}
