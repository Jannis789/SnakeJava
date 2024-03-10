// GameBoard.java
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JPanel {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private BufferedImage dbImage;
    private Graphics dbg;
    private SnakeHead snakeHead;
    private String direction = "";
    private int previousX;
    private int previousY;
    private String previousDirection = "";
    private ArrayList<SnakeSegment> snakeSegmentList = new ArrayList<SnakeSegment>();
    private int SNAKE_LENGTH = 30;
    private boolean GameOver = false;

    public GameBoard() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        setVisible(true);
        spawnSnake();
    }

    public void spawnSnake() {
        Random random = new Random();
        int spawnX = random.nextInt(30) * 30;
        int spawnY = random.nextInt(30) * 30;
        snakeHead = new SnakeHead(spawnX, spawnY);
        snakeHead.setGameBoard(this);
    }

    public void gameUpdate() {
        if (!direction.isEmpty() && !GameOver) {
            previousX = snakeHead.getX();
            previousY = snakeHead.getY();

            if ("up".equals(direction)) {
                snakeHead.move(0, -1);
            } else if ("down".equals(direction)) {
                snakeHead.move(0, 1);
            } else if ("right".equals(direction)) {
                snakeHead.move(1, 0);
            } else if ("left".equals(direction)) {
                snakeHead.move(-1, 0);
            }

        }
        gameRender();
        paintScreen();
        repaint();

    }

    public void gameRender() {
        if (dbImage == null) {
            dbImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            if (dbImage == null) {
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }

        // Setzt die Farbe auf Weiß, um das vorherige Feld zu übermalen
        if (GameOver) {
            dbg.setColor(Color.WHITE);
            dbg.fillRect(0, 0, WIDTH, HEIGHT);
            // GameOver = false;
        }
        // Übermalt das vorherige Feld mit festen Werten für Breite und Höhe
        int sizeX = 0;
        int sizeY = 0;

        if (!previousDirection.isEmpty() && previousDirection != direction) {
            if (previousDirection == "right") { // right
                sizeX = 30;
                sizeY = 1;
            }
            if (previousDirection == "left") { //  left
                sizeX = 1;
                sizeY = 30;
                previousX += 30;
            }
            if (previousDirection == "down") { // down
                sizeX = 1;
                sizeY = 30;
            }
            if (previousDirection == "up") { // up
                sizeX = 30;
                sizeY = 1;
                previousY += 30;
            }
            if (previousDirection == "up" && direction == "right") {
                snakeSegmentList.add(new SnakeSegment(previousX, previousY - 30, 1, 30));
            }
            if (previousDirection == "left" && direction == "down") {
                snakeSegmentList.add(new SnakeSegment(previousX-30, previousY, 30, 1));
            }
            previousDirection = "";
            snakeSegmentList.add(new SnakeSegment(previousX, previousY, sizeX, sizeY));
        } else {
            if (direction == "right") { // right
                sizeX = 1;
                sizeY = 30;
            }
            if (direction == "left") { //  left
                sizeX = 1;
                sizeY = 30;
                previousX += 30;
            }
            if (direction == "down") { // down
                sizeX = 30;
                sizeY = 1;
            }
            if (direction == "up") { // up
                sizeX = 30;
                sizeY = 1;
                previousY += 30;
            }
            //dbg.setColor(Color.WHITE);
            // dbg.fillRect(previousX, previousY, sizeX, sizeY);
            snakeSegmentList.add(new SnakeSegment(previousX, previousY, sizeX, sizeY));
        }
        dbg.setColor(Color.WHITE);
        while (snakeSegmentList.size() > SNAKE_LENGTH) {
            SnakeSegment snakeSegment = snakeSegmentList.get(0);
            dbg.fillRect(snakeSegment.getX(), snakeSegment.getY(), snakeSegment.getWidth(), snakeSegment.getHeight());
            snakeSegmentList.remove(0); 
        }
        dbg.setColor(Color.BLUE);
        for (int i = 0; i < snakeSegmentList.size(); i++) {
            SnakeSegment snakeSegment = snakeSegmentList.get(i);
            dbg.fillRect(snakeSegment.getX(), snakeSegment.getY(), snakeSegment.getWidth(), snakeSegment.getHeight());
        }
        // Zeichnet den Schlangenkopf an der neuen Position
        dbg.setColor(Color.BLUE);
        dbg.fillRect(snakeHead.getX(), snakeHead.getY(), SnakeHead.BOX_SIZE, SnakeHead.BOX_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Stellt sicher, dass das Panel korrekt gerendert wird
        if (dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    public void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    }

    public void setDirection(String dir) {
        if (dir == "restart") {
            GameOver = false;
        }
        if (GameOver) {
            return;
        } else {
            GameOver = false;
        }
        
        previousDirection = direction;
        direction = dir;
        
    }

    public String getDirection() {
        if (GameOver) {
            return "GameOver";
        }
        return direction;
    }
    
    public void setGameOver() {
        GameOver = true;
        direction = "";
    }
}