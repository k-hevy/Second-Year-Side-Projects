package objects.PowerUp_Effects;

import java.awt.Color;
import java.awt.Graphics;

import managers.PowerUpManager;
import managers.ScoreManager;
import user_interface.GamePanel;

public class PowerUps_DoublePoints extends PowerUps {

    public PowerUps_DoublePoints (PowerUpManager powerUpManager, int x, int y, int tileSize, int boardWidth, int boardHeight, ScoreManager scoreManager) {
        super(powerUpManager, x, y, tileSize, boardWidth, boardHeight, scoreManager);
    }

    @Override
    public void applyPowerUp(GamePanel gamePanel) {
        powerUpManager.setPowerUpEndTime(System.currentTimeMillis() + 10000);
        scoreManager.setMultiplier(2);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }

    @Override
    public void update() {
        
    }

    @Override
    public int getPowerUpType() {
        return 2;
    }

}
