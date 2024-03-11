import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Color;

class GameBoard extends JPanel {
    private Game game;

    public GameBoard() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        setVisible(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (game == null) {
            return;
        }
        super.paintComponent(g); // Stellt sicher, dass das Panel korrekt gerendert wird.
        BufferedImage dbImage = game.getDbImage();
        if (dbImage != null) {
            g.drawImage(dbImage, 0, 0, this);
        }
        Toolkit.getDefaultToolkit().sync(); // Verbessert die Animation auf einigen Systemen.
    }
    
    public void setGame(Game gameInstance) {
        this.game = gameInstance;
    }
}