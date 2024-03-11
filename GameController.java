// GameController.java
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private String direction = "";
    private String injectedDirection = "";

    @Override
    public void keyPressed(KeyEvent e) {        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                direction = "up";
                break;
            case KeyEvent.VK_A:
                direction = "left";
                break;
            case KeyEvent.VK_S:
                direction = "down";
                break;
            case KeyEvent.VK_D:
                direction = "right";
                break;
            default:
                break;
        }
    }

    public void setPreviousDirection(String dir) {
        if (dir == injectedDirection) {
            return;
        }
        injectedDirection = dir;
    }

    public String getDirection() {
        if (!injectedDirection.isEmpty()) {
            String oppositeDirection = getOppositeDirection(direction);
            if (injectedDirection.equals(oppositeDirection)) {
                return injectedDirection;
            }
        }
        return direction;
    }

    private String getOppositeDirection(String dir) {
        switch (dir) {
            case "up":
                return "down";
            case "down":
                return "up";
            case "left":
                return "right";
            case "right":
                return "left";
            default:
                return "";
        }
    }
    
    
    public void clearDirection() {
        injectedDirection = "";
        direction = "";
    }
}