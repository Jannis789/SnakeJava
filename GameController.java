// GameController.java
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private String direction = "";
    private GameBoard gameBoard;

    @Override
    public void keyPressed(KeyEvent e) {
        String injectedDirection = gameBoard.getDirection();
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (injectedDirection != "down") {
                    direction = "up";
                }
                break;
            case KeyEvent.VK_A:
                if (injectedDirection != "right") {
                    direction = "left";
                }
                break;
            case KeyEvent.VK_S:
                if (injectedDirection != "up") {
                    direction = "down";
                }
                break;
            case KeyEvent.VK_D:
                if (injectedDirection != "left") {
                    direction = "right";
                }
                break;
            default:
                break;
        }
    }

    public String getDirection() {
        if (gameBoard.getDirection() == "GameOver") {
            direction = "";
            return "restart";
        }
        return direction;
    }

    public void clearDirection() {
        direction = "";
    }

    public void setGameBoard(GameBoard pGameBoard) {
        this.gameBoard = pGameBoard;
    }
}