public class GameLoop implements Runnable {
    private boolean running;
    private Thread animator;
    private long lastUpdateTime;
    private static final int UPDATE_COUNT = 30; // Schritte innterhalb eines vollen Intervalls
    private static final int UPDATE_PERIOD_MS = 200; 
    private static final int UPDATE_INTERVAL_MS = UPDATE_PERIOD_MS / UPDATE_COUNT; // Schrittdauer in Millisekunden
    private static Game game;
    public GameLoop(Game gameInstance) {
        this.game = gameInstance;
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
                game.update();
                
                lastUpdateTime = currentTime - (elapsedTime % UPDATE_INTERVAL_MS); // tatsächliche Schrittdauer - vorgabe Schrittdauer
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
