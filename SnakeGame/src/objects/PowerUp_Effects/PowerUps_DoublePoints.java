package objects.PowerUp_Effects;

import java.awt.Color;
import java.awt.Graphics;

import managers.PowerUpManager;
import user_interface.GamePanel;

public class PowerUps_DoublePoints extends PowerUps {

    public PowerUps_DoublePoints (PowerUpManager powerUpManager, int x, int y, int tileSize, int boardWidth, int boardHeight) {
        super(powerUpManager, x, y, tileSize, boardWidth, boardHeight);
    }

    @Override
    public void applyPowerUp(GamePanel gamePanel) {
        powerUpManager.setPowerUpEndTime(System.currentTimeMillis() + 10000);
        gamePanel.activateDoublePoints();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }

}
