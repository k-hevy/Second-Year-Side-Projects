package objects.PowerUp_Effects;

import java.awt.Color;
import java.awt.Graphics;

import user_interface.GamePanel;

public class PowerUps_SpeedBoost extends PowerUps {

    public PowerUps_SpeedBoost (int x, int y, int tileSize, int boardWidth, int boardHeight) {
        super(x, y, tileSize, boardWidth, boardHeight);
    }

    @Override
    public void applyPowerUp(GamePanel gamePanel) {
        gamePanel.setBoostActive(true);
        gamePanel.setPopowerUpEndTime(System.currentTimeMillis() + 8000);
        gamePanel.getGameLoopTimer().setDelay(80);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }
    

}
