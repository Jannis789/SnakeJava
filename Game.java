import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import java.util.ArrayList;

public class Game {
    private int updateCount = 0;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private BufferedImage dbImage;
    private GameBoard gameBoard;
    private GameLoop gameLoop;
    private SnakeHead snakeHead;
    private String currentDirection = "";
    private int previousX;
    private int previousY;
    private ArrayList<SnakeSegment> snakeSegments = new ArrayList<>();
    private int snakeLength = 30;
    private GameController gameController;
    private String previousDirection = "";
    private Food food;
    private int score = 0;
    public Game(GameBoard gameBoardInstance) {
        this.gameBoard = gameBoardInstance;
        this.gameLoop = new GameLoop(this);
        this.gameLoop.startGame();
        this.gameController = new GameController();
        this.gameBoard.addKeyListener(gameController);
        gameBoard.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void update() {
        updateCount++;        

        if (snakeHead != null) {
            if (updateCount % 30 == 0) {
                gameController.setPreviousDirection(currentDirection);
                previousDirection = currentDirection;
                currentDirection = gameController.getDirection();
                switch (previousDirection) {
                    case "left":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX() + snakeHead.getSize(), snakeHead.getY(), 1, 30));
                        break;
                    case "up":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY() + snakeHead.getSize(), 30, 1));
                        break;
                }
                switch (currentDirection) {
                    case "right":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY(), 1, 30));
                        snakeHead.move(1, 0);
                        break;
                    case "left":
                        snakeHead.move(-1, 0);
                        break;
                    case "down":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY(), 30, 1));
                        snakeHead.move(0, 1);
                        break;
                    case "up":
                        snakeHead.move(0, -1);
                        break;
                }
            } else {
                switch (currentDirection) {
                    case "right":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY(), 1, 30));
                        snakeHead.move(1, 0);
                        break;
                    case "left":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX() + snakeHead.getSize(), snakeHead.getY(), 1, 30));
                        snakeHead.move(-1, 0);
                        break;
                    case "down":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY(), 30, 1));
                        snakeHead.move(0, 1);
                        break;
                    case "up":
                        snakeSegments.add(new SnakeSegment(snakeHead.getX(), snakeHead.getY() + snakeHead.getSize(), 30, 1));
                        snakeHead.move(0, -1);
                        break;
                }
            }
        }

        if (snakeLength >= snakeSegments.size() && snakeSegments.size() != score && score < snakeSegments.size()) {
            score = snakeSegments.size();
            System.out.println("Length: " + (snakeSegments.size()+30));
        }
        revalidateSnakeLength();
        gameRender();
        gameBoard.repaint();
    }
    
    private void gameRender() {
        if (dbImage == null) {
            dbImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            if (dbImage == null) {
                return;
            }
        }
        Graphics dbg = dbImage.getGraphics();
        if (snakeHead == null) {
            dbg.setColor(Color.BLACK);
            dbg.fillRect(0, 0, WIDTH, HEIGHT);
        }
        removeSnakeSegment(dbg);
        createSnakeHead(dbg);
        createFood(dbg);
        if (isSnakeHeadTouchingSnakeSegment() || snakeHead.hasReachedBounds()) {
            restart(dbg);
        }
        dbg.dispose(); // Wichtig, um Ressourcen freizugeben und Lecks zu vermeiden.
    }

    public void createSnakeHead(Graphics dbg) {
        dbg.setColor(Color.BLUE);
        if (snakeHead == null) {
            Random random = new Random();
            int spawnX = random.nextInt(30) * 30;
            int spawnY = random.nextInt(30) * 30;
            snakeHead = new SnakeHead(spawnX, spawnY);
        }
        dbg.fillRect(snakeHead.getX(), snakeHead.getY(),snakeHead.getSize(), snakeHead.getSize());        
    }

    public void removeSnakeSegment(Graphics dbg) {
        dbg.setColor(Color.BLACK);
        while (snakeLength <= snakeSegments.size() && !snakeSegments.isEmpty()) {
            SnakeSegment tailSegment = snakeSegments.remove(0);
            dbg.fillRect(tailSegment.getX(), tailSegment.getY(), tailSegment.getWidth(), tailSegment.getHeight());
        }
    }

    public void createFood(Graphics dbg) {
        if (food == null) {
            boolean validPosition = false;
            int spawnX = 0; 
            int spawnY = 0;
            Random random = new Random();

            while (!validPosition) {
                spawnX = random.nextInt(30) * 30;
                spawnY = random.nextInt(30) * 30;

                // Überprüfen, ob die zufällige Position gültig ist
                if (!isPositionOccupied(spawnX, spawnY, 30, 30)) {
                    validPosition = true;
                }
            }
            this.food = new Food(spawnX, spawnY);
            dbg.setColor(Color.YELLOW);
            dbg.fillRect(food.getX(), food.getY(), 30, 30); // Zeichnen der food mit Größe 30x30
        }
    }

    private void revalidateSnakeLength() {
        if (isSnakeHeadTouchingFood()) {
            snakeLength += 30;
            food = null;
        }
    }

    private boolean isSnakeHeadTouchingFood() {
        if (snakeHead != null && food != null) {
            int snakeHeadX = snakeHead.getX();
            int snakeHeadY = snakeHead.getY();
            int snakeHeadSize = snakeHead.getSize();
            int foodX = food.getX();
            int foodY = food.getY();
            int foodSize = food.getSize();

            return (snakeHeadX + snakeHeadSize > foodX &&
                snakeHeadX < foodX + foodSize &&
                snakeHeadY + snakeHeadSize > foodY &&
                snakeHeadY < foodY + foodSize);
        }
        return false;
    }

    private boolean isSnakeHeadTouchingSnakeSegment() {
        if (snakeHead != null && !snakeSegments.isEmpty()) {
            int snakeHeadX = snakeHead.getX();
            int snakeHeadY = snakeHead.getY();
            int snakeHeadSize = snakeHead.getSize();

            for (SnakeSegment segment : snakeSegments) {
                int segmentX = segment.getX();
                int segmentY = segment.getY();
                int segmentWidth = segment.getWidth();
                int segmentHeight = segment.getHeight();

                if (snakeHeadX + snakeHeadSize > segmentX && snakeHeadX < segmentX + segmentWidth &&
                snakeHeadY + snakeHeadSize > segmentY && snakeHeadY < segmentY + segmentHeight) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPositionOccupied(int x, int y, int width, int height) {
        int snakeHeadSize = snakeHead.getSize();
        int snakeHeadX = snakeHead.getX();
        int snakeHeadY = snakeHead.getY();

        // Überprüfen, ob die Position mit der SnakeHead übereinstimmt
        if (snakeHead != null &&
        x + width >= snakeHeadX && x <= snakeHeadX + snakeHeadSize &&
        y + height >= snakeHeadY && y <= snakeHeadY + snakeHeadSize) {
            return true;
        }

        // Überprüfen, ob die Position mit einem SnakeSegment übereinstimmt
        for (SnakeSegment segment : snakeSegments) {
            int segmentX = segment.getX();
            int segmentY = segment.getY();
            int segmentWidth = segment.getWidth();
            int segmentHeight = segment.getHeight();

            if (x + width >= segmentX && x <= segmentX + segmentWidth &&
            y + height >= segmentY && y <= segmentY + segmentHeight) {
                return true;
            }
        }

        return false;
    }

    public BufferedImage getDbImage() {
        return dbImage;
    }

    public void restart(Graphics dbg) {
        // Zurücksetzen der Spielvariablen
        snakeHead = null;
        snakeSegments.clear();
        food = null;
        snakeLength = 30;
        currentDirection = "";
        previousDirection = "";
        gameController.clearDirection();
        score = 0;

        snakeHead = null;
        food = null;
        gameRender();
        gameBoard.repaint();
    }
}
