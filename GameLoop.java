// GameLoop.java
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
public class GameLoop implements Runnable {
    private static final int UPDATE_COUNT = 30; // Schritte innterhalb eines vollen Intervalls
    private static final int UPDATE_PERIOD_MS = 200; // Gesamt dauer eines vollen Intervall
    private static final int UPDATE_INTERVAL_MS = UPDATE_PERIOD_MS / UPDATE_COUNT; // Schrittdauer in Millisekunden
    private Thread animator;
    private volatile boolean running = false;
    private final GameBoard gameBoard;
    private GameController gameController;
    private long lastUpdateTime;
    private String direction = "";
    private int updateCounter = 0;
    public GameLoop(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.gameController = new GameController();
        this.gameController.setGameBoard(gameBoard);
        this.gameBoard.addKeyListener(gameController);
        this.gameBoard.setFocusable(true);
        this.gameBoard.requestFocusInWindow();
    }

    public void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        lastUpdateTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;

            if (elapsedTime >= UPDATE_INTERVAL_MS) {
                updateCounter++;
                gameBoard.gameUpdate();
                
                lastUpdateTime = currentTime - (elapsedTime % UPDATE_INTERVAL_MS); // tatsächliche Schrittdauer - vorgabe Schrittdauer

                if (updateCounter == UPDATE_COUNT) { // >=
                    gameBoard.setDirection(gameController.getDirection());
                    updateCounter = 0;
                }
            }

            // Kurze Pause, um CPU-Auslastung zu reduzieren
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.exit(0);
    }

    
}